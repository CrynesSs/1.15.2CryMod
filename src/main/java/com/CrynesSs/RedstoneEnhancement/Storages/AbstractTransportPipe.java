package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import com.CrynesSs.RedstoneEnhancement.cables.AbstractCableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeItem;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractTransportPipe extends Block {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final VoxelShape shape1 = Block.makeCuboidShape(6, 6, 0, 10, 10, 6);
    public static final VoxelShape shape2 = Block.makeCuboidShape(6, 6, 6, 10, 10, 10);

    protected AbstractTransportPipe(Properties builder, Float transportRate) {
        super(builder);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, WEST, EAST, UP, DOWN);
        super.fillStateContainer(builder);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        System.out.println("Abstract Pipe creating Tileentity");
        return TileEntityTypes.TPIPE.get().create();


    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        List<VoxelShape> Boxes = new ArrayList<>();
        if (state.get(NORTH)) {
            Boxes.add(shape1);
        }
        if (state.get(SOUTH)) {
            Boxes.add(AbstractCableBlock.rotateShape2(shape1, Direction.SOUTH));
        }
        if (state.get(EAST)) {
            Boxes.add(AbstractCableBlock.rotateShape2(shape1, Direction.EAST));
        }
        if (state.get(WEST)) {
            Boxes.add(AbstractCableBlock.rotateShape2(shape1, Direction.WEST));
        }
        if (state.get(UP)) {
            Boxes.add(AbstractCableBlock.rotateShape2(shape1, Direction.UP));
        }
        if (state.get(DOWN)) {
            Boxes.add(AbstractCableBlock.rotateShape2(shape1, Direction.DOWN));
        }
        Boxes.add(shape2);
        return Boxes.stream().reduce((k, v) -> VoxelShapes.combineAndSimplify(k, v, IBooleanFunction.OR)).get();
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        System.out.println("Getting state for Placement");
        return getPipesConnected(context.getPos(), this.getDefaultState(), context.getWorld(), true, context.getNearestLookingDirection());
    }


    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        BlockState state = getPipesConnected(currentPos, stateIn, (World) worldIn, false, Direction.NORTH);
        if (worldIn.getTileEntity(currentPos) instanceof TransportPipeTileEntity && !((World) worldIn).isRemote) {
            TransportPipeTileEntity tile = (TransportPipeTileEntity) worldIn.getTileEntity(currentPos);
            if (tile != null) {
                tile.setDIRECTIONS(state);
            }
        }
        return state;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    public BlockState getPipesConnected(BlockPos currentPos, BlockState stateIn, World worldIn, boolean placement, Direction facing) {
        //*Facing pos is the Block that send this Update,Currentpos the Pos of this Block
        //*Facing is the Direction in which the sending Block is located. If the Block is North -> North
        boolean[] pipeconnections = new boolean[6];
        int i = 0;
        int k = 0;
        for (Direction d : Direction.values()) {
            if (worldIn.getBlockState(currentPos.offset(d)).getBlock() instanceof AbstractTransportPipe) {
                if (!worldIn.getBlockState(currentPos.offset(d)).has(ClearPipe.COLOR) || !stateIn.has(ClearPipe.COLOR)) {
                    pipeconnections[i] = true;
                    k++;
                } else {
                    if (worldIn.getBlockState(currentPos.offset(d)).get(ClearPipe.COLOR).equals(stateIn.get(ClearPipe.COLOR))) {
                        pipeconnections[i] = true;
                        k++;
                    }
                }
            } else {
                pipeconnections[i] = false;
            }
            i++;
        }
        if (placement && k == 0) {
            pipeconnections[facing.getIndex()] = true;
            k++;
        }
        if (k == 0) {
            return stateIn;
        }
        if (k == 1) {
            if (pipeconnections[0] || pipeconnections[1]) {
                pipeconnections[0] = true;
                pipeconnections[1] = true;
            }
            if (pipeconnections[2] || pipeconnections[3]) {
                pipeconnections[2] = true;
                pipeconnections[3] = true;
            }
            if (pipeconnections[4] || pipeconnections[5]) {
                pipeconnections[4] = true;
                pipeconnections[5] = true;
            }
        }
        return stateIn.with(DOWN, pipeconnections[0]).with(UP, pipeconnections[1]).with(NORTH, pipeconnections[2])
                .with(SOUTH, pipeconnections[3]).with(WEST, pipeconnections[4]).with(EAST, pipeconnections[5]);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (canBeInteractedWith(player, state)) {
            interactWith(state, worldIn, pos, player, handIn, hit);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    boolean canBeInteractedWith(PlayerEntity player, BlockState state) {
        return player.getHeldItemMainhand().getItem() instanceof DyeItem && state.has(ClearPipe.COLOR);
    }

    void interactWith(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!(state.get(ClearPipe.COLOR) == ((DyeItem) player.getHeldItemMainhand().getItem()).getDyeColor())) {
            worldIn.setBlockState(pos, state.with(ClearPipe.COLOR, ((DyeItem) player.getHeldItemMainhand().getItem()).getDyeColor()));
            player.getHeldItemMainhand().shrink(1);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }
}
