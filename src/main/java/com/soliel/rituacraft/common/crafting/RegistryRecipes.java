package com.soliel.rituacraft.common.crafting;

import com.soliel.rituacraft.Rituacraft;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryRecipes {
    public static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Rituacraft.MODID);

    //HAHA NO DEFERRED REGISTER FOR THIS JUST BREAK MY PATTERN HAHA
    public static IRecipeType<RunePressRecipe> RUNE_PRESS_RECIPE = IRecipeType.register("rituacraft:rune_press");

    public static RegistryObject<IRecipeSerializer<RunePressRecipe>> RUNE_PRESS_SERIALIZER = RECIPE_SERIALIZERS.register("rune_press", RunePressRecipe.RunePressSerializer::new);
}
