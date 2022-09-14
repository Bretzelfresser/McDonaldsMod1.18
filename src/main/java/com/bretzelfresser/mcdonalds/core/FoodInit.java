package com.bretzelfresser.mcdonalds.core;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodInit {

    public static final FoodProperties RAW_MEAT = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.2F).meat().build();
    public static final FoodProperties COOKED_MEAT = new FoodProperties.Builder().nutrition(4)
            .saturationMod(0.8F).meat().build();
    public static final FoodProperties BURNT_MEAT = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.0F).meat().alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0F).build();

    public static final FoodProperties BREAD_CROWN = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties BREAD_MIDDLE = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties BREAD_BASE = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties BREAD_SIMPLE = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();

    public static final FoodProperties AMERICAN_CHEESE = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties BIG_MAC_SAUCE = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.1F).fast().build();

    public static final FoodProperties PICKLES = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.3F).build();
    public static final FoodProperties ONION = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties ONION_RING = new FoodProperties.Builder().nutrition(3)
            .saturationMod(0.4F).build();
    public static final FoodProperties ICEBERG_LETTUCE = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties ICEBERG_LETTUCE_SLICE = new FoodProperties.Builder().nutrition(3)
            .saturationMod(0.4F).build();
    public static final FoodProperties TOMATO = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.3F).build();
    public static final FoodProperties TOMATO_SLICE = new FoodProperties.Builder().nutrition(3)
            .saturationMod(0.4F).build();

    public static final FoodProperties MAYONNAISE = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.1F).fast().build();
    public static final FoodProperties MUSTARD = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.1F).fast().build();
    public static final FoodProperties KETCHUP = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.1F).fast().build();

    public static final FoodProperties FRIES = new FoodProperties.Builder().nutrition(4)
            .saturationMod(0.5F).build();
    public static final FoodProperties BURNT_FRIES = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.0F).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0F).build();
    public static final FoodProperties FRIES_WITH_BOX = new FoodProperties.Builder().nutrition(4)
            .saturationMod(0.5F).build();
    public static final FoodProperties STRIPPED_POTATOES = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.2F).build();
    public static final FoodProperties SODA = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.1F).build();
    public static final FoodProperties BIG_MAC = new FoodProperties.Builder().nutrition(8)
            .saturationMod(0.7F).build();
    public static final FoodProperties QUARTER_POUNDER = new FoodProperties.Builder().nutrition(8)
            .saturationMod(0.7F).build();
    public static final FoodProperties CHEESE_BURGER = new FoodProperties.Builder().nutrition(6)
            .saturationMod(0.5F).build();
    public static final FoodProperties MC_ROYAL_DELUXE = new FoodProperties.Builder().nutrition(8)
            .saturationMod(0.7F).build();

}
