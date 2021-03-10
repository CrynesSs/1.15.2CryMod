package com.CrynesSs.RedstoneEnhancement.CustomProperties;

import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.util.Direction;

import java.util.Collection;

public class Outwards extends DirectionProperty implements IProperty<Direction> {
    protected Outwards(String name, Collection<Direction> values) {
        super(name, values);
    }
}
