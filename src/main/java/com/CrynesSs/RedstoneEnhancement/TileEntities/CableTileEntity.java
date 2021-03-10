package com.CrynesSs.RedstoneEnhancement.TileEntities;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import com.CrynesSs.RedstoneEnhancement.cables.AbstractCableBlock;
import net.minecraft.tileentity.TileEntity;

public class CableTileEntity extends TileEntity {
    public static AbstractCableBlock cable;


    public CableTileEntity() {
        super(TileEntityTypes.CABLE.get());
    }

    public CableTileEntity(AbstractCableBlock cableIn) {
        super(TileEntityTypes.CABLE.get());
        cable = cableIn;
    }
}
