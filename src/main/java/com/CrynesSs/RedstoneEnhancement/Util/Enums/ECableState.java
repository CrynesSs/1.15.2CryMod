package com.CrynesSs.RedstoneEnhancement.Util.Enums;

import net.minecraft.util.IStringSerializable;

public enum ECableState implements IStringSerializable {
    STRAIGHT("straight"),
    TSECTION("tsection"),
    TWITHARM("twitharm"),
    CROSS("cross"),
    XYZ("xyz"),
    CROSSWITHARM("crossarm"),
    ALLCABLE("allcable"),
    CORNER("corner");

    private final String name;

    ECableState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
