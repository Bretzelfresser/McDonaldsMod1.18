package com.bretzelfresser.mcdonalds.common.container;

import com.bretzelfresser.mcdonalds.core.ContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PaperBagContainer extends UtilContainer {
    protected final IItemHandler inventory;

    public PaperBagContainer(int id, Inventory playerInv, IItemHandler inventory) {
        super(ContainerInit.PAPER_BAG.get(), id, playerInv);
        this.inventory = inventory;
    }

    public PaperBagContainer(int id, Inventory playerInv, FriendlyByteBuf buffer){
        this(id, playerInv, new ItemStackHandler(buffer.readInt()));
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
