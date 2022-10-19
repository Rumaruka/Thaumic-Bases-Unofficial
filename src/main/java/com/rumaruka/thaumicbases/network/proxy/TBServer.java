package com.rumaruka.thaumicbases.network.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

public class TBServer  {

    public void preInit(FMLPreInitializationEvent e){

    }

    public void Renders(){

    }
    public void init(FMLInitializationEvent e){}
    public void playGuitarSound(String sound)
    {

    }


    public World clientWorld()
    {
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


    public boolean fancyGraphicsEnable(){return false;}

    public EntityPlayer getPlayerOnSide(INetHandler handler) {
        if(handler instanceof NetHandlerPlayServer) {
            return ((NetHandlerPlayServer)handler).player;
        }
        return null;
    }
}
