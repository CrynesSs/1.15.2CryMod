package com.CrynesSs.RedstoneEnhancement.Util.Helpers;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedstoneEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    @SubscribeEvent
    public static void onPlayerLeftClickEmpty(final PlayerInteractEvent.LeftClickEmpty event) {
        System.out.println("Player leftClicked Air");
    }

    @SubscribeEvent
    public static void onItemPickup(final PlayerEvent.ItemPickupEvent event) {
        System.out.println("Item Picked Up1");
    }

    @SubscribeEvent
    public static void onEntityItemPickup(final EntityItemPickupEvent event) {
        System.out.println("Item Picked Up2");
    }
}
