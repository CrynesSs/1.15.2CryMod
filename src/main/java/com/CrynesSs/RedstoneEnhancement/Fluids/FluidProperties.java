package com.CrynesSs.RedstoneEnhancement.Fluids;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class FluidProperties extends ForgeFlowingFluid.Properties {
    public FluidProperties(Supplier<? extends Fluid> still, Supplier<? extends Fluid> flowing, FluidAttributes.Builder attributes, int fluidTickRate) {
        super(still, flowing, attributes);
        try {
            Field f1 = ForgeFlowingFluid.Properties.class.getDeclaredField("tickRate");
            f1.setAccessible(true);
            f1.setInt(this, fluidTickRate);
            f1.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
