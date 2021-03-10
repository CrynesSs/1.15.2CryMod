package com.CrynesSs.RedstoneEnhancement;

import com.CrynesSs.RedstoneEnhancement.Util.Enums.EStructureType;
import com.CrynesSs.RedstoneEnhancement.structures.StructureData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class SimpleJsonDataManager<T> extends JsonReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public Map<ResourceLocation, JsonObject> objects = new HashMap<>();

    /**
     * The raw data that we parsed from json last time resources were reloaded
     **/
    protected Map<ResourceLocation, T> data = new HashMap<>();

    private final Class<T> dataClass;

    /**
     * @param folder This is the name of the folders that the resource loader looks in, e.g. assets/modid/FOLDER
     */
    public SimpleJsonDataManager(String folder, Class<T> dataClass) {
        super(GSON, folder);
        this.dataClass = dataClass;
    }

    /**
     * Get the data object represented by the json at the given resource location
     **/
    public T getData(ResourceLocation id) {
        return this.data.get(id);
    }

    /**
     * Called on resource reload, the jsons have already been found for us and we just need to parse them in here
     **/
    @Override
    protected void apply(@Nonnull Map<ResourceLocation, JsonObject> jsons, @Nonnull IResourceManager manager, @Nonnull IProfiler profiler) {
        this.objects = jsons;
        RedstoneEnhancement.LOGGER.info("ApplyingJsons");
        this.data = SimpleJsonDataManager.mapValues(jsons, this::getJsonAsData);
        this.MakeMapsandsubClasses(this.objects, this.data, null);


    }

    /**
     * Use a json object (presumably one from an assets/modid/mondobooks folder) to generate a data object
     **/
    protected T getJsonAsData(JsonObject json) {
        return GSON.fromJson(json, this.dataClass);
    }

    public Map<ResourceLocation, T> getAllData() {
        return this.data;
    }

    /**
     * Converts all the values in a map to new values; the new map uses the same keys as the old map
     **/

    public static <Key, In, Out> Map<Key, Out> mapValues(Map<Key, In> inputs, Function<In, Out> mapper) {
        Map<Key, Out> newMap = new HashMap<>();

        inputs.forEach((key, input) -> newMap.put(key, mapper.apply(input)));

        return newMap;
    }

    public void MakeMapsandsubClasses(Map<ResourceLocation, JsonObject> incomingData, Map<ResourceLocation, T> existingData, T[] subclasses) {
        Field[] allfields = this.dataClass.getDeclaredFields();
        RedstoneEnhancement.LOGGER.info(this.dataClass.getName());
        List<Field> fieldList = new ArrayList<>();
        for (Field f : allfields) {
            if (Modifier.isTransient(f.getModifiers())) {
                fieldList.add(f);
            }
        }
        RedstoneEnhancement.LOGGER.info(fieldList.toString());
        try {
            existingData.keySet().forEach(k -> {
                JsonObject obj = incomingData.get(k);
                if (dataClass.equals(StructureData.class)) {
                    StructureData s = (StructureData) existingData.get(k);
                    for (Field field : fieldList) {
                        if (obj.has(field.getName()) || field.getName().equals("type")) {
                            switch (field.getName()) {
                                case "blocks": {
                                    RedstoneEnhancement.LOGGER.info("SettingUpBoi");
                                    HashMap<String, String[]> blocks = new HashMap<>();
                                    JsonElement ele = obj.get("blocks");
                                    ele.getAsJsonObject().entrySet().forEach(e -> {
                                        String[] arr = new String[e.getValue().getAsJsonArray().size()];
                                        AtomicInteger i = new AtomicInteger(0);
                                        e.getValue().getAsJsonArray().forEach(element -> {
                                            arr[i.get()] = element.getAsString();
                                            i.getAndIncrement();
                                        });
                                        blocks.put(e.getKey(), arr);
                                    });
                                    s.setBlocks(blocks);
                                    break;
                                }
                                case "blockkeys": {
                                    RedstoneEnhancement.LOGGER.info("SettingUpBoi");
                                    HashMap<String, String> blockkeys = new HashMap<>();
                                    JsonElement ele = obj.get("blockkeys");
                                    ele.getAsJsonObject().entrySet().forEach(e -> blockkeys.put(e.getKey(), e.getValue().getAsString()));
                                    s.setBlockkeys(blockkeys);
                                    break;
                                }
                                case "blockPosByLayer": {
                                    RedstoneEnhancement.LOGGER.info("SettingUpBoi");
                                    s.setBlockPosByLayer(obj.get("blockPosByLayer").getAsJsonObject());
                                    break;
                                }
                                case "type": {
                                    System.out.println("Setting type");
                                    switch (s.getStructurename()) {
                                        case "multi_block_furnace": {
                                            s.setType(EStructureType.MULTI_BLOCK_FURNACE);
                                            break;
                                        }
                                        case "iron_fluid_tank": {
                                            s.setType(EStructureType.IRON_FLUID_TANK);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            existingData.put(k, (T) s);
                        } else {
                            RedstoneEnhancement.LOGGER.info(field.getName() + " missing field");
                        }
                    }
                }
            });
        } catch (NullPointerException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}

