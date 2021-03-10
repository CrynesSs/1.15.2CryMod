package com.CrynesSs.RedstoneEnhancement.Machines.TemperingOven;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TemperingOvenContainer extends AbstractMBFContainer {
    protected TemperingOvenContainer(ContainerType<?> containerTypeIn, int id, IItemHandlerModifiable playerInventory) {
        super(containerTypeIn, id, playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}
