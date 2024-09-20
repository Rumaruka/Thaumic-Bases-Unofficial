package com.rumaruka.thaumicbases.network.proxy;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;

import java.awt.*;

public class TBServer  {

    public void preInit(FMLPreInitializationEvent e){

    }

    public void Renders(){

    }
    public void init(FMLInitializationEvent e){
        GolemMaterial.register(new GolemMaterial("THAUMINITE", new String[]{"TB.ALCHEMY"}, new ResourceLocation("thaumicbases","textures/entity/golem/mat_thauminite.png"), new Color(75,101,220).getRGB(), 26, 12, 5, new ItemStack(TBItems.thauminite_plate), new ItemStack(ItemsTC.mechanismSimple), new EnumGolemTrait[]{EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FIREPROOF, EnumGolemTrait.LIGHT}));}


    public void postInit(FMLPostInitializationEvent e){
    }

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
