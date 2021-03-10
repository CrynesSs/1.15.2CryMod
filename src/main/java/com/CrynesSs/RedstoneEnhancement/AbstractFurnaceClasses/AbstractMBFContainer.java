package com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;


public abstract class AbstractMBFContainer extends Container {
    public final IItemHandlerModifiable INVENTORY;

    protected AbstractMBFContainer(ContainerType<?> containerTypeIn, int id, IItemHandlerModifiable playerInventory) {
        super(containerTypeIn, id);
        this.INVENTORY = playerInventory;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new SlotItemHandler(playerInventory, k, 8 + k * 18, 142));
        }
    }


}
