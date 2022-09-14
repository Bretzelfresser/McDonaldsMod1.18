package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.block.BurgerBox;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        McDonalds.LOGGER.info(BurgerBox.setBurger(BlockInit.BURGER_BOX.get().asItem().getDefaultInstance(), BlockInit.BIG_MAC.get().asItem().getDefaultInstance()).getTag().toString());
        ShapelessRecipeBuilder.shapeless(BlockInit.BURGER_BOX.get()).requires(BlockInit.BURGER_BOX.get()).requires(BlockInit.BIG_MAC.get()).unlockedBy("hasItem", has(BlockInit.BURGER_BOX.get())).save(consumer);
    }
}
