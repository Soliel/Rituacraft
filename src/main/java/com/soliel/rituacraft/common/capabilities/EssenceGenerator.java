package com.soliel.rituacraft.common.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EssenceGenerator {
    private int tickGeneration;
    private final TileEntity genTile;

    public EssenceGenerator(TileEntity tile) {
        this(0, tile);
    }

    public EssenceGenerator(int startingAmount, TileEntity tile) {
        this.tickGeneration = startingAmount;
        this.genTile = tile;

        this.genTile.markDirty();
    }

    public int getGeneration() {
        return this.tickGeneration;
    }

    public void setGeneration(int newGenAmount) {
        this.tickGeneration = newGenAmount;
        this.genTile.markDirty();
    }

    public static class EssenceGeneratorStorage implements Capability.IStorage<EssenceGenerator> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<EssenceGenerator> capability, EssenceGenerator instance, Direction side) {
            return IntNBT.valueOf(instance.tickGeneration);
        }

        @Override
        public void readNBT(Capability<EssenceGenerator> capability, EssenceGenerator instance, Direction side, INBT nbt) {
            int tickGeneration = 0;
            if(nbt.getType() == IntNBT.TYPE) {
                tickGeneration = ((IntNBT)nbt).getInt();
            }

            instance.setGeneration(tickGeneration);
        }
    }
}
