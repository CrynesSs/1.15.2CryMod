package com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFContainer;
import com.CrynesSs.RedstoneEnhancement.Util.Init.BlockInit;
import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.ContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import java.util.Objects;

public class OreCrusherContainer extends AbstractMBFContainer {
    private final IWorldPosCallable canInteractWithCallable;

    public OreCrusherContainer(int id, IItemHandlerModifiable playerInventory, final OreCrusherTE tile) {
        super(ContainerTypes.ORE_CRUSHER_CONTAINER.get(), id, playerInventory);
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tile.getWorld()), tile.getPos());
        IItemHandlerModifiable tileInv = tile.getInventory();
        this.addSlot(new SlotItemHandler(tileInv, 0, 22, 38));
        this.addSlot(new SlotItemHandler(tileInv, 1, 71, 24));
        this.addSlot(new SlotItemHandler(tileInv, 2, 71, 51));
        this.addSlot(new SlotItemHandler(tileInv, 3, 96, 24));
        this.addSlot(new SlotItemHandler(tileInv, 4, 96, 51));
    }

    public OreCrusherContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, new InvWrapper(playerInventory), getTileEntity(data, playerInventory.player.world.getWorld()));
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.ORE_CRUSHER.get());
    }

    private static OreCrusherTE getTileEntity(final PacketBuffer data, final World world) {
        if (world != null) {
            final TileEntity tileAtPos = world.getTileEntity(data.readBlockPos());
            if (tileAtPos instanceof OreCrusherTE) {
                return (OreCrusherTE) tileAtPos;
            }
        }
        return null;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (index >= 36) {
            mergeItemStack(slot.getStack(), 0, 35, true);
        } else {

        }
        return ItemStack.EMPTY;

    }
}
