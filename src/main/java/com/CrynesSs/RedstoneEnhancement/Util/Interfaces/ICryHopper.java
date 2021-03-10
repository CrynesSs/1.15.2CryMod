package com.CrynesSs.RedstoneEnhancement.Util.Interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ICryHopper {
    IItemHandlerModifiable getInventory(Direction direction);

    ItemStack putItem();

    ItemStack suckItem();

    IItemHandlerModifiable getMyInventory();
}
