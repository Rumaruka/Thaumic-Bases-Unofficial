package com.rumaruka.tb.client.gui.elements;

import DummyCore.Client.GuiElement;
import net.minecraft.util.ResourceLocation;

public abstract class GuiSlots extends GuiElement {
    private ResourceLocation rec = new ResourceLocation("thaumicbases","textures/gui/slot_common.png");

    public int x;
    public int y;

    public GuiSlots(int i, int j) {
        x = i;
        y = j;
    }

    @Override
    public ResourceLocation getElementTexture() {
        return rec;
    }

    @Override
    public void draw(int posX, int posY, int mouseX, int mouseY) {
        this.drawTexturedModalRect(posX, posY, 0, 0, 17, 18);
        this.drawTexturedModalRect(posX+17, posY, 1, 0, 16, 18);
        this.drawTexturedModalRect(posX+17+16, posY, 1, 0, 16, 18);
        this.drawTexturedModalRect(posX+17+32, posY, 1, 0, 16, 18);
        this.drawTexturedModalRect(posX+17+48, posY, 1, 0, 17, 18);
        drawText(posX,posY);
    }

    public abstract void drawText(int posX, int posY);

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}