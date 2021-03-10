package com.CrynesSs.RedstoneEnhancement;

import com.CrynesSs.RedstoneEnhancement.structures.ImageData;
import com.CrynesSs.RedstoneEnhancement.structures.StructureData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

public class DataManager {
    public static final SimpleJsonDataManager<ImageData> IMAGE_DATA = new SimpleJsonDataManager<>("exampletest", ImageData.class);
    public static final SimpleJsonDataManager<StructureData> STRUCTURE_DATA = new SimpleJsonDataManager<>("structure_plans", StructureData.class);

    public static void onClientInit() {
        IResourceManager manager = Minecraft.getInstance().getResourceManager();
        if (manager instanceof IReloadableResourceManager) {
            IReloadableResourceManager reloader = (IReloadableResourceManager) manager;
            reloader.addReloadListener(IMAGE_DATA);
            reloader.addReloadListener(STRUCTURE_DATA);
        }
    }

}
