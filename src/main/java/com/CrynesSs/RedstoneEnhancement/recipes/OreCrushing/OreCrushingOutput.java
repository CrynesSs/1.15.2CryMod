package com.CrynesSs.RedstoneEnhancement.recipes.OreCrushing;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class OreCrushingOutput {
    private final int[] weights;
    private final ItemStack[] outputs;
    private final int[][] amounts;
    private final int[][] prob;

    public OreCrushingOutput(int[] weights, ItemStack[] outputs, int[][] amounts, int[][] prob) {
        this.weights = weights;
        this.outputs = outputs;
        this.amounts = amounts;
        this.prob = prob;
    }

    public int[][] getAmounts() {
        return amounts;
    }

    public int[] getWeights() {
        return weights;
    }

    public ItemStack[] getOutputs() {
        return outputs;
    }

    public int[][] getProb() {
        return prob;
    }

    public OreCrushingOutput(JsonObject output) {
        int i = 0;
        ArrayList<int[]> amounts = new ArrayList<>();
        ArrayList<int[]> probs = new ArrayList<>();
        ArrayList<Integer> weigthlist = new ArrayList<>();
        ArrayList<ItemStack> stackList = new ArrayList<>();

        while (output.has(String.valueOf(i)) && output.isJsonObject()) {
            ItemStack stack = CraftingHelper.getItemStack((JsonObject) output.get(String.valueOf(i)), false);
            System.out.println(output.toString());
            int weight = output.getAsJsonObject(String.valueOf(i)).get("weight").getAsInt();
            JsonArray amountsarr = output.getAsJsonObject(String.valueOf(i)).get("amounts").getAsJsonArray();
            int[] amoun = new int[amountsarr.size()];
            AtomicInteger j = new AtomicInteger(0);
            amountsarr.forEach(k -> {
                amoun[j.get()] = k.getAsInt();
                j.getAndIncrement();
            });
            j.set(0);
            JsonArray probArr = output.getAsJsonObject(String.valueOf(i)).get("prob").getAsJsonArray();
            int[] prob = new int[probArr.size()];
            probArr.forEach(k -> {
                prob[j.get()] = k.getAsInt();
                j.getAndIncrement();
            });
            amounts.add(i, amoun);
            probs.add(i, prob);
            weigthlist.add(i, weight);
            stackList.add(i, stack);
            i++;
        }
        this.amounts = new int[amounts.size()][];
        this.prob = new int[probs.size()][];
        this.weights = new int[weigthlist.size()];
        this.outputs = new ItemStack[stackList.size()];
        for (int ii = 0; ii < amounts.size(); ii++) {
            this.amounts[ii] = amounts.get(ii);
        }
        for (int ii = 0; ii < probs.size(); ii++) {
            this.prob[ii] = probs.get(ii);
        }
        for (int ii = 0; ii < weigthlist.size(); ii++) {
            this.weights[ii] = weigthlist.get(ii);
        }
        for (int ii = 0; ii < stackList.size(); ii++) {
            this.outputs[ii] = stackList.get(ii);
        }
    }

    public void deserialize(PacketBuffer buffer) {
        buffer.writeVarIntArray(this.weights);
        buffer.writeInt(this.amounts.length);
        for (int[] i : this.amounts) {
            buffer.writeVarIntArray(i);
        }
        buffer.writeInt(this.prob.length);
        for (int[] i : this.prob) {
            buffer.writeVarIntArray(i);
        }
        buffer.writeInt(this.outputs.length);
        for (ItemStack s : this.outputs) {
            buffer.writeItemStack(s);
        }
    }

    public static OreCrushingOutput serialize(PacketBuffer buffer) {
        int[] weigths = buffer.readVarIntArray();
        int amountslength = buffer.readInt();
        int[][] amounts = new int[amountslength][];
        for (int i = 0; i < amountslength; i++) {
            amounts[i] = buffer.readVarIntArray();
        }
        int problength = buffer.readInt();
        int[][] probs = new int[problength][];
        for (int i = 0; i < amountslength; i++) {
            probs[i] = buffer.readVarIntArray();
        }
        int outputlength = buffer.readInt();
        ItemStack[] outputs = new ItemStack[outputlength];
        for (int i = 0; i < amountslength; i++) {
            outputs[i] = buffer.readItemStack();
        }
        return new OreCrushingOutput(weigths, outputs, amounts, probs);
    }

}
