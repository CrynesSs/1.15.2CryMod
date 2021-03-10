package com.CrynesSs.RedstoneEnhancement.OreBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class RubyOre extends OreBlock {
    public RubyOre() {
        super(Block.Properties.create(Material.IRON)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(3)
                .hardnessAndResistance(2.5f, 5f)
                .sound(SoundType.STONE)
        );
    }
}
