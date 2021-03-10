package com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMaschine;
import com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace.AlloyFurnaceTE;
import com.CrynesSs.RedstoneEnhancement.Util.Init.RecipeSerializerInit;
import com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing.OreCrushingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Set;

public class OreCrusherBlock extends AbstractMaschine<OreCrusherTE> {
    public static final BooleanProperty CRUSHING = BooleanProperty.create("crushing");

    public OreCrusherBlock() {
        super(Block.Properties.create(Material.IRON)
                        //Ironblock res : 5.0 Gravel 0.6
                        .hardnessAndResistance(7.5f, 6.125f)
                        .sound(SoundType.METAL)
                        //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                        .harvestLevel(2)
                        .harvestTool(ToolType.PICKAXE)
                        .notSolid()
                , OreCrusherTE.class);
    }

    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherTE) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherTE) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nonnull
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        Set<IRecipe<?>> recipes = AlloyFurnaceTE.findRecipeByType(RecipeSerializerInit.ORE_CRUSHING_TYPE, worldIn);
        System.out.println(recipes.size());
        if (recipes.size() > 0) {
            if (recipes.toArray()[0] instanceof OreCrushingRecipe) {
                OreCrushingRecipe r = (OreCrushingRecipe) recipes.toArray()[0];
                System.out.println(Arrays.toString(r.getOutput().getWeights()));
                System.out.println(Arrays.deepToString(r.getOutput().getAmounts()));
            }
        }


        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
