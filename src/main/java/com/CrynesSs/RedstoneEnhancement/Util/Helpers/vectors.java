package com.CrynesSs.RedstoneEnhancement.Util.Helpers;

import net.minecraft.util.math.Vec3d;

public class vectors {
    public static Vec3d halfVector(Vec3d vec) {
        double x = vec.x / 2f;
        double y = vec.y / 2f;
        double z = vec.z / 2f;
        return new Vec3d(x, y, z);
    }
}
