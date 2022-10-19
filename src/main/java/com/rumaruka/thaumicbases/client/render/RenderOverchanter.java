package com.rumaruka.thaumicbases.client.render;

import com.rumaruka.thaumicbases.common.tiles.TileOverchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderOverchanter extends TileEntitySpecialRenderer {



    //public static void renderItemStack(ItemStack stk, double posX, double posY, double posZ, double screenPosX, double screenPosY, double screenPosZ, float rotation, float colorRed, float colorGreen, float colorBlue, int renderPass, int itemsAmount, boolean force3DRender)
    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        TileOverchanter overchanter = (TileOverchanter) te;
        if(!overchanter.inventory.isEmpty()){
            GlStateManager.pushMatrix();
            int time = Minecraft.getMinecraft().player !=null ? Minecraft.getMinecraft().player.ticksExisted:0;

            GlStateManager.popMatrix();
        }
    }
}
