package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.ContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class GoldenHopperContainer extends Container {
    public GoldenHopperContainer(int id, IItemHandlerModifiable inventory, GoldenHopperTE tile) {
        super(ContainerTypes.GOLDEN_HOPPER_CONTAINER.get(), id);
        createInventory(inventory);

    }

    public GoldenHopperContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, new InvWrapper(playerInventory), getTileEntity(playerInventory, data));
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return false;
    }

    private static GoldenHopperTE getTileEntity(PlayerInventory playerInventory, PacketBuffer data) {
        if (playerInventory.player.world.getTileEntity(data.readBlockPos()) instanceof GoldenHopperTE) {
            return (GoldenHopperTE) playerInventory.player.world.getTileEntity(data.readBlockPos());
        }
        return null;
    }

    private void createInventory(IItemHandlerModifiable inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new SlotItemHandler(inventory, k, 8 + k * 18, 142));
        }
    }
}
