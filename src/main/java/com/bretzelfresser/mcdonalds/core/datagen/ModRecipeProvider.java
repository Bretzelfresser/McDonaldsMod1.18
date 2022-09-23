package com.bretzelfresser.mcdonalds.core.datagen;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.block.BurgerBox;
import com.bretzelfresser.mcdonalds.core.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
       makeBurgerBoxRecipe(consumer, BlockInit.CHEESEBURGER.get(), BlockInit.CHEESBURGER_BOX.get());
       makeBurgerBoxRecipe(consumer, BlockInit.BIG_MAC.get(), BlockInit.BIG_MAC_BOX.get());
       makeBurgerBoxRecipe(consumer,BlockInit.QUARTER_POUNDER.get(), BlockInit.QUARTER_POUNDER_BOX.get());
       makeBurgerBoxRecipe(consumer, BlockInit.ROYAL_DELUXE.get(), BlockInit.ROYAL_DELUXE_BOX.get());
    }

    private void makeBurgerBoxRecipe(Consumer<FinishedRecipe> consumer, Block burger, Block burgerBox){
        ShapelessRecipeBuilder.shapeless(burgerBox).requires(BlockInit.EMPTY_BURGER_BOX.get()).requires(burger).unlockedBy("hasItem", has(burger)).save(consumer);
    }
}
