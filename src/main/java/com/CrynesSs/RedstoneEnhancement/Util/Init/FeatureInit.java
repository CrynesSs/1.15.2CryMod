package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.structures.City.CityConfig;
import com.CrynesSs.RedstoneEnhancement.structures.City.CityStructure;
import com.CrynesSs.RedstoneEnhancement.structures.ExampleStructure.ExampleStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureInit {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, RedstoneEnhancement.MOD_ID);

    public static final RegistryObject<Structure<CityConfig>> CITY = FEATURES.register("city", () -> new CityStructure(CityConfig::deserialize));
    public static final RegistryObject<Structure<NoFeatureConfig>> EXAMPLE = FEATURES.register("example", () -> new ExampleStructure(NoFeatureConfig::deserialize));


    public static void addToBiomes() {
        //Add our structure to all biomes including other modded biomes
        //
        //You can filter to certain biomes based on stuff like temperature, scale, precipitation, mod id,
        //and if you use the BiomeDictionary, you can even check if the biome has certain tags like swamp or snowy.
        for (Biome biome : ForgeRegistries.BIOMES) {
            // All structures needs to be added by .addStructure AND .addFeature in order to spawn.
            //
            // .addStructure tells Minecraft that this biome can start the generation of the structure.
            // .addFeature tells Minecraft that the pieces of the structure can be made in this biome.
            //
            // Thus it is best practice to do .addFeature for all biomes and do .addStructure as well for
            // the biome you want the structure to spawn in. That way, the structure will only spawn in the
            // biomes you want but will not get cut off when generating if part of it goes into a non-valid biome.
            //
            //Note: If your mappings are out of data,
            //	Biome.addStructure will be Biome.func_225566_b_ ,
            //      Feature.withConfiguration will be Feature.func_225566_b_ ,
            //	ConfiguredFeature.withPlacement will be ConfiguredFeature.func_227228_a_ ,
            //	Placement.configure will be Placement.func_227446_a_

            biome.addStructure(FeatureInit.CITY.get()
                    .withConfiguration(new CityConfig(new ResourceLocation("village/plains/town_centers"), 1)));

            biome.addStructure(RedstoneEnhancement.HOUSE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

            //biome.addStructure(FeatureInit.EXAMPLE.get()
            //        .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

            //biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,FeatureInit.EXAMPLE.get()
            //        .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
            //        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureInit.CITY.get()
                    .withConfiguration(new CityConfig(new ResourceLocation("village/plains/town_centers"), 1))
                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, RedstoneEnhancement.HOUSE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            //biome.addStructure(com.CrynesSs.RedstoneEnhancement.Util.Init.FeatureInit.CITY.get().withConfiguration(new CityConfig(new ResourceLocation(""),6)));
        }
    }


}
