package com.soliel.rituacraft.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EssenceChunk implements INBTSerializable<CompoundNBT> {
    private final Chunk chunk;
    private int baseGenerationAmount = 40;
    private int currentGenerationAmount = baseGenerationAmount;
    private final Map<BlockPos, MutableInt> essenceGenerators = new ConcurrentHashMap<>();

    public EssenceChunk(Chunk chunk) {
        this.chunk = chunk;
        baseGenerationAmount = 40;
        this.chunk.markDirty();
    }

    public void setBaseGenerationAmount(int baseGenerationAmount) {
        this.baseGenerationAmount = baseGenerationAmount;
        this.updateEssenceGeneration();
        this.chunk.markDirty();
    }

    public int getCurrentBaseGenerationAmount() {
        return this.baseGenerationAmount;
    }

    public int getCurrentGenerationAmount() {
        return this.currentGenerationAmount;
    }

    public void updateEssenceGeneration() {
        if(!this.chunk.getWorld().getChunkProvider().isChunkLoaded(this.chunk.getPos()))
            return;

        int currentGenerationTotal = baseGenerationAmount;

        for (Map.Entry<BlockPos, TileEntity> entry : this.chunk.getTileEntityMap().entrySet()) {
            BlockPos blockPos = entry.getKey();
            TileEntity tileEntity = entry.getValue();

            if (tileEntity.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_GEN).isPresent()) {
                EssenceGenerator tileEssenceGen = tileEntity.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_GEN).orElse(null);

                if (essenceGenerators.get(blockPos) == null) {
                    essenceGenerators.put(blockPos, new MutableInt(tileEssenceGen.getGeneration()));
                }
                currentGenerationTotal += tileEssenceGen.getGeneration();
            }
        }

        int highestSurrounding = findHighestSurroundingChunk();

        if(currentGenerationTotal < highestSurrounding) {
            currentGenerationTotal = (int)Math.floor(Math.min((double)currentGenerationTotal + 0.6 * (double)highestSurrounding, highestSurrounding));
        }

        if (currentGenerationAmount != currentGenerationTotal) {
            this.chunk.markDirty();
            currentGenerationAmount = currentGenerationTotal;
        }

    }

    private int findHighestSurroundingChunk() {
        int currentHighest = 0;
        int chunkX = this.chunk.getPos().x;
        int chunkZ = this.chunk.getPos().z;
        World world = this.chunk.getWorld();

        for(int i = chunkX - 1; i < chunkX + 2; i++) {
            for (int j = chunkZ - 1; j < chunkZ + 2; j++) {
                Chunk chunk = world.getChunkProvider().getChunkNow(i, j);
                if(chunk == null)
                    continue;

                EssenceChunk eChunk = chunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null);
                int genAmount = eChunk.getCurrentGenerationAmount();
                if (genAmount > currentHighest) currentHighest = genAmount;
            }
        }

        return currentHighest;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("base_gen", this.baseGenerationAmount);
        compound.putInt("current_gen", this.currentGenerationAmount);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        int genAmount = nbt.getInt("base_gen");
        int currentAmount = nbt.getInt("current_gen");
        this.baseGenerationAmount = genAmount;
        this.currentGenerationAmount = currentAmount;
        this.updateEssenceGeneration();
    }
}
