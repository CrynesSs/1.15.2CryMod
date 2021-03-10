package com.CrynesSs.RedstoneEnhancement.Util.Interfaces;

import com.CrynesSs.RedstoneEnhancement.structures.CityWall.CityWallPieces;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public interface IStructurePieceType {
    net.minecraft.world.gen.feature.structure.IStructurePieceType CITY = register(CityWallPieces.City::new, "CVi");


    static net.minecraft.world.gen.feature.structure.IStructurePieceType register(net.minecraft.world.gen.feature.structure.IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}
