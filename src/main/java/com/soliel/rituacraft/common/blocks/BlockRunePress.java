package com.soliel.rituacraft.common.blocks;


import com.soliel.rituacraft.common.tile.TileEntityRunePress;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class BlockRunePress extends Block {
    public BlockRunePress() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .setRequiresTool()
                .hardnessAndResistance(2.0f, 6.0f)
                .notSolid()
                .setOpaque((p1, p2, p3) -> false)
                .setBlocksVision((p1, p2, p3) -> false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityRunePress();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        final TileEntityRunePress tile = (TileEntityRunePress) worldIn.getTileEntity(pos);
        ItemStack item = player.getHeldItem(handIn);

        if(tile != null) {
            if(item != ItemStack.EMPTY) {
                ItemStack insertResult = tile.insertItem(item, true);
                if(insertResult != item) {
                    tile.insertItem(item.split(1), false);
                    if(worldIn.isRemote) {
                        worldIn.notifyBlockUpdate(pos, state, state, 3);
                    }

                    return ActionResultType.SUCCESS;
                }
            }

            if(player.isSneaking()) {
                if(tile.hasResultItem()) {
                    ItemHandlerHelper.giveItemToPlayer(player, tile.grabResultItem());
                } else {
                    ItemHandlerHelper.giveItemToPlayer(player, tile.grabMaterialItem());
                }
                if(worldIn.isRemote) {
                    worldIn.notifyBlockUpdate(pos, state, state, 3);
                }
                return ActionResultType.SUCCESS;
            }

            if(tile.addCharge()) {
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
}
