package com.CrynesSs.RedstoneEnhancement.items;

import com.CrynesSs.RedstoneEnhancement.Blocks.MultiBlockLol;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.Init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().group(RedstoneEnhancement.TAB));
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        System.out.println(context.getItem().getItem().getRegistryName() + " is used");
        if (context.getItem().getItem().equals(ItemInit.MULTI_LOL_ITEM.get())) {
            if (!MultiBlockLol.checkIfSpace(null, new MultiBlockLol(3, 3, 3), context.getWorld(), context.getPos(), context.getFace())) {

            }
        }
        return super.onItemUse(context);
    }
}
