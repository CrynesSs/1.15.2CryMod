package com.CrynesSs.RedstoneEnhancement.TileEntities;

import com.CrynesSs.RedstoneEnhancement.Storages.TransportPipeTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbstractTPTE extends TileEntity {
    private final List<Direction> validDir = new ArrayList<>();
    private final HashMap<TransportPipeTileEntity.ItemHandle, Vec3d> ITEMPOS = new HashMap<>();

    public AbstractTPTE(TileEntityType<?> tileEntityTypeIn, BlockState state) {
        super(tileEntityTypeIn);
    }

}
