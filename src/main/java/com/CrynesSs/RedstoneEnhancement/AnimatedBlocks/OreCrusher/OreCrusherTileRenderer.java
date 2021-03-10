package com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.OreCrusher;

import com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class OreCrusherTileRenderer extends GeoBlockRenderer<OreCrusherTE> {
    public OreCrusherTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new OreCrusherModel());
    }

    @Override
    public RenderType getRenderType(OreCrusherTE animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(getTextureLocation(animatable));
    }
}
