package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock.DoubleSlabBlock;
import com.CrynesSs.RedstoneEnhancement.Blocks.*;
import com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace.AlloyFurnace;
import com.CrynesSs.RedstoneEnhancement.Machines.BigFurnaceBrick;
import com.CrynesSs.RedstoneEnhancement.Machines.FurnaceController;
import com.CrynesSs.RedstoneEnhancement.Machines.HotAirOutlet;
import com.CrynesSs.RedstoneEnhancement.Machines.InductionHeatingCoil;
import com.CrynesSs.RedstoneEnhancement.Machines.InlayingMaschine.InlayingMaschineBlock;
import com.CrynesSs.RedstoneEnhancement.Machines.MetalExtruder.MetalExtruderBlock;
import com.CrynesSs.RedstoneEnhancement.Machines.MetalFormer.MetalFormerBlock;
import com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherBlock;
import com.CrynesSs.RedstoneEnhancement.OreBlocks.RubyOre;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Storages.ClearPipe;
import com.CrynesSs.RedstoneEnhancement.Storages.GoldenHopper;
import com.CrynesSs.RedstoneEnhancement.Storages.SucccPipe;
import com.CrynesSs.RedstoneEnhancement.cables.LowVoltageCable;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RedstoneEnhancement.MOD_ID);
    //*Blocks new Class for every Block
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<OreBlock> RUBY_ORE = BLOCKS.register("ruby_ore", RubyOre::new);
    public static final RegistryObject<Block> COBALD_BLOCK = BLOCKS.register("cobald_block", CobaldBlock::new);
    public static final RegistryObject<Block> BIG_FURNACE_BLOCK = BLOCKS.register("bigfurnacebrick", BigFurnaceBrick::new);
    public static final RegistryObject<FurnaceController> FURNACE_CONTROLLER = BLOCKS.register("furnace_controller", FurnaceController::new);
    public static final RegistryObject<Block> HOT_AIR_OUTLET = BLOCKS.register("hotairoutlet", HotAirOutlet::new);
    public static final RegistryObject<Block> INDUCTION_HEATING_COIL = BLOCKS.register("induction_heating_coil", InductionHeatingCoil::new);
    public static final RegistryObject<Block> LOW_VOLTAGE_CABLE = BLOCKS.register("low_voltage_cable", LowVoltageCable::new);
    public static final RegistryObject<Block> HIGH_TEMP_GLASS = BLOCKS.register("high_temp_glass", HighTemperatureGlassBlock::new);
    public static final RegistryObject<Block> CLEAR_PIPE = BLOCKS.register("clear_pipe", ClearPipe::new);
    public static final RegistryObject<Block> SUCCC_PIPE = BLOCKS.register("succc_pipe", SucccPipe::new);
    public static final RegistryObject<Block> ALLOY_FURANCE = BLOCKS.register("alloy_furnace", AlloyFurnace::new);
    public static final RegistryObject<Block> GOLDEN_HOPPER = BLOCKS.register("golden_hopper", GoldenHopper::new);
    public static final RegistryObject<Block> RED_MIST = BLOCKS.register("red_mist", RedMist::new);
    public static final RegistryObject<Block> MULTI_LOL = BLOCKS.register("lol", MultiBlockLol::new);
    public static final RegistryObject<Block> ORE_CRUSHER = BLOCKS.register("ore_crusher", OreCrusherBlock::new);
    public static final RegistryObject<Block> METAL_FORMER = BLOCKS.register("metal_former", MetalFormerBlock::new);
    public static final RegistryObject<Block> METAL_EXTRUDER = BLOCKS.register("metal_extruder", MetalExtruderBlock::new);
    public static final RegistryObject<Block> INLAYING_MASCHINE = BLOCKS.register("inlaying_machine", InlayingMaschineBlock::new);
    public static final RegistryObject<Block> TEMPERING_OVEN = BLOCKS.register("tempering_oven", InlayingMaschineBlock::new);
    public static final RegistryObject<Block> DOUBLE_SLAB_BLOCK = BLOCKS.register("double_slab", DoubleSlabBlock::new);
}
