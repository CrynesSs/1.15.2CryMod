package com.CrynesSs.RedstoneEnhancement.Blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block blockIn, ItemGroup group) {
        super(blockIn, new Item.Properties().group(group));
    }
}
