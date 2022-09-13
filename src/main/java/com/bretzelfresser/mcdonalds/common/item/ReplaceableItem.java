package com.bretzelfresser.mcdonalds.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ReplaceableItem extends Item {

    private final Supplier<Item> item;

    public ReplaceableItem(Properties properties, Supplier<Item> item) {
        super(properties);
        this.item = item;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(stack, level, livingEntity);
        return livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild ? itemstack : new ItemStack(item.get());
    }
}
