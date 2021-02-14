package com.soliel.rituacraft.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;

import javax.annotation.Nullable;

public class RitualCreateContainer extends ModContainer {
    protected RitualCreateContainer(int windowId, PlayerInventory inv, PacketBuffer data) {
        super(ModContainerTypes.RITUAL_CREATE.get(), windowId);


    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
