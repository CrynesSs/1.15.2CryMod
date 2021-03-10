package com.CrynesSs.RedstoneEnhancement.Util;

import net.minecraft.entity.item.minecart.*;
import net.minecraft.world.World;

public class MoJank {
    //From AbstractMinecartEntity
    public static AbstractMinecartEntity create(World worldIn, double x, double y, double z, AbstractMinecartEntity.Type typeIn) {
        switch (typeIn) {
            case CHEST:
                return new ChestMinecartEntity(worldIn, x, y, z);
            case FURNACE:
                return new FurnaceMinecartEntity(worldIn, x, y, z);
            case HOPPER:
                return new HopperMinecartEntity(worldIn, x, y, z);
            case SPAWNER:
                return new SpawnerMinecartEntity(worldIn, x, y, z);
            case TNT:
                return new TNTMinecartEntity(worldIn, x, y, z);
            case COMMAND_BLOCK:
                return new MinecartCommandBlockEntity(worldIn, x, y, z);
            default:
                return new MinecartEntity(worldIn, x, y, z);
        }
    }

    public static AbstractMinecartEntity createJank(World worldIn, double x, double y, double z, AbstractMinecartEntity.Type typeIn) {
        if (typeIn == AbstractMinecartEntity.Type.CHEST) {
            return new ChestMinecartEntity(worldIn, x, y, z);
        } else if (typeIn == AbstractMinecartEntity.Type.FURNACE) {
            return new FurnaceMinecartEntity(worldIn, x, y, z);
        } else if (typeIn == AbstractMinecartEntity.Type.TNT) {
            return new TNTMinecartEntity(worldIn, x, y, z);
        } else if (typeIn == AbstractMinecartEntity.Type.SPAWNER) {
            return new SpawnerMinecartEntity(worldIn, x, y, z);
        } else if (typeIn == AbstractMinecartEntity.Type.HOPPER) {
            return new HopperMinecartEntity(worldIn, x, y, z);
        } else {
            return (AbstractMinecartEntity) (typeIn == AbstractMinecartEntity.Type.COMMAND_BLOCK ? new MinecartCommandBlockEntity(worldIn, x, y, z) : new MinecartEntity(worldIn, x, y, z));
        }

    }
}
