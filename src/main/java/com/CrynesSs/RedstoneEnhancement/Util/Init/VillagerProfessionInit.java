package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class VillagerProfessionInit {
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, RedstoneEnhancement.MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, RedstoneEnhancement.MOD_ID);

    public static final RegistryObject<PointOfInterestType> WOODCHOPPER = POI.register("logger",
            () -> new PointOfInterestType("logger", PointOfInterestType.getAllStates(BlockInit.COBALD_BLOCK.get()), 0, 1));
    public static final RegistryObject<VillagerProfession> WOODCUTTER = PROFESSIONS.register("logger",
            () -> new VillagerProfession("logger", WOODCHOPPER.get(), ImmutableSet.<Item>builder().build(), ImmutableSet.<Block>builder().build(), SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER));

}


