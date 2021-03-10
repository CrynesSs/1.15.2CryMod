package com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.data.ModelProperty;

public class DoubleSlabTE extends TileEntity {
    public static final ModelProperty<BlockState> MIMIC = new ModelProperty<>();

    public DoubleSlabTE() {
        super(TileEntityTypes.DOUBLE_SLAB.get());
    }
}
