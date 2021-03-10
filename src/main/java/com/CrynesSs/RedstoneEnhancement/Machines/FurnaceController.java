package com.CrynesSs.RedstoneEnhancement.Machines;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFController;
import com.CrynesSs.RedstoneEnhancement.TileEntities.BigFurnaceTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Collections;

public class FurnaceController extends AbstractMBFController {
    public FurnaceController() {
        super();
    }

    @Override
    protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
        if (s != null) {
            System.out.println(s.getSize() + s.getTypof().getStructurename());
            if (worldIn.getTileEntity(pos) instanceof BigFurnaceTileEntity) {
                BigFurnaceTileEntity tile = (BigFurnaceTileEntity) worldIn.getTileEntity(pos);
                if (tile != null) {
                    tile.getInventory().setStackInSlot(0, new ItemStack(Items.IRON_ORE, 64));
                }
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        if (s == null) {
            System.out.println("s in hastileentity null");
        }
        return state.get(INVALIDSTRUCTURE);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        if (this.s == null) {
            return new BigFurnaceTileEntity();
        }
        System.out.println("s in hastileentity not null");
        return new BigFurnaceTileEntity(5, 500, s, Collections.emptyList(), 0);
    }
}
