package com.rumaruka.thaumicbases.client.render;

import com.rumaruka.thaumicbases.common.item.ItemUkulele;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.client.lib.obj.AdvancedModelLoader;
import thaumcraft.client.lib.obj.IModelCustom;


public class ItemUkuleleRenderer extends TileEntityItemStackRenderer {

    public static TransformType transform = GUI;

    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("thaumicbases", "models/ukulele/ukulele.obj"));
    public static final ResourceLocation base = new ResourceLocation("thaumicbases", "textures/items/ukulele/ukulelebase.png");
    public static final ResourceLocation strings = new ResourceLocation("thaumicbases", "textures/items/ukulele/ukulelestrings.png");
    public static final ResourceLocation handle = new ResourceLocation("thaumicbases", "textures/items/ukulele/ukulelehandle.png");

    @Override
    public void renderByItem(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemUkulele))
            return;

        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();

        if (transform == GUI) {
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(45, 0, 0, 1);
            GlStateManager.translate(0.5, -2.6, 0);
            GlStateManager.scale(1, 1, 0.7);
        }

        if (transform == FIRST_PERSON_RIGHT_HAND || transform == FIRST_PERSON_LEFT_HAND) {
            GlStateManager.scale(1.8, 1.8, 1.8);
            GlStateManager.translate(2, 3, 2);
            GlStateManager.rotate(210, 0, 1, 0);
            GlStateManager.rotate(10, 0, 0, 1);

            GlStateManager.pushMatrix();

            Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().player.getLocationSkin());

            Render<?> render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(Minecraft.getMinecraft().player);
            RenderPlayer renderplayer = (RenderPlayer) render;

            GlStateManager.pushMatrix();

            GlStateManager.translate(0, 1, 2);
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(-90, 0, 0, 1);
            GlStateManager.translate(0, -1.3, 0);
            GlStateManager.rotate(20, 1, 0, 0);
            GlStateManager.rotate(-20, 0, 0, 1);
            GlStateManager.scale(1.5, 1.5, 1.5);

            if (Minecraft.getMinecraft().player.isHandActive()) {
                float mOffset = Minecraft.getMinecraft().player.ticksExisted % 30 * 12;
                float nOffset = Minecraft.getMinecraft().player.ticksExisted % 20 * 18;
                float oOffset = Minecraft.getMinecraft().player.ticksExisted % 10 * 36;
                GlStateManager.translate(0.3 + Math.sin(Math.toRadians(mOffset)) / 6, 0, 0);
                GlStateManager.translate(0, Math.sin(Math.toRadians(nOffset)) / 10, 0);
                GlStateManager.translate(0, 0, Math.sin(Math.toRadians(oOffset)) / 20);
            }

            renderplayer.renderRightArm(Minecraft.getMinecraft().player);

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();

            GlStateManager.translate(-0.5, 0.8, -1);
            GlStateManager.rotate(90, 1, 0, 0);
            GlStateManager.rotate(-90, 0, 0, 1);
            GlStateManager.translate(0, -1.3, 0);
            GlStateManager.rotate(20, 1, 0, 0);
            GlStateManager.rotate(-50, 0, 0, 1);
            GlStateManager.scale(1.5, 3, 1.5);

            if (Minecraft.getMinecraft().player.isHandActive()) {
                float mOffset = System.currentTimeMillis() / 40 % 20 * 18;
                float oOffset = Minecraft.getMinecraft().player.ticksExisted % 10 * 36;
                GlStateManager.translate(Math.sin(Math.toRadians(-mOffset)) / 6, 0, 0);
                GlStateManager.translate(0, 0, -0.03 + Math.sin(Math.toRadians(-oOffset)) / 100);
            }

            renderplayer.renderRightArm(Minecraft.getMinecraft().player);

            GlStateManager.popMatrix();

            GlStateManager.popMatrix();
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(base);
        model.renderPart("base_Cube.001");
        Minecraft.getMinecraft().renderEngine.bindTexture(strings);
        model.renderPart("strings_Cube.003");
        Minecraft.getMinecraft().renderEngine.bindTexture(handle);
        model.renderPart("hand_Cube.002");
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}
