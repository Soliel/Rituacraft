package com.soliel.rituacraft.client.render;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class GeometryCube {
    private final Map<Direction, float[]> UVMap = new HashMap();
    private final List<Direction> excludedFaces = new ArrayList<>();

    private Vector3f[] vertexArray;
    private Vector3f pos;
    private Vector3f size;

    public GeometryCube(Vector3f pos, Vector3f size) {
        this.pos = pos;
        this.size = size;

        calculateVertices();

        for (Direction dir : Direction.values()) {
            setUvForFace(dir, 0, 1, 0, 1);
        }
    }

    public void calculateVertices() {
        vertexArray = new Vector3f[8];

        for(int i = 0; i < 8; i++) {
            vertexArray[i] = pos.copy();
        }

        vertexArray[1].add(new Vector3f(size.getX(), 0, 0));
        vertexArray[2].add(new Vector3f(size.getX(), size.getY(), 0));
        vertexArray[3].add(new Vector3f(0, size.getY(), 0));
        vertexArray[4].add(new Vector3f(0, 0, size.getZ()));
        vertexArray[5].add(new Vector3f(size.getX(), 0, size.getZ()));
        vertexArray[6].add(new Vector3f(0, size.getY(), size.getZ()));
        vertexArray[7].add(size);
    }

    public void setSize(Vector3f size) {
        this.size = size;
        calculateVertices();
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
        calculateVertices();
    }

    public void setPosAndSize(Vector3f pos, Vector3f size) {
        this.pos = pos;
        this.size = size;
        calculateVertices();
    }

    public void excludeFacesByDirection(Direction ...dirs) {
        for(Direction dir : dirs) {
            excludedFaces.add(dir);
        }
    }

    public void setUvForFace(Direction dir, float x1, float y1, float x2, float y2) {
        UVMap.put(dir, new float[] {x1, y1, x2, y2});
    }

    public void setUvForFaces(float x1, float y1, float x2, float y2, Direction ...dirs) {
        for(Direction dir : dirs) {
            setUvForFace(dir, x1, y1, x2, y2);
        }
    }

    public void renderCube(RenderContext context, BiConsumer<RenderContext, VertexContext> vertexFunction) {
        for(Direction dir : Direction.values()) {
            if(excludedFaces.contains(dir)) continue;

            int[] vertOrder = getVertexOrderForFace(dir);
            int[][] UVMultiplier = getUVMultiplierForFaceVerticies(dir);
            float[] uvArr = UVMap.get(dir);

            float uRange = uvArr[2] - uvArr[0];
            float vRange = uvArr[3] - uvArr[1];

            int index = 0;
            for(int vert : vertOrder) {
                float u = uvArr[0] + (UVMultiplier[index][0] * uRange);
                float v = uvArr[1] + (UVMultiplier[index][1] * vRange);
                VertexContext newVertex = new VertexContext(vertexArray[vert], context.sprite.getInterpolatedU(u), context.sprite.getInterpolatedV(v));
                vertexFunction.accept(context, newVertex);
                index++;
            }
        }
    }

    public static int[] getVertexOrderForFace(Direction direction) {
        switch(direction) {
            case UP:
                return new int[]{3, 6, 7, 2};
            case DOWN:
                return new int[]{0, 1, 5, 4};
            case EAST:
                return new int[]{1, 2, 7, 5};
            case WEST:
                return new int[]{0, 4, 6, 3};
            case NORTH:
                return new int[]{0, 3, 2, 1};
            case SOUTH:
                return new int[]{4, 5, 7, 6};
            default:
                return null;
        }
    }

    public static int[][] getUVMultiplierForFaceVerticies(Direction direction) {
        switch(direction) {
            case UP:
                return new int[][]{{0, 0}, {0, 1}, {1, 1}, {1, 0}};
            case DOWN:
            case WEST:
            case SOUTH:
                return new int[][]{{0, 1}, {1, 1}, {1, 0}, {0, 0}};
            case EAST:
            case NORTH:
                return new int[][]{{1, 1}, {1, 0}, {0, 0}, {0, 1}};
            default:
                return null;
        }
    }




}
