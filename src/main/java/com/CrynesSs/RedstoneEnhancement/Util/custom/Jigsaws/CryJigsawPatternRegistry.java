package com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class CryJigsawPatternRegistry {
    public Map<ResourceLocation, CryJigsawPattern> getRegistry() {
        return registry;
    }

    private final Map<ResourceLocation, CryJigsawPattern> registry = Maps.newHashMap();

    public CryJigsawPatternRegistry() {
        this.register(CryJigsawPattern.EMPTY);
    }

    public void register(CryJigsawPattern pattern) {
        this.registry.put(pattern.getName(), pattern);
    }

    public CryJigsawPattern get(ResourceLocation name) {
        CryJigsawPattern jigsawpattern = this.registry.get(name);
        return jigsawpattern != null ? jigsawpattern : CryJigsawPattern.INVALID;
    }
}

