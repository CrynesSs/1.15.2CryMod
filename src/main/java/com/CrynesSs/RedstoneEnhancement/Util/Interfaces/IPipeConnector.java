package com.CrynesSs.RedstoneEnhancement.Util.Interfaces;

import com.CrynesSs.RedstoneEnhancement.Storages.AbstractPipe;
import com.CrynesSs.RedstoneEnhancement.Storages.TransportPipeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.HashMap;

public interface IPipeConnector {
    default boolean canconnectPipes() {
        return true;
    }

    //Get all the Pipes that are connected to this
    HashMap<Direction, AbstractPipe> getPipesConnected();

    boolean canTakeItems(Direction direction, ItemStack stack);

    ItemStack handleItem(TransportPipeTileEntity.ItemHandle handle);

    boolean canGiveItems();

    @Nullable
    HashMap<Direction, ItemStack> GiveItems(Direction direction, ItemStack stack);

    int[] getOutputSlots();

    TransportPipeTileEntity.ItemHandle sendItem();

    default void test(IWorld world, BlockPos pos) {
        Block b = world.getBlockState(pos).getBlock();
        if (b instanceof IFluidProvider) {
            ((IFluidProvider) b).fill(Fluids.FLOWING_LAVA, 25);
        }
    }


}
