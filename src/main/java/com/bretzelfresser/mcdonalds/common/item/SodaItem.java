package com.bretzelfresser.mcdonalds.common.item;

import com.bretzelfresser.mcdonalds.core.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class SodaItem extends Item {

    private static final int DRINK_DURATION = 40;

    public SodaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        if(itemStack.isEmpty()){
            return new ItemStack(ItemInit.EMPTY_SODA.get());
        }
        else {
            itemStack.hurtAndBreak(1, livingEntity, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            return itemStack;
        }
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return DRINK_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }
}
