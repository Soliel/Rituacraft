package com.soliel.rituacraft.client.render;

import net.minecraft.util.math.vector.Vector3f;

public class VertexContext {
    public Vector3f pos;
    public float u;
    public float v;

    public VertexContext(Vector3f pos, float u, float v) {
        this.pos = pos;
        this.u = u;
        this.v = v;
    }
}
