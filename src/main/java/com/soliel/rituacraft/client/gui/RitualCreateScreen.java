package com.soliel.rituacraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.common.container.RitualCreateContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class RitualCreateScreen extends ContainerScreen<RitualCreateContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Rituacraft.MODID, "textures/gui/container_ritual_chalk.png");

    public RitualCreateScreen(RitualCreateContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
    }
}
