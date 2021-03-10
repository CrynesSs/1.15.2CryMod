package com.CrynesSs.RedstoneEnhancement.TileEntities;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFController;
import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.RegularFurnaceBlock;
import com.CrynesSs.RedstoneEnhancement.DataManager;
import com.CrynesSs.RedstoneEnhancement.Machines.FurnaceController;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import com.CrynesSs.RedstoneEnhancement.Util.Interfaces.IFluidProvider;
import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import com.CrynesSs.RedstoneEnhancement.structures.MultiBlockFurnace;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BigFurnaceTileEntity extends InventoryTile implements IFluidProvider, ITickableTileEntity, INamedContainerProvider {
    private int maxCapacity;
    private int transmissionRate;
    private int heat;
    private int tick = 0;


    public void setS(AbstractStructure s) {
        this.requiresUpdate = true;
        this.markDirty();
        this.s = s;
    }

    private AbstractStructure s;
    private final Map<Fluid, Integer> fluidStored;
    private int maxFluids;
    private boolean hasStructure = false;

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        System.out.println("Reading values");
        this.maxCapacity = compound.getInt("cap");
        this.transmissionRate = compound.getInt("trans");
        this.maxFluids = compound.getInt("maxf");
        this.s = serializeStructure(compound, 0);
        if (this.s != null) {
            System.out.println(s.getSize());
        } else {
            System.out.println("Read s as null");
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        System.out.println("writing Values");
        compound.putInt("cap", maxCapacity);
        compound.putInt("trans", transmissionRate);
        compound.putInt("maxf", maxFluids);
        if (this.s != null) {
            compound = deserializeStructure(compound, 0);
        } else {
            System.out.println("Structure is null");
        }
        return super.write(compound);
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.read(tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 1, nbt);
    }

    @Override
    public boolean isRemoved() {
        return this.removed;
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public CompoundNBT deserializeStructure(CompoundNBT nbt, int index) {
        if (s == null) {
            System.out.println("s is null");
            return nbt;
        }
        BlockPos corner = s.getCorner();
        BlockPos size = s.getSize();
        nbt.putInt("sizex" + index, size.getX());
        nbt.putInt("sizey" + index, size.getY());
        nbt.putInt("sizez" + index, size.getZ());
        nbt.putInt("cornerx" + index, corner.getX());
        nbt.putInt("cornery" + index, corner.getY());
        nbt.putInt("cornerz" + index, corner.getZ());
        nbt.putString("typof" + index, s.getTypof().getStructurename());
        return nbt;
    }

    public static AbstractStructure serializeStructure(CompoundNBT nbt, int index) {
        if (!nbt.contains("sizex" + index)) {
            System.out.println("Serialize Structure failed");
            return null;
        }
        BlockPos size = new BlockPos(nbt.getInt("sizex" + index), nbt.getInt("sizey" + index), nbt.getInt("sizez" + index));
        BlockPos corner = new BlockPos(nbt.getInt("cornerx" + index), nbt.getInt("cornery" + index), nbt.getInt("cornerz" + index));
        String typof = nbt.getString("typof" + index).replace(Integer.toString(index), "");
        System.out.println(typof);
        switch (typof) {
            case "multi_block_furnace": {
                return new MultiBlockFurnace(size, null, corner, DataManager.STRUCTURE_DATA.getData(new ResourceLocation("redsenhance:multi_block_furnace")));
            }
            default:
                return null;
        }
    }


    IIntArray furnaceDate = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return BigFurnaceTileEntity.this.maxCapacity;
                }
                case 1: {
                    return BigFurnaceTileEntity.this.transmissionRate;
                }
                case 2: {
                    return BigFurnaceTileEntity.this.maxFluids;
                }

                default:
                    return 0;
            }

        }

        @Override
        public void set(int index, int value) {
            if (index == 2) {
                maxFluids = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };


    public BigFurnaceTileEntity() {
        super(TileEntityTypes.BIG_FURNACE.get(), 3);
        this.maxCapacity = 0;
        this.transmissionRate = 0;
        this.s = null;
        this.hasStructure = false;
        this.fluidStored = new HashMap<>();
        this.maxFluids = 0;
    }

    public static int getVolume(AbstractStructure st) {
        BlockPos size = st.getSize();
        return ((size.getX() - 2) * (size.getY() - 2) * (size.getZ() - 2));
    }

    public BigFurnaceTileEntity(int transmissionRate, int maxCapacity, AbstractStructure s, List<Fluid> fluidList, Integer maxFluids) {
        super(TileEntityTypes.BIG_FURNACE.get(), getVolume(s) * 3);
        this.maxFluids = maxFluids;
        this.maxCapacity = maxCapacity;
        this.transmissionRate = transmissionRate;
        this.s = s;
        this.hasStructure = true;
        this.fluidStored = new HashMap<>();
        fluidList.forEach(f -> fluidStored.put(f, 0));
        System.out.println("Created a BigfurnaceTileEntity with " + s.getTypof().getStructurename() + "with Size " + s.getSize());
        this.setupOven(s);
        this.markDirty();
        this.requiresUpdate = true;
        this.updateTile();
    }

    public void getPipesConnected() {
        s.getBlocksinStructure().forEach((k, v) -> {
            if (v instanceof RegularFurnaceBlock || v instanceof AbstractMBFController) {

            }
        });
    }

    public void setupOven(AbstractStructure s) {
        System.out.println(s.getBlockQuantity().toString());
    }

    @Override
    public Integer getTransferRate() {
        return furnaceDate.get(1);
    }

    @Override
    public Integer getFluidAmount(Fluid fluid) {
        if (this.hasFluid(fluid)) {
            return fluidStored.get(fluid);
        }
        return -1;
    }

    @Override
    public Integer getMaxStorage() {
        return this.furnaceDate.get(0);
    }

    @Override
    public boolean hasFluid(Fluid fluid) {
        return this.fluidStored.containsKey(fluid);
    }

    @Override
    public boolean canTakeFluid(Fluid fluid, Integer amount) {
        return false;
    }

    @Override
    public void fill(Fluid fluid, Integer amount) {

    }

    public AbstractStructure getStructure() {
        return s;
    }

    @Override
    public boolean isFluidProvider() {
        return true;
    }

    @Override
    public int maxFluidsAmount() {
        return this.furnaceDate.get(2);
    }

    @Override
    public void tick() {
        tick++;
        if (tick == 50 && Objects.requireNonNull(this.getWorld()).isRemote) {
            tick = 0;
            //System.out.println(RegistryHandler.STRUCTURES.size());
        }
        if (!Objects.requireNonNull(this.getWorld()).getBlockState(getPos()).get(FurnaceController.INVALIDSTRUCTURE)) {
            this.remove();
        }
        if (s != null && s.getBlocksinStructure() == null) {
            s.fillBlocks(this.getWorld());
        }
        if (s != null && s.getTypof() == null) {
            s.refreshTypof();
        }
        if (!RedstoneEnhancement.STRUCTURES.contains(s) && !this.getWorld().isRemote) {
            RedstoneEnhancement.STRUCTURES.add(s);
        }
        if (world != null && !world.isRemote) {
            performUpdate();
        }
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return false;
    }

    @Nonnull
    @Override
    public ISidedInventory createInventory(BlockState state, IWorld world, BlockPos pos) {
        return null;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity entity) {
        return null;
    }


    private void performUpdate() {

    }
}
