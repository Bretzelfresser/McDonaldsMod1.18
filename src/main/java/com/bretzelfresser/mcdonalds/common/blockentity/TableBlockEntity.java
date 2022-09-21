package com.bretzelfresser.mcdonalds.common.blockentity;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.recipe.TableRecipe;
import com.bretzelfresser.mcdonalds.core.BlockEntityInit;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TableBlockEntity extends BlockEntity implements Container {

    private final List<ItemStack> inv = new ArrayList<>();

    public TableBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityInit.TABLE.get(), p_155229_, p_155230_);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", saveItems());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loadItems(tag.getCompound("inventory"));
    }

    private void loadItems(CompoundTag tag) {
        setSize(tag.contains("Size", Tag.TAG_INT) ? tag.getInt("Size") : inv.size());
        ListTag tagList = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < tagList.size()) {
                inv.set(slot, ItemStack.of(itemTags));
            }
        }
    }

    private void setSize(int size) {
        this.inv.clear();
        for (int i = 0; i < size; i++) {
            this.inv.add(ItemStack.EMPTY);
        }
    }

    private CompoundTag saveItems() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < inv.size(); i++) {
            if (!inv.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                inv.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", inv.size());
        return nbt;
    }

    @Override
    public int getContainerSize() {
        return inv.size();
    }

    @Override
    public boolean isEmpty() {
        return inv.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return inv.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        if (count == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(index);

        ItemStack existing = this.inv.get(index);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(count, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            this.inv.set(index, ItemStack.EMPTY);
            blockUpdate();
            return existing;
        } else {
            this.inv.set(index, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            blockUpdate();
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack remainder = this.inv.set(index, ItemStack.EMPTY);
        blockUpdate();
        return remainder;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (stack.isEmpty())
            return;
        validateSlotIndex(index);

        ItemStack existing = this.inv.get(index);

        int limit = 1;

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return;

        boolean reachedLimit = stack.getCount() > limit;
        if (existing.isEmpty()) {
            this.inv.set(index, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
        } else {
            existing.grow(reachedLimit ? limit : stack.getCount());
        }
        blockUpdate();
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {
        this.inv.clear();
        blockUpdate();
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.inv.get(slot);

        int limit = 1;

        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.inv.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            blockUpdate();
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= inv.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + inv.size() + ")");
    }

    public List<ItemStack> getInv() {
        return inv;
    }

    public TableRecipe getRecipe(){
        return level.getRecipeManager().getRecipeFor(RecipeInit.TABLE_RECIPE, this, level).orElse(null);
    }

    public void blockUpdate(){
        level.sendBlockUpdated(getBlockPos(), getBlockState(),getBlockState(),3);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }
}
