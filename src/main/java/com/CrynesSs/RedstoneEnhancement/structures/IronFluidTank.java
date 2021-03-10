package com.CrynesSs.RedstoneEnhancement.structures;

import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

public class IronFluidTank extends AbstractStructure {
    @Override
    public void refreshTypof() {

    }

    public IronFluidTank(BlockPos size, HashMap<BlockPos, Block> BlockList, BlockPos Corner, StructureData typof) {
        super(size, BlockList, Corner, typof);
    }
}
