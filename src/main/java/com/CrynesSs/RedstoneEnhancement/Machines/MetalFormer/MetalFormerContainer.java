package com.CrynesSs.RedstoneEnhancement.Machines.MetalFormer;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MetalFormerContainer extends AbstractMBFContainer {
    protected MetalFormerContainer(ContainerType<?> containerTypeIn, int id, IItemHandlerModifiable playerInventory) {
        super(containerTypeIn, id, playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}
