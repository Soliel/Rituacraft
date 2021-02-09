package com.soliel.rituacraft.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class CapabilityEssenceChunk implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private final Chunk chunk;
    private final LazyOptional<EssenceChunk> lazyEssenceChunk = LazyOptional.of(this::getEssenceChunk);
    private EssenceChunk essenceChunk;

    public CapabilityEssenceChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    private EssenceChunk getEssenceChunk() {
        if (this.essenceChunk == null) {
            this.essenceChunk = new EssenceChunk(this.chunk);
        }

        return this.essenceChunk;
    }



    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK ? this.lazyEssenceChunk.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return this.getEssenceChunk().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.getEssenceChunk().deserializeNBT(nbt);
    }
}
