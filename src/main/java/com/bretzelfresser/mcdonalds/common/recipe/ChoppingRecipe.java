package com.bretzelfresser.mcdonalds.common.recipe;

import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ChoppingRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();
    private final Ingredient toChop;
    private final ItemStack output;
    private final ResourceLocation id;

    public ChoppingRecipe(Ingredient toChop, ItemStack output, ResourceLocation id) {
        this.toChop = toChop;
        this.output = output;
        this.id = id;
    }
    @Override
    public boolean matches(Container container, Level level) {
        return this.toChop.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container p_44001_) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.CHOPPING_RECIPE;
    }

    public static JsonElement getJsonElement(JsonObject obj, String name) {
        return GsonHelper.isArrayNode(obj, name) ? GsonHelper.getAsJsonArray(obj, name)
                : GsonHelper.getAsJsonObject(obj, name);
    }

    private static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ChoppingRecipe>{

        @Override
        public ChoppingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(getJsonElement(json, "input"));
            ItemStack output =  CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            return new ChoppingRecipe(input, output, recipeId);
        }

        @Nullable
        @Override
        public ChoppingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient toChop = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            return new ChoppingRecipe(toChop, output, recipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ChoppingRecipe recipe) {
            recipe.toChop.toNetwork(buffer);
            buffer.writeItem(recipe.output);
        }
    }
}
