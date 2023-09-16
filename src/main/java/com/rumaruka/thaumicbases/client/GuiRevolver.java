package com.rumaruka.thaumicbases.client;

import com.rumaruka.thaumicbases.common.inventory.ContainerRevolver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiRevolver extends GuiContainer
{

	public static final ResourceLocation revolverTextures = new ResourceLocation("thaumicbases","textures/gui/revolver.png");
	public int blockedSlot;

	protected boolean checkHotbarKeys(int par1) {
		return false;
	}

	public GuiRevolver(InventoryPlayer inv, World w, int x, int y, int z)
	{
		super(new ContainerRevolver(inv,w,x,y,z));
		this.blockedSlot = inv.currentItem;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	//Draw a cross above the revolver
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(revolverTextures);
		float storedZLevel = this.zLevel;
		
		this.zLevel = 200;
		
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		drawTexturedModalRect(8 + blockedSlot * 18, 142, 240, 0, 16, 16);
		
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		
		this.zLevel = storedZLevel;
	}
	
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseZ)
	{
		 if (Minecraft.getMinecraft().player.inventory.mainInventory.get(this.blockedSlot) == ItemStack.EMPTY)
			 Minecraft.getMinecraft().player.closeScreen();
		 
		 Minecraft.getMinecraft().renderEngine.bindTexture(revolverTextures);
		 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		 
		 int k = (this.width - this.xSize) / 2;
		 int l = (this.height - this.ySize) / 2;
		 
		 drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		 
	}

}