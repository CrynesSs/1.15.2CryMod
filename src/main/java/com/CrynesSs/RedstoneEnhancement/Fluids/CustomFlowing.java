package com.CrynesSs.RedstoneEnhancement.Fluids;

import net.minecraftforge.fluids.ForgeFlowingFluid;

public class CustomFlowing extends ForgeFlowingFluid.Flowing {
    public CustomFlowing(Properties properties, int sourceHeight) {
        super(properties);
        this.setDefaultState(getDefaultState().with(LEVEL_1_8, sourceHeight - 1));
    }

}
