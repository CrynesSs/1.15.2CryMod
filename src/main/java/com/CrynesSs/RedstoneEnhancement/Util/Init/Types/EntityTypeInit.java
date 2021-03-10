package com.CrynesSs.RedstoneEnhancement.Util.Init.Types;

import com.CrynesSs.RedstoneEnhancement.Entity.Robot.RobotEntity;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, RedstoneEnhancement.MOD_ID);
    public static final RegistryObject<EntityType<RobotEntity>> ROBOT_ENTITY_TYPE = ENTITY_TYPES.register("robot", () -> EntityType.Builder.create(RobotEntity::new, EntityClassification.CREATURE)
            .size(0.6f, 2f)
            .build(new ResourceLocation(RedstoneEnhancement.MOD_ID, "robot_entity").toString()));

}
