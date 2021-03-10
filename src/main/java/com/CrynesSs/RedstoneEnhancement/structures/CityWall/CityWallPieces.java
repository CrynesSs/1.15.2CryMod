package com.CrynesSs.RedstoneEnhancement.structures.CityWall;

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
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.*;

import java.util.List;

public class CityWallPieces {
    public static final ImmutableList<StructureProcessor> immutablelist1 = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.getDefaultState()))));
    private static final ResourceLocation houses = new ResourceLocation(RedstoneEnhancement.MOD_ID, "structures/city/houses");
    private static final ResourceLocation centers = new ResourceLocation(RedstoneEnhancement.MOD_ID, "structures/city/centers");

    public static void addPieces(ChunkGenerator<?> chunkGeneratorIn, TemplateManager templateManagerIn, BlockPos p_214838_2_, List<StructurePiece> structurePieces, SharedSeedRandom sharedSeedRandomIn, CityWallConfig cityWallConfigIn) {
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(RedstoneEnhancement.MOD_ID, "structures/city/houses"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(houses.toString() + "town_house_medium_1", immutablelist1), 5)


                ),
                JigsawPattern.PlacementBehaviour.RIGID
        ));


        JigsawManager.addPieces(cityWallConfigIn.startPool, cityWallConfigIn.size, CityWallPieces.City::new, chunkGeneratorIn, templateManagerIn, p_214838_2_, structurePieces, sharedSeedRandomIn);
    }


    public static class City extends AbstractVillagePiece {

        public City(TemplateManager templateManager, CompoundNBT nbt) {
            super(templateManager, nbt, com.CrynesSs.RedstoneEnhancement.Util.Interfaces.IStructurePieceType.CITY);
        }

        public City(TemplateManager templateManagerIn, JigsawPiece jigsawPieceIn, BlockPos posIn, int groundLevelDelta, Rotation rotationIn, MutableBoundingBox boundsIn) {
            super(com.CrynesSs.RedstoneEnhancement.Util.Interfaces.IStructurePieceType.CITY, templateManagerIn, jigsawPieceIn, posIn, groundLevelDelta, rotationIn, boundsIn);
        }
    }

}
