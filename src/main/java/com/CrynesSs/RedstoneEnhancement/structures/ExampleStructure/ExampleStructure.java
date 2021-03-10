package com.CrynesSs.RedstoneEnhancement.structures.ExampleStructure;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;

public class ExampleStructure extends Structure<NoFeatureConfig> {

    public ExampleStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Nonnull
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = 128;
        int j = 64;
        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 420691337);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }

    @Override
    public boolean canBeGenerated(@Nonnull BiomeManager biomeManagerIn, @Nonnull ChunkGenerator<?> generatorIn, @Nonnull Random randIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn) {
        ChunkPos chunkpos = this.getStartPositionForPosition(generatorIn, randIn, chunkX, chunkZ, 0, 0);
        return (chunkX == chunkpos.x && chunkZ == chunkpos.z) && generatorIn.hasStructure(biomeIn, this);
    }

    @Nonnull
    @Override
    public IStartFactory getStartFactory() {
        return ExampleStructure.Start::new;
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return RedstoneEnhancement.MOD_ID + ":example";
    }

    @Override
    public int getSize() {
        return 0;
    }

    public static class Start extends MarginedStructureStart {
        public Start(Structure<?> structure, int chunkX, int chunkZ, MutableBoundingBox p_i225821_4_, int structureId, long seed) {
            super(structure, chunkX, chunkZ, p_i225821_4_, structureId, seed);
        }


        public void init(@Nonnull ChunkGenerator<?> generator, @Nonnull TemplateManager templateManagerIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn) {
            BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            RedstoneEnhancement.LOGGER.log(Level.DEBUG, "Example at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
            ExamplePieces.addPieces(generator, templateManagerIn, blockpos, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }
}
