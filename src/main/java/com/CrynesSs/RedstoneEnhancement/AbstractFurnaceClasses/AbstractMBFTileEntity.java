package com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses;

import com.CrynesSs.RedstoneEnhancement.TileEntities.InventoryTile;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ForgeHooks;

abstract public class AbstractMBFTileEntity extends InventoryTile implements ITickableTileEntity, INamedContainerProvider {

    public AbstractMBFTileEntity(TileEntityType<?> typeIn, int invSize) {
        super(typeIn, invSize);
    }

    abstract public void tick();

    public static boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack) > 0;
    }


}
