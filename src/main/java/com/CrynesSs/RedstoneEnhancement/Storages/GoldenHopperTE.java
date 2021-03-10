package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import com.CrynesSs.RedstoneEnhancement.Util.Interfaces.ICryHopper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class GoldenHopperTE extends TileEntity implements ICryHopper, ITickableTileEntity, INamedContainerProvider {
    private final int size;
    private final HashMap<Direction, IItemHandlerModifiable> inventories;

    public GoldenHopperTE(int size, Direction facing) {
        super(TileEntityTypes.GOLDEN_HOPPER.get());
        this.size = size;
        this.inventories = new HashMap<>();
        this.inventories.put(facing, getInventory(facing));
        this.inventories.put(facing.getOpposite(), getInventory(facing.getOpposite()));
    }

    public GoldenHopperTE() {
        super(TileEntityTypes.GOLDEN_HOPPER.get());
        this.size = 0;
        this.inventories = new HashMap<>();
    }

    protected LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);

    @Nonnull
    public IItemHandlerModifiable createHandler() {
        return new ItemStackHandler(this.size) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }
        };
    }

    @Override
    public IItemHandlerModifiable getInventory(Direction direction) {
        return this.inventories.get(direction);
    }

    @Override
    public ItemStack putItem() {
        return null;
    }

    @Override
    public ItemStack suckItem() {
        return null;
    }

    @Override
    public IItemHandlerModifiable getMyInventory() {
        return this.handler.orElse(createHandler());
    }

    @Override
    public void tick() {
        if (this.world != null && !this.world.isRemote) {

        }
    }

    public ItemStack checkPull() {

        return ItemStack.EMPTY;
    }

    public ItemStack checkSuck() {
        return ItemStack.EMPTY;
    }


    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("redsenhance.container.golden_hopper");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        return null;
    }
}
