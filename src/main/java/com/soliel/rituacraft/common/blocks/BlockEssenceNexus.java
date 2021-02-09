package com.soliel.rituacraft.common.blocks;

import com.soliel.rituacraft.common.tile.TileEntityEssenceNexus;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockEssenceNexus extends Block {

    private static final VoxelShape bounds = Block.makeCuboidShape(4, 2, 4, 12, 14, 12);

    public BlockEssenceNexus() {
        super(AbstractBlock.Properties.create(Material.ROCK)
                .doesNotBlockMovement());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityEssenceNexus();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return bounds;
    }
}
