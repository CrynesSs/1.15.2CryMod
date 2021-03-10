package com.CrynesSs.RedstoneEnhancement.Util;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.*;

public class VanillaCode {
    public static ImmutableList<StructureProcessor> immutablelist2 = ImmutableList.of(
            new RuleStructureProcessor(ImmutableList.of(
                    new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_PATH), new BlockMatchRuleTest(Blocks.WATER), Blocks.OAK_PLANKS.getDefaultState()),
                    new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.GRASS_BLOCK.getDefaultState()),
                    new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_BLOCK), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()),
                    new RuleEntry(new BlockMatchRuleTest(Blocks.DIRT), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()))
            ));

    static {
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation("village/plains/streets"),
                new ResourceLocation("village/plains/terminators"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/corner_01", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/corner_02", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/corner_03", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/straight_01", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 4),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/straight_02", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 4),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/straight_03", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 7),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/straight_04", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 7),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/straight_05", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 3),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/straight_06", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 4),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/crossroad_01", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/crossroad_02", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 1),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/crossroad_03", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/crossroad_04", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/crossroad_05", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/crossroad_06", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 2),
                        new Pair<>(new SingleJigsawPiece("village/plains/streets/turn_01", immutablelist2, JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING), 3)),
                JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));

    }

}
