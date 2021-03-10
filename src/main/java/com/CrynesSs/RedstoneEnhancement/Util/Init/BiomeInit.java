package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.Biomes.WasteLand;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, RedstoneEnhancement.MOD_ID);

    public static final RegistryObject<Biome> WASTELAND = BIOMES.register("wasteland", () ->
            new WasteLand(
                    new Biome.Builder()
                            .precipitation(Biome.RainType.RAIN)
                            .scale(1.2f)
                            .temperature(-0.5f)
                            .surfaceBuilder(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.STONE.getDefaultState(), Blocks.MAGMA_BLOCK.getDefaultState(), Blocks.CLAY.getDefaultState()))
                            .category(Biome.Category.PLAINS)
                            .downfall(0.3f)
                            .depth(0.125f)
                            .parent(null)
                            .waterColor(3305384)
                            .waterFogColor(3305384)
            ));

    public static void registerBiomes() {
        registerBiome(WASTELAND.get(), BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD, BiomeDictionary.Type.OVERWORLD);
    }

    private static void registerBiome(Biome biome, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);

    }


}
