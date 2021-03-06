package com.CrynesSs.RedstoneEnhancement.TileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class InventoryTile extends TileEntity implements ITickableTileEntity, ISidedInventory, ISidedInventoryProvider {

    public int size;
    public int timer;
    public boolean requiresUpdate = true;

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    protected LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);

    public InventoryTile(TileEntityType<?> tileEntityTypeIn, int size) {
        super(tileEntityTypeIn);
        this.size = size;
    }

    @Override
    public void tick() {
        this.timer++;
        if (this.world != null) {
            if (this.requiresUpdate) {
                updateTile();
                this.requiresUpdate = false;
            }
        }
    }


    @Nonnull
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(@Nonnull net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    public LazyOptional<IItemHandlerModifiable> getHandler() {
        return this.handler;
    }

    public IItemHandlerModifiable getInventory() {
        return this.handler.orElse(createHandler());
    }

    @Nonnull
    public IItemHandlerModifiable createHandler() {
        return new ItemStackHandler(this.size) {
            @Override
            protected void onContentsChanged(int slot) {
                updateTile();
                markDirty();
            }
        };
    }

    public ItemStack getItemInSlot(int slot) {
        return this.handler.map(inventory -> inventory.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }

    public ItemStack insertItem(int slot, ItemStack stack) {
        ItemStack itemIn = stack.copy();
        stack.shrink(itemIn.getCount());
        this.requiresUpdate = true;
        return this.handler.map(inventory -> inventory.insertItem(slot, itemIn, false)).orElse(ItemStack.EMPTY);
    }

    public ItemStack extractItem(int slot) {
        int count = getItemInSlot(slot).getCount();
        this.requiresUpdate = true;
        return this.handler.map(inventory -> inventory.extractItem(slot, count, false)).orElse(ItemStack.EMPTY);
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        ListNBT list = compound.getList("Items", 10);
        for (int x = 0; x < list.size(); ++x) {
            CompoundNBT nbt = list.getCompound(x);
            int r = nbt.getByte("Slot") & 255;
            this.handler.ifPresent(inventory -> {
                int invslots = inventory.getSlots();
                if (r < invslots) {
                    inventory.setStackInSlot(r, ItemStack.read(nbt));
                }
            });
        }
        this.requiresUpdate = true;
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        ListNBT list = new ListNBT();
        this.handler.ifPresent(inventory -> {
            int slots = inventory.getSlots();
            for (int x = 0; x < slots; ++x) {
                ItemStack stack = inventory.getStackInSlot(x);
                if (!stack.isEmpty()) {
                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putByte("Slot", (byte) x);
                    stack.write(nbt);
                    list.add(nbt);
                }
            }
        });
        if (!list.isEmpty()) {
            compound.put("Items", list);
        }
        return compound;
    }

    public void updateTile() {
        this.requestModelDataUpdate();
        this.markDirty();
        if (this.getWorld() != null) {
            this.getWorld().notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
        }
    }


    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), -1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    @Nonnull
    public CompoundNBT getUpdateTag() {
        return this.serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.deserializeNBT(tag);
    }


    @Nonnull
    @Override
    abstract public int[] getSlotsForFace(@Nonnull Direction side);

    @Override
    abstract public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction);

    @Override
    abstract public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction);

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    abstract public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack);


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.getInventory().getStackInSlot(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return this.getInventory().extractItem(index, count, false);
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return this.extractItem(index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        this.getInventory().setStackInSlot(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
