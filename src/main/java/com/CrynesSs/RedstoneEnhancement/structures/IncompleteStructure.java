package com.CrynesSs.RedstoneEnhancement.structures;

import com.CrynesSs.RedstoneEnhancement.Util.Enums.EStructureType;
import net.minecraft.util.math.BlockPos;

public class IncompleteStructure {
    public BlockPos corner;
    public Boolean isValid;
    public String structureName;
    public EStructureType type;
    public BlockPos size;
    public StructureData s;

    public IncompleteStructure(BlockPos corner, BlockPos size, StructureData s, Boolean isValid, EStructureType type) {
        this.corner = corner;
        this.size = size;
        this.structureName = s.getStructurename();
        this.s = s;
        this.isValid = isValid;
        this.type = type;
    }
}