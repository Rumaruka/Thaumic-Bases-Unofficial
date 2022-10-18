package com.rumaruka.thaumicbases.network.proxy;






import com.rumaruka.thaumicbases.client.render.HerobrinesScytheMH;
import com.rumaruka.thaumicbases.client.render.RenderCampfire;
import com.rumaruka.thaumicbases.client.render.RenderOverchanter;

import com.rumaruka.thaumicbases.common.tiles.TileCampfire;
import com.rumaruka.thaumicbases.common.tiles.TileOverchanter;

import com.rumaruka.thaumicbases.core.TBCore;
import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.beams.FXArc;
import java.lang.reflect.Field;

public class TBClient extends TBServer {


    @Override
    public void preInit(FMLPreInitializationEvent e) {





        ModelLoader.setCustomStateMapper(TBBlocks.pyrofluid,new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }



    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        OBJLoader.INSTANCE.addDomain(TBCore.modid);
    }


    @Override
    public void Renders() {
        TBBlocks.Render();
        TBItems.Renders();

}

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public void registerRenderInformation() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileOverchanter.class, new RenderOverchanter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new RenderCampfire());
        MinecraftForge.EVENT_BUS.register(new HerobrinesScytheMH());




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
