package com.rumaruka.thaumicbases.network.proxy;

import com.rumaruka.thaumicbases.client.render.HerobrinesScytheMH;
import com.rumaruka.thaumicbases.client.render.RenderBullet;
import com.rumaruka.thaumicbases.client.render.RenderCampfire;
import com.rumaruka.thaumicbases.client.render.RenderOverchanter;
import com.rumaruka.thaumicbases.common.entity.EntityRevolverBullet;
import com.rumaruka.thaumicbases.common.tiles.TileCampfire;
import com.rumaruka.thaumicbases.common.tiles.TileOverchanter;
import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TBClient extends TBServer {


    @Override
    public void preInit(FMLPreInitializationEvent e) {
        ModelLoader.setCustomStateMapper(TBBlocks.pyrofluid,new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }



    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        registerRenderInformation();
    }


    @Override
    public void Renders() {
        TBBlocks.Render();
        TBItems.Renders();

}

    public void registerRenderInformation() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileOverchanter.class, new RenderOverchanter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new RenderCampfire());
        MinecraftForge.EVENT_BUS.register(new HerobrinesScytheMH());
        RenderingRegistry.registerEntityRenderingHandler(EntityRevolverBullet.class, new RenderBullet());

      /*  RenderAccessLibrary.registerItemRenderingHandler(TBItems.ukulele, new UkuleleRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(TBItems.revolver, new RenderRevolver());

        RenderAccessLibrary.registerItemRenderingHandler(TBItems.nodeFoci, new NodeFociRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(TBItems.spawnerCompass, new SpawnerCompassRenderer());


       */
    }

    @Override
    public boolean fancyGraphicsEnable() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }
}
