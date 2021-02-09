package com.soliel.rituacraft.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityEssenceGenerator implements ICapabilitySerializable<INBT> {
    public static final String ESSENCE_NBT = "essence_gen";

    private final EssenceGenerator generator;

    public CapabilityEssenceGenerator(int baseGeneration, TileEntity tile) {
        generator = new EssenceGenerator(baseGeneration, tile);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(RegisterCapabilities.CAPABILITY_ESSENCE_GEN == cap) {
            return (LazyOptional<T>)LazyOptional.of(() -> generator);
        }

        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return getCapability(cap, null);
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT newNbt = new CompoundNBT();
        INBT generatorNBT = RegisterCapabilities.CAPABILITY_ESSENCE_GEN.writeNBT(generator, null);
        newNbt.put(ESSENCE_NBT, generatorNBT);
        return newNbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT)nbt;
        INBT generatorNBT = compoundNBT.get(ESSENCE_NBT);

        RegisterCapabilities.CAPABILITY_ESSENCE_GEN.readNBT(generator, null, generatorNBT);
    }
}
