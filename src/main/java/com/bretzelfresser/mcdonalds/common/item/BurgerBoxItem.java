package com.bretzelfresser.mcdonalds.common.item;

import com.bretzelfresser.mcdonalds.common.block.Burger;
import com.bretzelfresser.mcdonalds.common.block.BurgerBox;
import com.bretzelfresser.mcdonalds.core.util.ModCreativeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BurgerBoxItem extends BlockItem {
    public BurgerBoxItem(Block b) {
        super(b, new Item.Properties().tab(ModCreativeTab.MC_DONALDS_TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_40573_, List<Component> tooltip, TooltipFlag flags) {
        if (stack.hasTag() && !BurgerBox.getBurger(stack).isEmpty()){
            tooltip.add(new TranslatableComponent("tooltip.mcdonalds.burgerbox.burger").append(BurgerBox.getBurger(stack).getDisplayName()));
        }
        super.appendHoverText(stack, p_40573_, tooltip, flags);
    }
}
