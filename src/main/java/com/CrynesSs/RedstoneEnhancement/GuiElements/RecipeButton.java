package com.CrynesSs.RedstoneEnhancement.GuiElements;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.recipes.AlloySmelting.AlloySmeltingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class RecipeButton extends ImageButton {

    private static final ResourceLocation loc = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/gui/alloy_recipe_buttons.png");

    public boolean isToggled() {
        return toggled;
    }

    private boolean toggled = false;

    public AlloySmeltingRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(AlloySmeltingRecipe recipe) {
        this.recipe = recipe;
    }

    public void toggle() {
        this.toggled = !this.toggled;
    }

    private AlloySmeltingRecipe recipe;

    public RecipeButton(int xIn, int yIn, int id, IPressable onPressIn) {
        super(xIn, yIn, 100, 28, 0, 0, 28, loc, 100, 56, onPressIn);
    }

    @Override
    public void onPress() {
        super.onPress();
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(loc);
        RenderSystem.disableDepthTest();
        int i = 0;
        if (this.isHovered() || this.toggled) {
            i += 28;
        }

        blit(this.x, this.y, 0, (float) i, this.width, this.height, 100, 56);
        RenderSystem.enableDepthTest();
    }
}
