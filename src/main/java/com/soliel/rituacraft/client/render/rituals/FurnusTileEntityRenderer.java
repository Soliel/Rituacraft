package com.soliel.rituacraft.client.render.rituals;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.client.render.utils.RenderingUtils;
import com.soliel.rituacraft.common.rituals.RegistryRituals;
import com.soliel.rituacraft.common.rituals.tiles.TileFurnusRitual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class FurnusTileEntityRenderer extends TileEntityRenderer<TileFurnusRitual> {
    public static final ResourceLocation FURNUS_INNER_CIRCLE = new ResourceLocation(Rituacraft.MODID, "block/rituals/furnus_inner_circle");
    public static final ResourceLocation FURNUS_OUTER_CIRCLE = new ResourceLocation(Rituacraft.MODID, "block/rituals/furnus_outer_circle");
    public static final ResourceLocation FURNUS_UPPER_CIRCLE = new ResourceLocation(Rituacraft.MODID, "block/rituals/furnus_upper_circle");
    public static final ResourceLocation FURNUS_LOWER_CIRCLE = new ResourceLocation(Rituacraft.MODID, "block/rituals/furnus_lower_circle");
    public static final ResourceLocation FURNUS_UPPER_CIRCLE_SMALL = new ResourceLocation(Rituacraft.MODID, "block/rituals/furnus_upper_circle_small");
    public static final ResourceLocation FURNUS_LOWER_CIRCLE_SMALL = new ResourceLocation(Rituacraft.MODID, "block/rituals/furnus_lower_circle_small");

    private static final float small_circle_scale_offset = 0.445f;
    private static final float small_circle_x_offset = 0.277f;
    private static final float upper_circle_y_offset = 0.054f;
    private static final float lower_circle_y_offset = 0.5f;

    private static final float item_slot_x_offset = .5f;
    private static final float input_slot_y_offset = .269f;
    private static final float output_slot_y_offset = .722f;


    public FurnusTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void addVertex(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(15728880)
                .normal(1, 0, 0 )
                .endVertex();
    }

    private void addFlatSpriteQuad(IVertexBuilder renderer, MatrixStack stack, TextureAtlasSprite sprite, float height, float scale) {
        createFrontFaceVerticies(renderer, stack, sprite, height, scale);

        addVertex(renderer, stack, 1 * scale, height, 0 * scale, sprite.getMinU(), sprite.getMinV());
        addVertex(renderer, stack, 1 * scale, height, 1 * scale, sprite.getMinU(), sprite.getMaxV());
        addVertex(renderer, stack, 0 * scale, height, 1 * scale, sprite.getMaxU(), sprite.getMaxV());
        addVertex(renderer, stack, 0 * scale, height, 0 * scale, sprite.getMaxU(), sprite.getMinV());
    }

    private void createFrontFaceVerticies(IVertexBuilder renderer, MatrixStack stack, TextureAtlasSprite sprite, float height, float scale) {
        addVertex(renderer, stack, 0 * scale, height, 0 * scale, sprite.getMaxU(), sprite.getMinV());
        addVertex(renderer, stack, 0 * scale, height, 1 * scale, sprite.getMaxU(), sprite.getMaxV());
        addVertex(renderer, stack, 1 * scale, height, 1 * scale, sprite.getMinU(), sprite.getMaxV());
        addVertex(renderer, stack, 1 * scale, height, 0 * scale, sprite.getMinU(), sprite.getMinV());
    }

    @Override
    public void render(TileFurnusRitual tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TextureAtlasSprite innerCircleSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(FURNUS_INNER_CIRCLE);
        TextureAtlasSprite outerCircleSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(FURNUS_OUTER_CIRCLE);
        TextureAtlasSprite upperCircleSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(FURNUS_UPPER_CIRCLE_SMALL);
        TextureAtlasSprite lowerCircleSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(FURNUS_LOWER_CIRCLE_SMALL);

        matrixStackIn.push();

        float time = System.currentTimeMillis() % 5000;
        float adjustedTime = time * .072f;

        float angle = (adjustedTime * 2) % 360;
        float reverseScaleOffset = 1/small_circle_scale_offset;

        matrixStackIn.translate(.5f, .5f, .5f);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(angle));
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        matrixStackIn.translate(item_slot_x_offset, .5f, input_slot_y_offset);
        matrixStackIn.scale(small_circle_scale_offset, small_circle_scale_offset, small_circle_scale_offset);
        RenderingUtils.renderItem(tileEntityIn.getInputSlot(), tileEntityIn.getWorld(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.scale(reverseScaleOffset, reverseScaleOffset, reverseScaleOffset);
        matrixStackIn.translate(-item_slot_x_offset, -.5f, -input_slot_y_offset);

        matrixStackIn.translate(item_slot_x_offset, .5f, output_slot_y_offset);
        matrixStackIn.scale(small_circle_scale_offset, small_circle_scale_offset, small_circle_scale_offset);
        RenderingUtils.renderItem(tileEntityIn.getOutputSlot(), tileEntityIn.getWorld(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        matrixStackIn.scale(reverseScaleOffset, reverseScaleOffset, reverseScaleOffset);
        matrixStackIn.translate(-item_slot_x_offset, -.5f, -output_slot_y_offset);

        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getCutout());

        addFlatSpriteQuad(builder, matrixStackIn, innerCircleSprite, 0.4f, 1);

        float yOffset = 0.2F * (float) tileEntityIn.getProgressPercent();

        matrixStackIn.translate(small_circle_x_offset, yOffset, upper_circle_y_offset);
        addFlatSpriteQuad(builder, matrixStackIn, upperCircleSprite, 0.4f, small_circle_scale_offset);
        matrixStackIn.translate(-small_circle_x_offset, -yOffset, -upper_circle_y_offset);

        matrixStackIn.translate(small_circle_x_offset, 0, lower_circle_y_offset);
        addFlatSpriteQuad(builder, matrixStackIn, lowerCircleSprite, 0.4f, small_circle_scale_offset);
        matrixStackIn.translate(-small_circle_x_offset, 0, -lower_circle_y_offset);

        matrixStackIn.pop();
        matrixStackIn.push();

        time = System.currentTimeMillis() % 6000;
        angle = (time % 6000) * .06f;

        matrixStackIn.translate(.5f, .5f, .5f);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-angle));
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        addFlatSpriteQuad(builder, matrixStackIn, outerCircleSprite, 0.3f, 1);


        matrixStackIn.pop();

    }
}
