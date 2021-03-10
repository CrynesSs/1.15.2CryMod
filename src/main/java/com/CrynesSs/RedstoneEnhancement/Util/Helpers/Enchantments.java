package com.CrynesSs.RedstoneEnhancement.Util.Helpers;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Enchantments {

    public static ItemStack enchantItemRandom(ItemStack stack, Enchantment enchant, int maxlevel, int minlevel, int chance) {
        int rand = (int) Math.floor(Math.random() * chance);
        if (rand == 0) {
            Map<Enchantment, Integer> map = Maps.newHashMap();
            map.put(enchant, randomInt(minlevel, maxlevel));
            EnchantmentHelper.setEnchantments(map, stack);
        }
        return stack;
    }

    public static ItemStack multipleEnchantItemRandom(ItemStack stack, Enchantment[] enchant, int[] maxlevel, int[] minLevel, int[] chance, boolean[] randomLevel) {
        Map<Enchantment, Integer> map = Maps.newHashMap();
        AtomicInteger i = new AtomicInteger(0);
        Arrays.stream(enchant).forEach(k -> {
            int rand = (int) Math.floor(Math.random() * chance[i.get()]);
            if (rand == 0 && !randomLevel[i.get()]) {
                map.put(k, maxlevel[i.get()]);
            } else if (rand == 0) {
                map.put(k, randomInt(minLevel[i.get()], maxlevel[i.get()]));
            }
            i.getAndIncrement();
        });
        EnchantmentHelper.setEnchantments(map, stack);
        return stack;
    }

    public static int randomInt(int lowerBound, int upperBound) {
        return (int) Math.floor(Math.random() * (upperBound - lowerBound) + lowerBound);
    }
}
