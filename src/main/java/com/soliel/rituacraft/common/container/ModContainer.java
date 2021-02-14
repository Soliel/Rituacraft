package com.soliel.rituacraft.common.container;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public abstract class ModContainer extends Container {
    protected ModContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }


}
