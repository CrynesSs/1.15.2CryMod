package com.CrynesSs.RedstoneEnhancement.Util.Interfaces;

import net.minecraft.fluid.Fluid;

public interface IFluidProvider {
    Integer getTransferRate();

    Integer getFluidAmount(Fluid fluid);

    Integer getMaxStorage();

    boolean hasFluid(Fluid fluid);

    boolean canTakeFluid(Fluid fluid, Integer amount);

    void fill(Fluid fluid, Integer amount);

    boolean isFluidProvider();

    int maxFluidsAmount();
}
