package com.CrynesSs.RedstoneEnhancement.structures.City;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import javax.annotation.Nonnull;

public class CityConfig implements IFeatureConfig {
    public final ResourceLocation startPool;
    public final int size;

    public CityConfig(ResourceLocation startPool, int size) {
        this.startPool = startPool;
        this.size = size;
    }

    @Nonnull
    @Override
    public <T> Dynamic<T> serialize(@Nonnull DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("start_pool"), ops.createString(this.startPool.toString()), ops.createString("size"), ops.createInt(this.size))));
    }

    public static <T> CityConfig deserialize(Dynamic<T> data) {
        String s = data.get("start_pool").asString("");
        int i = data.get("size").asInt(15);
        return new CityConfig(new ResourceLocation(s), i);
    }

}
