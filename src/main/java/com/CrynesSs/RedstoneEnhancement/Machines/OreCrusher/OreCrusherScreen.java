package com.CrynesSs.RedstoneEnhancement.Machines.OreCrusher;

import com.CrynesSs.RedstoneEnhancement.GuiElements.RecipeButton;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;

public class OreCrusherScreen extends ContainerScreen<OreCrusherContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/gui/ore_crusher.png");
    private boolean flag1;
    private ArrayList<RecipeButton> buttons = new ArrayList<>();

    public OreCrusherScreen(OreCrusherContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 183;
        this.flag1 = true;
        this.buttons = new ArrayList<>();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        //*Checking for Null
        if (this.minecraft == null) {
            return;
        }
        //*Bind the Texture that is rendered in the Background
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        //*Halves the Screen Width(So it is centered Vertically)
        int x = (this.width - this.xSize) / 2;
        //*Halves the Screen Height(So it is centered Horizontally)
        int y = (this.height - this.ySize) / 2;
        int j = this.guiTop;
        int l = this.guiLeft;
        //*Position x of Screen,Position y of Screen,x start of texture,y start of texture,x width of texture,y width of texture
        this.blit(l, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void render(final int mouseX, final int mouseY, float partialTicks) {
        super.renderBackground(0);
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

    }
}
