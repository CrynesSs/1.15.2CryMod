package com.CrynesSs.RedstoneEnhancement.Machines.InlayingMaschine;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMaschine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;

public class InlayingMaschineBlock extends AbstractMaschine<InlayingMaschineTE> {
    public InlayingMaschineBlock() {
        super(Block.Properties.create(Material.IRON)
                //Ironblock res : 5.0 Gravel 0.6
                .hardnessAndResistance(7.5f, 6.125f)
                .sound(SoundType.METAL)
                //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .notSolid(), InlayingMaschineTE.class);
    }

    @Nonnull
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
