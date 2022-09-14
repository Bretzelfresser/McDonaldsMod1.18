package com.bretzelfresser.mcdonalds.common.blockentity;

import com.bretzelfresser.mcdonalds.common.block.Fryer;
import com.bretzelfresser.mcdonalds.common.recipe.FryerRecipe;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.bretzelfresser.mcdonalds.core.SoundInit;
import com.bretzelfresser.mcdonalds.core.config.McDonaldsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class FryerBlockEntity extends BlockEntity {


    private int itemsFried = 0, counter = 0, maxCounter = 0, prevCounter = -43;
    private ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            FryerBlockEntity.this.setChanged();
            blockUpdate();
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }
    };

    public FryerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityInit.FRYER.get(), p_155229_, p_155230_);
    }

    public void tick() {
        if (!level.isClientSide()) {
            FryerRecipe recipe = getRecipe();
            if (recipe != null && canProcess(recipe)) {
                if (counter == 0) {
                    startProcessing(recipe);
                    counter++;
                } else if (counter <= maxCounter) {
                    process(recipe);
                } else {
                    finishProcessing(recipe);
                }
                blockUpdate();
            } else
                reset();
        }else{
            if (counter > 0 && counter < maxCounter){
                BlockPos pos = getBlockPos();
                level.addParticle(ParticleTypes.CLOUD, pos.getX() + level.random.nextDouble()*0.6, pos.getY() + 1, pos.getZ() + level.random.nextDouble()*0.6, 0, 0.02, 0);
            }
        }
    }

    protected void reset() {
        this.counter = 0;
        this.maxCounter = 0;
        prevCounter = -43;
    }

    protected boolean canProcess(FryerRecipe recipe) {
        if (hasEnoughOil() && getBlockState().getValue(Fryer.BASKET)) {
            return true;
        }
        return false;
    }

    protected void startProcessing(FryerRecipe recipe) {
        this.maxCounter = recipe.getFryTime();
    }

    protected void process(FryerRecipe recipe) {
        this.counter++;
        if (counter - prevCounter >= 42) {
            level.playSound(null, getBlockPos(), SoundInit.FRENCH_FRIES.get(), SoundSource.BLOCKS, 1f, 1f);
            prevCounter = counter;
        }
    }

    protected void finishProcessing(FryerRecipe recipe) {
        ItemStack result = recipe.getResultItem().copy();
        result.setCount(inv.getStackInSlot(0).getCount());
        inv.setStackInSlot(0, result);
        increaseItemsFried();
        reset();
    }

    protected void increaseItemsFried(){
        this.itemsFried++;
        if (this.itemsFried >= McDonaldsConfig.ITEMS_FRIED.get()){
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(Fryer.OIL, 0), 3);
            this.itemsFried = 0;
        }
    }

    public boolean hasEnoughOil() {
        return getBlockState().getValue(Fryer.OIL) >= Fryer.OIL.getPossibleValues().stream().max(Comparator.comparingInt(i -> i)).orElse(Integer.MAX_VALUE);
    }

    @Nullable
    protected FryerRecipe getRecipe() {
        return level.getRecipeManager().getRecipeFor(RecipeInit.FRYER_RECIPE, new SimpleContainer(this.inv.getStackInSlot(0)), level).orElse(null);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("itemsFried", this.itemsFried);
        tag.put("items", this.inv.serializeNBT());
        tag.putInt("counter", this.counter);
        tag.putInt("maxCounter", this.maxCounter);
        tag.putInt("prevCounter", this.prevCounter);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.itemsFried = tag.getInt("itemsFried");
        this.inv.deserializeNBT(tag.getCompound("items"));
        this.counter = tag.getInt("counter");
        this.maxCounter = tag.getInt("maxCounter");
        this.prevCounter = tag.getInt("prevCounter");
    }

    protected void blockUpdate(){
        this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public ItemStackHandler getInv() {
        return inv;
    }
}
