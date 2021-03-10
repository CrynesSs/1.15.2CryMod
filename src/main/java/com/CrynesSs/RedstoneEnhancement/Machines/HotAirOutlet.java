package com.CrynesSs.RedstoneEnhancement.Machines;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class HotAirOutlet extends Block {
    public HotAirOutlet() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(7.5f, 6.125f)
                .sound(SoundType.METAL)
                //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
