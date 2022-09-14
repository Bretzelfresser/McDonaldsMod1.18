package com.bretzelfresser.mcdonalds.common.container;

import com.bretzelfresser.mcdonalds.core.ContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class PaperBagContainer extends UtilContainer {
    protected final IItemHandler inventory;

    public PaperBagContainer(int id, Inventory playerInv, IItemHandler inventory) {
        super(ContainerInit.PAPER_BAG.get(), id, playerInv);
        this.inventory = inventory;
        init();
    }

    public PaperBagContainer(int id, Inventory playerInv, FriendlyByteBuf buffer){
        this(id, playerInv, new ItemStackHandler(buffer.readInt()));
    }

    protected void init(){
        addPlayerInventory(8, 93);

        addSlot(new SlotItemHandler(inventory, 0, 37, 27));
        addSlot(new SlotItemHandler(inventory, 1, 80, 54));
        addSlot(new SlotItemHandler(inventory, 2, 122, 27));
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            final ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 3) {
                if (!moveItemStackTo(itemstack1, 3, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(itemstack1, 0, 3, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }
}
