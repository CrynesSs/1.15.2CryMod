package com.CrynesSs.RedstoneEnhancement.structures.CityWall;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public abstract class AbstractWallPiece extends StructurePiece {

    protected AbstractWallPiece(IStructurePieceType structurePieceTypeIn, int componentTypeIn) {
        super(structurePieceTypeIn, componentTypeIn);
    }

    public AbstractWallPiece(IStructurePieceType structurePierceTypeIn, CompoundNBT nbt) {
        super(structurePierceTypeIn, nbt);
    }
}
