package com.CrynesSs.RedstoneEnhancement.structures;

import com.CrynesSs.RedstoneEnhancement.Util.Enums.EStructureType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
//Only needs the first 4 to generate a hollow cube

public class StructureData {
    private Integer[] xSize;
    private Integer[] ySize;
    private Integer[] zSize;
    private Integer[] controllerlayers;
    private Integer controllerAmount;
    private String structurename;
    private String controllername;
    private boolean matching;
    private boolean isCube;
    private boolean isFixedSize;
    private boolean controllerNeeded;
    private boolean controllerFixed;
    private boolean isHollow;
    private transient Map<String, String[]> blocks;
    private transient Map<String, String> blockkeys;
    private String[] controllerpos;
    private String[] blocksIncluded;
    private boolean nonRegular;
    private transient BlocksByLayer blockPosByLayer;
    private boolean hasStickouts;

    public EStructureType getType() {
        return type;
    }

    private transient EStructureType type;

    public Integer[] getxSize() {
        return xSize;
    }

    public Integer[] getySize() {
        return ySize;
    }

    public Integer[] getzSize() {
        return zSize;
    }

    public Integer[] getControllerlayers() {
        return controllerlayers;
    }

    public Integer getControllerAmount() {
        return controllerAmount;
    }

    public String getStructurename() {
        return structurename;
    }

    public boolean isHollow() {
        return isHollow;
    }


    public String getControllername() {
        return controllername;
    }

    public String[] getControllerpos() {
        return controllerpos;
    }

    public boolean isMatching() {
        return matching;
    }

    public boolean isCube() {
        return isCube;
    }

    public boolean isFixedSize() {
        return isFixedSize;
    }

    public boolean isControllerNeeded() {
        return controllerNeeded;
    }

    public boolean isControllerFixed() {
        return controllerFixed;
    }

    public Map<String, String[]> getBlocks() {
        return blocks;
    }

    public Map<String, String> getBlockkeys() {
        return blockkeys;
    }

    public boolean isNonRegular() {
        return nonRegular;
    }

    public BlocksByLayer getBlockPosByLayer() {
        return blockPosByLayer;
    }

    public boolean isHasStickouts() {
        return hasStickouts;
    }

    public void setBlocks(Map<String, String[]> blocks) {
        this.blocks = blocks;
    }

    public void setBlockkeys(Map<String, String> blockkeys) {
        this.blockkeys = blockkeys;
    }

    public void setBlockPosByLayer(JsonObject object) {
        this.blockPosByLayer = new BlocksByLayer(object);
    }

    public void setType(EStructureType type) {
        this.type = type;
    }

    public String[] getBlocksIncluded() {
        return blocksIncluded;
    }

    public boolean containsBlock(String Blockname) {
        for (String s : this.blocksIncluded) {
            if (s.equals(Blockname)) {
                return true;
            }
        }
        return false;
    }

    public boolean isControllerThere(String posIn) {
        for (String s : this.controllerpos) {
            if (s.equals(posIn)) {
                return true;
            }
        }
        return false;
    }

    public String getBlockbyKey(String name) {
        if (name.equals("a")) {
            return "minecraft:air";
        }
        return this.getBlockkeys().get(name);
    }

    public List<String> getBlockNames(String posIn) {
        List<String> keys = Arrays.asList(blocks.get(posIn));
        List<String> names = new ArrayList<>();
        keys.forEach(k -> names.add(blockkeys.get(k)));
        return names;
    }


    public static class BlocksByLayer {
        private HashMap<Integer, String[][]> Blocks;
        private static final String FIELD = "blockPosByLayer";

        public static String getField() {
            return FIELD;
        }

        public HashMap<Integer, String[][]> getBlocks() {
            return Blocks;
        }

        public BlocksByLayer(JsonObject object) {
            System.out.println("Setting up Blockposbylayer");
            HashMap<Integer, String[][]> tempmap = new HashMap<>();
            if (object.isJsonObject()) {
                object.entrySet().forEach(k -> {
                    int key = Integer.parseInt(k.getKey());
                    if (k.getValue().isJsonArray()) {
                        JsonArray arr = k.getValue().getAsJsonArray();
                        AtomicInteger i = new AtomicInteger(0);
                        String[][] value = new String[arr.size()][arr.get(0).getAsJsonArray().size()];
                        arr.forEach(e -> {
                            String[] layer = e.getAsString().split(",");
                            value[i.get()] = layer;
                            i.getAndIncrement();
                        });
                        tempmap.put(key, value);
                    }
                });
                tempmap.forEach((k, v) -> {
                    System.out.println(Arrays.deepToString(v));
                });
                Blocks = tempmap;
                getLayers();
            }
        }

        private List<String[]> getLayers() {
            List<String[]> layerList = new ArrayList<>();
            Blocks.forEach((k, v) -> {
                String[] layer = new String[v[0].length * v.length];
                int i = 0;
                for (String[] arr : v) {
                    for (String s : arr) {
                        layer[i] = s;
                        i++;
                    }
                }
                layerList.add(layer);
            });
            return layerList;
        }
    }
}
