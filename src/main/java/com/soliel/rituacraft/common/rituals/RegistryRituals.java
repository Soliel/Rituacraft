package com.soliel.rituacraft.common.rituals;

import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.common.blocks.RegistryBlocks;
import com.soliel.rituacraft.common.rituals.blocks.BlockFurnusRitual;
import com.soliel.rituacraft.common.rituals.tiles.TileFurnusRitual;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryRituals {
    public static final DeferredRegister<Block> RITUAL_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Rituacraft.MODID);
    public static final DeferredRegister<TileEntityType<?>> RITUAL_TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Rituacraft.MODID);

    //BLOCKS
    public static final RegistryObject<Block> FURNUS_RITUAL_BLOCK = RITUAL_BLOCKS.register("ritual_furnus", BlockFurnusRitual::new);

    //TILES
    public static final RegistryObject<TileEntityType<TileFurnusRitual>> FURNUS_RITUAL_TILE = RITUAL_TILES.register("ritual_furnus", () ->
            TileEntityType.Builder.create(TileFurnusRitual::new, FURNUS_RITUAL_BLOCK.get()).build(null));
}
