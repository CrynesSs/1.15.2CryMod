package com.CrynesSs.RedstoneEnhancement;

import com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock.DoubleSlabBlock;
import com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock.DoubleSlabModel;
import com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.InlayingMaschine.InlayingMaschineTileRenderer;
import com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.OreCrusher.OreCrusherTileRenderer;
import com.CrynesSs.RedstoneEnhancement.Entity.Robot.RobotEntityRender;
import com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace.AlloyFurnaceScreen;
import com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherScreen;
import com.CrynesSs.RedstoneEnhancement.Storages.ClearPipe;
import com.CrynesSs.RedstoneEnhancement.Storages.TPTERenderer;
import com.CrynesSs.RedstoneEnhancement.Util.Init.*;
import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.ContainerTypes;
import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.EntityTypeInit;
import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import com.CrynesSs.RedstoneEnhancement.Util.Packages.Networking;
import com.CrynesSs.RedstoneEnhancement.Util.StructureCheck;
import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.RoadConstructor;
import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import com.CrynesSs.RedstoneEnhancement.structures.House.HousePieces;
import com.CrynesSs.RedstoneEnhancement.structures.House.HouseStructure;
import com.CrynesSs.RedstoneEnhancement.structures.MBFRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
//*This is the Name my Mod has MOD_ID
@Mod("redsenhance")
@Mod.EventBusSubscriber(modid = RedstoneEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RedstoneEnhancement {
    public static int i = 0;
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "redsenhance";
    public static RedstoneEnhancement instance;
    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final List<AbstractStructure> STRUCTURES = new ArrayList<>();

    public RedstoneEnhancement() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> DataManager::onClientInit);
        GeckoLib.initialize();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested in
        ItemInit.ITEMS.register(modEventBus);
        FluidInit.FLUIDS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        System.out.println("KEKW2");
        FeatureInit.FEATURES.register(modEventBus);
        TileEntityTypes.TILE_ENTITY_TYPE.register(modEventBus);
        BiomeInit.BIOMES.register(modEventBus);
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        VillagerProfessionInit.POI.register(modEventBus);
        VillagerProfessionInit.PROFESSIONS.register(modEventBus);
        ContainerTypes.CONTAINER_TYPES.register(modEventBus);
        RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        instance = this;
        RoadConstructor constructor = new RoadConstructor(3, 3, new ChunkPos(100, 100), new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets"));
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void setup(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        FeatureInit.addToBiomes();
        DoubleSlabModel model = new DoubleSlabModel();
        model.combineModels(new DoubleSlabBlock((SlabBlock) Blocks.COBBLESTONE_SLAB.getBlock(), SlabType.BOTTOM));
    }


    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("GOT game setting{} ", event.getMinecraftSupplier().get().gameSettings);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ROBOT_ENTITY_TYPE.get(), RobotEntityRender::new);
        try {
            OBJModel model = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(
                    new ResourceLocation(MOD_ID, "models/obj/anvil.obj"), true, true, false, false, "anvil.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(3, 4, 1, 3));
        for (Iterator<Integer> it = integers.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
            System.out.println(it.next());
            System.out.println("TESTteteteette");
        }

        bindScreens();
        setRenderLayers();
        bindTileEntityRenderers();
        //j = IlightReader k=BlockPos h=BlockState
        initColorHandler();
    }


    public void setRenderLayers() {
        RenderTypeLookup.setRenderLayer(BlockInit.HIGH_TEMP_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CLEAR_PIPE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.RED_MIST.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.ORE_CRUSHER.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.INLAYING_MASCHINE.get(), RenderType.getTranslucent());
    }

    public void bindTileEntityRenderers() {
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.TPIPE.get(), TPTERenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.BIG_FURNACE.get(), MBFRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.ORE_CRUSHER.get(), OreCrusherTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.INLAYING_MASCHINE.get(), InlayingMaschineTileRenderer::new);
    }

    public void bindScreens() {
        ScreenManager.registerFactory(ContainerTypes.ALLOY_FURNACE_C.get(), AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(ContainerTypes.ORE_CRUSHER_CONTAINER.get(), OreCrusherScreen::new);
    }

    public void initColorHandler() {
        BlockColors colors = Minecraft.getInstance().getBlockColors();
        colors.register((state, blockaccess, pos, tintindex) -> {
            DyeColor color = state.get(ClearPipe.COLOR);
            return color.getColorValue();
        }, BlockInit.CLEAR_PIPE.get());

    }

    @SubscribeEvent
    public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
        System.out.println("KEKW1");
        BiomeInit.registerBiomes();
    }

    public static Structure<NoFeatureConfig> HOUSE = new HouseStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType HOUSE_PIECE = HousePieces.Piece::new;

    public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event) {

        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        HOUSE.setRegistryName(new ResourceLocation(RedstoneEnhancement.MOD_ID, "house"));  // the string here must be the same as in HouseStructure.getStructureName
        registry.register(HOUSE);

        Registry.register(Registry.STRUCTURE_PIECE, "house", HOUSE_PIECE);


        RedstoneEnhancement.LOGGER.log(Level.INFO, "features/structures registered.");
    }

    //*This is a new Itemgroup to display all the Items that are associated with this Mod
    //*It needs to be mentioned in the en_us.json
    public static final ItemGroup TAB = new ItemGroup("RedstoneEnhancements") {
        @Override
        public ItemStack createIcon() {
            //*This Sets the Item that will be in the Thumbnail of the Item Tab
            return new ItemStack(ItemInit.RUBY.get());
        }
    };
    public static final ItemGroup TAB2 = new ItemGroup("RedstoneEnhancements2") {
        @Override
        public ItemStack createIcon() {
            //*This Sets the Item that will be in the Thumbnail of the Item Tab
            return new ItemStack(ItemInit.RUBY.get());
        }
    };
    public static final ItemGroup Maschines = new ItemGroup("Machines") {
        @Override
        public ItemStack createIcon() {
            //*This Sets the Item that will be in the Thumbnail of the Item Tab
            return new ItemStack(ItemInit.ALLOY_FURNACE_ITEM.get());
        }
    };
    public static final ItemGroup MATERIALS = new ItemGroup("Materials") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.COPPER_INGOT.get());
        }
    };

    @SubscribeEvent
    public static void ItemCraftEvent(PlayerEvent.ItemCraftedEvent event) {
        System.out.println("Item Crafted : Itemname " + Objects.requireNonNull(event.getCrafting().getItem().getRegistryName()).toString());
    }

    public static void LivingDamageevent(final LivingDamageEvent event) {
        LivingEntity entity = (LivingEntity) event.getSource().getTrueSource();
        if (entity instanceof PlayerEntity) {
            PlayerEntity entity1 = (PlayerEntity) entity;
            entity1.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 2, 2));
        }


    }

    @SubscribeEvent
    public static void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            StructureCheck.checkStructure(event.getPos(), event.getPlacedBlock().getBlock(), event.getWorld().getWorld(), (PlayerEntity) event.getEntity());
        }
        if (i == 0) {
            List<Block> blockList = new ArrayList<>();
            List<Item> itemList = new ArrayList<>();
            BlockInit.BLOCKS.getEntries().forEach(k -> {
                blockList.add(k.get());
            });
            Arrays.stream(Blocks.class.getFields()).forEach(k -> {
                try {
                    blockList.add((Block) k.get(Block.class));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            Arrays.stream(Items.class.getFields()).forEach(k -> {
                try {
                    itemList.add((Item) k.get(Item.class));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            i++;
            System.out.println(blockList.size() + "   " + itemList.size());
            List<Item> forgeItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
            System.out.println(forgeItems.size());
        }

    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        List<AbstractStructure> toRemove = new ArrayList<>();
        STRUCTURES.forEach(s -> {
            if (s.getBlocksinStructure() == null) {
                System.out.println("Structure at " + s.getCorner() + " has null blocks");
            }
            if (s.getBlocksinStructure().containsKey(event.getPos())) {
                System.out.println("Removing one Structure");
                s.remove((World) event.getWorld());
                toRemove.add(s);
            }
        });
        STRUCTURES.removeIf(k -> k.getBlocksinStructure().containsKey(event.getPos()));
        toRemove.forEach(STRUCTURES::remove);
    }


}
