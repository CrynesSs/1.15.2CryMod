package com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OreCrushingSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<OreCrushingRecipe> {
    @Nonnull
    @Override
    public OreCrushingRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        System.out.println("Reading from " + recipeId.toString());
        Ingredient input = Ingredient.deserialize(json.getAsJsonObject("input"));
        OreCrushingOutput output = new OreCrushingOutput(json.getAsJsonObject("output"));
        ItemStack inputstack = CraftingHelper.getItemStack(json.getAsJsonObject("input"), true);
        Integer smelttime = JSONUtils.getInt(json, "smelttime");
        return new OreCrushingRecipe(recipeId, input, inputstack, output, smelttime);
    }

    @Nullable
    @Override
    public OreCrushingRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
        System.out.println("Reading from " + recipeId.toString());
        Ingredient input = Ingredient.read(buffer);
        ItemStack inputstack = buffer.readItemStack();
        Integer smelttime = buffer.readInt();
        return new OreCrushingRecipe(recipeId, input, inputstack, OreCrushingOutput.serialize(buffer), smelttime);
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, OreCrushingRecipe recipe) {
        System.out.println("Writing at Recipe " + recipe.getId());
        Ingredient input = recipe.getIngredients().get(0);
        ItemStack inputStack = recipe.getInputStack();

        input.write(buffer);
        buffer.writeItemStack(inputStack);
        buffer.writeInt(recipe.getSmeltTime());
        recipe.getOutput().deserialize(buffer);
    }
}
