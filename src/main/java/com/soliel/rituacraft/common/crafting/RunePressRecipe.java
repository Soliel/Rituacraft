package com.soliel.rituacraft.common.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class RunePressRecipe implements IRecipe<IInventory> {
    private final Ingredient material;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public RunePressRecipe(Ingredient material, ItemStack result, ResourceLocation recipeId) {
        this.material = material;
        this.result = result;
        this.recipeId = recipeId;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.material.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryRecipes.RUNE_PRESS_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RegistryRecipes.RUNE_PRESS_RECIPE;
    }

    static class RunePressSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RunePressRecipe> {

        @Override
        public RunePressRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonElement material = json.get("material");
            JsonObject result = json.getAsJsonObject("result");

            Ingredient matIngredient = CraftingHelper.getIngredient(material);
            ItemStack resStack = CraftingHelper.getItemStack(result, false);

            return new RunePressRecipe(matIngredient, resStack, recipeId);
        }

        @Nullable
        @Override
        public RunePressRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient mat = Ingredient.read(buffer);
            ItemStack res = buffer.readItemStack();

            return new RunePressRecipe(mat, res, recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, RunePressRecipe recipe) {
            recipe.material.write(buffer);
            buffer.writeItemStack(recipe.result);
        }
    }
}
