package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.Blocks.BlockItemBase;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Tools.RubyPickaxe;
import com.CrynesSs.RedstoneEnhancement.Util.Enums.ModItemTiers;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RedstoneEnhancement.MOD_ID);


    //*BlockItems
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(BlockInit.RUBY_BLOCK.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore", () -> new BlockItemBase(BlockInit.RUBY_ORE.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> COBLAD_BLOCK_ITEM = ITEMS.register("cobald_block", () -> new BlockItemBase(BlockInit.COBALD_BLOCK.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> BIG_FURNACE_BLOCK_ITEM = ITEMS.register("bigfurnacebrick", () -> new BlockItemBase(BlockInit.BIG_FURNACE_BLOCK.get(), RedstoneEnhancement.TAB2));
    public static final RegistryObject<Item> FURNACE_CONTROLLER_ITEM = ITEMS.register("furnace_controller", () -> new BlockItemBase(BlockInit.FURNACE_CONTROLLER.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> HOT_AIR_OUTLET_ITEM = ITEMS.register("hotairoutlet", () -> new BlockItemBase(BlockInit.HOT_AIR_OUTLET.get(), RedstoneEnhancement.TAB2));
    public static final RegistryObject<Item> INDUCTION_HEATING_COIL_ITEM = ITEMS.register("induction_heating_coil", () -> new BlockItemBase(BlockInit.INDUCTION_HEATING_COIL.get(), RedstoneEnhancement.TAB2));
    public static final RegistryObject<Item> LOW_VOLTAGE_CABLE_ITEM = ITEMS.register("low_voltage_cable", () -> new BlockItemBase(BlockInit.LOW_VOLTAGE_CABLE.get(), RedstoneEnhancement.TAB2));
    public static final RegistryObject<Item> HIGH_TEMP_GLASS_ITEM = ITEMS.register("high_temp_glass", () -> new BlockItemBase(BlockInit.HIGH_TEMP_GLASS.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> CLEAR_PIPE_ITEM = ITEMS.register("clear_pipe", () -> new BlockItemBase(BlockInit.CLEAR_PIPE.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> SUCCC_PIPE_ITEM = ITEMS.register("succc_pipe", () -> new BlockItemBase(BlockInit.SUCCC_PIPE.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> ALLOY_FURNACE_ITEM = ITEMS.register("alloy_furnace", () -> new BlockItemBase(BlockInit.ALLOY_FURANCE.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> GOLDEN_HOPPER_ITEM = ITEMS.register("golden_hopper", () -> new BlockItemBase(BlockInit.GOLDEN_HOPPER.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> RED_MIST_ITEM = ITEMS.register("red_mist", () -> new BlockItemBase(BlockInit.RED_MIST.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> MULTI_LOL_ITEM = ITEMS.register("lol", () -> new BlockItemBase(BlockInit.MULTI_LOL.get(), RedstoneEnhancement.TAB));
    public static final RegistryObject<Item> ORE_CRUSHER_ITEM = ITEMS.register("ore_crusher", () -> new BlockItemBase(BlockInit.ORE_CRUSHER.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> INLAYING_MASCHINE_ITEM = ITEMS.register("inlaying_machine", () -> new BlockItemBase(BlockInit.INLAYING_MASCHINE.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> METAL_FORMER_ITEM = ITEMS.register("metal_former", () -> new BlockItemBase(BlockInit.METAL_FORMER.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> METAL_EXTRUDER_ITEM = ITEMS.register("metal_extruder", () -> new BlockItemBase(BlockInit.METAL_EXTRUDER.get(), RedstoneEnhancement.Maschines));
    public static final RegistryObject<Item> TEMPERING_OVEN_ITEM = ITEMS.register("tempering_oven", () -> new BlockItemBase(BlockInit.TEMPERING_OVEN.get(), RedstoneEnhancement.Maschines));

    //*Items without Block
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    //public static final RegistryObject<Item> NBT_ITEM =                       ITEMS.register("nbtitem",()-> new ItemNBT(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> EXAMPLE = ITEMS.register("example", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> EMERALD_DUST = ITEMS.register("emerald_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> RUBY_DUST = ITEMS.register("ruby_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> BRONZE_DUST = ITEMS.register("bronze_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> ALUMINIUM_DUST = ITEMS.register("aluminium_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> STEEL_DUST = ITEMS.register("steel_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));
    public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust", () -> new Item(new Item.Properties().group(RedstoneEnhancement.MATERIALS)));

    //*BucketItems
    public static final RegistryObject<BucketItem> MOLTEN_METAL_BUCKET = ITEMS.register("molten_metal_bucket",
            () -> new BucketItem(FluidInit.MOLTEN_METAL_SOURCE,
                    new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(RedstoneEnhancement.TAB2).maxStackSize(1)));

    //*Tools
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new RubyPickaxe(ModItemTiers.RUBYPICKAXE, 25, 5.0f, new Item.Properties().group(RedstoneEnhancement.TAB)));

}
