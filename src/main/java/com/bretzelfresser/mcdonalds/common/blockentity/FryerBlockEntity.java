package com.bretzelfresser.mcdonalds.common.blockentity;

import com.bretzelfresser.mcdonalds.common.recipe.FryerRecipe;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.bretzelfresser.mcdonalds.core.config.McDonaldsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FryerBlockEntity extends BlockEntity {


    private int oilLevel = 0, itemsFried = 0, counter = 0, maxCounter = 0;
    private ItemStackHandler inv = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            FryerBlockEntity.this.setChanged();
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 20;
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
        }
    }

    protected void reset() {
        this.counter = 0;
        this.maxCounter = 0;
    }

    protected boolean canProcess(FryerRecipe recipe) {
        if (hasEnoughOil()) {
            return inv.getStackInSlot(1).getItem() == recipe.getResultItem().getItem() && inv.getStackInSlot(1).getCount() + recipe.getResultItem().getCount() <= recipe.getResultItem().getMaxStackSize();
        }
        return false;
    }

    protected void startProcessing(FryerRecipe recipe) {
        this.maxCounter = recipe.getFryTime();
    }

    protected void process(FryerRecipe recipe) {
        this.counter++;
    }

    protected void finishProcessing(FryerRecipe recipe) {
        inv.getStackInSlot(0).shrink(1);
        if (inv.getStackInSlot(1).isEmpty()){
            inv.setStackInSlot(1, recipe.getResultItem().copy());
        }else {
            inv.getStackInSlot(1).grow(recipe.getResultItem().getCount());
        }
        increaseItemsFried();
        reset();
    }

    protected void increaseItemsFried(){
        this.itemsFried++;
        if (this.itemsFried >= McDonaldsConfig.ITEMS_FRIED.get()){
            this.oilLevel = 0;
            this.itemsFried = 0;
        }
    }

    public boolean hasEnoughOil() {
        return this.oilLevel == McDonaldsConfig.MAX_OIL_LEVEL.get();
    }

    public void addOilLevel() {
        oilLevel = Math.min(McDonaldsConfig.MAX_OIL_LEVEL.get(), oilLevel + 1);
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
        tag.putInt("oilLevel", this.oilLevel);
        tag.put("items", this.inv.serializeNBT());
        tag.putInt("counter", this.counter);
        tag.putInt("maxCounter", this.maxCounter);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.itemsFried = tag.getInt("itemsFried");
        this.oilLevel = tag.getInt("oilLevel");
        this.inv.deserializeNBT(tag.getCompound("items"));
        this.counter = tag.getInt("counter");
        this.maxCounter = tag.getInt("maxCounter");
    }

    protected void blockUpdate(){
        this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public ItemStackHandler getInv() {
        return inv;
    }
}
