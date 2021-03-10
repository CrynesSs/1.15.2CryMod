package com.CrynesSs.RedstoneEnhancement.Fluids;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.StateContainer;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;

public class CustomSource extends ForgeFlowingFluid.Source {
    public CustomSource(Properties properties, @Nullable Integer sourceHeight) {
        super(properties);
        if (sourceHeight != null && sourceHeight > 0) {
            this.setDefaultState(this.getDefaultState().with(LEVEL_1_8, sourceHeight));
        } else {
            this.setDefaultState(this.getDefaultState().with(LEVEL_1_8, 8));
        }
    }

    @Override
    public int getLevel(IFluidState state) {
        return state.get(LEVEL_1_8);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
        super.fillStateContainer(builder);
        builder.add(LEVEL_1_8);
    }
}
