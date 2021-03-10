package com.CrynesSs.RedstoneEnhancement.Machines.MetalFormer;

import com.CrynesSs.RedstoneEnhancement.TileEntities.InventoryTile;
import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalFormerTE extends InventoryTile {
    public MetalFormerTE() {
        super(TileEntityTypes.METAL_FORMER.get(), 5);
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public ISidedInventory createInventory(BlockState p_219966_1_, IWorld p_219966_2_, BlockPos p_219966_3_) {
        return null;
    }
}
