package com.CrynesSs.RedstoneEnhancement.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompoundMetalBlock extends Block {

    private final Hardness hardness;
    private final Map<Ingredient, Integer> mixture = new HashMap<>();
    private final int color;

    public CompoundMetalBlock(List<Ingredient> materials, List<Integer> quantities, Hardness hardness, int color) {
        super(Properties.create(Material.IRON));
        this.hardness = hardness;
        this.color = color;
        setMixture(materials, quantities);

    }

    public void setMixture(List<Ingredient> materials, List<Integer> quantities) {
        if (materials.size() != quantities.size()) {
            return;
        }
        int j = 0;
        for (Ingredient i : materials) {
            mixture.put(i, quantities.get(j));
            j++;
        }

    }

    public Hardness getHardness() {
        return hardness;
    }

    public Map<Ingredient, Integer> getMixture() {
        return mixture;
    }

    public int getColor() {
        return color;
    }

    public enum Hardness {
        SOFT, MEDIUM, HARD
    }


}
