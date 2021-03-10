package com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.InlayingMaschine;

import com.CrynesSs.RedstoneEnhancement.Machines.InlayingMaschine.InlayingMaschineTE;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class InlayingMaschineModel extends AnimatedGeoModel<InlayingMaschineTE> {
    public static final ResourceLocation MODEL_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "geo/inlayingmachine.geo.json");
    //public static final ResourceLocation TEXTURE_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID,"textures/blocks/orecrusher/orecrusher.png");
    public static final ResourceLocation TEXTURE_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/blocks/cobald_block.png");
    public static final ResourceLocation ANIMATION_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "animations/inlaying.animation.middleslot.json");

    public InlayingMaschineModel() {

    }

    @Override
    public ResourceLocation getModelLocation(InlayingMaschineTE inlayingMaschineTE) {
        return MODEL_RL;
    }

    @Override
    public ResourceLocation getTextureLocation(InlayingMaschineTE inlayingMaschineTE) {
        return TEXTURE_RL;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(InlayingMaschineTE inlayingMaschineTE) {
        return ANIMATION_RL;
    }
}
