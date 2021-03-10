package com.CrynesSs.RedstoneEnhancement.cables;

import com.CrynesSs.RedstoneEnhancement.Properties.CableState;
import com.CrynesSs.RedstoneEnhancement.Util.Enums.ECableState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.*;


public abstract class AbstractCableBlock extends BreakableBlock {

    public static final CableState CABLESTATE = CableState.create("cablestate");
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    private static final String[] canConnect = {"redsenhance:induction_heating_coil"};
    public static final List<String> canConnectTo = Arrays.asList((canConnect));


    public AbstractCableBlock(Material mat, Float hardness, Float resistance, SoundType soundType, Integer harvestLevel, ToolType harvestedBy) {
        super(
                Block.Properties.create(mat)
                        .hardnessAndResistance(hardness, resistance)
                        .sound(soundType)
                        //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                        .harvestLevel(harvestLevel)
                        .harvestTool(harvestedBy)

        );
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE).with(UP, Boolean.FALSE).with(DOWN, Boolean.FALSE).with(CABLESTATE, ECableState.STRAIGHT));

    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CABLESTATE, NORTH, SOUTH, WEST, EAST, UP, DOWN);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getConnections(context.getWorld(), context.getPos(), context.getNearestLookingDirection());
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return this.getConnections(worldIn, currentPos, facing);
    }

    public BlockState getConnections(IBlockReader reader, BlockPos posThis, Direction looking) {
        boolean north = Objects.requireNonNull(reader.getBlockState(posThis.north()).getBlock().getRegistryName()).toString().equals(getName());
        boolean south = Objects.requireNonNull(reader.getBlockState(posThis.south()).getBlock().getRegistryName()).toString().equals(getName());
        boolean west = Objects.requireNonNull(reader.getBlockState(posThis.west()).getBlock().getRegistryName()).toString().equals(getName());
        boolean east = Objects.requireNonNull(reader.getBlockState(posThis.east()).getBlock().getRegistryName()).toString().equals(getName());
        boolean up = Objects.requireNonNull(reader.getBlockState(posThis.up()).getBlock().getRegistryName()).toString().equals(getName());
        boolean down = Objects.requireNonNull(reader.getBlockState(posThis.down()).getBlock().getRegistryName()).toString().equals(getName());
        if (!north) {
            if (canConnectTo.contains(Objects.requireNonNull(reader.getBlockState(posThis.north()).getBlock().getRegistryName()).toString())) {
                north = true;
            }
        }
        if (!south) {
            if (canConnectTo.contains(Objects.requireNonNull(reader.getBlockState(posThis.south()).getBlock().getRegistryName()).toString())) {
                south = true;
            }
        }
        if (!east) {
            if (canConnectTo.contains(Objects.requireNonNull(reader.getBlockState(posThis.east()).getBlock().getRegistryName()).toString())) {
                east = true;
            }
        }
        if (!west) {
            if (canConnectTo.contains(Objects.requireNonNull(reader.getBlockState(posThis.west()).getBlock().getRegistryName()).toString())) {
                west = true;
            }
        }
        if (!up) {
            if (canConnectTo.contains(Objects.requireNonNull(reader.getBlockState(posThis.up()).getBlock().getRegistryName()).toString())) {
                up = true;
            }
        }
        if (!down) {
            if (canConnectTo.contains(Objects.requireNonNull(reader.getBlockState(posThis.down()).getBlock().getRegistryName()).toString())) {
                down = true;
            }
        }
        Boolean[] bools = {north, south, west, east, up, down};
        final boolean b = north && east || east && south || south && west || north && west;
        ECableState cableState = ECableState.STRAIGHT;
        int i = 0;
        for (boolean bo : bools) {
            if (bo) {
                i++;
            }
        }
        if (i == 0) {
            switch (looking) {
                case NORTH:
                case SOUTH: {
                    north = true;
                    south = true;
                    break;
                }
                case WEST:
                case EAST: {
                    west = true;
                    east = true;
                    break;
                }
                case DOWN:
                case UP: {
                    down = true;
                    up = true;
                    break;
                }
            }
        }
        if (i == 1) {
            if (north) {
                south = true;
            }
            if (south) {
                north = true;
            }
            if (west) {
                east = true;
            }
            if (east) {
                west = true;
            }
            if (up) {
                down = true;
            }
            if (down) {
                up = true;
            }
        }
        if (i == 2) {
            if (!(north && south || west && east || up && down)) {
                cableState = ECableState.CORNER;
            }
        }
        if (i == 3) {
            if (north && south && west || north && south && east || east && west && north || east && west && south || down && west && east || down && north && south) {
                cableState = ECableState.TSECTION;
            }

            if (up) {
                if (north && south || west && east || down && north || down && south || down && east || down && west) {
                    cableState = ECableState.TSECTION;
                }
                if (b) {
                    cableState = ECableState.XYZ;
                }
            }
            if (down) {
                if (b) {
                    cableState = ECableState.XYZ;
                }
            }
        }
        if (i == 4) {
            if (north && south && east && west || up && down && east && west || up && down && south && north) {
                cableState = ECableState.CROSS;
            }
            if (up && down && b) {
                cableState = ECableState.TWITHARM;
            }
            if ((up && !down) || (down && !up)) {
                if (north && east && west || north && south && east || north && south && west || south && east && west) {
                    cableState = ECableState.TWITHARM;
                }
            }
        }
        if (i == 5) {
            cableState = ECableState.CROSSWITHARM;
        }
        if (i == 6) {
            cableState = ECableState.ALLCABLE;
        }
        return this.getDefaultState().with(NORTH, north).with(SOUTH, south).with(WEST, west).with(EAST, east).with(UP, up).with(DOWN, down).with(CABLESTATE, cableState);

    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideInvisible(@Nonnull BlockState state, @Nonnull BlockState adjacentBlockState, Direction side) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(CABLESTATE)) {
            case STRAIGHT: {
                if (state.get(NORTH) || state.get(SOUTH)) {
                    return Hitboxes.STRAIGHT;
                }
                if (state.get(WEST) || state.get(EAST)) {
                    return rotateShape2(Hitboxes.STRAIGHT, Direction.WEST);
                }
                if (state.get(UP) || state.get(DOWN)) {
                    return rotateShape2(Hitboxes.STRAIGHT, Direction.UP);
                }
                return Hitboxes.STRAIGHT;
            }
            //*Working
            case CORNER: {
                if (state.get(NORTH) && state.get(EAST)) {
                    return Hitboxes.CORNER;
                }
                if (state.get(NORTH) && state.get(WEST)) {
                    return rotateShape2(Hitboxes.CORNER, Direction.WEST);
                }
                if (state.get(SOUTH) && state.get(EAST)) {
                    return rotateShape2(Hitboxes.CORNER, Direction.EAST);
                }
                if (state.get(SOUTH) && state.get(WEST)) {
                    return rotateShape2(Hitboxes.CORNER, Direction.SOUTH);
                }
                if (state.get(NORTH) && state.get(UP)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.UP), Direction.WEST);
                }
                if (state.get(NORTH) && state.get(DOWN)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.DOWN), Direction.WEST);
                }
                if (state.get(SOUTH) && state.get(UP)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.UP), Direction.EAST);
                }
                if (state.get(SOUTH) && state.get(DOWN)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.DOWN), Direction.EAST);
                }
                if (state.get(EAST) && state.get(UP)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.UP), Direction.NORTH);
                }
                if (state.get(EAST) && state.get(DOWN)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.DOWN), Direction.NORTH);
                }
                if (state.get(WEST) && state.get(UP)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.UP), Direction.SOUTH);
                }
                if (state.get(WEST) && state.get(DOWN)) {
                    return rotateShape2(rotateShape2(Hitboxes.CORNER, Direction.DOWN), Direction.SOUTH);
                }
                break;
            }
            //*Works
            case TSECTION: {
                if (state.get(NORTH) && state.get(SOUTH)) {
                    if (state.get(WEST)) {
                        return rotateShape2(Hitboxes.TCONNECTION, Direction.SOUTH);
                    }
                    if (state.get(EAST)) {
                        return Hitboxes.TCONNECTION;
                    }
                    if (state.get(UP)) {
                        return rotateShape2(rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.WEST), Direction.UP), Direction.EAST);
                    }
                    if (state.get(DOWN)) {
                        return rotateShape2(rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.WEST), Direction.DOWN), Direction.EAST);
                    }
                }
                if (state.get(WEST) && state.get(EAST)) {
                    if (state.get(NORTH)) {
                        return rotateShape2(Hitboxes.TCONNECTION, Direction.WEST);
                    }
                    if (state.get(SOUTH)) {
                        return rotateShape2(Hitboxes.TCONNECTION, Direction.EAST);
                    }
                    if (state.get(UP)) {
                        return rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.WEST), Direction.UP);
                    }
                    if (state.get(DOWN)) {
                        return rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.WEST), Direction.DOWN);
                    }
                }
                if (state.get(UP) && state.get(DOWN)) {
                    if (state.get(NORTH)) {
                        return rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.UP), Direction.WEST);
                    }
                    if (state.get(SOUTH)) {
                        return rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.UP), Direction.EAST);
                    }
                    if (state.get(WEST)) {
                        return rotateShape2(rotateShape2(Hitboxes.TCONNECTION, Direction.UP), Direction.SOUTH);
                    }
                    if (state.get(EAST)) {
                        return rotateShape2(Hitboxes.TCONNECTION, Direction.UP);
                    }
                }
                break;
            }
            //*Working
            case CROSS: {
                if (state.get(NORTH) && state.get(WEST)) {
                    return Hitboxes.CROSS;
                }
                if (state.get(UP) && state.get(WEST)) {
                    return rotateShape2(Hitboxes.CROSS, Direction.UP);
                }
                if (state.get(UP) && state.get(NORTH)) {
                    return rotateShape2(rotateShape2(Hitboxes.CROSS, Direction.UP), Direction.WEST);
                }
                break;
            }
            //*Works
            case ALLCABLE: {
                return Hitboxes.ALLCABLE;
            }
            //*Works
            case CROSSWITHARM: {
                if (!state.get(DOWN)) {
                    return Hitboxes.CROSSARM;
                }
                if (!state.get(UP)) {
                    return rotateShape2(rotateShape2(Hitboxes.CROSSARM, Direction.DOWN), Direction.DOWN);
                }
                if (!state.get(NORTH)) {
                    return rotateShape2(rotateShape2(Hitboxes.CROSSARM, Direction.DOWN), Direction.SOUTH);
                }
                if (!state.get(SOUTH)) {
                    return rotateShape2(Hitboxes.CROSSARM, Direction.DOWN);
                }
                if (!state.get(WEST)) {
                    return rotateShape2(rotateShape2(Hitboxes.CROSSARM, Direction.DOWN), Direction.EAST);
                }
                if (!state.get(EAST)) {
                    return rotateShape2(rotateShape2(Hitboxes.CROSSARM, Direction.DOWN), Direction.WEST);
                }
                break;
            }
            //*Works
            case XYZ: {
                if (state.get(UP) && !state.get(DOWN)) {
                    if (state.get(NORTH) && state.get(WEST)) {
                        return Hitboxes.XYZ;
                    }
                    if (state.get(NORTH) && state.get(EAST)) {
                        return rotateShape2(Hitboxes.XYZ, Direction.EAST);
                    }
                    if (state.get(SOUTH) && state.get(WEST)) {
                        return rotateShape2(Hitboxes.XYZ, Direction.WEST);
                    }
                    if (state.get(SOUTH) && state.get(EAST)) {
                        return rotateShape2(Hitboxes.XYZ, Direction.SOUTH);
                    }
                }
                if (!state.get(UP) && state.get(DOWN)) {
                    if (state.get(NORTH) && state.get(WEST)) {
                        return rotateShape2(Hitboxes.XYZ, Direction.DOWN);
                    }
                    if (state.get(NORTH) && state.get(EAST)) {
                        return rotateShape2(rotateShape2(Hitboxes.XYZ, Direction.DOWN), Direction.EAST);
                    }
                    if (state.get(SOUTH) && state.get(WEST)) {
                        return rotateShape2(rotateShape2(Hitboxes.XYZ, Direction.DOWN), Direction.WEST);
                    }
                    if (state.get(SOUTH) && state.get(EAST)) {
                        return rotateShape2(rotateShape2(Hitboxes.XYZ, Direction.DOWN), Direction.SOUTH);
                    }
                }
                break;
            }
            //*Works
            case TWITHARM: {
                if (state.get(UP) && !state.get(DOWN)) {
                    if (!state.get(NORTH)) {
                        return rotateShape2(Hitboxes.TWITHARM, Direction.WEST);
                    }
                    if (!state.get(SOUTH)) {
                        return rotateShape2(Hitboxes.TWITHARM, Direction.EAST);
                    }
                    if (!state.get(EAST)) {
                        return Hitboxes.TWITHARM;
                    }
                    if (!state.get(WEST)) {
                        return rotateShape2(Hitboxes.TWITHARM, Direction.SOUTH);
                    }
                }
                if (!state.get(UP) && state.get(DOWN)) {
                    if (!state.get(NORTH)) {
                        return rotateShape2(rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.EAST), Direction.DOWN), Direction.SOUTH);
                    }
                    if (!state.get(SOUTH)) {
                        return rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.EAST), Direction.DOWN);
                    }
                    if (!state.get(EAST)) {
                        return rotateShape2(rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.EAST), Direction.DOWN), Direction.WEST);
                    }
                    if (!state.get(WEST)) {
                        return rotateShape2(rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.EAST), Direction.DOWN), Direction.EAST);
                    }
                }
                if (state.get(UP) && state.get(DOWN)) {
                    if (state.get(NORTH) && state.get(WEST)) {
                        return rotateShape2(Hitboxes.TWITHARM, Direction.DOWN);
                    }
                    if (state.get(NORTH) && state.get(WEST)) {
                        return rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.DOWN), Direction.EAST);
                    }
                    if (state.get(SOUTH) && state.get(WEST)) {
                        return rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.DOWN), Direction.WEST);
                    }
                    if (state.get(SOUTH) && state.get(EAST)) {
                        return rotateShape2(rotateShape2(Hitboxes.TWITHARM, Direction.DOWN), Direction.SOUTH);
                    }
                }
                break;
            }
        }
        return Hitboxes.STRAIGHT;
    }

    public static VoxelShape rotateShape2(VoxelShape shape, Direction direction) {
        if (direction == Direction.NORTH) return shape;
        Set<VoxelShape> rotatedShapes = new HashSet<>();

        shape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
            y1 = (y1 * 16) - 8;
            y2 = (y2 * 16) - 8;
            x1 = (x1 * 16) - 8;
            x2 = (x2 * 16) - 8;
            z1 = (z1 * 16) - 8;
            z2 = (z2 * 16) - 8;

            if (direction == Direction.EAST)
                rotatedShapes.add(Block.makeCuboidShape(8 - z1, y1 + 8, 8 + x1, 8 - z2, y2 + 8, 8 + x2));
            else if (direction == Direction.SOUTH)
                rotatedShapes.add(Block.makeCuboidShape(8 - x1, y1 + 8, 8 - z1, 8 - x2, y2 + 8, 8 - z2));
            else if (direction == Direction.WEST)
                rotatedShapes.add(Block.makeCuboidShape(8 + z1, y1 + 8, 8 - x1, 8 + z2, y2 + 8, 8 - x2));
            else if (direction == Direction.UP)
                rotatedShapes.add(Block.makeCuboidShape(x1 + 8, 8 - z2, 8 + y1, x2 + 8, 8 - z1, 8 + y2));
            else if (direction == Direction.DOWN)
                rotatedShapes.add(Block.makeCuboidShape(x1 + 8, 8 + z1, 8 - y2, x2 + 8, 8 + z2, 8 - y1));

        });
        return rotatedShapes.stream().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }

    abstract String getName();

    public BlockPos[] get9FromCenter(BlockPos pos) {
        BlockPos[] positions = new BlockPos[9];
        for (int i = 0; i < 9; i++) {
            positions[i] = pos.add((i / 3) - 1, 0, (i % 3) - 1);
        }
        return positions;
    }

}
