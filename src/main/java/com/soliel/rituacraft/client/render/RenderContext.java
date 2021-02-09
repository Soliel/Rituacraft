package com.soliel.rituacraft.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class RenderContext {
    public IVertexBuilder vertexBuilder;
    public MatrixStack matrixStack;
    public TextureAtlasSprite sprite;
    public int combinedLightIn;

    public RenderContext(IVertexBuilder vb, MatrixStack ms, TextureAtlasSprite sprite, int combinedLightIn) {
        vertexBuilder = vb;
        matrixStack = ms;
        this.sprite = sprite;
        this.combinedLightIn = combinedLightIn;
    }
}
