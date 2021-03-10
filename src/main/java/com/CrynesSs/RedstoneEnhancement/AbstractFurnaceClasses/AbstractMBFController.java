package com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses;

import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

abstract public class AbstractMBFController extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty INVALIDSTRUCTURE = BooleanProperty.create("invalidstructure");
    public AbstractStructure s = null;

    protected AbstractMBFController() {
        super(Block.Properties.create(Material.IRON));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(INVALIDSTRUCTURE, false));
    }

    protected abstract void interactWith(World worldIn, BlockPos pos, PlayerEntity player);

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(INVALIDSTRUCTURE, false);
    }

    public void setInvalidstructure(World w, boolean value, BlockPos pos) {
        w.setBlockState(pos, w.getBlockState(pos).with(INVALIDSTRUCTURE, value));
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public abstract TileEntity createTileEntity(BlockState state, IBlockReader worldIn);

    @Nonnull
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        if (!worldIn.isRemote) {
            this.interactWith(worldIn, pos, player);
        }
        return ActionResultType.SUCCESS;
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, INVALIDSTRUCTURE);
    }

}
