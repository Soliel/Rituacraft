package com.soliel.rituacraft.common.tile;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityRunePress extends TileEntity implements ITickableTileEntity {
    private final int requiredTicks = 100;
    private int passedTicks = 0;
    private int chargedTicks = 0;

    public TileEntityRunePress() {
        super(RegistryTileEntity.RUNE_PRESS.get());
    }

    @Override
    public void tick() {
        if(this.chargedTicks > 0) {
            if(passedTicks < requiredTicks) {
                passedTicks++;
            } else {
                passedTicks = 0;
            }

            chargedTicks--;
        }
    }

    public boolean addCharge() {
        if(this.chargedTicks < 3) {
            this.chargedTicks += 5;
            return true;
        }

        return false;
    }

    public float getProgress() {
        return (float) passedTicks / (float) requiredTicks;
    }
}
