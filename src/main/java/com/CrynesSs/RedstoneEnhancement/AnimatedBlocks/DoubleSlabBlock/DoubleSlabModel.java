package com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.DoubleSlabBlock;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class DoubleSlabModel implements IDynamicBakedModel {

    public static final ResourceLocation TEXTURE = new ResourceLocation(RedstoneEnhancement.MOD_ID, "block/fancyblock");


    public void combineModels(DoubleSlabBlock block) {
        SlabBlock slab1 = block.getBottomSlab();
        SlabBlock slab2 = block.getTopSlab();
        IBakedModel model1 = null;
        IBakedModel model2 = null;
        if (slab1 != null) {
            model1 = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Objects.requireNonNull(slab1.getRegistryName()).getPath()));
            return;
        }
        if (slab2 != null) {
            model2 = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Objects.requireNonNull(slab2.getRegistryName()).getPath()));
            return;
        }
        List<BakedQuad> quads1 = model1.getQuads(slab1.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM), null, new Random());
        List<BakedQuad> quads2 = model2.getQuads(slab1.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), null, new Random());
        List<BakedQuad> quads = new ArrayList<>();
        quads.addAll(quads1);
        quads.addAll(quads2);


    }


    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(TEXTURE);
    }

    @Override
    public boolean func_230044_c_() {
        return false;
    }

    private void putVertex(BakedQuadBuilder builder, Vec3d normal,
                           double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {

        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0; j < elements.size(); j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, 1.0f);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getInterpolatedU(u);
                            float iv = sprite.getInterpolatedV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }

    private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
        int tw = sprite.getWidth();
        int th = sprite.getHeight();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, 0, th, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, tw, th, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    private static Vec3d v(double x, double y, double z) {
        return new Vec3d(x, y, z);
    }


    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        RenderType layer = MinecraftForgeClient.getRenderLayer();

        BlockState mimic = extraData.getData(DoubleSlabTE.MIMIC);
        if (mimic != null && !(mimic.getBlock() instanceof DoubleSlabBlock)) {
            if (layer == null || RenderTypeLookup.canRenderInLayer(mimic, layer)) {
                IBakedModel model = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(mimic);
                try {
                    return model.getQuads(mimic, side, rand, EmptyModelData.INSTANCE);
                } catch (Exception e) {
                    return Collections.emptyList();
                }
            }
            return Collections.emptyList();
        }

        if (side != null || (layer != null && !layer.equals(RenderType.getSolid()))) {
            return Collections.emptyList();
        }

        TextureAtlasSprite texture = getTexture();
        List<BakedQuad> quads = new ArrayList<>();
        double l = .2;
        double r = 1 - .2;
        quads.add(createQuad(v(l, r, l), v(l, r, r), v(r, r, r), v(r, r, l), texture));
        quads.add(createQuad(v(l, l, l), v(r, l, l), v(r, l, r), v(l, l, r), texture));
        quads.add(createQuad(v(r, r, r), v(r, l, r), v(r, l, l), v(r, r, l), texture));
        quads.add(createQuad(v(l, r, l), v(l, l, l), v(l, l, r), v(l, r, r), texture));
        quads.add(createQuad(v(r, r, l), v(r, l, l), v(l, l, l), v(l, r, l), texture));
        quads.add(createQuad(v(l, r, r), v(l, l, r), v(r, l, r), v(r, r, r), texture));

        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Nonnull
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }
}
