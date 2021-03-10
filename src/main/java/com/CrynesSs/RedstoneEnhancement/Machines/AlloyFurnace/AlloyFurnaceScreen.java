package com.CrynesSs.RedstoneEnhancement.Machines.AlloyFurnace;

import com.CrynesSs.RedstoneEnhancement.GuiElements.RecipeButton;
import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.Util.Init.RecipeSerializerInit;
import com.CrynesSs.RedstoneEnhancement.Util.Packages.ChangeRecipePackage;
import com.CrynesSs.RedstoneEnhancement.Util.Packages.Networking;
import com.CrynesSs.RedstoneEnhancement.recipes.AlloySmelting.AlloySmeltingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class AlloyFurnaceScreen extends ContainerScreen<AlloyFurnaceContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/gui/alloy_smelter.png");
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation(RedstoneEnhancement.MOD_ID, "textures/gui/example.png");

    private int tick = 0;
    private int rand = 0;
    private boolean flag1;
    private int toggledID = -1;
    private final ArrayList<RecipeButton> buttons;

    public AlloyFurnaceScreen(AlloyFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 175;
        this.ySize = 183;
        this.flag1 = true;
        this.buttons = new ArrayList<>();

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
        this.blit(l, j, 0, 0, this.xSize, this.ySize);
        AlloyFurnaceTE tile = this.container.tile;

        if (tile.isSmelting) {
            if (tick == 20) {
                tick = 0;
                rand = (int) (2 * ((Math.floor(Math.random() * 3)) + 1)); // 2-6
            }
            tick++;
            //*Middle Smelting Bar (Adding Randomness for a "sparkling" effect
            this.blit(x + 51, y + 23, 176, 13 + rand, 66, 2);
            //*Progress Canister
            //*Checking if the Recipe is actually still going
            if (tile.recipeTime > 0) {
                //*Checking if the Recipe is already finished
                if (tile.smelttime > tile.recipeTime) {
                    //*Render the Container as Full
                    this.blit(x + 101, y + 57 - 16, 176, 36 - 16, 11, 16);
                } else {
                    //*Get the amount of Pixels needed to be rendered onto the Screen
                    float dec = ((float) tile.smelttime / (float) tile.recipeTime);
                    int pixels = (int) Math.floor(dec * 17);
                    if (pixels == 17) {
                        pixels = 16;
                    }
                    //*Rendering the Pixels on the Screen
                    this.blit(x + 101, y + 57 - pixels, 176, 36 - pixels, 11, pixels);
                }

            }
        }
        //*Flame 1 rendering if it is burning and also if the Burntimes are greater than 0
        if (tile.isBurning && tile.burntime1 > 0 && tile.totalburntime1 > 0) {
            float dec = ((float) tile.burntime1 / (float) tile.totalburntime1);
            int flame1 = (int) Math.floor(dec * 15);
            this.blit(x + 35, y + 50 - flame1, 176, 12 - flame1, 14, flame1);
        }
        //*Flame 2 rendering if it is burning and also if the Burntimes are greater than 0
        if (tile.isBurning && tile.burntime2 > 0 && tile.totalburntime2 > 0) {
            float dec = ((float) tile.burntime2 / (float) tile.totalburntime2);
            int flame2 = (int) Math.floor(dec * 15);
            this.blit(x + 120, y + 50 - flame2, 176, 12 - flame2, 14, flame2);
        }
    }

    //*Init the Screen and Buttons
    @Override
    protected void init() {
        super.init();
        this.addButton(new ImageButton(this.guiLeft + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (but -> {
            //*Function what to do if the button is Pressed
            but.visible = true;
            if (flag1) {
                ((ImageButton) but).setPosition(this.guiLeft + 25, this.height / 2 - 49);
                flag1 = false;
            } else {
                ((ImageButton) but).setPosition(this.guiLeft + 5, this.height / 2 - 49);
                flag1 = true;
            }
        })));
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            RecipeButton button = this.addButton(new RecipeButton(this.guiLeft - 110, this.guiTop + (29 * i), i, but ->
                    this.buttonClicked(finalI, (RecipeButton) but)
            ));
            buttons.add(i, button);
            button.visible = false;
            button.active = false;
            if (i == this.container.tile.getRecipeID()) {
                button.toggle();
                this.toggledID = i;
            }
        }

        System.out.println("Init Screen");
    }

    @Override
    public void render(final int mouseX, final int mouseY, float partialTicks) {
        super.renderBackground(0);
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        renderRecipes();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);

    }

    protected void renderRecipes() {
        AlloyFurnaceTE tile = this.container.tile;
        if (Objects.requireNonNull(tile.getWorld()).isRemote) {
            //*Gets the Recipes
            Set<IRecipe<?>> recipes = AlloyFurnaceTE.findRecipeByType(RecipeSerializerInit.ALLOY_TYPE, tile.getWorld());
            int yOffset = 0;
            int renders = 0;
            RenderSystem.pushMatrix();
            RenderSystem.enableRescaleNormal();
            for (IRecipe<?> r : recipes) {
                if (r instanceof AlloySmeltingRecipe && renders < 5) {
                    AlloySmeltingRecipe rr = (AlloySmeltingRecipe) r;
                    ItemStack s1 = rr.getInput1Stack();
                    ItemStack s2 = rr.getInput2Stack();
                    ItemStack s3 = rr.getRecipeOutput();
                    //!Renders Item inside GUI
                    this.itemRenderer.zLevel = 0f;
                    //*Toggle the Button if a Recipe is there
                    this.buttons.get(renders).visible = true;
                    this.buttons.get(renders).active = true;
                    this.buttons.get(renders).setRecipe(rr);
                    renders++;
                    this.itemRenderer.zLevel = 100.0F;
                    //*Render the ItemStack1 into the GUI
                    this.itemRenderer.renderItemAndEffectIntoGUI(s1, this.guiLeft - 110 + 6, this.guiTop + yOffset + 6);
                    this.itemRenderer.renderItemOverlays(this.font, s1, this.guiLeft - 110 + 6, this.guiTop + yOffset + 6);
                    //*Render the ItemStack2 into the GUI
                    this.itemRenderer.renderItemAndEffectIntoGUI(s2, this.guiLeft - 110 + 26, this.guiTop + yOffset + 6);
                    this.itemRenderer.renderItemOverlays(this.font, s2, this.guiLeft - 110 + 26, this.guiTop + yOffset + 6);
                    //*Render the ItemStack3 into the GUI
                    this.itemRenderer.renderItemAndEffectIntoGUI(s3, this.guiLeft - 110 + 77, this.guiTop + yOffset + 6);
                    this.itemRenderer.renderItemOverlays(this.font, s3, this.guiLeft - 110 + 77, this.guiTop + yOffset + 6);
                    //*Increase the Y_Offset
                    yOffset += 29;
                    this.itemRenderer.zLevel = 0f;
                }
            }
            RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }
    }

    protected void buttonClicked(int id, RecipeButton button) {
        System.out.println(id);
        if (toggledID < 0) {
            toggledID = id;
            buttons.get(id).toggle();
            Networking.sendToServer(new ChangeRecipePackage(1, id));
        } else if (id != toggledID) {
            buttons.get(toggledID).toggle();
            buttons.get(id).toggle();
            toggledID = id;
            Networking.sendToServer(new ChangeRecipePackage(1, id));
        } else {
            toggledID = -1;
            buttons.get(id).toggle();
            Networking.sendToServer(new ChangeRecipePackage(1, -1));
        }
    }

}
