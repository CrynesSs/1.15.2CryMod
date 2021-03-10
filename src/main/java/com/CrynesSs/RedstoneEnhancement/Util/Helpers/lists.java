package com.CrynesSs.RedstoneEnhancement.Util.Helpers;

import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class lists {

    public static BlockPos findOutermostCorner(List<BlockPos> posList) {
        AtomicReference<BlockPos> outermost = new AtomicReference<>();
        AtomicInteger i = new AtomicInteger();
        System.out.println(posList.toString());
        posList.forEach(k -> {
            if (i.get() == 0) {
                outermost.set(k);
                i.getAndIncrement();
            } else {
                if (((k.getX() < outermost.get().getX()) && (k.getZ() <= outermost.get().getZ())) || ((k.getX() <= outermost.get().getX()) && (k.getZ() < outermost.get().getZ()))) {
                    outermost.set(k);
                } else if (k.getX() == outermost.get().getX() && k.getZ() == outermost.get().getZ() && k.getY() < outermost.get().getY()) {
                    outermost.set(k);
                }
            }
        });
        System.out.println(outermost + "THIS IS OUTERMOST");
        return outermost.get();
    }

}
