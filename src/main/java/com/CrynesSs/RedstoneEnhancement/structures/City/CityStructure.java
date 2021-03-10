package com.CrynesSs.RedstoneEnhancement.structures.City;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.Init.FeatureInit;
import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.RoadConstructor;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class CityStructure extends Structure<CityConfig> {
    public CityStructure(Function<Dynamic<?>, ? extends CityConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public static final ArrayList<ChunkPos> GENERATED = new ArrayList<>();
    public static final HashMap<ChunkPos, RoadConstructor> ROADMAP = new HashMap<>();
    public static final HashMap<ChunkPos, ArrayList<ChunkPos>> CHUNKLIST = new HashMap<>();

    //TODO GENERATE CLUSTERS OF STRUCTURES
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
        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 123567687);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }

    @Override
    public boolean canBeGenerated(@Nonnull BiomeManager biomeManagerIn, @Nonnull ChunkGenerator<?> generatorIn, @Nonnull Random randIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn) {
        ChunkPos chunkpos = this.getStartPositionForPosition(generatorIn, randIn, chunkX, chunkZ, 0, 0);

        if (generatorIn.hasStructure(biomeIn, this) && withIn16ChunkDistance(chunkX, chunkZ)) {
            System.out.println(chunkX + "  " + chunkZ + "    " + chunkpos + "   " + "This is Sparta1 " + GENERATED.toString());

            return true;
        } else if ((chunkX == chunkpos.x && chunkZ == chunkpos.z) && generatorIn.hasStructure(biomeIn, this) && (furtherThan16ChunksAway(chunkX, chunkZ) || GENERATED.isEmpty())) {
            System.out.println(chunkX + "  " + chunkZ + "    " + chunkpos + "   " + "This is Sparta2 " + GENERATED.toString());
            GENERATED.add(chunkpos);
            CHUNKLIST.put(chunkpos, new ArrayList<>());
            CHUNKLIST.get(chunkpos).add(chunkpos);
            ROADMAP.put(chunkpos, new RoadConstructor(3, 3, chunkpos, new ResourceLocation(RedstoneEnhancement.MOD_ID, "city/streets")));
            return true;
        }
        return false;
    }

    public boolean withIn16ChunkDistance(int chunkX, int chunkZ) {
        AtomicBoolean withinDistance = new AtomicBoolean(false);
        GENERATED.forEach(k -> {
            if (((Math.abs(k.x - chunkX) == 17) && ((k.z - chunkZ) == 0)) || ((Math.abs(k.x - chunkX) == 17) && (Math.abs(k.z - chunkZ) == 17)) || (Math.abs(k.x - chunkX) == 0) && (Math.abs(k.z - chunkZ) == 17)) {
                System.out.println("Satellite Structure to Structure at " + k.toString());
                CHUNKLIST.get(k).add(new ChunkPos(chunkX, chunkZ));
                withinDistance.set(true);
            }
            if ((Math.abs(k.x - chunkX) == 8) && Math.abs((k.z - chunkZ)) == 8) {
                System.out.println("Satellite Structure to Structure at " + k.toString());
                CHUNKLIST.get(k).add(new ChunkPos(chunkX, chunkZ));
                withinDistance.set(true);
            }

        });

        return withinDistance.get();
    }

    public boolean furtherThan16ChunksAway(int chunkX, int chunkZ) {
        AtomicBoolean withinDistance = new AtomicBoolean(false);
        GENERATED.forEach(k -> {
            if (((Math.abs(k.x - chunkX) >= 17) && (Math.abs(k.z - chunkZ) >= 0)) || ((Math.abs(k.x - chunkX) >= 17) && (Math.abs(k.z - chunkZ) >= 17)) || (Math.abs(k.x - chunkX) >= 0) && (Math.abs(k.z - chunkZ) >= 17)) {
                System.out.println("Within 16 chunks " + withinDistance.toString() + "chunkX: " + chunkX + "chunkZ: " + chunkZ);
                withinDistance.set(true);
            }
        });
        return withinDistance.get();
    }

    @Nonnull
    @Override
    public IStartFactory getStartFactory() {
        return CityStructure.Start::new;
    }


    @Nonnull
    @Override
    public String getStructureName() {
        return RedstoneEnhancement.MOD_ID + ":city";
    }

    @Override
    public int getSize() {
        return 25;
    }

    public static class Start extends MarginedStructureStart {
        public Start(Structure<?> structure, int chunkX, int chunkZ, MutableBoundingBox p_i225821_4_, int structureId, long seed) {
            super(structure, chunkX, chunkZ, p_i225821_4_, structureId, seed);
        }


        public void init(ChunkGenerator<?> generator, @Nonnull TemplateManager templateManagerIn, int chunkX, int chunkZ, @Nonnull Biome biomeIn) {
            CityConfig cityConfig = generator.getStructureConfig(biomeIn, FeatureInit.CITY.get());
            BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            if (cityConfig == null) {
                System.out.println("City config is null");
                return;
            }
            RedstoneEnhancement.LOGGER.log(Level.DEBUG, "City at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
            CityPieces.addPieces(generator, templateManagerIn, blockpos, this.components, this.rand, cityConfig);
            this.recalculateStructureSize();
        }
    }
}
