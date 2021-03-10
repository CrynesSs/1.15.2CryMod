package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Interfaces.IFluidProvider;
import net.minecraft.fluid.Fluid;

public class FluidPipe implements IFluidProvider {

    @Override
    public Integer getTransferRate() {
        return null;
    }

    @Override
    public Integer getFluidAmount(Fluid fluid) {
        return null;
    }

    @Override
    public Integer getMaxStorage() {
        return null;
    }

    @Override
    public boolean hasFluid(Fluid fluid) {
        return false;
    }

    @Override
    public boolean canTakeFluid(Fluid fluid, Integer amount) {
        return false;
    }

    @Override
    public void fill(Fluid fluid, Integer amount) {

    }

    @Override
    public boolean isFluidProvider() {
        return false;
    }

    @Override
    public int maxFluidsAmount() {
        return 0;
    }
}
