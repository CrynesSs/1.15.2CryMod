package com.CrynesSs.RedstoneEnhancement.structures;

import com.CrynesSs.RedstoneEnhancement.DataManager;
import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class MultiBlockFurnace extends AbstractStructure {

    @Override
    public void refreshTypof() {
        this.setTypof(DataManager.STRUCTURE_DATA.getData(new ResourceLocation("redsenhance:multi_block_furnace")));
    }

    public MultiBlockFurnace(BlockPos size, HashMap<BlockPos, Block> BlockList, BlockPos CornerPos, StructureData s) {
        super(size, BlockList, CornerPos, s);
    }

    public void onCreated(World w) {
        super.onCreated(w, this);
    }

}
