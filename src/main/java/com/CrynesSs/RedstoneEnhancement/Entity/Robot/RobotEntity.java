package com.CrynesSs.RedstoneEnhancement.Entity.Robot;

import com.CrynesSs.RedstoneEnhancement.EntityGoals.FleeMoonGoal;
import com.CrynesSs.RedstoneEnhancement.EntityGoals.RestrictMoonGoal;
import com.CrynesSs.RedstoneEnhancement.Util.Helpers.Enchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RobotEntity extends MonsterEntity implements IRangedAttackMob, IAnimatable {
    private final RangedAttackGoal aiFireRocket = new RangedAttackGoal(this, 1, 10, 25f);
    private final AnimationFactory factory = new AnimationFactory(this);

    public RobotEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreFrustumCheck = true;
    }


    @Override
    public void attackEntityWithRangedAttack(@Nonnull LivingEntity target, float distanceFactor) {

    }


    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35F);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 25f));
        this.goalSelector.addGoal(2, new RestrictMoonGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, SheepEntity.class, 12f, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new FleeMoonGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));

        super.registerGoals();
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(@Nonnull DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        switch (difficulty.getDifficulty()) {
            case EASY: {
                ItemStack sword = new ItemStack(Items.GOLDEN_SWORD);
                ItemStack helmet = Enchantments.multipleEnchantItemRandom(new ItemStack(Items.GOLDEN_HELMET), new Enchantment[]{net.minecraft.enchantment.Enchantments.UNBREAKING, net.minecraft.enchantment.Enchantments.PROTECTION}, new int[]{3, 3}, new int[]{1, 1}, new int[]{2, 2}, new boolean[]{true, true});
                ItemStack chest = Enchantments.multipleEnchantItemRandom(new ItemStack(Items.GOLDEN_CHESTPLATE), new Enchantment[]{net.minecraft.enchantment.Enchantments.UNBREAKING, net.minecraft.enchantment.Enchantments.PROTECTION}, new int[]{3, 3}, new int[]{1, 1}, new int[]{2, 2}, new boolean[]{true, true});
                ItemStack legs = Enchantments.multipleEnchantItemRandom(new ItemStack(Items.GOLDEN_LEGGINGS), new Enchantment[]{net.minecraft.enchantment.Enchantments.UNBREAKING, net.minecraft.enchantment.Enchantments.PROTECTION}, new int[]{3, 3}, new int[]{1, 1}, new int[]{2, 2}, new boolean[]{true, true});
                ItemStack feet = Enchantments.multipleEnchantItemRandom(new ItemStack(Items.GOLDEN_BOOTS), new Enchantment[]{net.minecraft.enchantment.Enchantments.UNBREAKING, net.minecraft.enchantment.Enchantments.PROTECTION}, new int[]{3, 3}, new int[]{1, 1}, new int[]{2, 2}, new boolean[]{true, true});
                Enchantments.enchantItemRandom(sword, net.minecraft.enchantment.Enchantments.SHARPNESS, 4, 2, 3);
                this.setItemStackToSlot(EquipmentSlotType.HEAD, helmet);
                this.setItemStackToSlot(EquipmentSlotType.CHEST, chest);
                this.setItemStackToSlot(EquipmentSlotType.LEGS, legs);
                this.setItemStackToSlot(EquipmentSlotType.FEET, feet);
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, sword);
                break;
            }
            case NORMAL: {
            }
            case HARD: {
            }
        }
    }

    @Override
    public void tick() {
        int lightvalue = this.world.getLightValue(this.getPosition().down());
        super.tick();
    }

    @Override
    protected void setEnchantmentBasedOnDifficulty(@Nonnull DifficultyInstance difficulty) {
        super.setEnchantmentBasedOnDifficulty(difficulty);
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose poseIn, @Nonnull EntitySize sizeIn) {
        return super.getStandingEyeHeight(poseIn, sizeIn);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robot.new", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
