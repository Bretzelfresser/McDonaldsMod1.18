package com.bretzelfresser.mcdonalds.core;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class ModCreativeTab extends CreativeModeTab {

    public static final CreativeModeTab MC_DONALDS_TAB = new ModCreativeTab("mc_donalds", () -> Blocks.BLAST_FURNACE);

    private final Supplier<ItemLike> icon;
    public ModCreativeTab(String label, Supplier<ItemLike> icon) {
        super(label);
        this.icon = icon;
    }

    public ModCreativeTab(int id, String name, Supplier<ItemLike> icon) {
        super(id, name);
        this.icon = icon;
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(this.icon.get());
    }
}
