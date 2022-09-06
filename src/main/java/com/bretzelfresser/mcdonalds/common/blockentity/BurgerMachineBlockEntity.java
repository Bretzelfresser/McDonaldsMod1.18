package com.bretzelfresser.mcdonalds.common.blockentity;

import com.bretzelfresser.mcdonalds.common.recipe.BurgerMachineRecipe;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.bretzelfresser.mcdonalds.core.config.McDonaldsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BurgerMachineBlockEntity extends BlockEntity {

    public static final int MAX_DEGREES = 60;

    private Container inv = new SimpleContainer(1);
    private boolean closed = false, closing = false, opening = false;
    private int counter = 0, maxCounter = 0, burnCounter = 0, maxBurnCounter = 0;
    private float degrees = MAX_DEGREES;

    protected final ContainerData data;

    public BurgerMachineBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.CHOPPING_BOARD.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BurgerMachineBlockEntity.this.counter;
                    case 1 -> BurgerMachineBlockEntity.this.maxCounter;
                    case 2 -> BurgerMachineBlockEntity.this.burnCounter;
                    case 3 -> BurgerMachineBlockEntity.this.maxBurnCounter;
                    case 4 -> BurgerMachineBlockEntity.MAX_DEGREES;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BurgerMachineBlockEntity.this.counter = value;
                    case 1 -> BurgerMachineBlockEntity.this.maxCounter = value;
                    case 2 -> BurgerMachineBlockEntity.this.burnCounter = value;
                    case 3 -> BurgerMachineBlockEntity.this.maxBurnCounter = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BurgerMachineBlockEntity entity) {
        if (!level.isClientSide) {
            if (entity.closing) {
                if (entity.degrees > 0) {
                    entity.degrees -= 1f;
                } else {
                    entity.degrees = 0;
                    entity.closing = false;
                    entity.closed = true;
                }
                entity.blockUpdate();
            }else if(entity.opening){
                if (entity.degrees < MAX_DEGREES) {
                    entity.degrees += 1f;
                } else {
                    entity.degrees = MAX_DEGREES;
                    entity.opening = false;
                    entity.closed = false;
                }
                entity.blockUpdate();
            }
            if (entity.closed) {
                BurgerMachineRecipe recipe = entity.getRecipe();
                if (recipe != null) {
                    if (entity.maxBurnCounter > 0) {
                        if (entity.inv.getItem(0).isEmpty())
                            entity.resetBurn();
                        else {
                            entity.burnCounter++;
                            if (entity.maxBurnCounter < entity.burnCounter) {
                                entity.finishBurning(recipe);
                            }
                        }
                    } else {
                        if (entity.counter <= 0) {
                            entity.startWorking(recipe);
                            entity.counter++;
                        } else {
                            entity.work(recipe);
                        }
                        if (entity.counter > entity.maxCounter) {
                            entity.finishWork(recipe);
                        }
                    }
                } else {
                    entity.reset();
                    entity.resetBurn();
                    entity.blockUpdate();
                }
            }
        }
    }

    private void blockUpdate() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public boolean isOpening() {
        return opening;
    }

    public float getDegrees() {
        return degrees;
    }

    private void startWorking(BurgerMachineRecipe recipe) {
        this.maxCounter = recipe.getBurnTime();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void work(BurgerMachineRecipe recipe) {
        this.counter++;
        for (int i = 0; i < 30; i++) {
            level.addParticle(ParticleTypes.SMOKE, getBlockPos().getX() + level.random.nextDouble() - 0.5, getBlockPos().getY() + 1, getBlockPos().getZ() + level.random.nextDouble() - 0.5, 0, 0.01, 0);
        }
    }

    private void finishWork(BurgerMachineRecipe recipe) {
        this.inv.setItem(0, recipe.getResultItem());
        int maxBurnCounter = McDonaldsConfig.MACHINE_CONFIG.get();
        reset();
        if (maxBurnCounter > 0) {
            this.maxBurnCounter = maxBurnCounter;
        }
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void finishBurning(BurgerMachineRecipe recipe) {
        this.inv.setItem(0, recipe.getBurnt());
        resetBurn();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void reset() {
        counter = 0;
        this.maxCounter = 0;
        this.opening = true;
    }

    private void resetBurn() {
        this.burnCounter = 0;
        this.maxBurnCounter = 0;
    }

    public void setClosing() {
        this.closing = true;
    }

    /*@Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return new ClientGamePacketListener(this.getBlockPos(), 0, write(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.read(null, pkt.CompoundTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.write(new CompoundTag());
    }*/

    public boolean isClosing() {
        return closing;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag inventory = nbt.getCompound("inventory");
        for (int i = 0; inventory.contains("stack" + i); i++) {
            this.inv.setItem(i, ItemStack.of(inventory.getCompound("stack" + i)));
        }
        this.closed = nbt.getBoolean("closed");
        this.counter = nbt.getInt("counter");
        this.maxCounter = nbt.getInt("maxCounter");
        this.burnCounter = nbt.getInt("burnCounter");
        this.maxBurnCounter = nbt.getInt("maxBurnCounter");
        this.closing = nbt.getBoolean("closing");
        this.degrees = nbt.getFloat("degrees");
        this.opening = nbt.getBoolean("opening");
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        CompoundTag inventory = new CompoundTag();
        for (int i = 0; i < this.inv.getContainerSize(); i++) {
            CompoundTag stack = this.inv.getItem(i).save(new CompoundTag());
            inventory.put("stack" + i, stack);
        }
        compound.put("inventory", inventory);
        compound.putInt("counter", this.counter);
        compound.putInt("maxCounter", this.maxCounter);
        compound.putInt("burnCounter", this.burnCounter);
        compound.putInt("maxBurnCounter", this.maxBurnCounter);
        compound.putBoolean("closed", this.closed);
        compound.putBoolean("closing", this.closing);
        compound.putFloat("degrees", this.degrees);
        compound.putBoolean("opening", this.opening);
    }

    @javax.annotation.Nullable
    private BurgerMachineRecipe getRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.BURGER_RECIPE, this.inv, this.level).orElse(null);
    }

    public Container getInv() {
        return inv;
    }
}
