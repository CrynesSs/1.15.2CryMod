package com.CrynesSs.RedstoneEnhancement.structures.ExampleStructure;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.*;

import java.util.List;
import java.util.Locale;

public class ExamplePieces {
    public static void addPieces(ChunkGenerator<?> chunkGeneratorIn, TemplateManager templateManagerIn, BlockPos structurePos, List<StructurePiece> structurePieces, SharedSeedRandom sharedSeedRandomIn) {
        JigsawManager.addPieces(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/base"), 4, ExamplePiece::new, chunkGeneratorIn, templateManagerIn, structurePos, structurePieces, sharedSeedRandomIn);
    }

    static {
        ImmutableList<StructureProcessor> transformStone = ImmutableList.of(
                new RuleStructureProcessor(ImmutableList.of(
                        new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_PATH), new BlockMatchRuleTest(Blocks.WATER), Blocks.OAK_PLANKS.getDefaultState()),
                        new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.1f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.getDefaultState())
                ))
        );
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/base"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/base/examplebase").toString(), transformStone), 5)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));

        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/streets"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/streets/examplecorner").toString(), transformStone), 1),
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/streets/examplestraight").toString(), transformStone), 1),
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/streets/examplethreeway").toString(), transformStone), 1)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/houses"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/houses/examplehouse").toString(), transformStone), 1)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/decors"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/decor/exampledecor_01").toString(), transformStone), 1),
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(RedstoneEnhancement.MOD_ID, "example/decor/exampledecor_02").toString(), transformStone), 1)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        System.out.println("Registered Jigsaw Patterns");
    }

    public static class ExamplePiece extends AbstractVillagePiece {
        public ExamplePiece(TemplateManager templateManagerIn, JigsawPiece jigsawPieceIn, BlockPos posIn, int groundLevelDelta, Rotation rotationIn, MutableBoundingBox boundsIn) {
            super(EXAMPLETYPE, templateManagerIn, jigsawPieceIn, posIn, groundLevelDelta, rotationIn, boundsIn);
        }

        public ExamplePiece(TemplateManager p_i50891_1_, CompoundNBT p_i50891_2_) {
            super(p_i50891_1_, p_i50891_2_, EXAMPLETYPE);
        }
    }

    public static final IStructurePieceType EXAMPLETYPE = register(ExamplePiece::new, "example");

    static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}
