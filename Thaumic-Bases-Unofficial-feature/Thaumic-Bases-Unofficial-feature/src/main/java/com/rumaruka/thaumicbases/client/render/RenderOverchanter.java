package com.rumaruka.thaumicbases.client.render;

import com.rumaruka.thaumicbases.common.tiles.TileOverchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderOverchanter extends TileEntitySpecialRenderer<TileOverchanter>{

	public void render(TileOverchanter ped, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(ped, x, y, z, partialTicks, destroyStage, alpha);
		if (!ped.isEmpty() && !ped.inventory.isEmpty()) {
			EntityItem entityitem;
			float ticks = Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + partialTicks;
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x + 0.5f, (float)y + 0.75f, (float)z + 0.5f);
			GL11.glScaled(1.25, 1.25, 1.25);
			GL11.glRotatef(ticks % 360.0f, 0.0f, 1.0f, 0.0f);
			ItemStack is = ped.inventory.copy();
			is.setCount(1);
			entityitem = new EntityItem(Minecraft.getMinecraft().world, 0.0, 0.0, 0.0, is);
			entityitem.hoverStart = 0.0f;
			RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
			rendermanager.renderEntity(entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
			GL11.glPopMatrix();
		}
	}
}