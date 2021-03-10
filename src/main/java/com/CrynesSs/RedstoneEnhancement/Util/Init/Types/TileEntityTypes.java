package com.CrynesSs.RedstoneEnhancement.Util.Init.Types;

import com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock.DoubleSlabTE;
import com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace.AlloyFurnaceTE;
import com.CrynesSs.RedstoneEnhancement.Machines.InlayingMaschine.InlayingMaschineTE;
import com.CrynesSs.RedstoneEnhancement.Machines.MetalExtruder.MetalExtruderTE;
import com.CrynesSs.RedstoneEnhancement.Machines.MetalFormer.MetalFormerTE;
import com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherTE;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Storages.GoldenHopperTE;
import com.CrynesSs.RedstoneEnhancement.Storages.SucccPipeTE;
import com.CrynesSs.RedstoneEnhancement.Storages.TransportPipeTileEntity;
import com.CrynesSs.RedstoneEnhancement.TileEntities.BigFurnaceTileEntity;
import com.CrynesSs.RedstoneEnhancement.TileEntities.CableTileEntity;
import com.CrynesSs.RedstoneEnhancement.Util.Init.BlockInit;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RedstoneEnhancement.MOD_ID);
    public static final RegistryObject<TileEntityType<CableTileEntity>> CABLE = TILE_ENTITY_TYPE.register("cable", () -> TileEntityType.Builder.create(CableTileEntity::new, BlockInit.LOW_VOLTAGE_CABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<BigFurnaceTileEntity>> BIG_FURNACE = TILE_ENTITY_TYPE.register("big_furnace", () -> TileEntityType.Builder.create(BigFurnaceTileEntity::new, BlockInit.FURNACE_CONTROLLER.get()).build(null));
    public static final RegistryObject<TileEntityType<TransportPipeTileEntity>> TPIPE = TILE_ENTITY_TYPE.register("transport_pipe", () -> TileEntityType.Builder.create(TransportPipeTileEntity::new, BlockInit.CLEAR_PIPE.get()).build(null));
    public static final RegistryObject<TileEntityType<SucccPipeTE>> SUCCPIPE = TILE_ENTITY_TYPE.register("succ_pipe", () -> TileEntityType.Builder.create(SucccPipeTE::new, BlockInit.SUCCC_PIPE.get()).build(null));
    public static final RegistryObject<TileEntityType<AlloyFurnaceTE>> ALLOY_FURNACE = TILE_ENTITY_TYPE.register("alloy_furnace", () -> TileEntityType.Builder.create(AlloyFurnaceTE::new, BlockInit.ALLOY_FURANCE.get()).build(null));
    public static final RegistryObject<TileEntityType<GoldenHopperTE>> GOLDEN_HOPPER = TILE_ENTITY_TYPE.register("golden_hopper", () -> TileEntityType.Builder.create(GoldenHopperTE::new, BlockInit.GOLDEN_HOPPER.get()).build(null));
    public static final RegistryObject<TileEntityType<OreCrusherTE>> ORE_CRUSHER = TILE_ENTITY_TYPE.register("ore_crusher", () -> TileEntityType.Builder.create(OreCrusherTE::new, BlockInit.ORE_CRUSHER.get()).build(null));
    public static final RegistryObject<TileEntityType<MetalFormerTE>> METAL_FORMER = TILE_ENTITY_TYPE.register("metal_former", () -> TileEntityType.Builder.create(MetalFormerTE::new, BlockInit.METAL_FORMER.get()).build(null));
    public static final RegistryObject<TileEntityType<MetalExtruderTE>> METAL_EXTRUDER = TILE_ENTITY_TYPE.register("metal_extruder", () -> TileEntityType.Builder.create(MetalExtruderTE::new, BlockInit.METAL_EXTRUDER.get()).build(null));
    public static final RegistryObject<TileEntityType<InlayingMaschineTE>> INLAYING_MASCHINE = TILE_ENTITY_TYPE.register("inlaying_machine", () -> TileEntityType.Builder.create(InlayingMaschineTE::new, BlockInit.INLAYING_MASCHINE.get()).build(null));
    public static final RegistryObject<TileEntityType<DoubleSlabTE>> DOUBLE_SLAB = TILE_ENTITY_TYPE.register("double_slab", () -> TileEntityType.Builder.create(DoubleSlabTE::new, BlockInit.DOUBLE_SLAB_BLOCK.get()).build(null));

}
