package com.soliel.rituacraft.common.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.shapes.ISelectionContext;

public class BlockUtils {
    public static boolean placeBlock(BlockItemUseContext context, Block newBlock) {
        final BlockState newState = getBlockStateForNewBlock(context, newBlock);
        if(newState == null) return false;
        final boolean didPlaceSucceed = placeBlock(context, newState);
        if(didPlaceSucceed) {
            BlockItem.setTileEntityNBT(context.getWorld(), context.getPlayer(), context.getPos(), context.getItem());
            newBlock.onBlockPlacedBy(context.getWorld(), context.getPos(), newState, context.getPlayer(), context.getItem());
        }

        return didPlaceSucceed;
    }


    public static boolean placeBlock(BlockItemUseContext context, BlockState state) {
        return context.getWorld().setBlockState(context.getPos(), state, 11); //Magic flag means render on the main thread, send to client, and trigger a block update.
    }

    public static BlockState getBlockStateForNewBlock(BlockItemUseContext context, Block newBlock) {
        BlockState blockState = newBlock.getStateForPlacement(context);
        return blockState != null && canPlaceBlock(context, blockState) ? blockState : null;
    }

    public static boolean canPlaceBlock(BlockItemUseContext context, BlockState newState) {
        PlayerEntity placerEntity = context.getPlayer();
        ISelectionContext selectionContext = placerEntity == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(placerEntity);
        return (newState.isValidPosition(context.getWorld(), context.getPos()) && context.getWorld().placedBlockCollides(newState, context.getPos(), selectionContext));
    }
}
