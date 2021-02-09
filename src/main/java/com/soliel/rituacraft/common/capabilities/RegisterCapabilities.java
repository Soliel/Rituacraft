package com.soliel.rituacraft.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class RegisterCapabilities {
    @CapabilityInject(EssenceChunk.class)
    public static Capability<EssenceChunk> CAPABILITY_ESSENCE_CHUNK;
    @CapabilityInject(EssenceGenerator.class)
    public static Capability<EssenceGenerator> CAPABILITY_ESSENCE_GEN;

    public static void Register() {
        CapabilityManager.INSTANCE.register(EssenceChunk.class, new NullCapStorage<>(), () -> null);
        CapabilityManager.INSTANCE.register(EssenceGenerator.class, new EssenceGenerator.EssenceGeneratorStorage(), () -> null);
    }
}
