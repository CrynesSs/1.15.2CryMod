package com.CrynesSs.RedstoneEnhancement.Util;

import com.CrynesSs.RedstoneEnhancement.DataManager;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.multiBlocks.AbstractStructure;
import com.CrynesSs.RedstoneEnhancement.structures.IncompleteStructure;
import com.CrynesSs.RedstoneEnhancement.structures.StructureData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StructureSaver extends WorldSavedData {
    private static final String DATA_NAME = RedstoneEnhancement.MOD_ID + "_structuredata";
    public static final List<IncompleteStructure> STRUCTURES_TO_SPAWN = new ArrayList<>();

    public StructureSaver() {
        super(DATA_NAME);
    }

    public StructureSaver(String name) {
        super(name);
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt) {
        int index = 0;
        while (nbt.contains("structure" + index)) {
            serializeAndAdd(nbt.getCompound("structure" + index));
            index++;
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        AtomicInteger index = new AtomicInteger(0);

        for (AbstractStructure s : RedstoneEnhancement.STRUCTURES) {
            compound = desrializeAndAdd(compound, s, index.get());
            index.getAndIncrement();
        }
        return compound;
    }


    public CompoundNBT desrializeAndAdd(CompoundNBT nbt, AbstractStructure s, int index) {
        BlockPos corner = s.getCorner();
        BlockPos size = s.getSize();
        StructureData k = s.getTypof();
        String structuretype = k.getStructurename();
        CompoundNBT subTag = new CompoundNBT();
        subTag.putInt("cornerx", corner.getX());
        subTag.putInt("cornery", corner.getY());
        subTag.putInt("cornerz", corner.getZ());
        subTag.putInt("sizex", size.getX());
        subTag.putInt("sizey", size.getY());
        subTag.putInt("sizez", size.getZ());
        subTag.putString("typof", structuretype);
        nbt.put("structure" + index, subTag);
        return nbt;
    }

    public void serializeAndAdd(CompoundNBT subtag) {
        String typof = subtag.getString("typof");
        StructureData s = DataManager.STRUCTURE_DATA.getData(new ResourceLocation(typof));
        BlockPos corner = new BlockPos(subtag.getInt("cornerx"), subtag.getInt("cornery"), subtag.getInt("cornerz"));
        BlockPos size = new BlockPos(subtag.getInt("sizex"), subtag.getInt("sizey"), subtag.getInt("sizez"));
        IncompleteStructure structure = new IncompleteStructure(corner, size, s, true, s.getType());
        STRUCTURES_TO_SPAWN.add(structure);
    }
}
