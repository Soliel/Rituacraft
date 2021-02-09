package com.soliel.rituacraft.common.blocks;

import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.common.blocks.BlockEssenceNexus;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Rituacraft.MODID);

    public static final RegistryObject<Block> ESSENCE_NEXUS = BLOCKS.register("essence_nexus", BlockEssenceNexus::new);
    public static final RegistryObject<Block> RUNE_PRESS = BLOCKS.register("rune_press", BlockRunePress::new);

}
