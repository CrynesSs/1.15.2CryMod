package com.CrynesSs.RedstoneEnhancement.Machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;

public class InductionHeatingCoil extends Block {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static ArrayList<String> canConnect = new ArrayList<>();

    public InductionHeatingCoil() {
        super(
                net.minecraft.block.Block.Properties.create(Material.IRON)
                        .hardnessAndResistance(7.5f, 6.125f)
                        .sound(SoundType.METAL)
                        //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                        .harvestLevel(2)
                        .harvestTool(ToolType.PICKAXE)
        );
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(POWERED, true));
        canConnect.add("lifeWire");
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING);
    }
}
