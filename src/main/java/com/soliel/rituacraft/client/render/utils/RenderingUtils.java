package com.soliel.rituacraft.client.render.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RenderingUtils {
    public static void renderItem(ItemStack item, World worldIn, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        IBakedModel iBakedModel = itemRenderer.getItemModelWithOverrides(item, worldIn, null);
        itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, buffer, combinedLight, combinedOverlay, iBakedModel);
    }
}
