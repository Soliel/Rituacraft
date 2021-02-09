package com.soliel.rituacraft.common.worldgen;

import com.soliel.rituacraft.Rituacraft;

import com.soliel.rituacraft.common.blocks.RegistryBlocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGen {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Rituacraft.MODID);
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, Rituacraft.MODID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> ESSENCE_NEXUS = FEATURES.register("essence_nexus", () -> new EssenceNexusFeature<BlockClusterFeatureConfig>(BlockClusterFeatureConfig.field_236587_a_));
    public static final RegistryObject<Placement<NoPlacementConfig>> CHUNK_SPREAD_HEIGHTMAP = PLACEMENTS.register("chunk_spread_heightmap", ChunkSpreadPlacement::new);

    public static ConfiguredFeature<?, ?> GEN_ESSENCE_NEXUS;


    public static void init() {
        GEN_ESSENCE_NEXUS = ESSENCE_NEXUS.get()
                        .withConfiguration(new BlockClusterFeatureConfig
                                .Builder(new SimpleBlockStateProvider(RegistryBlocks.ESSENCE_NEXUS.get().getDefaultState()), SimpleBlockPlacer.PLACER)
                                .tries(12)
                                .build())
                        .withPlacement(CHUNK_SPREAD_HEIGHTMAP.get().configure(NoPlacementConfig.NO_PLACEMENT_CONFIG));
    }

    public static void attachBiomeFeatures(BiomeLoadingEvent event) {
        WorldGen.init();

        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        gen.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, GEN_ESSENCE_NEXUS);
    }
}
