package com.CrynesSs.RedstoneEnhancement.Util.Init.Types;

import com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace.AlloyFurnaceContainer;
import com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherContainer;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Storages.GoldenHopperContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, RedstoneEnhancement.MOD_ID);
    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE_C = CONTAINER_TYPES.register("alloy_furnace", () -> IForgeContainerType.create(AlloyFurnaceContainer::new));
    public static final RegistryObject<ContainerType<GoldenHopperContainer>> GOLDEN_HOPPER_CONTAINER = CONTAINER_TYPES.register("golden_hopper", () -> IForgeContainerType.create(GoldenHopperContainer::new));
    public static final RegistryObject<ContainerType<OreCrusherContainer>> ORE_CRUSHER_CONTAINER = CONTAINER_TYPES.register("ore_crusher", () -> IForgeContainerType.create(OreCrusherContainer::new));
}
