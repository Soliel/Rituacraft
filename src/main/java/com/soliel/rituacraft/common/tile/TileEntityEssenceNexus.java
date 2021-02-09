package com.soliel.rituacraft.common.tile;

import com.soliel.rituacraft.common.capabilities.EssenceGenerator;
import com.soliel.rituacraft.common.capabilities.RegisterCapabilities;
import com.soliel.rituacraft.common.scheduling.ScheduledEssenceUpdate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityEssenceNexus extends TileEntity implements ICapabilityProvider {
    private LazyOptional<EssenceGenerator> essenceGenerator = LazyOptional.of(() -> new EssenceGenerator(360, this));

    public TileEntityEssenceNexus() {
        super(RegistryTileEntity.ESSENCE_NEXUS.get());
    }

    @Override
    public void onLoad() {
        int chunkX = this.pos.getX() >> 4;
        int chunkZ = this.pos.getZ() >> 4;

        for (int i = chunkX - 2; i < chunkX + 2; i++) {
            for(int j = chunkZ - 2; j < chunkZ + 2; j++) {
                if(this.world != null) {
                    Chunk currentChunk = this.world.getChunkProvider().getChunkNow(i, j);
                    if(currentChunk != null) {
                        currentChunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null).updateEssenceGeneration();
                        ScheduledEssenceUpdate.registerChunkForUpdate(currentChunk);
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == RegisterCapabilities.CAPABILITY_ESSENCE_GEN) {
            return essenceGenerator.cast();
        }

        return super.getCapability(cap);
    }
}
