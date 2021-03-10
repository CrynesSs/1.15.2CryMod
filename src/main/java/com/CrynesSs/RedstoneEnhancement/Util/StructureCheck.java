package com.CrynesSs.RedstoneEnhancement.Util;

import com.CrynesSs.RedstoneEnhancement.AbstractFurnaceClasses.RegularFurnaceBlock;
import com.CrynesSs.RedstoneEnhancement.DataManager;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.Helpers.arrays;
import com.CrynesSs.RedstoneEnhancement.Util.Helpers.lists;
import com.CrynesSs.RedstoneEnhancement.structures.IncompleteStructure;
import com.CrynesSs.RedstoneEnhancement.structures.IronFluidTank;
import com.CrynesSs.RedstoneEnhancement.structures.MultiBlockFurnace;
import com.CrynesSs.RedstoneEnhancement.structures.StructureData;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class StructureCheck {
    public static void checkStructure(BlockPos pos, Block block, World worldIn, PlayerEntity player) {
        System.out.println("Checking Structure");
        List<StructureData> structures = new ArrayList<>(DataManager.STRUCTURE_DATA.getAllData().values());
        structures.removeIf((StructureData s) -> !s.containsBlock(Objects.requireNonNull(block.getRegistryName()).toString()));
        if (!structures.isEmpty()) {
            structures.forEach(s -> {
                //Change to incompleteStructures List
                if (!s.isNonRegular()) {
                    Map<BlockPos, List<IncompleteStructure>> foundStructures = listValidStructures(s, pos, worldIn);
                    if (!foundStructures.isEmpty()) {
                        spawnStructures(foundStructures, worldIn, s);
                    }
                } else {
                    s.getBlockPosByLayer().getBlocks().forEach((k, v) -> System.out.println(Arrays.deepToString(v)));
                    HashMap<Integer, String[][]> blocks = s.getBlockPosByLayer().getBlocks();
                    List<BlockPos> corners = new ArrayList<>();
                    for (int j = 1; j < blocks.size() + 1; j++) {
                        String[][] stringarrarr = blocks.get(j);
                        for (int k = 0; k < stringarrarr.length; k++) {
                            String[] stringarr = stringarrarr[k];
                            for (int l = 0; l < stringarr.length; l++) {
                                if (!stringarr[l].equals("a") && s.getBlockbyKey(stringarr[l]).equals(Objects.requireNonNull(block.getRegistryName()).toString())) {
                                    corners.add(pos.add(new BlockPos(-k, -j, -l).add(0, 1, 0)));
                                }
                            }
                        }
                    }
                    System.out.println(corners.toString());

                    List<BlockPos> valids = new ArrayList<>();
                    corners.forEach(kk -> {
                        AtomicBoolean flag = new AtomicBoolean(true);
                        System.out.println("Checking Corner " + kk);
                        for (int j = 0; j < s.getxSize()[0]; j++) {
                            if (!flag.get()) {
                                break;
                            }
                            for (int k = 1; k < s.getySize()[0] + 1; k++) {
                                if (!flag.get()) {
                                    break;
                                }
                                for (int l = 0; l < s.getzSize()[0]; l++) {
                                    if (!flag.get()) {
                                        break;
                                    }
                                    System.out.println(Objects.requireNonNull(worldIn.getBlockState(kk.add(j, k - 1, l)).getBlock().getRegistryName()).toString() + "  " + s.getBlockbyKey(blocks.get(k)[j][l]));
                                    if (!s.getBlockbyKey(blocks.get(k)[j][l]).equals(Objects.requireNonNull(worldIn.getBlockState(kk.add(j, k - 1, l)).getBlock().getRegistryName()).toString())) {
                                        flag.set(false);
                                    }
                                }
                            }
                        }
                        if (flag.get()) {
                            valids.add(kk);
                        }
                    });
                    System.out.println(s.getStructurename() + "   " + "detected at " + valids.toString());

                }
            });
        } else {
            System.out.println("No Structures available");
        }

    }


    public static void spawnStructures(Map<BlockPos, List<IncompleteStructure>> input, World w, StructureData s) {
        System.out.println(input.toString());
        input.forEach((k, v) -> {
            if (v.size() == 1) {
                IncompleteStructure struc = (IncompleteStructure) v.toArray()[0];
                HashMap<BlockPos, Block> blocklist = new HashMap<>();
                for (int i = 0; i < struc.size.getX(); i++) {
                    for (int j = 0; j < struc.size.getZ(); j++) {
                        for (int h = 0; h < struc.size.getY(); h++) {
                            if (!Objects.requireNonNull(w.getBlockState(struc.corner.add(i, h, j)).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                blocklist.put(struc.corner.add(i, h, j), w.getBlockState(struc.corner.add(i, h, j)).getBlock());
                            }
                        }
                    }
                }
                switch (struc.type) {
                    case MULTI_BLOCK_FURNACE: {
                        MultiBlockFurnace furnace = new MultiBlockFurnace(struc.size, blocklist, struc.corner, s);
                        RedstoneEnhancement.STRUCTURES.add(furnace);
                        furnace.onCreated(w);
                    }
                    case IRON_FLUID_TANK: {
                        IronFluidTank tank = new IronFluidTank(struc.size, blocklist, struc.corner, s);
                        RedstoneEnhancement.STRUCTURES.add(tank);
                        tank.onCreated(w, tank);
                    }
                }
            } else {
                //here Logic for Multiple choices
            }
        });
    }

    public static Map<BlockPos, List<IncompleteStructure>> listValidStructures(StructureData s, BlockPos pos, World world) {
        System.out.println("Checking");
        Map<BlockPos, List<IncompleteStructure>> validStructures = new HashMap<>();
        List<String> BlockNames = new ArrayList<>(Arrays.asList(s.getBlocksIncluded()));
        BlockNames.add(s.getControllername());
        //*Find the possible Corners of the Structure
        List<BlockPos> possibleCorners = findCorners(s, pos, world, BlockNames);
        System.out.println(possibleCorners.toString());
        //*If there are no possible Corners break
        if (possibleCorners.isEmpty()) {
            return validStructures;
        }
        //*Find valid Sizes to those Corners
        Map<BlockPos, List<BlockPos>> posSizes = findSizes(s, world, possibleCorners, BlockNames);
        //*Check every Block
        posSizes.forEach((k, v) -> {
            List<IncompleteStructure> temp = isValid(k, v, s, world);
            if (!temp.isEmpty()) {
                validStructures.put(k, temp);
            }
        });
        //*Return the structures found for deviding logic if necessary
        return validStructures;
    }

    public static List<BlockPos> findCorners(StructureData s, BlockPos pos, World world, Collection<String> BlockNames) {
        List<BlockPos> possibleCorners = new ArrayList<>();
        for (int ii = 0; ii < arrays.max(s.getySize()); ii++) {
            BlockPos newPos1 = pos.add(0, -ii, 0);
            if ((BlockNames.contains(Objects.requireNonNull(world.getBlockState(newPos1).getBlock().getRegistryName()).toString())) || ((Objects.requireNonNull(world.getBlockState(newPos1).getBlock().getRegistryName()).toString().equals("minecraft:air")))) {
                for (int kk = 0; kk < arrays.max(s.getxSize()); kk++) {
                    BlockPos newPos2 = pos.add(-kk, -ii, 0);
                    if (BlockNames.contains(Objects.requireNonNull(world.getBlockState(newPos2).getBlock().getRegistryName()).toString())) {
                        for (int ll = 0; ll < arrays.max(s.getzSize()); ll++) {
                            BlockPos newPos3 = pos.add(-kk, -ii, -ll);
                            if (BlockNames.contains(Objects.requireNonNull(world.getBlockState(newPos3).getBlock().getRegistryName()).toString())) {
                                if (BlockNames.contains(Objects.requireNonNull(world.getBlockState(newPos3.add(1, 0, 0)).getBlock().getRegistryName()).toString()) && BlockNames.contains(Objects.requireNonNull(world.getBlockState(newPos3.add(0, 1, 0)).getBlock().getRegistryName()).toString()) && BlockNames.contains(Objects.requireNonNull(world.getBlockState(newPos3.add(0, 0, 1)).getBlock().getRegistryName()).toString()) && !Objects.requireNonNull(world.getBlockState(newPos3).getBlock().getRegistryName()).toString().equals("minecraft:air")) {
                                    if (world.getBlockState(newPos3).getBlock() instanceof RegularFurnaceBlock) {
                                        if (!world.getBlockState(newPos3).get(RegularFurnaceBlock.INVALIDSTRUCTURE)) {
                                            possibleCorners.add(newPos3);
                                        }
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        BlockPos outermost = lists.findOutermostCorner(possibleCorners);
        List<BlockPos> moreCorners = new ArrayList<>();
        if (!possibleCorners.isEmpty()) {
            for (int i : s.getxSize()) {
                if (world.getBlockState(outermost.add(i, 0, 0)).getBlock() instanceof RegularFurnaceBlock && !world.getBlockState(outermost.add(i, 0, 0)).get(RegularFurnaceBlock.INVALIDSTRUCTURE)) {
                    moreCorners.add(outermost.add(i, 0, 0));
                }
                if (world.getBlockState(outermost.add(-i, 0, 0)).getBlock() instanceof RegularFurnaceBlock && !world.getBlockState(outermost.add(-i, 0, 0)).get(RegularFurnaceBlock.INVALIDSTRUCTURE)) {
                    moreCorners.add(outermost.add(-i, 0, 0));
                }
            }
            for (int k : s.getzSize()) {
                if (world.getBlockState(outermost.add(0, 0, k)).getBlock() instanceof RegularFurnaceBlock && !world.getBlockState(outermost.add(0, 0, k)).get(RegularFurnaceBlock.INVALIDSTRUCTURE)) {
                    moreCorners.add(outermost.add(0, 0, k));
                }
                if (world.getBlockState(outermost.add(0, 0, -k)).getBlock() instanceof RegularFurnaceBlock && !world.getBlockState(outermost.add(0, 0, -k)).get(RegularFurnaceBlock.INVALIDSTRUCTURE)) {
                    moreCorners.add(outermost.add(0, 0, -k));
                }
            }
            possibleCorners.addAll(moreCorners);
        }
        return possibleCorners;
    }

    public static Map<BlockPos, List<BlockPos>> findSizes(StructureData s, World world, List<BlockPos> possibleCorners, Collection<String> BlockNames) {
        Map<BlockPos, List<BlockPos>> posSizes = new HashMap<>();
        ArrayList<BlockPos> size = new ArrayList<>();
        boolean flag1 = s.isCube();
        boolean flag2 = s.isMatching();
        boolean flag3 = s.isFixedSize();

        if (!s.isCube() && !s.isMatching() && !s.isFixedSize()) {
            for (BlockPos p : possibleCorners) {
                for (int i : s.getxSize()) {
                    for (int k : s.getySize()) {
                        for (int j : s.getzSize()) {
                            if (BlockNames.contains(Objects.requireNonNull(world.getBlockState(p.add(i - 1, k - 1, j - 1)).getBlock().getRegistryName()).toString())) {
                                size.add(new BlockPos(i, k, j));
                            }
                        }
                    }
                }
                if (!size.isEmpty()) {
                    System.out.println(size);
                    posSizes.put(p, new ArrayList<>(size));
                    size.clear();
                }
            }
        } else if (s.isMatching() && !s.isFixedSize()) {
            for (BlockPos p : possibleCorners) {
                int k = 0;
                for (int i : s.getxSize()) {
                    if (k == s.getxSize().length || k == s.getySize().length || k == s.getzSize().length) {
                        break;
                    }
                    if (BlockNames.contains(Objects.requireNonNull(world.getBlockState(p.add(s.getxSize()[k] - 1, s.getySize()[k] - 1, s.getzSize()[k] - 1)).getBlock().getRegistryName()).toString())) {
                        size.add(new BlockPos(s.getxSize()[k], s.getySize()[k], s.getzSize()[k]));
                    }
                    k++;
                }
                posSizes.put(p, new ArrayList<>(size));
                size.clear();
            }
        } else if (s.isCube() && !s.isFixedSize()) {
            for (int i : s.getxSize()) {
                size.add(new BlockPos(i - 1, i - 1, i - 1));
            }
            for (BlockPos p : possibleCorners) {
                posSizes.put(p, size);
            }
        } else if (s.isFixedSize()) {
            size.add(new BlockPos(s.getxSize()[0], s.getySize()[0], s.getzSize()[0]));
            for (BlockPos p : possibleCorners) {
                posSizes.put(p, size);
            }
        }
        System.out.println("possible Sizes : " + posSizes.toString());
        return posSizes;
    }

    public static boolean checkBlock(World w, BlockPos p, String name) {
        return Objects.requireNonNull(w.getBlockState(p).getBlock().getRegistryName()).toString().equals(name);
    }


    public static List<IncompleteStructure> isValid(BlockPos corner, List<BlockPos> sizes, StructureData s, World w) {
        AtomicInteger controllersFound = new AtomicInteger(0);
        List<BlockPos> controllerPos = new ArrayList<>();
        List<IncompleteStructure> templist = new ArrayList<>();
        AtomicBoolean flag1 = new AtomicBoolean(true);
        System.out.println(corner + "Checking this Corner with sizes " + sizes.toString());
        if (s.isNonRegular()) {
            s.getBlockPosByLayer().getBlocks().forEach((k, v) -> {
                int ki = 0, vi = 0;
                for (String[] sArr : v) {
                    if (!flag1.get()) {
                        break;
                    }
                    for (String sSingle : sArr) {
                        if (!flag1.get()) {
                            break;
                        }
                        if (!checkBlock(w, corner.add(vi, k - 1, ki), s.getBlockbyKey(sSingle))) {
                            flag1.set(false);
                        }
                        vi++;
                    }
                    ki++;
                }
            });
            if (flag1.get()) {
                templist.add(new IncompleteStructure(corner, sizes.get(0), s, true, s.getType()));
                return templist;
            }
        }
        for (BlockPos size : sizes) {
            System.out.println(size + "Checking this Size");
            controllersFound.set(0);
            flag1.set(true);
            try {
                s.getBlocks().forEach((k, v) -> {
                    if (flag1.get()) {
                        switch (k) {
                            case "onEdge": {
                                List<String> Blocknames = s.getBlockNames("onEdge");
                                BlockPos diaCorner = corner.add(size.getX() - 1, size.getY() - 1, size.getZ() - 1);
                                for (int h = 1; h <= size.getY() - 2; h++) {
                                    if (!flag1.get()) {
                                        break;
                                    }
                                    for (int i = 1; i <= size.getX() - 2; i++) {
                                        if (!flag1.get()) {
                                            break;
                                        }
                                        if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(i, h, 0)).getBlock().getRegistryName()).toString())) {
                                            if (s.isControllerThere("onEdge") && (Objects.requireNonNull(w.getBlockState(corner.add(i, h, 0)).getBlock().getRegistryName())).toString().equals(s.getControllername())) {
                                                System.out.println("Controllerfound 1");
                                                controllersFound.getAndIncrement();
                                                controllerPos.add(corner.add(i, h, 0));
                                            } else {
                                                System.out.println("Invalid1");
                                                System.out.println(corner.add(i, h, 0));
                                                flag1.set(false);
                                                break;
                                            }
                                        }
                                        if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(diaCorner.add(-i, -h, 0)).getBlock().getRegistryName()).toString())) {
                                            if (s.isControllerThere("onEdge") && (Objects.requireNonNull(w.getBlockState(diaCorner.add(-i, -h, 0)).getBlock().getRegistryName())).toString().equals(s.getControllername())) {
                                                System.out.println("Controllerfound 2");
                                                controllersFound.getAndIncrement();
                                                controllerPos.add(diaCorner.add(-i, -h, 0));
                                            } else {
                                                System.out.println("Invalid2");
                                                flag1.set(false);
                                                break;
                                            }
                                        }
                                    }
                                    if (flag1.get()) {
                                        for (int j = 1; j <= size.getZ() - 2; j++) {
                                            if (!flag1.get()) {
                                                break;
                                            }
                                            if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(0, h, j)).getBlock().getRegistryName()).toString())) {
                                                if (s.isControllerThere("onEdge") && (Objects.requireNonNull(w.getBlockState(corner.add(0, h, j)).getBlock().getRegistryName())).toString().equals(s.getControllername())) {
                                                    System.out.println("Controllerfound 3");
                                                    controllersFound.getAndIncrement();
                                                    controllerPos.add(corner.add(0, h, j));
                                                } else {
                                                    System.out.println("Invalid3");
                                                    System.out.println(corner.add(0, h, j));
                                                    flag1.set(false);
                                                    break;
                                                }
                                            }
                                            if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(diaCorner.add(0, -h, -j)).getBlock().getRegistryName()).toString())) {
                                                if (s.isControllerThere("onEdge") && (Objects.requireNonNull(w.getBlockState(diaCorner.add(0, -h, -j)).getBlock().getRegistryName())).toString().equals(s.getControllername())) {
                                                    System.out.println("Controllerfound 4");
                                                    controllersFound.getAndIncrement();
                                                    controllerPos.add(diaCorner.add(0, -h, -j));
                                                } else {
                                                    System.out.println("Invalid4");
                                                    flag1.set(false);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                            case "top": {
                                List<String> Blocknames = s.getBlockNames("top");
                                for (int i = 1; i <= size.getX() - 2; i++) {
                                    if (!flag1.get()) {
                                        break;
                                    }
                                    for (int j = 1; j <= size.getZ() - 2; j++) {
                                        if (!flag1.get()) {
                                            break;
                                        }
                                        if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(i, size.getY() - 1, j)).getBlock().getRegistryName()).toString())) {
                                            if (s.isControllerThere("top") && Objects.requireNonNull(w.getBlockState(corner.add(i, size.getY() - 1, j)).getBlock().getRegistryName()).toString().equals(s.getControllername())) {
                                                System.out.println("Controllerfound 5");
                                                controllersFound.getAndIncrement();
                                                controllerPos.add(corner.add(i, size.getY() - 1, j));
                                            } else {
                                                System.out.println("Invalid5 + " + size.getY() + "   " + corner.add(i, size.getY() - 1, j));
                                                flag1.set(false);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                            case "bottom": {
                                List<String> Blocknames = s.getBlockNames("bottom");
                                for (int i = 1; i <= size.getX() - 2; i++) {
                                    if (!flag1.get()) {
                                        break;
                                    }
                                    for (int j = 1; j <= size.getZ() - 2; j++) {
                                        if (!flag1.get()) {
                                            break;
                                        }
                                        if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(i, 0, j)).getBlock().getRegistryName()).toString())) {
                                            if (s.isControllerThere("bottom") && Objects.requireNonNull(w.getBlockState(corner.add(i, 0, j)).getBlock().getRegistryName()).toString().equals(s.getControllername())) {
                                                System.out.println("Controllerfound 6");
                                                controllersFound.getAndIncrement();
                                                controllerPos.add(corner.add(i, 0, j));
                                            } else {
                                                System.out.println("Invalid6");
                                                flag1.set(false);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                            case "frame": {
                                //*Still buggy with odd sized Structures, Too many with single, too less with 2 Structures next to each other
                                List<String> Blocknames = s.getBlockNames("bottom");
                                BlockPos diaCorner = corner.add(size.getX() - 1, size.getY() - 1, size.getZ() - 1);
                                for (int h = 0; h <= size.getY() - 1; h++) {
                                    if (!flag1.get()) {
                                        break;
                                    }
                                    if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(0, h, 0)).getBlock().getRegistryName()).toString())) {
                                        System.out.println("Invalid81");
                                        flag1.set(false);
                                        break;
                                    }
                                    if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(size.getX() - 1, h, 0)).getBlock().getRegistryName()).toString())) {
                                        System.out.println("Invalid82");
                                        flag1.set(false);
                                        break;
                                    }
                                    if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(0, h, size.getZ() - 1)).getBlock().getRegistryName()).toString())) {
                                        System.out.println("Invalid83");
                                        flag1.set(false);
                                        break;
                                    }
                                    if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(size.getX() - 1, h, size.getZ() - 1)).getBlock().getRegistryName()).toString())) {
                                        System.out.println("Invalid84");
                                        flag1.set(false);
                                        break;
                                    }
                                    if (h == 0 || h == size.getY() - 1) {
                                        for (int ii = 1; ii <= size.getX() - 2; ii++) {
                                            if (!flag1.get()) {
                                                break;
                                            }
                                            if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(ii, h, 0)).getBlock().getRegistryName()).toString())) {
                                                System.out.println("Invalid91");
                                                flag1.set(false);
                                                break;
                                            }
                                            if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(diaCorner.add(-ii, -h, 0)).getBlock().getRegistryName()).toString())) {
                                                System.out.println("Invalid92");
                                                flag1.set(false);
                                                break;
                                            }
                                        }
                                        for (int jj = 1; jj < size.getZ() - 2; jj++) {
                                            if (!flag1.get()) {
                                                break;
                                            }
                                            if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(corner.add(0, h, jj)).getBlock().getRegistryName()).toString())) {
                                                System.out.println("Invalid93");
                                                flag1.set(false);
                                                break;
                                            }
                                            if (!Blocknames.contains(Objects.requireNonNull(w.getBlockState(diaCorner.add(0, -h, -jj)).getBlock().getRegistryName()).toString())) {
                                                System.out.println("Invalid94");
                                                flag1.set(false);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("ExceptionCaugt");
            }
            //*checks middle Blocks
            if (s.isHollow()) {
                for (int i = 1; i <= size.getX() - 2; i++) {
                    if (!flag1.get()) {
                        break;
                    }
                    for (int j = 1; j <= size.getZ() - 2; j++) {
                        if (!flag1.get()) {
                            break;
                        }
                        for (int k = 1; k <= size.getY() - 2; k++) {
                            if (!checkBlock(w, corner.add(i, k, j), "minecraft:air") && !(w.getBlockState(corner.add(i, k, j)).getBlock() instanceof FlowingFluidBlock)) {
                                System.out.println("Invalid7");
                                flag1.set(false);
                                break;
                            }
                        }
                    }
                }
                //*Check here the Blocks if the Structure is in fact not Hollow
            } else {

            }
            //System.out.println(s.isControllerNeeded() + "   " + s.getControllerAmount() + "    " + controllersFound.get());
            if (s.isControllerNeeded() && s.getControllerAmount() != controllersFound.get()) {
                System.out.println(controllersFound.get());
                flag1.set(false);
            }
            if (flag1.get()) {
                System.out.println("This is valid");
                templist.add(new IncompleteStructure(corner, size, s, true, s.getType()));

            } else {
                System.out.println("This is not valid");
            }
        }
        return templist;
    }
}


