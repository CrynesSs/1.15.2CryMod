package com.CrynesSs.RedstoneEnhancement.Blocks;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;

public class HighTemperatureGlassBlock extends AbstractGlassBlock implements IBeaconBeamColorProvider {
    public final DyeColor color = DyeColor.LIME;

    public HighTemperatureGlassBlock() {
        super(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid());
    }

    @Override
    public DyeColor getColor() {
        return color;
    }
}

