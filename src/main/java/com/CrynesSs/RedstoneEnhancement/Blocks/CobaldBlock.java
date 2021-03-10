package com.CrynesSs.RedstoneEnhancement.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.lang.reflect.Field;

public class CobaldBlock extends Block {
    public CobaldBlock() {
        super(Block.Properties.create(Material.IRON)
                //Ironblock res : 5.0 Gravel 0.6
                .hardnessAndResistance(7.5f, 6.125f)
                .sound(SoundType.METAL)
                //*HarvestLevel 0 = Wood 1=Stone/Gold 2= Iron 3 = Diamond 4 = ?
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
        );
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        FirstPersonRenderer renderer = Minecraft.getInstance().getFirstPersonRenderer();
        ItemRenderer renderer1 = Minecraft.getInstance().getItemRenderer();
        Minecraft.getInstance().getFirstPersonRenderer().resetEquippedProgress(Hand.MAIN_HAND);
        try {
            Field f = renderer.getClass().getDeclaredField("equippedProgressMainHand");
            f.setAccessible(true);
            f.setFloat(renderer, 1.0f);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
