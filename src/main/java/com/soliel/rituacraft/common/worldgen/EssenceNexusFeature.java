package com.soliel.rituacraft.common.worldgen;

import com.mojang.serialization.Codec;
import com.soliel.rituacraft.common.blocks.RegistryBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class EssenceNexusFeature<U extends IFeatureConfig> extends Feature<U> {

    public EssenceNexusFeature(Codec<U> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, U config) {
        BlockState placeBlock = RegistryBlocks.ESSENCE_NEXUS.get().getDefaultState();
        BlockPos newPos = pos.up(3);
        newPos = newPos.add(8, 0,8);

        int i = 0;

        if(reader.isAirBlock(newPos) && newPos.getY() < 255 && reader.isAirBlock(newPos.down())) {
            reader.setBlockState(newPos, placeBlock, 2);
            i++;
        }

        return i > 0;
    }
}
