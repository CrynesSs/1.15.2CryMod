package com.CrynesSs.RedstoneEnhancement.structures;

import com.CrynesSs.RedstoneEnhancement.TileEntities.BigFurnaceTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class MBFRenderer extends TileEntityRenderer<BigFurnaceTileEntity> {
    int tick = 0;

    public MBFRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull BigFurnaceTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn.getStructure() == null) {
            return;
        }
        if (!Minecraft.getInstance().isGamePaused()) {
            BlockPos corner = tileEntityIn.getStructure().getCorner();
            BlockPos tilepos = tileEntityIn.getPos();
            BlockPos connect = connectionVec(tilepos, corner);

            matrixStackIn.push();

            matrixStackIn.pop();
        }
    }

    private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
    }

    private BlockPos connectionVec(BlockPos p1, BlockPos p2) {
        return new BlockPos(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());
    }

}
