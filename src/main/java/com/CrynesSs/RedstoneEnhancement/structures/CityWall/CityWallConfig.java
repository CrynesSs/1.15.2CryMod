package com.CrynesSs.RedstoneEnhancement.structures.CityWall;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import javax.annotation.Nonnull;

public class CityWallConfig implements IFeatureConfig {
    public final ResourceLocation startPool;
    public final int size;

    public CityWallConfig(ResourceLocation startPool, int size) {
        this.startPool = startPool;
        this.size = size;
    }

    public CityWallConfig(String startPool, int size) {
        this.startPool = new ResourceLocation(startPool);
        this.size = size;
    }

    @Nonnull
    @Override
    public <T> Dynamic<T> serialize(@Nonnull DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("start_pool"), ops.createString(this.startPool.toString()), ops.createString("size"), ops.createInt(this.size))));
    }

    public static <T> CityWallConfig deserialize(Dynamic<T> data) {
        String s = data.get("start_pool").asString("");
        int i = data.get("size").asInt(20);
        return new CityWallConfig(new ResourceLocation(RedstoneEnhancement.MOD_ID, "structures/city/houses"), 5);
    }
}
