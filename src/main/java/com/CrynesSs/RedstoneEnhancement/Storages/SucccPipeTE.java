package com.CrynesSs.RedstoneEnhancement.Storages;

import com.CrynesSs.RedstoneEnhancement.Util.Init.Types.TileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class SucccPipeTE extends TileEntity implements ITickableTileEntity {
    private final List<Direction> validDirections = new ArrayList<>();
    private int tick = 0;
    private final Map<Direction, IInventory> inventoryMap = new HashMap<>();
    private IInventory active_inventory = null;
    private Direction inventoryDir = null;
    private boolean setup = true;


    public SucccPipeTE() {
        super(TileEntityTypes.SUCCPIPE.get());
    }

    public void setup(BlockState state) {
        if (state.get(AbstractTransportPipe.NORTH)) {
            this.validDirections.add(Direction.NORTH);
        }
        if (state.get(AbstractTransportPipe.SOUTH)) {
            this.validDirections.add(Direction.SOUTH);
        }
        if (state.get(AbstractTransportPipe.EAST)) {
            this.validDirections.add(Direction.EAST);
        }
        if (state.get(AbstractTransportPipe.WEST)) {
            this.validDirections.add(Direction.WEST);
        }
        if (state.get(AbstractTransportPipe.DOWN)) {
            this.validDirections.add(Direction.DOWN);
        }
        if (state.get(AbstractTransportPipe.UP)) {
            this.validDirections.add(Direction.UP);
        }
        checkForInventories();
        selectInventory();
        System.out.println(this.validDirections.toString() + "   " + pos);
    }

    public void checkForInventories() {
        System.out.println("checking for Inventories");
        Direction[] dirs = Direction.values();
        if (world != null) {
            for (Direction d : dirs) {
                getInventoryAtPosition(world, this.pos, d);
            }
        }
    }

    public void selectInventory() {
        if (!inventoryMap.isEmpty()) {
            active_inventory = (IInventory) inventoryMap.values().toArray()[0];
            inventoryDir = (Direction) inventoryMap.keySet().toArray()[0];
        }
    }

    public void pullItem() {
        if (!active_inventory.isEmpty()) {
            if (active_inventory instanceof FurnaceTileEntity) {
                ItemStack stack = active_inventory.getStackInSlot(2);
                if (stack.getCount() > 0) {
                    ItemStack stack1 = new ItemStack(stack.getItem(), 1);
                    active_inventory.decrStackSize(2, 1);
                    Direction[] valids = new Direction[6];
                    validDirections.forEach(k -> {
                        if (!k.equals(inventoryDir) && !inventoryMap.containsKey(k)) {
                            valids[0] = k;
                        }
                    });
                    System.out.println(Arrays.toString(valids));
                    if (world != null) {
                        if (world.getTileEntity(this.pos.offset(valids[0])) instanceof TransportPipeTileEntity) {
                            TransportPipeTileEntity tile = (TransportPipeTileEntity) world.getTileEntity(this.pos.offset(valids[0]));
                            tile.putItem(new TransportPipeTileEntity.ItemHandle(stack1, inventoryDir, valids[0]));
                        }

                    }

                }
            }
        }
    }


    @Override
    public void tick() {
        if (setup) {
            setup = false;
            if (world != null) {
                setup(world.getBlockState(this.pos));
            }
        }
        if (tick == 50) {
            tick = 0;
            pullItem();
        }
        tick++;
    }

    public void getInventoryAtPosition(World worldIn, BlockPos pos, Direction d) {
        IInventory iinventory = null;
        BlockPos blockpos = pos.offset(d);
        BlockState blockstate = worldIn.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof ISidedInventoryProvider) {
            iinventory = ((ISidedInventoryProvider) block).createInventory(blockstate, worldIn, blockpos);
        } else if (blockstate.hasTileEntity()) {
            TileEntity tileentity = worldIn.getTileEntity(blockpos);
            if (tileentity instanceof IInventory) {
                iinventory = (IInventory) tileentity;
                if (iinventory instanceof ChestTileEntity && block instanceof ChestBlock) {
                    iinventory = ChestBlock.func_226916_a_((ChestBlock) block, blockstate, worldIn, blockpos, true);
                }
            }
        }
        if (iinventory != null) {
            inventoryMap.put(d, iinventory);
        }
    }
}
