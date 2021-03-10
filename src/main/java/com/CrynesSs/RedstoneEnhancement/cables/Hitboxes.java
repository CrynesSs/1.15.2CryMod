package com.CrynesSs.RedstoneEnhancement.cables;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

public class Hitboxes {
    public static final VoxelShape STRAIGHT = Block.makeCuboidShape(4, 4, 0, 12, 12, 16);
    public static final VoxelShape TCONNECTION = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 4, 0, 12, 12, 16), Block.makeCuboidShape(4, 4, 4, 16, 12, 12), IBooleanFunction.OR);
    public static final VoxelShape CORNER = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 4, 0, 12, 12, 4), Block.makeCuboidShape(4, 4, 4, 16, 12, 12), IBooleanFunction.OR);
    public static final VoxelShape TWITHARM = Stream.of(
            Block.makeCuboidShape(4, 4, 0, 12, 12, 16),
            Block.makeCuboidShape(0, 4, 4, 12, 12, 12),
            Block.makeCuboidShape(4, 4, 4, 12, 16, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public static final VoxelShape ALLCABLE = Stream.of(
            Block.makeCuboidShape(4, 4, 0, 12, 12, 16),
            Block.makeCuboidShape(0, 4, 4, 16, 12, 12),
            Block.makeCuboidShape(4, 0, 4, 12, 16, 12)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();
    public static final VoxelShape CROSSARM = Stream.of(
            Block.makeCuboidShape(4, 4, 0, 12, 12, 16),
            Block.makeCuboidShape(0, 4, 4, 16, 12, 12),
            Block.makeCuboidShape(4, 4, 4, 12, 16, 12)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();
    public static final VoxelShape XYZ = Stream.of(
            Block.makeCuboidShape(4, 4, 0, 12, 12, 12),
            Block.makeCuboidShape(0, 4, 4, 12, 12, 12),
            Block.makeCuboidShape(4, 4, 4, 12, 16, 12)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    public static final VoxelShape CROSS = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 4, 0, 12, 12, 16), Block.makeCuboidShape(0, 4, 4, 16, 12, 12), IBooleanFunction.OR);


}
