package com.CrynesSs.RedstoneEnhancement.recipes.AlloySmelting;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

//Same
public interface IAlloySmeltingRecipe extends IRecipe<RecipeWrapper> {
    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(RedstoneEnhancement.MOD_ID, "alloysmelting");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getValue(RECIPE_TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }

    Ingredient getInput1();

    Ingredient getInput2();

    ItemStack getInput1Stack();

    ItemStack getInput2Stack();

    Integer getSmeltTime();


}
