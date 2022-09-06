package com.bretzelfresser.mcdonalds.common.recipe;

import com.bretzelfresser.mcdonalds.core.RecipeInit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class BurgerMachineRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final ItemStack output, burnt;
    private final int burnTime;
    private final ResourceLocation id;

    public BurgerMachineRecipe(Ingredient input, ItemStack output, ItemStack burnt, int burnTime, ResourceLocation id) {
        this.input = input;
        this.output = output;
        this.burnt = burnt;
        this.burnTime = burnTime;
        this.id = id;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return input.test(inv.getItem(0)) || inv.getItem(0).getItem() == output.getItem();
    }

    @Override
    public ItemStack assemble(Container inv) {
        return this.output;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getBurnt() {
        return burnt.copy();
    }

    public int getBurnTime() {
        return burnTime;
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
        return RecipeInit.BURGER_RECIPE;
    }

    public static JsonElement getJsonElement(JsonObject obj, String name) {
        return GsonHelper.isArrayNode(obj, name) ? GsonHelper.getAsJsonArray(obj, name)
                : GsonHelper.getAsJsonObject(obj, name);
    }

    private static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BurgerMachineRecipe>{

        @Override
        public BurgerMachineRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(getJsonElement(json, "input"));
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            ItemStack burnt = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "burnt"), true);
            int burnTime = GsonHelper.getAsInt(json, "burnTime", 200);
            return new BurgerMachineRecipe(input, output, burnt, burnTime,recipeId);
        }

        @Nullable
        @Override
        public BurgerMachineRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            ItemStack burnt = buffer.readItem();
            int burnTime = buffer.readInt();
            return new BurgerMachineRecipe(input, output, burnt, burnTime, recipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BurgerMachineRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeItem(recipe.burnt);
            buffer.writeInt(recipe.burnTime);
        }
    }
}
