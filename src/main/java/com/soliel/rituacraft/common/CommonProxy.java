package com.soliel.rituacraft.common;

import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.common.blocks.BlockEssenceNexus;
import com.soliel.rituacraft.common.blocks.RegistryBlocks;
import com.soliel.rituacraft.common.capabilities.CapabilityEssenceChunk;
import com.soliel.rituacraft.common.capabilities.RegisterCapabilities;
import com.soliel.rituacraft.common.commands.CommandEssence;
import com.soliel.rituacraft.common.crafting.RegistryRecipes;
import com.soliel.rituacraft.common.items.RegistryItems;
import com.soliel.rituacraft.common.items.runes.RegistryRune;
import com.soliel.rituacraft.common.rituals.RegistryRituals;
import com.soliel.rituacraft.common.scheduling.CommonScheduler;
import com.soliel.rituacraft.common.scheduling.ScheduledEssenceUpdate;
import com.soliel.rituacraft.common.tile.RegistryTileEntity;
import com.soliel.rituacraft.common.worldgen.WorldGen;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class CommonProxy {
    private final TickHandler tickHandler = new TickHandler();
    private final CommonScheduler scheduler = new CommonScheduler(tickHandler);

    public static final ItemGroup ITEM_GROUP_RC = new ItemGroup(Rituacraft.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryBlocks.ESSENCE_NEXUS.get());
        }
    };

    public void initialize() {
        scheduler.register(new ScheduledEssenceUpdate());
    }

    public void attachEventHandlers(IEventBus eventBus) {
        tickHandler.attachTickEvents(eventBus);
        eventBus.addListener(this::onRegisterCommands);
        eventBus.addListener(WorldGen::attachBiomeFeatures);
    }

    public void attachLifecycle(IEventBus modEventBus) {
        modEventBus.addListener(this::onCommonSetup);
        RegistryTileEntity.TILE_ENTITIES.register(modEventBus);
        RegistryRituals.RITUAL_TILES.register(modEventBus);
        RegistryBlocks.BLOCKS.register(modEventBus);
        RegistryRituals.RITUAL_BLOCKS.register(modEventBus);
        RegistryRune.registerRunes(RegistryItems.ITEMS);
        RegistryItems.ITEMS.register(modEventBus);
        RegistryRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        WorldGen.FEATURES.register(modEventBus);
        WorldGen.PLACEMENTS.register(modEventBus);
        modEventBus.addGenericListener(Item.class, this::onItemsRegistry);
    }

    public void onItemsRegistry(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        RegistryBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().group(CommonProxy.ITEM_GROUP_RC);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    blockItem.setRegistryName(block.getRegistryName());
                    registry.register(blockItem);
                });
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        RegisterCapabilities.Register();
        MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, this::attachChunkCapability);
    }

    private void attachChunkCapability(AttachCapabilitiesEvent<Chunk> chunkEvent) {
        chunkEvent.addCapability(new ResourceLocation(Rituacraft.MODID, "essence_chunk"), new CapabilityEssenceChunk(chunkEvent.getObject()));
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        CommandEssence.Register(event.getDispatcher());
    }


}
