package com.bretzelfresser.mcdonalds.core;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements Tier {
    IRON_KNIFE(200, 1, 15, 2f,1f, () -> Ingredient.of(Items.IRON_INGOT));

    private final int uses, harvestLevel, enchantmentValue;
    private final float efficiency, attackDamageBonus;
    private final Supplier<Ingredient> repairMaterial;

    ModToolMaterial(int uses, int harvestLevel, int enchantmentValue, float efficiency, float attackDamageBonus, Supplier<Ingredient> repairMaterial) {
        this.uses = uses;
        this.harvestLevel = harvestLevel;
        this.enchantmentValue = enchantmentValue;
        this.efficiency = efficiency;
        this.attackDamageBonus = attackDamageBonus;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
