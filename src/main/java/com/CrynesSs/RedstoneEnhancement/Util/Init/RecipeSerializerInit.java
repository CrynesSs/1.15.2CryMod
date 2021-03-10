package com.CrynesSs.RedstoneEnhancement.Util.Init;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.recipes.AlloySmelting.AlloySmeltingRecipe;
import com.CrynesSs.RedstoneEnhancement.recipes.AlloySmelting.AlloySmeltingSerializer;
import com.CrynesSs.RedstoneEnhancement.recipes.AlloySmelting.IAlloySmeltingRecipe;
import com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing.IOreCrushingRecipe;
import com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing.OreCrushingRecipe;
import com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing.OreCrushingSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

//same
public class RecipeSerializerInit {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RedstoneEnhancement.MOD_ID);


    public static final IRecipeSerializer<AlloySmeltingRecipe> ALLOY_SMELTING_RECIPE_SERIALIZER = new AlloySmeltingSerializer();
    public static final IRecipeSerializer<OreCrushingRecipe> ORE_CRUSHING_RECIPE_SERIALIZER = new OreCrushingSerializer();

    public static final IRecipeType<IAlloySmeltingRecipe> ALLOY_TYPE = Registry.register(Registry.RECIPE_TYPE, IAlloySmeltingRecipe.RECIPE_TYPE_ID, new RecipeType<>());
    public static final IRecipeType<IOreCrushingRecipe> ORE_CRUSHING_TYPE = Registry.register(Registry.RECIPE_TYPE, IOreCrushingRecipe.RECIPE_TYPE_ID, new RecipeType<>());


    public static final RegistryObject<IRecipeSerializer<?>> ALLOY_SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register("alloysmelting",
            () -> ALLOY_SMELTING_RECIPE_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> ORE_CRUSHING_SERIALIZER = RECIPE_SERIALIZERS.register("ore_crushing",
            () -> ORE_CRUSHING_RECIPE_SERIALIZER);


    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Objects.requireNonNull(Registry.RECIPE_TYPE.getKey(this)).toString();
        }
    }

}
