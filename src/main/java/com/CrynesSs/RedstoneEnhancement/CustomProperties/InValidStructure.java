package com.CrynesSs.RedstoneEnhancement.CustomProperties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;

public class InValidStructure extends BooleanProperty implements IProperty<Boolean> {
    protected InValidStructure(String name) {
        super(name);
    }
}
