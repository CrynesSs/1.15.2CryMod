package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import com.CrynesSs.RedstoneEnhancement.Util.Interfaces.IPipe;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//* SAVE EVERYTHING RELEVANT AS NBT
public class TransportPipeTileEntity extends TileEntity implements IPipe, ITickableTileEntity {
    public HashMap<ItemHandle, Vec3d> ITEMPOS = new HashMap<>();
    private final List<Direction> DIRECTIONS = new ArrayList<>();
    private Map<ItemHandle, Integer> trackItem = new HashMap<>();
    private final boolean[] dirs = new boolean[6];
    private Direction lastDirection;
    private boolean setup = true;
    private final int tick = 0;

    public TransportPipeTileEntity() {
        super(TileEntityTypes.TPIPE.get());
    }

    public void setDIRECTIONS(BlockState state) {
        this.DIRECTIONS.clear();
        if (state.get(AbstractTransportPipe.NORTH)) {
            this.DIRECTIONS.add(Direction.NORTH);
        }
        if (state.get(AbstractTransportPipe.SOUTH)) {
            this.DIRECTIONS.add(Direction.SOUTH);
        }
        if (state.get(AbstractTransportPipe.EAST)) {
            this.DIRECTIONS.add(Direction.EAST);
        }
        if (state.get(AbstractTransportPipe.WEST)) {
            this.DIRECTIONS.add(Direction.WEST);
        }
        if (state.get(AbstractTransportPipe.UP)) {
            this.DIRECTIONS.add(Direction.UP);
        }
        if (state.get(AbstractTransportPipe.DOWN)) {
            this.DIRECTIONS.add(Direction.DOWN);
        }
        this.markDirty();
    }


    public Vec3d halfVector(Vec3d vec) {
        double x = vec.x / 2f;
        double y = vec.y / 2f;
        double z = vec.z / 2f;
        return new Vec3d(x, y, z);
    }


    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        int i = 0;
        System.out.println("Getting Values...");
        while (compound.contains("x" + i)) {
            ITEMPOS.clear();
            Vec3d vec = new Vec3d(compound.getDouble("x" + i), compound.getDouble("y" + i), compound.getDouble("z" + i));
            Direction from = Direction.byIndex(compound.getInt("from" + i));
            Direction to = Direction.byIndex(compound.getInt("to" + i));
            String item = compound.getString("item" + i);
            int count = compound.getInt("count" + i);
            ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)), count);
            ITEMPOS.put(new ItemHandle(stack, from, to), vec);
            i++;
        }
        System.out.println("Got Values");
    }

    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        AtomicInteger number = new AtomicInteger(0);
        System.out.println("Writing ItemPos " + ITEMPOS.toString());
        ITEMPOS.forEach((k, v) -> {
            int j = number.get();
            compound.putDouble("x" + j, v.x);
            compound.putDouble("y" + j, v.y);
            compound.putDouble("z" + j, v.z);
            compound.putString("item" + j, Objects.requireNonNull(k.getStack().getItem().getRegistryName()).toString());
            compound.putInt("from" + j, k.getFrom().getIndex());
            compound.putInt("to" + j, k.getTo().getIndex());
            compound.putInt("count" + j, k.getStack().getCount());
            number.getAndIncrement();
        });
        return super.write(compound);
    }

    @Override
    public String toString() {
        return "TransportPipeTileEntity{" +
                "ITEMPOS=" + ITEMPOS +
                ", DIRECTIONS=" + DIRECTIONS +
                ", dirs=" + Arrays.toString(dirs) +
                ", lastDirection=" + lastDirection +
                ", setup=" + setup +
                ", tick=" + tick +
                '}';
    }

    @Override
    public boolean canAcceptItems() {
        return true;
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

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        this.read(pkt.getNbtCompound());
        BlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(this.pos, state, state, 3);
    }

    @Override
    public void passItem(ItemHandle handle) {
        if (world != null && !world.isRemote) {
            if (world.getTileEntity(this.getPos().offset(handle.getTo())) instanceof TransportPipeTileEntity) {
                ((TransportPipeTileEntity) Objects.requireNonNull(world.getTileEntity(this.getPos().add(handle.getTo().getDirectionVec())))).putItem(handle);
            } else {

                InventoryHelper.spawnItemStack(Objects.requireNonNull(this.getWorld()), this.pos.getX(), this.pos.getY(), this.pos.getZ(), handle.stack);
            }
        }
        if (!world.isRemote) {
            BlockState state = this.world.getBlockState(this.pos);
            this.world.notifyBlockUpdate(this.pos, state, state, 3);
        }
        this.markDirty();

    }


    @Override
    public void putItem(ItemHandle handle) {
        System.out.println("Putting Item from " + handle.getFrom() + "  to  " + handle.getTo());
        System.out.println(DIRECTIONS.toString());
        List<Direction> valids = new ArrayList<>();
        if (this.DIRECTIONS.size() == 2) {
            this.DIRECTIONS.forEach(k -> {
                if (!k.equals(handle.to.getOpposite())) {
                    valids.add(k);
                }
            });
        } else {
            this.DIRECTIONS.forEach(k -> {
                if (!k.equals(handle.to.getOpposite()) && !k.equals(lastDirection)) {
                    valids.add(k);
                }
            });
        }
        if (valids.size() == 1 && this.DIRECTIONS.size() == 2) {
            ItemHandle handle1 = new ItemHandle(handle.stack, handle.getTo().getOpposite(), valids.get(0));
            this.ITEMPOS.put(handle1, halfVector(new Vec3d(handle1.getFrom().getDirectionVec())));
            this.trackItem.put(handle1, 33);
        } else if (valids.size() != 0) {
            int choose = (int) Math.floor(Math.random() * valids.size());
            ItemHandle handle1 = new ItemHandle(handle.stack, handle.getTo().getOpposite(), valids.get(choose));
            this.ITEMPOS.put(handle1, halfVector(new Vec3d(handle.getFrom().getDirectionVec())));
            this.trackItem.put(handle1, 33);
            this.lastDirection = valids.get(choose);
        }

        if (!world.isRemote) {
            BlockState state = this.world.getBlockState(this.pos);
            this.world.notifyBlockUpdate(this.pos, state, state, 3);
        }
        this.markDirty();
    }

    public HashMap<ItemHandle, Vec3d> getITEMPOS() {
        return ITEMPOS;
    }

    public void setITEMPOS(HashMap<ItemHandle, Vec3d> ITEMPOS) {
        this.ITEMPOS = ITEMPOS;
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            trackProgress();
        }
        if (setup) {
            setup = false;
            if (world != null) {
                setDIRECTIONS(world.getBlockState(this.pos));
            }
        }
    }
    //*Implement Code to Track Item Progress Serverside here via Tick

    public void trackProgress() {
        Map<ItemHandle, Integer> updated = new HashMap<>();

        trackItem.forEach((k, v) -> {
            if (v > 0) {
                updated.put(k, v - 1);
            } else {
                this.ITEMPOS.remove(k);
                passItem(k);
            }
        });
        this.trackItem = updated;

    }

    private double getLengthBetweenPoints(Vec3d vec1, Vec3d vec2) {
        return Math.sqrt(Math.pow((vec1.x - vec2.x), 2) + Math.pow((vec1.y - vec2.y), 2) + Math.pow((vec1.z - vec2.z), 2));
    }


    public static class ItemHandle {
        public void setStack(ItemStack stack) {
            this.stack = stack;
        }

        private ItemStack stack;
        private final Direction from;
        private final Direction to;

        public ItemHandle(ItemStack stack, Direction from, Direction to) {
            this.stack = stack;
            this.from = from;
            this.to = to;
        }

        public String toString() {
            return this.from.toString() + " " + this.to.toString();
        }

        public ItemStack getStack() {
            return stack;
        }

        public Direction getFrom() {
            return from;
        }

        public Direction getTo() {
            return to;
        }
    }
}
