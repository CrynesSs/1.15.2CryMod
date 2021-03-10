package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.Fluids.CustomFlowing;
import com.CrynesSs.RedstoneEnhancement.Fluids.CustomSource;
import com.CrynesSs.RedstoneEnhancement.Fluids.FluidProperties;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class FluidInit {
    public static final ResourceLocation MOLTEN_METAL_STILL_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "blocks/molten_metal_still");
    public static final ResourceLocation MOLTEN_METAL_FLOWING_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "blocks/molten_metal_flowing");
    public static final ResourceLocation MOLTEN_METAL_OVERLAY_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "blocks/molten_metal_overlay");

    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, RedstoneEnhancement.MOD_ID);
    //*Fluids
    public static final RegistryObject<FlowingFluid> MOLTEN_METAL_SOURCE = FLUIDS.register("molten_metal_source", () -> new CustomSource(FluidInit.MOLTEN_METAL_PROPERTIES, 8));
    public static final RegistryObject<FlowingFluid> MOLTEN_METAL_FLOWING = FLUIDS.register("molten_metal_flowing", () -> new CustomFlowing(FluidInit.MOLTEN_METAL_PROPERTIES, 8));

    public static final FluidProperties MOLTEN_METAL_PROPERTIES = (FluidProperties) new FluidProperties(() -> MOLTEN_METAL_SOURCE.get(), () -> MOLTEN_METAL_FLOWING.get(),
            FluidAttributes.builder(MOLTEN_METAL_STILL_RL, MOLTEN_METAL_FLOWING_RL)
                    .luminosity(10).rarity(Rarity.RARE).sound(SoundEvents.BLOCK_LAVA_AMBIENT).overlay(MOLTEN_METAL_OVERLAY_RL), 40)
            .block(() -> FluidInit.MOLTEN_METAL_BLOCK.get())
            .bucket(() -> ItemInit.MOLTEN_METAL_BUCKET.get())
            .slopeFindDistance(0)
            .levelDecreasePerBlock(2);
    public static final Block.Properties MOLTEN_METAL_PROPS = Block.Properties.create(Material.LAVA).speedFactor(0.2f).jumpFactor(0.4f).noDrops().doesNotBlockMovement().hardnessAndResistance(100.0f).lightValue(6);
    public static final RegistryObject<FlowingFluidBlock> MOLTEN_METAL_BLOCK = BlockInit.BLOCKS.register("molten_metal",
            () -> new FlowingFluidBlock(() -> FluidInit.MOLTEN_METAL_SOURCE.get(),
                    MOLTEN_METAL_PROPS));
}
