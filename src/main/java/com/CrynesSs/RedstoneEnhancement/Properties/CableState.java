package com.CrynesSs.RedstoneEnhancement.Properties;

import com.CrynesSs.RedstoneEnhancement.Util.Enums.ECableState;
import net.minecraft.state.EnumProperty;

import java.util.Arrays;
import java.util.Collection;

public class CableState extends EnumProperty<ECableState> {

    protected CableState(String name, Collection<ECableState> values) {
        super(name, ECableState.class, values);
    }

    public static CableState create(String name) {
        ECableState[] vals = {ECableState.STRAIGHT, ECableState.CROSS, ECableState.TSECTION, ECableState.CORNER, ECableState.TWITHARM, ECableState.XYZ, ECableState.CROSSWITHARM, ECableState.ALLCABLE};
        Collection<ECableState> states = Arrays.asList(vals);
        return new CableState(name, states);
    }

}
