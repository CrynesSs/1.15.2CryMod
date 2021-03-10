package com.CrynesSs.RedstoneEnhancement.Util.Enums;

import net.minecraft.util.IStringSerializable;

public enum MaschineType implements IStringSerializable {
    FURNACE("furnace"),
    STORAGE("storage"),
    GENERATOR("generator");

    private final String name;

    MaschineType(String s) {
        this.name = s;

    }

    @Override
    public String getName() {
        return this.name;
    }
}
