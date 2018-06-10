package com.rumaruka.tb.client.render;

import DummyCore.Utils.DrawUtils;
import com.rumaruka.tb.common.tiles.TileOverchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderOverchanter extends TileEntitySpecialRenderer {



    //public static void renderItemStack(ItemStack stk, double posX, double posY, double posZ, double screenPosX, double screenPosY, double screenPosZ, float rotation, float colorRed, float colorGreen, float colorBlue, int renderPass, int itemsAmount, boolean force3DRender)
    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        TileOverchanter overchanter = (TileOverchanter) te;
        if(!overchanter.inventory.isEmpty()){
            GlStateManager.pushMatrix();
            int time = Minecraft.getMinecraft().player !=null ? Minecraft.getMinecraft().player.ticksExisted:0;
           // DrawUtils.renderItemStack(overchanter.inventory,0,0,0,x,y,z, time%360+partialTicks,0,1,1,1,1,false);

            GlStateManager.popMatrix();
        }
        if(overchanter.renderedLightning !=null){
            GlStateManager.pushMatrix();
            overchanter.renderedLightning.render(x+1D, y+1D, z+1D, partialTicks);
            GlStateManager.popMatrix();

        }
    }
}
