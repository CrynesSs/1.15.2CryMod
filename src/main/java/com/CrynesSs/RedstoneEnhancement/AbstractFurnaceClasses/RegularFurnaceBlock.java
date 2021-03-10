package com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses;

import com.CrynesSs.RedstoneEnhancement.Properties.StructureBlocksPosition;
import com.CrynesSs.RedstoneEnhancement.Util.Enums.EStructureBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public abstract class RegularFurnaceBlock extends Block {
    public static final BooleanProperty INVALIDSTRUCTURE = BooleanProperty.create("invalidstructure");
    public static final DirectionProperty OUTWARDS = DirectionProperty.create("outwards", Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
    public static final StructureBlocksPosition BLOCKS_POSITION = StructureBlocksPosition.create("position", EStructureBlocks.size.SMALL);


    public RegularFurnaceBlock(Material mat, Float hardness, Float resistance, SoundType soundType, Integer harvestLevel, ToolType harvestedBy) {
        super(
                Block.Properties.create(mat)
                        .hardnessAndResistance(hardness, resistance)
                        .sound(soundType)
                        //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                        .harvestLevel(harvestLevel)
                        .harvestTool(harvestedBy)
        );
        this.setDefaultState(this.stateContainer.getBaseState().with(INVALIDSTRUCTURE, false).with(OUTWARDS, Direction.NORTH).with(BLOCKS_POSITION, EStructureBlocks.CENTER));
    }

    public static IProperty<Boolean> getINVALIDSTRUCTURE() {
        return INVALIDSTRUCTURE;
    }

    public static IProperty<Direction> getOUTWARDS() {
        return OUTWARDS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(OUTWARDS, INVALIDSTRUCTURE, BLOCKS_POSITION);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        return false;
    }

    public void setnewBlockstate(Direction d, Boolean inValidStructure, EStructureBlocks position, World w, BlockPos pos) {
        w.setBlockState(pos, this.getDefaultState().with(INVALIDSTRUCTURE, inValidStructure).with(OUTWARDS, d).with(BLOCKS_POSITION, position));
    }

    public boolean isCornerBlock(BlockPos corner, BlockPos size, BlockPos block) {
        if (corner.getX() - block.getX() == 0 && corner.getZ() - block.getZ() == 0 && (corner.getY() - block.getY() == 0 || block.getY() - corner.getY() == (size.getY() - 1))) {
            return true;
        } else if (Math.abs(corner.getX() - block.getX()) == size.getX() - 1 && corner.getZ() - block.getZ() == 0 && (corner.getY() - block.getY() == 0 || block.getY() - corner.getY() == (size.getY() - 1))) {
            return true;
        } else if (corner.getX() - block.getX() == 0 && Math.abs(corner.getZ() - block.getZ()) == size.getZ() - 1 && (corner.getY() - block.getY() == 0 || block.getY() - corner.getY() == (size.getY() - 1))) {
            return true;
        } else
            return Math.abs(corner.getX() - block.getX()) == size.getX() - 1 && Math.abs(corner.getZ() - block.getZ()) == size.getZ() - 1 && (corner.getY() - block.getY() == 0 || block.getY() - corner.getY() == size.getY() - 1);
    }

    public boolean isEdge(BlockPos corner, BlockPos size, BlockPos block) {
        if (corner.getY() == block.getY() || block.getY() - corner.getY() == size.getY() - 1) {
            if (block.getX() > corner.getX() && block.getX() < corner.add(size.getX() - 1, 0, 0).getX() && block.getZ() == corner.getZ()) {
                return true;
            }
            if (block.getZ() > corner.getZ() && block.getZ() < corner.add(0, 0, size.getZ() - 1).getZ() && block.getX() == corner.getX()) {
                return true;
            }
            if (block.getX() > corner.getX() && block.getX() < corner.add(size.getX() - 1, 0, 0).getX() && block.getZ() == corner.add(0, 0, size.getZ() - 1).getZ()) {
                return true;
            }
            if (block.getZ() > corner.getZ() && block.getZ() < corner.add(0, 0, size.getZ() - 1).getZ() && block.getX() == corner.add(size.getX() - 1, 0, 0).getX()) {
                return true;
            }
        }
        if (corner.getY() < block.getY() && corner.add(0, size.getY() - 1, 0).getY() > block.getY()) {
            if (corner.getX() - block.getX() == 0 && corner.getZ() - block.getZ() == 0) {
                return true;
            } else if (Math.abs(corner.getX() - block.getX()) == size.getX() - 1 && corner.getZ() - block.getZ() == 0) {
                return true;
            } else if (corner.getX() - block.getX() == 0 && Math.abs(corner.getZ() - block.getZ()) == size.getZ() - 1) {
                return true;
            } else
                return Math.abs(corner.getX() - block.getX()) == size.getX() - 1 && Math.abs(corner.getZ() - block.getZ()) == size.getZ() - 1;
        }
        return false;
    }

    //*Needs Fixing
    public Direction getFacing(BlockPos corner, BlockPos size, BlockPos block) {
        if (block.getX() > corner.getX() && block.getX() < corner.add(size.getX() - 1, 0, 0).getX() && block.getZ() == corner.getZ()) {
            return Direction.NORTH;
        }
        if (block.getZ() > corner.getZ() && block.getZ() < corner.add(0, 0, size.getZ() - 1).getZ() && block.getX() == corner.getX()) {
            return Direction.EAST;
        }
        if (block.getX() > corner.getX() && block.getX() < corner.add(size.getX() - 1, 0, 0).getX() && block.getZ() == corner.add(0, 0, size.getZ() - 1).getZ()) {
            return Direction.SOUTH;
        }
        if (block.getZ() > corner.getZ() && block.getZ() < corner.add(0, 0, size.getZ() - 1).getZ() && block.getX() == corner.add(size.getX() - 1, 0, 0).getX()) {
            return Direction.WEST;
        }
        return Direction.NORTH;
    }

    public Integer whichCorner(BlockPos corner, BlockPos block, BlockPos size) {
        if (corner == block) {
            return 1;
        }
        if (corner.getX() == block.getX() && block.getZ() - corner.getZ() == size.getZ() - 1 && corner.getY() == block.getY()) {
            return 4;
        }
        if (block.getX() - corner.getX() == size.getX() - 1 && block.getZ() == corner.getZ() && corner.getY() == block.getY()) {
            return 2;
        }
        if (block.getX() - corner.getX() == size.getX() - 1 && block.getZ() - corner.getZ() == size.getZ() - 1 && corner.getY() == block.getY()) {
            return 3;
        }
        if (corner.getX() == block.getX() && corner.getZ() == block.getZ() && block.getY() - corner.getY() == size.getY() - 1) {
            return 5;
        }
        if (corner.getX() == block.getX() && block.getZ() - corner.getZ() == size.getZ() - 1 && block.getY() - corner.getY() == size.getY() - 1) {
            return 8;
        }
        if (block.getX() - corner.getX() == size.getX() - 1 && block.getZ() == corner.getZ() && block.getY() - corner.getY() == size.getY() - 1) {
            return 6;
        }
        if (block.getX() - corner.getX() == size.getX() - 1 && block.getZ() - corner.getZ() == size.getZ() - 1 && block.getY() - corner.getY() == size.getY() - 1) {
            return 7;
        }
        return 0;
    }

    public Integer whichEdge(BlockPos corner, BlockPos block, BlockPos size) {
        if (corner.getY() == block.getY()) {
            return 1;
        }
        if (corner.getY() < block.getY() && block.getY() < corner.add(0, size.getY() - 1, 0).getY()) {
            if (corner.getX() - block.getX() == 0 && corner.getZ() - block.getZ() == 0) {
                return 3;
            } else if (Math.abs(corner.getX() - block.getX()) == size.getX() - 1 && corner.getZ() - block.getZ() == 0) {
                return 4;
            } else if (corner.getX() - block.getX() == 0 && Math.abs(corner.getZ() - block.getZ()) == size.getZ() - 1) {
                return 6;
            } else if (Math.abs(corner.getX() - block.getX()) == size.getX() - 1 && Math.abs(corner.getZ() - block.getZ()) == size.getZ() - 1) {
                return 5;
            }
        }
        if (block.getY() == corner.add(0, size.getY() - 1, 0).getY()) {
            return 2;
        }
        return 0;
    }
}
