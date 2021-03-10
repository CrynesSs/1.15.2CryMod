package com.CrynesSs.RedstoneEnhancement.Util.Enums;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum EStructureType implements IStringSerializable {
    MULTI_BLOCK_FURNACE("mbf"),
    IRON_FLUID_TANK("ift");

    private final String name;

    EStructureType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

}
