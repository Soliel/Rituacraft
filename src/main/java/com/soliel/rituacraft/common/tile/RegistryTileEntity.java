package com.soliel.rituacraft.common.tile;

import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.common.blocks.RegistryBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryTileEntity {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Rituacraft.MODID);

    public static final RegistryObject<TileEntityType<TileEntityEssenceNexus>> ESSENCE_NEXUS = TILE_ENTITIES.register("essence_nexus", () ->
            TileEntityType.Builder.create(TileEntityEssenceNexus::new, RegistryBlocks.ESSENCE_NEXUS.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityRunePress>> RUNE_PRESS = TILE_ENTITIES.register("rune_press", () ->
            TileEntityType.Builder.create(TileEntityRunePress::new, RegistryBlocks.RUNE_PRESS.get()).build(null));
}
