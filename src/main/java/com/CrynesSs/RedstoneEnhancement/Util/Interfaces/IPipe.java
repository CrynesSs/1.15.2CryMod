package com.CrynesSs.RedstoneEnhancement.Util.Interfaces;

import com.CrynesSs.RedstoneEnhancement.Storages.TransportPipeTileEntity;

public interface IPipe {
    void putItem(TransportPipeTileEntity.ItemHandle handle);

    boolean canAcceptItems();

    void passItem(TransportPipeTileEntity.ItemHandle handle);

}
