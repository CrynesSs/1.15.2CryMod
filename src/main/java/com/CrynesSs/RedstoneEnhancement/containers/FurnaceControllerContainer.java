package com.CrynesSs.RedstoneEnhancement.containers;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FurnaceControllerContainer extends AbstractMBFContainer {
    protected FurnaceControllerContainer(ContainerType<?> containerTypeIn, int id, PlayerInventory playerInventoryIn) {
        super(containerTypeIn, id, new InvWrapper(playerInventoryIn));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}
