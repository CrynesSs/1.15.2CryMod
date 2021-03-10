package com.CrynesSs.RedstoneEnhancement.Util.Enums;

import net.minecraft.util.IStringSerializable;

public enum EStructureBlocks implements IStringSerializable {
    CORNERUP("cornerup"),
    CORNERDOWN("cornerdown"),
    EDGEUP("edgeup"),
    EDGEDOWN("edgedown"),
    EDGEVERTICAL("edgevertical"),
    OUTERRING("outterring"),
    INNERRING("innerring"),
    CENTER("center");
    //use togheter with Facing
    private final String name;

    EStructureBlocks(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static EStructureBlocks[] getSizeStructure(size s) {
        switch (s) {
            case SMALL:
                return new EStructureBlocks[]{CORNERUP, CORNERDOWN, EDGEUP, EDGEDOWN, EDGEVERTICAL, CENTER};
            case MEDIUM:
                return new EStructureBlocks[]{CORNERUP, CORNERDOWN, EDGEUP, EDGEDOWN, EDGEVERTICAL, CENTER, INNERRING};
            case LARGE:
                return new EStructureBlocks[]{CORNERUP, CORNERDOWN, EDGEUP, EDGEDOWN, EDGEVERTICAL, CENTER, INNERRING, OUTERRING};
            default:
                return new EStructureBlocks[]{CORNERUP, CORNERDOWN};
        }
    }

    public enum size {
        SMALL, MEDIUM, LARGE
    }
}
