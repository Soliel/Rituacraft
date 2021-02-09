package com.soliel.rituacraft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.soliel.rituacraft.common.capabilities.EssenceChunk;
import com.soliel.rituacraft.common.capabilities.RegisterCapabilities;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.chunk.Chunk;

public class CommandEssence {
    public static void Register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("rituaessence").requires(p -> p.hasPermissionLevel(2))
            .then(Commands.literal("showChunkGen").executes(context -> {
                CommandSource source = context.getSource();
                BlockPos pos = new BlockPos(source.getPos());
                Chunk chunk = (Chunk) source.getWorld().getChunk(pos);

                EssenceChunk eChunk = chunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null);
                if (eChunk != null) {
                    source.sendFeedback(new StringTextComponent("Current chunk generation amount: " + eChunk.getCurrentGenerationAmount()), true);
                }

                return 0;
            })).then(Commands.literal("setChunkGen").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes( context -> {
                int newAmount = IntegerArgumentType.getInteger(context, "amount");
                CommandSource source = context.getSource();
                BlockPos pos = new BlockPos(source.getPos());
                Chunk chunk = (Chunk) source.getWorld().getChunk(pos);

                EssenceChunk eChunk = chunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null);
                if (eChunk != null) {
                    eChunk.setBaseGenerationAmount(newAmount);
                    source.sendFeedback(new StringTextComponent("Set chunk generation amount to: " + eChunk.getCurrentBaseGenerationAmount()), true);
                }

                return 0;
            }))).then(Commands.literal("updateChunkGen").executes( context -> {
                    CommandSource source = context.getSource();
                    BlockPos pos = new BlockPos(source.getPos());
                    Chunk chunk = (Chunk) source.getWorld().getChunk(pos);

                    EssenceChunk eChunk = chunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null);
                    if (eChunk != null) {
                        eChunk.updateEssenceGeneration();
                        source.sendFeedback(new StringTextComponent("Updated chunk generation amount: " + eChunk.getCurrentGenerationAmount()), true);
                    }

                    return 0;
            })));
    }
}
