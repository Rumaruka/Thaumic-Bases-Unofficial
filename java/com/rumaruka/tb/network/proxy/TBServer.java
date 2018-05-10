package com.rumaruka.tb.network.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class TBServer implements IGuiHandler {
    public void Renders(){

    }
    public void playGuitarSound(String sound)
    {

    }


    public World clientWorld()
    {
        return null;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {



        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    public void registerRenderInformation()
    {

    }

    public void lightning(World world, double sx, double sy, double sz, double ex, double ey, double ez, int dur, float curve, int speed, int type)
    {

    }

    public void sparkle(World w, double x, double y, double z, double dx, double dy, double dz, int color, float scale)
    {

    }
    public void addEnchantmentToCreativeTab(){

    }
}
