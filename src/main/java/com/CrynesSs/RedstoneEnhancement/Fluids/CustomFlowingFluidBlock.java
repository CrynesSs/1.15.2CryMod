package com.CrynesSs.RedstoneEnhancement.Fluids;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;

import java.util.function.Supplier;

public class CustomFlowingFluidBlock extends FlowingFluidBlock {
    public CustomFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

}
