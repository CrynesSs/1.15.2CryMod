package com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace;

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

public class AlloyFurnaceContainer extends AbstractMBFContainer {
    private final IWorldPosCallable canInteractWithCallable;
    public AlloyFurnaceTE tile;

    public AlloyFurnaceContainer(int id, IItemHandlerModifiable inventoryIn, final AlloyFurnaceTE tile) {
        super(ContainerTypes.ALLOY_FURNACE_C.get(), id, inventoryIn);
        this.tile = tile;
        IItemHandlerModifiable tileInv = tile.getInventory();
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tile.getWorld()), tile.getPos());
        this.addSlot(new SlotItemHandler(tileInv, 0, 34, 16));
        this.addSlot(new SlotItemHandler(tileInv, 1, 119, 16));
        this.addSlot(new SlotItemHandler(tileInv, 2, 34, 57));
        this.addSlot(new SlotItemHandler(tileInv, 3, 119, 57));
        this.addSlot(new SlotItemHandler(tileInv, 4, 77, 41));
        //System.out.println(inventoryIn.toString());


    }


    public AlloyFurnaceContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, new InvWrapper(playerInventory), getTileEntity(data, playerInventory.player.world.getWorld()));
    }


    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.ALLOY_FURANCE.get());
    }

    private static AlloyFurnaceTE getTileEntity(final PacketBuffer data, final World world) {
        if (world != null) {
            final TileEntity tileAtPos = world.getTileEntity(data.readBlockPos());
            if (tileAtPos instanceof AlloyFurnaceTE) {
                return (AlloyFurnaceTE) tileAtPos;
            }
        }
        return null;
    }


    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
    }


    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        ItemStack slotStack = slot.getStack().copy();
        //36/37 SmeltSlots 38/39FuelSlots 40 OutputSLot
        if (!slot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        if (index < 36) {
            //Fuel
            if (isFuel(slot.getStack())) {
                Slot slot1 = this.inventorySlots.get(38);
                Slot slot2 = this.inventorySlots.get(39);
                int slotcount = slot.getStack().getCount();
                //Set Stacks if not there
                if (slotcount > 0 && !slot1.getHasStack()) {
                    slotcount--;
                    slot1.putStack(new ItemStack(slot.getStack().getItem(), 1));
                }
                if (slotcount > 0 && !slot2.getHasStack()) {
                    slotcount--;
                    slot2.putStack(new ItemStack(slot.getStack().getItem(), 1));
                }

                int missing1 = 64 - slot1.getStack().getCount();
                int missing2 = 64 - slot2.getStack().getCount();
                if (missing1 == 0 && missing2 == 0 && (slot1.getStack().getItem().equals(slot.getStack().getItem()) && slot2.getStack().getItem().equals(slot.getStack().getItem()))) {
                    return ItemStack.EMPTY;
                }
                boolean onlyOne = !slot1.getStack().getItem().equals(slot.getStack().getItem()) || !slot2.getStack().getItem().equals(slot.getStack().getItem())
                        || (missing1 == 0 || missing2 == 0);

                if (!onlyOne) {
                    while (slotcount > 0 && (missing1 != 0 || missing2 != 0)) {
                        if (missing1 > missing2) {
                            slot1.getStack().setCount(slot1.getStack().getCount() + 1);
                            missing1--;
                        } else {
                            slot2.getStack().setCount(slot2.getStack().getCount() + 1);
                            missing2--;
                        }
                        slotcount--;
                    }
                } else {
                    if (missing1 != 0 && slot1.getStack().getItem().equals(slot.getStack().getItem())) {
                        while (slotcount > 0 && missing1 != 0) {
                            slot1.getStack().setCount(slot1.getStack().getCount() + 1);
                            missing1--;
                            slotcount--;
                        }
                    } else {
                        while (slotcount > 0 && missing2 != 0) {
                            slot2.getStack().setCount(slot2.getStack().getCount() + 1);
                            missing2--;
                            slotcount--;
                        }
                    }
                }
                slot.getStack().setCount(slotcount);
            } else if (Objects.requireNonNull(slot.getStack().getItem().getRegistryName()).toString().toLowerCase().contains("ingot")) {
                Slot slot1 = this.inventorySlots.get(36);
                Slot slot2 = this.inventorySlots.get(37);
                if (slot1.getStack().getItem().equals(slotStack.getItem())) {
                    mergeItemStack(slot.getStack(), 36, 37, false);
                } else if (slot2.getStack().getItem().equals(slotStack.getItem())) {
                    mergeItemStack(slot.getStack(), 37, 38, false);
                } else if (!slot1.getHasStack()) {
                    slot1.putStack(new ItemStack(slotStack.getItem(), slotStack.getCount()));
                    slot.getStack().setCount(0);
                } else if (!slot2.getHasStack()) {
                    slot2.putStack(new ItemStack(slotStack.getItem(), slotStack.getCount()));
                    slot.getStack().setCount(0);
                }
            }
        }
        //Transfer to Player inventory
        else {
            mergeItemStack(slot.getStack(), 0, 35, true);
        }
        return ItemStack.EMPTY;
    }

}
