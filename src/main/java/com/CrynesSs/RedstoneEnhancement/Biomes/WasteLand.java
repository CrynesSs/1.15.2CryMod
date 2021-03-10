package com.CrynesSs.RedstoneEnhancement.Biomes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class WasteLand extends Biome {

    public WasteLand(Builder biomeBuilder) {
        super(biomeBuilder);
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.BLAZE, 20, 2, 10));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.142f)));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.112f)));
        addCarver(GenerationStage.Carving.LIQUID, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.112f)));
        DefaultBiomeFeatures.addDeadBushes(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addSprings(this);
    }
}
