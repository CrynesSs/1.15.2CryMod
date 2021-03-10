package com.CrynesSs.RedstoneEnhancement.Storages;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ClearPipe extends AbstractTransportPipe {
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
    public static final IntegerProperty COLORINT = IntegerProperty.create("colorint", 0, Integer.MAX_VALUE);

    public ClearPipe() {
        super(Block.Properties.create(Material.GLASS)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .notSolid(), 0.25f);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE).with(UP, Boolean.FALSE).with(DOWN, Boolean.FALSE).with(COLOR, DyeColor.BLACK));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
        super.fillStateContainer(builder);
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        System.out.println("Block was replaced at " + pos);
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }


}
