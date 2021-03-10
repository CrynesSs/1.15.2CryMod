package com.CrynesSs.RedstoneEnhancement.cables;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class LowVoltageCable extends AbstractCableBlock {
    // public static final IntegerProperty VOLT = IntegerProperty.create("volt",-50000,50000);
    // public static final IntegerProperty AMPERE = IntegerProperty.create("ampere",-50000,50000);

    public LowVoltageCable() {
        super(Material.IRON, 7.5f, 6.125f, SoundType.METAL, 2, ToolType.PICKAXE);
    }

    @Override
    String getName() {
        return "redsenhance:low_voltage_cable";
    }

}
