package com.bretzelfresser.mcdonalds.common.recipe;

import com.bretzelfresser.mcdonalds.core.RecipeInit;
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

public class FryerRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int fryTime;

    public FryerRecipe(Ingredient input, ItemStack output, ResourceLocation id, int fryTime) {
        this.input = input;
        this.output = output;
        this.id = id;
        this.fryTime = fryTime;
    }

    public int getFryTime() {
        return fryTime;
    }

    @Override
    public boolean matches(Container inv, Level p_44003_) {
        return this.input.test(inv.getItem(0));
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
        return RecipeInit.FRYER_RECIPE;
    }

    private static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<FryerRecipe>{

        @Override
        public FryerRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(ChoppingRecipe.getJsonElement(json, "input"));
            ItemStack output =  CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            int fryTime = GsonHelper.getAsInt(json, "frytime", 200);
            return new FryerRecipe(input, output, id, fryTime);
        }

        @Nullable
        @Override
        public FryerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int fryTime = buffer.readInt();
            return new FryerRecipe(input, output, id, fryTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FryerRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeInt(recipe.fryTime);
        }
    }
}
