package com.CrynesSs.RedstoneEnhancement.Entity.Robot;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RobotEntityRender extends GeoEntityRenderer<RobotEntity> {
    //protected static final ResourceLocation TEXTURE = new ResourceLocation(RedstoneEnhancement.MOD_ID,"textures/entity/robot/robot.png");
    public RobotEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RobotEntityModel());
    }

    //public ResourceLocation getEntityTexture(@Nonnull RobotEntity entity) {
    //return TEXTURE;
    //}

}
