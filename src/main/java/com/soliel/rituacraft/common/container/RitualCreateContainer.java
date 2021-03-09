package com.soliel.rituacraft.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

public class RitualCreateContainer extends ModContainer {
    private IInventory containerInv;

    protected RitualCreateContainer(int windowId, PlayerInventory inv, PacketBuffer data) {
        super(ModContainerTypes.RITUAL_CREATE.get(), windowId);

        containerInv = new Inventory(6);

        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 84;
        final int slotSizePlus2 = 18;

        this.addSlot(new Slot(containerInv, 0, 43, 11));
        this.addSlot(new Slot(containerInv, 1, 23, 23));
        this.addSlot(new Slot(containerInv, 2, 63, 23));
        this.addSlot(new Slot(containerInv, 3, 43, 35));
        this.addSlot(new Slot(containerInv, 4, 26, 52));
        this.addSlot(new Slot(containerInv, 5, 60, 52));

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inv, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }

        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        // Player Hotbar slots
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(inv, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        if(!containerInv.isEmpty()) {
            InventoryHelper.dropInventoryItems(playerIn.world, playerIn, containerInv);
        }

        super.onContainerClosed(playerIn);
    }
}
