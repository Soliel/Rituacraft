package com.soliel.rituacraft.common.container;

import com.soliel.rituacraft.Rituacraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Rituacraft.MODID);

    public static final RegistryObject<ContainerType<RitualCreateContainer>> RITUAL_CREATE = CONTAINER_TYPES.register("ritual_create", () -> IForgeContainerType.create(RitualCreateContainer::new));
}
