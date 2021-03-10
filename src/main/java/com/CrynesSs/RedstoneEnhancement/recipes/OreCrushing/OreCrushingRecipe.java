package com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing;

import com.CrynesSs.RedstoneEnhancement.Util.Init.RecipeSerializerInit;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public class OreCrushingRecipe implements IOreCrushingRecipe {
    private final Ingredient input;
    private final OreCrushingOutput output;
    private final ResourceLocation id;
    private final Integer smelttime;
    private final ItemStack inputstack;

    public OreCrushingRecipe(ResourceLocation id, Ingredient input, ItemStack inputstack, OreCrushingOutput output, Integer smelttime) {
        this.input = input;
        this.output = output;
        this.id = id;
        this.smelttime = smelttime;
        this.inputstack = inputstack;
    }

    @Override
    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public ItemStack getInputStack() {
        return this.inputstack;
    }

    @Override
    public Integer getSmeltTime() {
        return this.smelttime;
    }

    @Override
    public OreCrushingOutput getOutput() {
        return this.output;
    }

    @Override
    public boolean matches(@Nonnull RecipeWrapper inv, @Nonnull World worldIn) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull RecipeWrapper inv) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.ORE_CRUSHING_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(null, this.input);
    }
}
