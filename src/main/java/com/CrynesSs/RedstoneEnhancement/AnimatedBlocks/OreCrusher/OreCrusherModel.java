package com.CrynesSs.RedstoneEnhancement.AnimatedBlocks.OreCrusher;

import com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher.OreCrusherTE;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OreCrusherModel extends AnimatedGeoModel<OreCrusherTE> {
    public static final ResourceLocation MODEL_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "geo/orecrusher.geo.json");
    //public static final ResourceLocation TEXTURE_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID,"textures/blocks/orecrusher/orecrusher.png");
    public static final ResourceLocation TEXTURE_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/blocks/cobald_block.png");
    public static final ResourceLocation ANIMATION_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "animations/orecrusher.animation.json");

    public OreCrusherModel() {

    }

    @Override
    public ResourceLocation getModelLocation(OreCrusherTE oreCrusherTE) {
        return MODEL_RL;
    }

    @Override
    public ResourceLocation getTextureLocation(OreCrusherTE oreCrusherTE) {
        return TEXTURE_RL;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OreCrusherTE oreCrusherTE) {
        return ANIMATION_RL;
    }
}
