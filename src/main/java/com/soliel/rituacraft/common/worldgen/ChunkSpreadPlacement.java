package com.soliel.rituacraft.common.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.*;

import java.util.Random;
import java.util.stream.Stream;

public class ChunkSpreadPlacement extends HeightmapBasedPlacement {
    private final int _chunk_spread = 20;

    public ChunkSpreadPlacement() {
        super(NoPlacementConfig.CODEC);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, IPlacementConfig config, BlockPos pos) {
        int i = pos.getX();
        int j = pos.getZ();
        int chunkX = (i/16) % _chunk_spread;
        int chunkZ = (j/16) % _chunk_spread;
        int k = helper.func_242893_a(this.func_241858_a(config), i, j);
        return k > 0 && chunkX == 0 && chunkZ == 0 ? Stream.of(new BlockPos(i, k, j)) : Stream.of();
    }

    @Override
    protected Heightmap.Type func_241858_a(IPlacementConfig config) {
        return Heightmap.Type.WORLD_SURFACE_WG;
    }
}
