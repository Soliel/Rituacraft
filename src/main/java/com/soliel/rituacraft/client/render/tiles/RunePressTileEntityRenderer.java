package com.soliel.rituacraft.client.render.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.client.render.GeometryCube;
import com.soliel.rituacraft.client.render.RenderContext;
import com.soliel.rituacraft.client.render.VertexContext;
import com.soliel.rituacraft.common.tile.TileEntityRunePress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RunePressTileEntityRenderer extends TileEntityRenderer<TileEntityRunePress> {
    public static final ResourceLocation RUNE_PRESS_TEX = new ResourceLocation(Rituacraft.MODID, "block/rune_press");

    private final GeometryCube   pressCube = setupPressCube();
    private final GeometryCube   pressShaft = setupPressShaft();
    private final GeometryCube[] crankCubes = setupCrank();

    public RunePressTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void addVertex(RenderContext context, VertexContext vertexContext) {
        context.vertexBuilder.pos(context.matrixStack.getLast().getMatrix(), vertexContext.pos.getX(), vertexContext.pos.getY(), vertexContext.pos.getZ())
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(vertexContext.u, vertexContext.v)
                .lightmap(context.combinedLightIn)
                .normal(1, 0, 0 )
                .endVertex();
    }

    @Override
    public void render(TileEntityRunePress tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TextureAtlasSprite runePressSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(RUNE_PRESS_TEX);

        matrixStackIn.push();
        matrixStackIn.translate(0.5f, 0.5f, 0.5f);
        //TODO: apply block rotation
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getSolid());
        RenderContext context = new RenderContext(builder, matrixStackIn, runePressSprite, combinedLightIn);

        float progress = 6 * tileEntityIn.getProgress();
        float angle = (progress * 360 * 5) % 360;

        pressCube.setPos(new Vector3f(3f/16f, (7f - progress)/16f, 3f/16f));
        pressShaft.setPosAndSize(new Vector3f(7f/16f, (8f - progress)/16f, 7f/16f), new Vector3f(2f/16f, (1f + progress)/16f, 2f/16f));

        pressCube.renderCube(context, this::addVertex);
        pressShaft.renderCube(context, this::addVertex);

        matrixStackIn.translate(0.5f, 0.5f, 0.5f);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(angle));
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        for(GeometryCube cube : crankCubes) {
            cube.renderCube(context, this::addVertex);
        }

        matrixStackIn.pop();
    }

    private GeometryCube setupPressCube() {
        GeometryCube pressCube = new GeometryCube(new Vector3f(3f/16f, 7f/16f, 3f/16f), new Vector3f(10f/16f, 1f/16f, 10f/16f));

        pressCube.setUvForFaces(1.25f,3.75f,  5.25f, 4.75f, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        pressCube.setUvForFaces(1.25f, 3.75f, 5.25f, 7.75f, Direction.UP, Direction.DOWN);

        return pressCube;
    }

    private GeometryCube setupPressShaft() {
        GeometryCube pressShaft = new GeometryCube(new Vector3f(7f/16f, 8f/16f, 7f/16f), new Vector3f(2f/16f, 1f/16f, 2f/16f));

        pressShaft.excludeFacesByDirection(Direction.UP, Direction.DOWN);
        pressShaft.setUvForFaces(6f, 12f, 8f, 14f, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

        return pressShaft;
    }

    private GeometryCube[] setupCrank() {
        GeometryCube[] crankCubes = new GeometryCube[5];

        //Middle Piece
        crankCubes[0] = new GeometryCube(new Vector3f(7f/16f, 12f/16f, 7f/16f), new Vector3f(2f/16f, 2f/16f, 2f/16f));
        crankCubes[0].setUvForFaces(6.25f, 13f, 8.25f, 14f, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        crankCubes[0].setUvForFace(Direction.UP, 5.5f, 10.25f, 6.25f, 11f);
        crankCubes[0].excludeFacesByDirection(Direction.DOWN);

        //North Handle
        crankCubes[1] = new GeometryCube(new Vector3f(7f/16f, 13f/16f, 3f/16f), new Vector3f(2f/16f, 1f/16f, 4f/16f));
        crankCubes[1].setUvForFaces(2.25f, 6.25f, 4.25f, 6.75f, Direction.EAST, Direction.WEST);
        crankCubes[1].setUvForFaces(1.75f, 4.5f, 2.75f, 6.5f, Direction.UP, Direction.DOWN);
        crankCubes[1].setUvForFace(Direction.NORTH, 2.25f, 6f, 4.25f, 7f);
        crankCubes[1].excludeFacesByDirection(Direction.SOUTH);

        //South Handle
        crankCubes[2] = new GeometryCube(new Vector3f(7f/16f, 13f/16f, 9f/16f), new Vector3f(2f/16f, 1f/16f, 4f/16f));
        crankCubes[2].setUvForFaces(2.25f, 6.25f, 4.25f, 6.75f, Direction.EAST, Direction.WEST);
        crankCubes[2].setUvForFaces(1.75f, 4.5f, 2.75f, 6.5f, Direction.UP, Direction.DOWN);
        crankCubes[2].setUvForFace(Direction.SOUTH, 2.25f, 6f, 4.25f, 7f);
        crankCubes[2].excludeFacesByDirection(Direction.NORTH);

        //East Handle
        crankCubes[3] = new GeometryCube(new Vector3f(9f/16f, 13f/16f, 7f/16f), new Vector3f(4f/16f, 1f/16f, 2f/16f));
        crankCubes[3].setUvForFaces(2.25f, 6.25f, 4.25f, 6.75f, Direction.NORTH, Direction.SOUTH);
        crankCubes[3].setUvForFaces(1.75f, 4.5f, 2.75f, 6.5f, Direction.UP, Direction.DOWN);
        crankCubes[3].setUvForFace(Direction.EAST, 2.25f, 6f, 4.25f, 7f);
        crankCubes[3].excludeFacesByDirection(Direction.WEST);

        //West Handle
        crankCubes[4] = new GeometryCube(new Vector3f(3f/16f, 13f/16f, 7f/16f), new Vector3f(4f/16f, 1f/16f, 2f/16f));
        crankCubes[4].setUvForFaces(2.25f, 6.25f, 4.25f, 6.75f, Direction.NORTH, Direction.SOUTH);
        crankCubes[4].setUvForFaces(1.75f, 4.5f, 2.75f, 6.5f, Direction.UP, Direction.DOWN);
        crankCubes[4].setUvForFace(Direction.WEST, 2.25f, 6f, 4.25f, 7f);
        crankCubes[4].excludeFacesByDirection(Direction.EAST);

        return crankCubes;
    }
}
