package com.CrynesSs.RedstoneEnhancement.containers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class ExampleHandler implements IItemHandlerModifiable {
    private final NonNullList<ItemStack> slots;

    public ExampleHandler(NonNullList<ItemStack> slots) {
        this.slots = slots;
    }


    @Override
    public int getSlots() {
        return slots.size();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slots.get(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return this.slots.set(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack stack = slots.get(slot);
        if (amount >= stack.getCount()) {
            slots.set(slot, ItemStack.EMPTY);
            return stack;
        } else {
            slots.set(slot, new ItemStack(stack.getItem(), stack.getCount() - amount));
            return new ItemStack(stack.getItem(), amount);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return slots.size();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        slots.set(slot, stack);
    }
}
