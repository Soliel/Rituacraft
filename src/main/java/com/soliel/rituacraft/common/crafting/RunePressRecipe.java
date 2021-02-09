package com.soliel.rituacraft.common.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    class RunePressSerializer implements IRecipeSerializer<RunePressRecipe> {

        @Override
        public RunePressRecipe read(ResourceLocation recipeId, JsonObject json) {
            return null;
        }

        @Nullable
        @Override
        public RunePressRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return null;
        }

        @Override
        public void write(PacketBuffer buffer, RunePressRecipe recipe) {

        }

        @Override
        public IRecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return null;
        }

        @Override
        public Class<IRecipeSerializer<?>> getRegistryType() {
            return null;
        }
    }
}
