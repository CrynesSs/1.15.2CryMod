package com.CrynesSs.RedstoneEnhancement.Particles.SpriteTexturedParticles;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnowParticle extends SpriteTexturedParticle {
    protected SnowParticle(World p_i50998_1_, double p_i50998_2_, double p_i50998_4_, double p_i50998_6_) {
        super(p_i50998_1_, p_i50998_2_, p_i50998_4_, p_i50998_6_);
    }

    protected SnowParticle(World p_i50999_1_, double p_i50999_2_, double p_i50999_4_, double p_i50999_6_, double p_i50999_8_, double p_i50999_10_, double p_i50999_12_) {
        super(p_i50999_1_, p_i50999_2_, p_i50999_4_, p_i50999_6_, p_i50999_8_, p_i50999_10_, p_i50999_12_);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i50495_1_) {
            this.spriteSet = p_i50495_1_;
        }

        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SnowParticle snowParticle = new SnowParticle(worldIn, x, y, z);
            snowParticle.selectSpriteRandomly(this.spriteSet);
            return snowParticle;
        }
    }
}
