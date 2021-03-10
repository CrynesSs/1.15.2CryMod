package com.CrynesSs.RedstoneEnhancement.WorldSaved;

import com.CrynesSs.RedstoneEnhancement.DataManager;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.StructureCheck;
import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import com.CrynesSs.RedstoneEnhancement.structures.IncompleteStructure;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class StructureSave extends WorldSavedData {
    private static final String DATA_NAME = RedstoneEnhancement.MOD_ID + "_StructureData";
    private final List<AbstractStructure> structures = RedstoneEnhancement.STRUCTURES;
    private final List<IncompleteStructure> toSpawn = new ArrayList<>();

    public StructureSave() {
        super(DATA_NAME);
    }

    public StructureSave(String name) {
        super(name);
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt) {
        AtomicInteger i = new AtomicInteger(0);
        System.out.println("Reading Structures1 " + structures.size());
        while (nbt.contains("name" + i)) {
            serializeStructure(nbt, i.get());
            i.getAndIncrement();
        }
        System.out.println("Reading Structures2 " + structures.size());
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbt) {
        AtomicInteger index = new AtomicInteger(0);
        System.out.println("Saving Structures with size " + structures.size());
        for (AbstractStructure s : structures) {
            nbt.putString("name" + index, s.getTypof().getStructurename());
            nbt.putInt("sx" + index, s.getSize().getX());
            nbt.putInt("sy" + index, s.getSize().getY());
            nbt.putInt("sz" + index, s.getSize().getZ());
            nbt.putInt("cx" + index, s.getCorner().getX());
            nbt.putInt("cy" + index, s.getCorner().getY());
            nbt.putInt("cz" + index, s.getCorner().getZ());
            index.getAndIncrement();
        }
        return nbt;
    }

    public static StructureSave get(World world) {
        if (!(world instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }

        ServerWorld overworld = Objects.requireNonNull(world.getServer()).getWorld(world.dimension.getType());

        DimensionSavedDataManager storage = overworld.getSavedData();
        return storage.getOrCreate(StructureSave::new, DATA_NAME);
    }

    private void serializeStructure(CompoundNBT nbt, int index) {
        String name = nbt.getString("name" + index);
        BlockPos size = new BlockPos(nbt.getInt("sx" + index), nbt.getInt("sy" + index), nbt.getInt("sz" + index));
        BlockPos corner = new BlockPos(nbt.getInt("cx" + index), nbt.getInt("cy" + index), nbt.getInt("cz" + index));
        IncompleteStructure s = new IncompleteStructure(corner, size, DataManager.STRUCTURE_DATA.getData(new ResourceLocation(name)), true, DataManager.STRUCTURE_DATA.getData(new ResourceLocation(name)).getType());
        toSpawn.add(s);
    }

    public void loadStructures(World w) {
        toSpawn.forEach(i -> {
            HashMap<BlockPos, List<IncompleteStructure>> temp = new HashMap<>();
            List<IncompleteStructure> tempList = new ArrayList<>();
            tempList.add(i);
            temp.put(i.corner, tempList);
            StructureCheck.spawnStructures(temp, w, i.s);
        });
        toSpawn.clear();

    }

}
