package com.bretzelfresser.mcdonalds.common.recipe;

import com.bretzelfresser.mcdonalds.common.blockentity.TableBlockEntity;
import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TableRecipe implements Recipe<TableBlockEntity> {

    public static final Serializer SERIALIZER = new Serializer();

    private final ItemStack result;
    private final ImmutableList<Ingredient> ingredients;
    private final ResourceLocation id;

    public TableRecipe(ItemStack result, ImmutableList<Ingredient> ingredients, ResourceLocation id) {
        this.result = result;
        this.ingredients = ingredients;
        this.id = id;
    }

    @Override
    public boolean matches(TableBlockEntity inv, Level level) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!inv.getItem(i).isEmpty() && !ingredients.get(i).test(inv.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(TableBlockEntity p_44001_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result.copy();
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
        return RecipeInit.TABLE_RECIPE;
    }

    private static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TableRecipe> {

        @Override
        public TableRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray ings = json.getAsJsonArray("ingredients");
            List<Ingredient> ingredients = Lists.newArrayList();
            for (JsonElement el : ings) {
                ingredients.add(Ingredient.fromJson(el));
            }
            ItemStack result = CraftingHelper.getItemStack(ChoppingRecipe.getJsonElement(json, "result").getAsJsonObject(), true);
            return new TableRecipe(result, ImmutableList.copyOf(ingredients), id);
        }

        @Nullable
        @Override
        public TableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int size = buffer.readInt();
            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ingredients.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack result = buffer.readItem();
            return new TableRecipe(result, ImmutableList.copyOf(ingredients), id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TableRecipe recipe) {
            buffer.writeInt(recipe.ingredients.size());
            for (Ingredient ing : recipe.ingredients) {
                ing.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
        }
    }
}
