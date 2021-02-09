package com.soliel.rituacraft.common.scheduling;

import com.soliel.rituacraft.common.capabilities.EssenceChunk;
import com.soliel.rituacraft.common.capabilities.RegisterCapabilities;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduledEssenceUpdate implements IScheduledTask{
    public static final List<ChunkPos> chunksToUpdate = new ArrayList<>();

    public static void registerChunkForUpdate(Chunk chunk) {
        ChunkPos pos = chunk.getPos();
        if(!chunksToUpdate.contains(pos)) {
            chunksToUpdate.add(pos);
        }
    }

    @Override
    public TickEvent.Type getEventType() {
        return TickEvent.Type.WORLD;
    }

    @Override
    public int getTickFrequency() {
        return 200;
    }

    @Override
    public void onSchedulerRun(TickEvent event) {
        TickEvent.WorldTickEvent worldEvent = (TickEvent.WorldTickEvent) event;

        for (ChunkPos pos : chunksToUpdate) {
            Chunk chunk = worldEvent.world.getChunkProvider().getChunkNow(pos.x, pos.z);
            if (chunk == null)
                continue;

            EssenceChunk eChunk = chunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null);
            if (eChunk != null) {
                eChunk.updateEssenceGeneration();
            }
        }
    }
}
