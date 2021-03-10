package com.CrynesSs.RedstoneEnhancement.Util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;


@Deprecated
public class NBTMapCodec<KEY, VALUE> {
    private final String name;
    private final BiConsumer<CompoundNBT, KEY> keyWriter;
    private final Function<CompoundNBT, KEY> keyReader;
    private final BiConsumer<CompoundNBT, VALUE> valueWriter;
    private final Function<CompoundNBT, VALUE> valueReader;

    /**
     * @param name        A unique identifier for the hashmap, allowing the map to be written into a CompoundNBT alongside other data
     * @param keyWriter   A function that, given a compoundNBT and a Key, writes that Key into the NBT
     * @param keyReader   A function that, given a compoundNBT, returns the Key written in that NBT
     * @param valueWriter A function that, given a compoundNBT and a Value, writes that Value into the NBT
     * @param valueReader A Function that ,given a compoundNBT, returns the Value written in that NBT
     */
    public NBTMapCodec(
            String name,
            BiConsumer<CompoundNBT, KEY> keyWriter,
            Function<CompoundNBT, KEY> keyReader,
            BiConsumer<CompoundNBT, VALUE> valueWriter,
            Function<CompoundNBT, VALUE> valueReader) {
        this.name = name;
        this.keyReader = keyReader;
        this.keyWriter = keyWriter;
        this.valueReader = valueReader;
        this.valueWriter = valueWriter;
    }

    /**
     * Reconstructs and returns a {@literal Map<KEY,VALUE>} from a CompoundNBT
     * If the nbt used was given by this.write(map), the map returned will be a reconstruction of the original Map
     *
     * @param nbt The CompoundNBT to read and construct the Map from
     * @return A Map that the data contained in the CompoundNBT represents
     */
    public Map<KEY, VALUE> read(final CompoundNBT nbt) {
        final Map<KEY, VALUE> newMap = new HashMap<>();

        final ListNBT keyList = nbt.getList(this.name, Constants.NBT.TAG_COMPOUND);

        final int keyListSize = keyList.size();

        if (keyListSize <= 0)
            return newMap;

        IntStream.range(0, keyListSize).mapToObj(keyList::getCompound)
                .forEach(keyNBT -> {
                    final KEY key = this.keyReader.apply(keyNBT);
                    final VALUE value = this.valueReader.apply(keyNBT);

                    newMap.put(key, value);
                });

        return newMap;
    }

    /**
     * Given a map and a CompoundNBT, writes the map into the NBT
     * The same compoundNBT can be given to this.read to retrieve that map
     *
     * @param map      A {@literal Map<KEY,VALUE>}
     * @param compound A CompoundNBT to write the map into
     * @return a CompoundNBT that, when used as the argument to this.read(nbt), will cause that function to reconstruct and return a copy of the original map
     */
    public CompoundNBT write(final Map<KEY, VALUE> map, final CompoundNBT compound) {
        final ListNBT listOfKeys = new ListNBT();
        map.entrySet().forEach(entry ->
        {
            final KEY key = entry.getKey();
            final VALUE value = entry.getValue();

            final CompoundNBT entryNBT = new CompoundNBT();
            this.keyWriter.accept(entryNBT, key);
            this.valueWriter.accept(entryNBT, value);

            listOfKeys.add(entryNBT);
        });

        compound.put(this.name, listOfKeys);

        return compound;
    }
}