package com.bretzelfresser.mcdonalds.common.blockentity;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.recipe.BurgerMachineRecipe;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.bretzelfresser.mcdonalds.core.SoundInit;
import com.bretzelfresser.mcdonalds.core.config.McDonaldsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
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
    private int counter = 0, maxCounter = 0, burnCounter = 0, maxBurnCounter = 0, prevCounter = -62;
    private float degrees = MAX_DEGREES;

    public BurgerMachineBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.BURGER_MACHINE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BurgerMachineBlockEntity entity) {
        if (level.isClientSide) {
            if (entity.burnCounter > 0) {
                for (int i = 0; i < 2; i++) {
                    level.addParticle(ParticleTypes.CLOUD, pos.getX() + level.random.nextDouble(), pos.getY() + 1, pos.getZ() + level.random.nextDouble(), 0, 0.02, 0);
                }
            } else if (entity.counter > 0 && level.random.nextDouble() < 0.5) {
                level.addParticle(ParticleTypes.CLOUD, pos.getX() + level.random.nextDouble(), pos.getY() + 1, pos.getZ() + level.random.nextDouble(), 0, 0.02, 0);
            }
        } else {
            if (entity.closing) {
                if (entity.degrees > 0) {
                    entity.degrees -= 1f;
                } else {
                    entity.degrees = 0;
                    entity.closing = false;
                    entity.closed = true;
                }
                entity.blockUpdate();
            } else if (entity.opening) {
                if (entity.degrees < MAX_DEGREES) {
                    entity.degrees += 1f;
                } else {
                    entity.degrees = MAX_DEGREES;
                    entity.opening = false;
                    entity.closed = false;
                }
                entity.blockUpdate();
            }
            BurgerMachineRecipe recipe = entity.getRecipe();
            if (recipe != null) {
                if (entity.canStart(recipe)) {
                    if (entity.maxBurnCounter > 0 && !entity.isOpening()) {
                        if (entity.inv.getItem(0).isEmpty())
                            entity.resetBurn();
                        else {
                            entity.burnCounter++;
                            if (entity.maxBurnCounter < entity.burnCounter) {
                                entity.finishBurning(recipe);
                            }else if (entity.burnCounter - entity.prevCounter >= 60){
                                level.playSound(null, entity.getBlockPos(), SoundInit.BURGER.get(), SoundSource.BLOCKS, 0.7f, 1f);
                                entity.prevCounter = entity.burnCounter;
                            }
                            entity.blockUpdate();
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
                }
            } else {
                entity.reset();
                entity.resetBurn();
                entity.blockUpdate();
            }
        }
    }

    protected boolean canStart(BurgerMachineRecipe recipe) {
        if (!isOpening() && !isClosing()) {
            return !recipe.isNeedsClosing() || this.isClosed();
        }
        return false;
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
        if (counter - prevCounter >= 60){
            level.playSound(null, getBlockPos(), SoundInit.BURGER.get(), SoundSource.BLOCKS, 0.7f, 1f);
        }
    }

    private void finishWork(BurgerMachineRecipe recipe) {
        this.inv.setItem(0, recipe.getResultItem());
        int maxBurnCounter = McDonaldsConfig.TIME_TO_BURN.get();
        reset();
        this.level.playSound(null, getBlockPos(), SoundInit.BURGER_FINISHED.get(), SoundSource.BLOCKS, 1f, 1f);
        if (maxBurnCounter > 0) {
            this.maxBurnCounter = maxBurnCounter;
        }
        burnCounter = 0;
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void finishBurning(BurgerMachineRecipe recipe) {
        this.inv.setItem(0, recipe.getBurnt());
        resetBurn();
        this.level.playSound(null, getBlockPos(), SoundInit.BURGER_FINISHED.get(), SoundSource.BLOCKS, 1f, 1f);
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    private void reset() {
        counter = 0;
        this.maxCounter = 0;
        if (isClosed())
            this.opening = true;
        prevCounter = -62;
        blockUpdate();
    }

    private void resetBurn() {
        this.burnCounter = 0;
        this.maxBurnCounter = 0;
        prevCounter = -62;
        blockUpdate();
    }

    public void setClosing() {
        if (getRecipe() != null && getRecipe().isNeedsClosing())
            this.closing = true;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

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
        this.prevCounter = nbt.getInt("prevCounter");
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
        compound.putInt("prevCounter", this.prevCounter);
    }

    @javax.annotation.Nullable
    private BurgerMachineRecipe getRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.BURGER_RECIPE, this.inv, this.level).orElse(null);
    }

    public Container getInv() {
        return inv;
    }
}
