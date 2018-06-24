package com.rumaruka.tb.client.render;

import com.rumaruka.tb.common.tiles.TileCampfire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderCampfire extends TileEntitySpecialRenderer<TileCampfire> {

    @Override
    public void render(TileCampfire te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);


    }

}
