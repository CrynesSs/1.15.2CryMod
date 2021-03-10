package com.CrynesSs.RedstoneEnhancement.Properties;

import com.CrynesSs.RedstoneEnhancement.Util.Enums.EStructureBlocks;
import net.minecraft.state.EnumProperty;

import java.util.Arrays;
import java.util.Collection;

public class StructureBlocksPosition extends EnumProperty<EStructureBlocks> {
    protected StructureBlocksPosition(String name, Collection<EStructureBlocks> values) {
        super(name, EStructureBlocks.class, values);
    }

    public static StructureBlocksPosition create(String name, EStructureBlocks.size s) {
        Collection<EStructureBlocks> values = Arrays.asList(EStructureBlocks.getSizeStructure(s));
        return new StructureBlocksPosition(name, values);
    }
}
