package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Helpers.vectors;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TPTERenderer extends TileEntityRenderer<TransportPipeTileEntity> {
    //Needs to also be ServerSide
    public TPTERenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull TransportPipeTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!Minecraft.getInstance().isGamePaused()) {
            HashMap<TransportPipeTileEntity.ItemHandle, Vec3d> items = tileEntityIn.ITEMPOS;
            HashMap<TransportPipeTileEntity.ItemHandle, Vec3d> afterRender = new HashMap<>();
            List<TransportPipeTileEntity.ItemHandle> toPass = new ArrayList<>();
            //Renders the Items in ItemPos
            items.forEach((k, v) -> {
                double x = 0d;
                double y = 0d;
                double z = 0d;
                matrixStackIn.push();
                Vec3d from = vectors.halfVector(new Vec3d(k.getFrom().getDirectionVec()));
                Vec3d to = vectors.halfVector(new Vec3d(k.getTo().getDirectionVec()));
                if (getLengthBetweenPoints(v, from) <= 0.5f) {
                    if (v.x != 0) {
                        if (v.x > 0f) {
                            x = v.x - 0.01f;
                        } else {
                            x = v.x + 0.01f;
                        }
                    }
                    if (v.y != 0) {
                        if (v.y > 0f) {
                            y = v.y - 0.01f;
                        } else {
                            y = v.y + 0.01f;
                        }
                    }
                    if (v.z != 0) {
                        if (v.z > 0f) {
                            z = v.z - 0.01f;
                        } else {
                            z = v.z + 0.01f;
                        }
                    }
                } else {
                    x = v.x + (to.x / 50);
                    y = v.y + (to.y / 50);
                    z = v.z + (to.z / 50);
                }
                if (!(x > 0.5f) && !(x < -0.5f) && !(y > 0.5f) && !(y < -0.5f) && !(z > 0.5f) && !(z < -0.5f)) {
                    afterRender.put(k, new Vec3d(x, y, z));
                    matrixStackIn.scale(0.25f, 0.25f, 0.25f);
                    matrixStackIn.translate((x + 0.5f) * 4, (y + 0.5f) * 4, (z + 0.5f) * 4);
                    renderItem(k, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
                } else {
                    toPass.add(k);
                }
                matrixStackIn.pop();
            });
            tileEntityIn.ITEMPOS = afterRender;

            toPass.forEach(k -> tileEntityIn.ITEMPOS.remove(k));
            if (!toPass.isEmpty()) {
                System.out.println("Deleting Item ClientSide");
            }
        }
    }

    private void renderItem(TransportPipeTileEntity.ItemHandle handle, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderItem(handle.getStack(), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
    }

    private double getLengthBetweenPoints(Vec3d vec1, Vec3d vec2) {
        return Math.sqrt(Math.pow((vec1.x - vec2.x), 2) + Math.pow((vec1.y - vec2.y), 2) + Math.pow((vec1.z - vec2.z), 2));
    }


}
