package com.rumaruka.thaumicbases.client.render;

import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.common.item.ItemRevolver;
import com.rumaruka.thaumicbases.utils.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.*;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.obj.AdvancedModelLoader;
import thaumcraft.client.lib.obj.IModelCustom;

import java.util.ArrayList;


public class RenderRevolver extends TileEntityItemStackRenderer {

	public static TransformType transform = GUI;

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases","models/revolver/revolver.obj"));
	public static final ResourceLocation handle = new ResourceLocation("thaumicbases","textures/items/revolver/revolver_handle_uv.png");
	public static final ResourceLocation barrel = new ResourceLocation("thaumicbases","textures/items/revolver/revolver_barrel_uv.png");
	public static final ResourceLocation metal = new ResourceLocation("thaumicbases","textures/items/revolver/revolver_dark_metal.png");
	public static final ResourceLocation gun = new ResourceLocation("thaumicbases","textures/items/revolver/revolver_gun_uv.png");
	public static final ResourceLocation press = new ResourceLocation("thaumicbases","textures/items/revolver/revolver_press_uv.png");

	@Override
	public void renderByItem(ItemStack item) {

		GlStateManager.pushMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		double scale = 0.2D;
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(5, -1, 5);

		if(transform == FIRST_PERSON_RIGHT_HAND)
		{
			GlStateManager.rotate(135, 0, 1, 0);
			GlStateManager.rotate(2, 1, 0, 0);
			GlStateManager.translate(4, 9, 2);
			GlStateManager.scale(1.2, 1.2, 1.2);

			if(Minecraft.getMinecraft().player.isSneaking())
			{
				GlStateManager.rotate(0, 0, 1, 0);
				GlStateManager.translate(-6.2, 2, 3);
			}
		}

		if(transform == FIRST_PERSON_LEFT_HAND){
			GlStateManager.rotate(-45, 0, 1, 0);
			GlStateManager.rotate(88, 1, 0, 0);
			GlStateManager.translate(4, 9, 2);
			GlStateManager.scale(1.2, 1.2, 1.2);

			if(Minecraft.getMinecraft().player.isSneaking())
			{
				GlStateManager.rotate(0, 0, 1, 0);
				GlStateManager.translate(6.2, 2, 3);
			}
		}

		ResourceLocation handle = RenderRevolver.handle;
		ResourceLocation barrel = RenderRevolver.barrel;
		ResourceLocation metal = RenderRevolver.metal;
		ResourceLocation gun = RenderRevolver.gun;
		ResourceLocation press = RenderRevolver.press;

		ArrayList<Pair<RevolverUpgrade,Integer>> upgrades = ItemRevolver.getAllUpgradesFor(item);
		for(Pair<RevolverUpgrade,Integer> p : upgrades)
		{
			RevolverUpgrade ru = p.getFirst();
			handle = ru.getOverridePartTexture(item, 0, p.getSecond()) == null ? handle : ru.getOverridePartTexture(item, 0, p.getSecond());
			barrel = ru.getOverridePartTexture(item, 1, p.getSecond()) == null ? barrel : ru.getOverridePartTexture(item, 1, p.getSecond());
			metal = ru.getOverridePartTexture(item, 2, p.getSecond()) == null ? metal : ru.getOverridePartTexture(item, 2, p.getSecond());
			gun = ru.getOverridePartTexture(item, 3, p.getSecond()) == null ? gun : ru.getOverridePartTexture(item, 3, p.getSecond());
			press = ru.getOverridePartTexture(item, 4, p.getSecond()) == null ? press : ru.getOverridePartTexture(item, 4, p.getSecond());
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(handle);
		model.renderPart("Cylinder");
		model.renderPart("Plane");

		double rotation = item.hasTagCompound() ? item.getTagCompound().getDouble("renderedRotation") : 0;

		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 2.9D, 0);
		GlStateManager.translate(0, -Math.cos(Math.toRadians(rotation))*2.8D, 0);
		GlStateManager.translate(Math.sin(Math.toRadians(rotation))*2.8D, 0, 0);
		GlStateManager.rotate((float) rotation, 0, 0, 1);

		Minecraft.getMinecraft().renderEngine.bindTexture(barrel);
		model.renderPart("rBarrel_Cube.001");
		GlStateManager.popMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(metal);
		model.renderPart("Cube.003_Cube.004");
		model.renderPart("Cube.002_Cube.003");
		model.renderPart("Cube_Cube.001");
		Minecraft.getMinecraft().renderEngine.bindTexture(gun);
		model.renderPart("Cylinder.002");
		model.renderPart("Plane.001");
		Minecraft.getMinecraft().renderEngine.bindTexture(press);
		model.renderPart("Cube.001_Cube.005");

		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();

	}

}
