package com.bretzelfresser.mcdonalds.core;

import com.bretzelfresser.mcdonalds.McDonalds;
import com.bretzelfresser.mcdonalds.common.recipe.BurgerMachineRecipe;
import com.bretzelfresser.mcdonalds.common.recipe.ChoppingRecipe;
import com.bretzelfresser.mcdonalds.common.recipe.FryerRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;

public class RecipeInit {

    public static final RecipeType<ChoppingRecipe> CHOPPING_RECIPE = create("chopping_recipe");
    public static final RecipeType<BurgerMachineRecipe> BURGER_RECIPE = create("burger_recipe");
    public static final RecipeType<FryerRecipe> FRYER_RECIPE = create("fryer_recipe");

    public static final void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event){
        registerRecipe(event, ChoppingRecipe.SERIALIZER, CHOPPING_RECIPE);
        registerRecipe(event, BurgerMachineRecipe.SERIALIZER, BURGER_RECIPE);
        registerRecipe(event, FryerRecipe.SERIALIZER, FRYER_RECIPE);
    }

    private static void registerRecipe(RegistryEvent.Register<RecipeSerializer<?>> event, RecipeSerializer<?> serializer, RecipeType<?> type){
        Registry.register(Registry.RECIPE_TYPE, type.toString(), type);
        event.getRegistry().register(serializer.setRegistryName(new ResourceLocation(type.toString())));
    }

    private static <T extends Recipe<?>> RecipeType<T> create(final String key) {
        return new RecipeType<T>() {
            public String toString() {
                return McDonalds.modLoc(key).toString();
            }
        };
    }
}
