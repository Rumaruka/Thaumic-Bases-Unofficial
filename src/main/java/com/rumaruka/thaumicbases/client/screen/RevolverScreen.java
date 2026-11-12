package com.rumaruka.thaumicbases.client.screen;

import com.rumaruka.thaumicbases.common.menu.RevolverMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RevolverScreen extends AbstractContainerScreen<RevolverMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/dispenser.png");

    public RevolverScreen(RevolverMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        // keep a single ammo slot area
        guiGraphics.fill(x + 61, y + 16, x + 115, y + 70, 0xFFC6C6C6);
        guiGraphics.fill(x + 79, y + 34, x + 97, y + 52, 0xFF8B8B8B);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        String shots = "Shots: " + this.menu.getAmmoCount() + "/6";
        guiGraphics.drawString(this.font, shots, 8, 20, 4210752, false);
        String jar = this.menu.getJarInfo();
        guiGraphics.drawString(this.font, jar, 8, 60, 4210752, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
