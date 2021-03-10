package com.CrynesSs.RedstoneEnhancement.Entity.Robot;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RobotEntityModel extends AnimatedGeoModel<RobotEntity> {
    public static final ResourceLocation MODEL_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "geo/robot.geo.json");
    public static final ResourceLocation TEXTURE_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/entity/robot/robot.png");
    public static final ResourceLocation ANIMATION_RL = new ResourceLocation(RedstoneEnhancement.MOD_ID, "animations/robot.animation.json");

    @Override
    public ResourceLocation getModelLocation(RobotEntity robotEntity) {
        return MODEL_RL;
    }

    @Override
    public ResourceLocation getTextureLocation(RobotEntity robotEntity) {
        return TEXTURE_RL;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RobotEntity robotEntity) {
        return ANIMATION_RL;
    }
}
