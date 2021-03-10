package com.CrynesSs.RedstoneEnhancement.multiBlocks;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.AbstractMBFController;
import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.RegularFurnaceBlock;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.Enums.EStructureBlocks;
import com.CrynesSs.RedstoneEnhancement.structures.StructureData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//* EVENT BUS DOES NOT NEED TO HAVE ,value : Dist.CLIENT at the End if it does not matter if it is happening Server or Clientside
@Mod.EventBusSubscriber(modid = RedstoneEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
abstract public class AbstractStructure extends Block {
    private HashMap<BlockPos, Block> BlocksinStructure;
    private final BlockPos SIZE;
    private Boolean isValid;
    private final BlockPos corner;

    public void setTypof(StructureData typof) {
        this.typof = typof;
    }

    private StructureData typof;

    public Map<String, Integer> getBlockQuantity() {
        return BlockQuantity;
    }

    private final Map<String, Integer> BlockQuantity = new HashMap<>();

    public HashMap<BlockPos, Block> getBlocksinStructure() {
        return BlocksinStructure;
    }

    public BlockPos getSize() {
        return SIZE;
    }

    public Boolean getValid() {
        return isValid;
    }

    public BlockPos getCorner() {
        return corner;
    }

    public StructureData getTypof() {
        return typof;
    }

    public abstract void refreshTypof();

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public AbstractStructure(BlockPos size, HashMap<BlockPos, Block> BlockList, BlockPos Corner, StructureData typof) {
        super(Block.Properties.create(Material.IRON));
        this.SIZE = size;
        this.BlocksinStructure = BlockList;
        this.corner = Corner;
        this.typof = typof;
        this.isValid = true;
    }

    public void fillBlocks(World w) {
        HashMap<BlockPos, Block> blocklist = new HashMap<>();
        List<Block> controllerList = new ArrayList<>();
        BlockPos size = getSize();
        for (int i = 0; i < size.getX(); i++) {
            for (int j = 0; j < size.getZ(); j++) {
                for (int h = 0; h < size.getY(); h++) {
                    if (!Objects.requireNonNull(w.getBlockState(corner.add(i, h, j)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                        blocklist.put(corner.add(i, h, j), w.getBlockState(corner.add(i, h, j)).getBlock());
                        if (w.getBlockState(corner.add(i, h, j)).getBlock() instanceof AbstractMBFController) {
                            controllerList.add(w.getBlockState(corner.add(i, h, j)).getBlock());
                        }
                    }
                }
            }
        }
        this.BlocksinStructure = blocklist;
        controllerList.forEach(b -> {
            AbstractMBFController con = (AbstractMBFController) b;
            con.s = this;
        });
    }

    public void remove(World w) {
        this.BlocksinStructure.forEach((k, v) -> {
            if (v instanceof RegularFurnaceBlock) {
                ((RegularFurnaceBlock) v).setnewBlockstate(Direction.NORTH, false, EStructureBlocks.CENTER, w, k);
            } else if (v instanceof AbstractMBFController) {
                ((AbstractMBFController) v).setInvalidstructure(w, false, k);
                ((AbstractMBFController) v).s = null;
            }
        });

    }

    public void onCreated(World w, AbstractStructure s) {
        blocksInStructurequantity();
        BlocksinStructure.forEach((k, v) -> {
            if (v instanceof RegularFurnaceBlock) {
                System.out.println("Setting new Blockstate");
                EStructureBlocks position = EStructureBlocks.CENTER;
                Direction direction = Direction.NORTH;

                if (((RegularFurnaceBlock) v).isCornerBlock(corner, getSize(), k)) {
                    int cornernum = ((RegularFurnaceBlock) v).whichCorner(corner, k, getSize());
                    switch (cornernum) {
                        case 1: {
                            position = EStructureBlocks.CORNERDOWN;
                            direction = Direction.SOUTH;
                            break;
                        }
                        case 2: {
                            position = EStructureBlocks.CORNERDOWN;
                            direction = Direction.WEST;
                            break;
                        }
                        case 3: {
                            position = EStructureBlocks.CORNERDOWN;
                            direction = Direction.NORTH;
                            break;
                        }
                        case 4: {
                            position = EStructureBlocks.CORNERDOWN;
                            direction = Direction.EAST;
                            break;
                        }
                        case 5: {
                            position = EStructureBlocks.CORNERUP;
                            direction = Direction.SOUTH;
                            break;
                        }
                        case 6: {
                            position = EStructureBlocks.CORNERUP;
                            direction = Direction.WEST;
                            break;
                        }
                        case 7: {
                            position = EStructureBlocks.CORNERUP;
                            direction = Direction.NORTH;
                            break;
                        }
                        case 8: {
                            position = EStructureBlocks.CORNERUP;
                            direction = Direction.EAST;
                            break;
                        }
                    }

                }
                if (((RegularFurnaceBlock) v).isEdge(corner, getSize(), k)) {
                    int edge_number = ((RegularFurnaceBlock) v).whichEdge(corner, k, getSize());
                    switch (edge_number) {
                        case 1: {
                            position = EStructureBlocks.EDGEDOWN;
                            direction = ((RegularFurnaceBlock) v).getFacing(corner, getSize(), k);
                            break;
                        }
                        case 2: {
                            position = EStructureBlocks.EDGEUP;
                            direction = ((RegularFurnaceBlock) v).getFacing(corner, getSize(), k);
                            break;
                        }
                        case 3: {
                            position = EStructureBlocks.EDGEVERTICAL;
                            direction = Direction.SOUTH;
                            break;
                        }
                        case 4: {
                            position = EStructureBlocks.EDGEVERTICAL;
                            direction = Direction.WEST;
                            break;
                        }
                        case 5: {
                            position = EStructureBlocks.EDGEVERTICAL;
                            direction = Direction.NORTH;
                            break;
                        }
                        case 6: {
                            position = EStructureBlocks.EDGEVERTICAL;
                            direction = Direction.EAST;
                        }
                    }

                }
                ((RegularFurnaceBlock) v).setnewBlockstate(direction, true, position, w, k);
            }
            if (v instanceof AbstractMBFController) {
                ((AbstractMBFController) v).s = s;
                ((AbstractMBFController) v).setInvalidstructure(w, true, k);

            }
        });
    }

    public void blocksInStructurequantity() {
        for (String k : typof.getBlocksIncluded()) {
            AtomicInteger l = new AtomicInteger(0);
            BlocksinStructure.values().forEach(j -> {
                if (Objects.requireNonNull(j.getRegistryName()).toString().equals(k)) {
                    l.getAndIncrement();
                }
            });
            BlockQuantity.put(k, l.get());
        }
    }

    public EStructureBlocks getStructureBlocks(BlockPos cornerPos, BlockPos blockPos, BlockPos size) {
        //CornerCheck
        if ((cornerPos.getX() == blockPos.getX() || cornerPos.getX() == blockPos.getX() - size.getX() + 1)
                && cornerPos.getZ() == blockPos.getZ() - size.getZ() + 1 || cornerPos.getZ() == blockPos.getZ()) {
            if (cornerPos.getY() == blockPos.getY()) {
                return EStructureBlocks.CORNERDOWN;
            } else if (cornerPos.getY() == blockPos.getY() - size.getY() + 1) {
                return EStructureBlocks.CORNERUP;
            }
        }
        //EdgeUp/EdgeDown/EdgeSide
        if (cornerPos.getX() < blockPos.getX() && cornerPos.getX() + size.getX() - 1 > blockPos.getX()
                && (cornerPos.getZ() == blockPos.getZ() || cornerPos.getZ() + size.getZ() - 1 == blockPos.getZ())) {
            if (cornerPos.getY() == blockPos.getY()) {
                return EStructureBlocks.EDGEDOWN;
            } else if (cornerPos.getY() + size.getY() - 1 == blockPos.getY()) {
                return EStructureBlocks.EDGEUP;
            }
        }
        if (cornerPos.getZ() < blockPos.getZ() && cornerPos.getZ() + size.getZ() - 1 > blockPos.getZ()
                && (cornerPos.getX() == blockPos.getX() || cornerPos.getX() + size.getX() - 1 == blockPos.getX())) {
            if (cornerPos.getY() == blockPos.getY()) {
                return EStructureBlocks.EDGEDOWN;
            } else if (cornerPos.getY() + size.getY() - 1 == blockPos.getY()) {
                return EStructureBlocks.EDGEUP;
            }
        }
        if (cornerPos.getY() < blockPos.getY() && cornerPos.getY() + size.getY() - 1 > blockPos.getY()) {
            if ((cornerPos.getX() == blockPos.getX() || cornerPos.getX() + size.getX() - 1 == blockPos.getX())
                    && (cornerPos.getZ() == blockPos.getZ() || cornerPos.getZ() + size.getZ() - 1 == blockPos.getZ())) {
                return EStructureBlocks.EDGEVERTICAL;
            }
        }
        return EStructureBlocks.CENTER;
    }


}
