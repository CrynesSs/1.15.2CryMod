package com.CrynesSs.RedstoneEnhancement.EntityGoals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.pathfinding.GroundPathNavigator;

public class RestrictMoonGoal extends Goal {
    private final CreatureEntity entity;

    public RestrictMoonGoal(CreatureEntity creature) {
        this.entity = creature;
    }

    @Override
    public boolean shouldExecute() {
        return !this.entity.world.isDaytime() && this.entity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() && this.entity.getNavigator() instanceof GroundPathNavigator;
    }
}
