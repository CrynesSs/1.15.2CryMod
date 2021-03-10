package com.CrynesSs.RedstoneEnhancement.structures.City;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.CryJigsawManager;
import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.CryJigsawPattern;
import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.CrySingleJigsawPiece;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.*;

import java.util.List;
import java.util.Locale;

public class CityPieces {

    public static void addPieces(ChunkGenerator<?> chunkGeneratorIn, TemplateManager templateManagerIn, BlockPos structurePos, List<StructurePiece> structurePieces, SharedSeedRandom sharedSeedRandomIn, CityConfig cityConfigIn) {
        CryJigsawManager.addPieces(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/centers"), 8, City::new, chunkGeneratorIn, templateManagerIn, structurePos, structurePieces, sharedSeedRandomIn);
    }

    public static void init() {
        System.out.println("Registering City Patterns");
        ImmutableList<StructureProcessor> BlackToWhiteTerracotta = ImmutableList.of(
                new RuleStructureProcessor(ImmutableList.of(
                        new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_PATH), new BlockMatchRuleTest(Blocks.WATER), Blocks.OAK_PLANKS.getDefaultState()),
                        new RuleEntry(new RandomBlockMatchRuleTest(Blocks.BLACK_CONCRETE, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.WHITE_TERRACOTTA.getDefaultState()),
                        new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_BLOCK), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()),
                        new RuleEntry(new BlockMatchRuleTest(Blocks.DIRT), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()))
                ));
        CryJigsawManager.REGISTRY.register(new CryJigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/centers"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets/cross_road_01").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, true, CrySingleJigsawPiece.horizontalDirections), 5)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        CryJigsawManager.REGISTRY.register(new CryJigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets/straight_01").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, false, ImmutableList.of(Direction.NORTH, Direction.SOUTH)), 1),
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets/straight_02").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, false, ImmutableList.of(Direction.NORTH, Direction.SOUTH)), 1),
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets/t_road_01").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, false, ImmutableList.of(Direction.SOUTH, Direction.EAST, Direction.WEST)), 1),
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets/corner_left_01").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, true, ImmutableList.of(Direction.SOUTH, Direction.EAST)), 1),
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets/cross_road_01").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, true, CrySingleJigsawPiece.horizontalDirections), 1)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        CryJigsawManager.REGISTRY.register(new CryJigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/houses"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new CrySingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/houses/small_house_01").toString(), BlackToWhiteTerracotta, JigsawPattern.PlacementBehaviour.RIGID, false, CrySingleJigsawPiece.horizontalDirections), 5)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        System.out.println("Registered Jigsaw Patterns");
    }

    static {


    }

    public static class City extends AbstractVillagePiece {
        public City(TemplateManager templateManagerIn, JigsawPiece jigsawPieceIn, BlockPos posIn, int groundLevelDelta, Rotation rotationIn, MutableBoundingBox boundsIn) {
            super(CityType, templateManagerIn, jigsawPieceIn, posIn, groundLevelDelta, rotationIn, boundsIn);
        }

        public City(TemplateManager p_i50891_1_, CompoundNBT p_i50891_2_) {
            super(p_i50891_1_, p_i50891_2_, CityType);
        }
    }

    public static final IStructurePieceType CityType = register(City::new, "city");

    static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}
