package com.CrynesSs.RedstoneEnhancement.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class RubyBlock extends Block {
    public RubyBlock() {
        super(
                Block.Properties.create(Material.IRON)
                        //Ironblock res : 5.0 Gravel 0.6
                        .hardnessAndResistance(7.5f, 6.125f)
                        .sound(SoundType.METAL)
                        //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                        .harvestLevel(2)
                        .harvestTool(ToolType.PICKAXE)
        );
    }
}
