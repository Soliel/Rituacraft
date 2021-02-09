package com.soliel.rituacraft.client;

import com.soliel.rituacraft.client.render.rituals.FurnusTileEntityRenderer;
import com.soliel.rituacraft.client.render.tiles.RunePressTileEntityRenderer;
import com.soliel.rituacraft.common.CommonProxy;
import com.soliel.rituacraft.common.blocks.RegistryBlocks;
import com.soliel.rituacraft.common.rituals.RegistryRituals;
import com.soliel.rituacraft.common.tile.RegistryTileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void attachEventHandlers(IEventBus eventBus) {
        super.attachEventHandlers(eventBus);
    }

    @Override
    public void attachLifecycle(IEventBus modEventBus) {
        super.attachLifecycle(modEventBus);

        modEventBus.addListener(this::onClientSetupEvent);
        modEventBus.addListener(this::preTextureStitchEvent);
    }

    public void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(RegistryBlocks.ESSENCE_NEXUS.get(), RenderType.getSolid());

        ClientRegistry.bindTileEntityRenderer(RegistryRituals.FURNUS_RITUAL_TILE.get(), FurnusTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(RegistryTileEntity.RUNE_PRESS.get(), RunePressTileEntityRenderer::new);
    }

    public void preTextureStitchEvent(TextureStitchEvent.Pre event) {
        if(!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) return;

        event.addSprite(FurnusTileEntityRenderer.FURNUS_INNER_CIRCLE);
        event.addSprite(FurnusTileEntityRenderer.FURNUS_OUTER_CIRCLE);
        event.addSprite(FurnusTileEntityRenderer.FURNUS_LOWER_CIRCLE);
        event.addSprite(FurnusTileEntityRenderer.FURNUS_UPPER_CIRCLE);
        event.addSprite(FurnusTileEntityRenderer.FURNUS_UPPER_CIRCLE_SMALL);
        event.addSprite(FurnusTileEntityRenderer.FURNUS_LOWER_CIRCLE_SMALL);
    }
}
