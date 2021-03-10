package com.CrynesSs.RedstoneEnhancement.Machines.TemperingOven;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class TemperingOvenScreen extends ContainerScreen<TemperingOvenContainer> {
    public TemperingOvenScreen(TemperingOvenContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
