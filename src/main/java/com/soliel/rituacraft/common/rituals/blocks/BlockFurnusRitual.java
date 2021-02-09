package com.soliel.rituacraft.common.rituals.blocks;

import com.soliel.rituacraft.common.rituals.tiles.TileFurnusRitual;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFurnusRitual extends Block {
    public BlockFurnusRitual() {
        super(Properties
                .create(Material.AIR)
                .notSolid()
                .doesNotBlockMovement()
                .setLightLevel(value -> 15)
                .noDrops()
                .setEmmisiveRendering((p1, p2, p3) -> true)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileFurnusRitual();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return makeCuboidShape(0,0,0, 16, 6, 16);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileFurnusRitual ritualTile = (TileFurnusRitual) worldIn.getTileEntity(pos);
        if (ritualTile == null) return;

        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 0.5, pos.getZ(), ritualTile.extractFromInputSlot(64));
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 0.5, pos.getZ(), ritualTile.extractFromOutputSlot(64));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileFurnusRitual ritualTile = (TileFurnusRitual) worldIn.getTileEntity(pos);
        if (ritualTile == null) return ActionResultType.SUCCESS;

        if(player.isSneaking()) {
            player.inventory.addItemStackToInventory(ritualTile.extractFromInputSlot(64));
        } else {
            player.inventory.addItemStackToInventory(ritualTile.extractFromOutputSlot(64));
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}