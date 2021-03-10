package com.CrynesSs.RedstoneEnhancement.Machines;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.RegularFurnaceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BigFurnaceBrick extends RegularFurnaceBlock {
    public BigFurnaceBrick() {
        super(Material.IRON, 7.5f, 6.125f, SoundType.METAL, 2, ToolType.PICKAXE);
    }
}
