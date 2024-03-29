package com.rumaruka.thaumicbases.core;


import com.rumaruka.thaumicbases.common.handlers.EnchatmentHandler;
import com.rumaruka.thaumicbases.common.handlers.RegisterHandlers;
import com.rumaruka.thaumicbases.init.*;
import com.rumaruka.thaumicbases.network.proxy.TBServer;
import com.rumaruka.thaumicbases.utils.KnowledgeTB;
import com.rumaruka.thaumicbases.utils.TBConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;

import java.util.ArrayList;


@Mod(modid = TBCore.modid, name = TBCore.name, version = TBCore.version,dependencies = TBCore.dependencies)
public class TBCore {

    public static final String modid = "thaumicbases";
    public static final String name = "Thaumic Bases";
    public static final String version = "3.3.500.6r";
    public static final String dependencies = "required-after:thaumcraft@[6.1.BETA26,)";

    //Networking
    public static final String serverProxy = "com.rumaruka.thaumicbases.network.proxy.TBServer";
    public static final String clientProxy = "com.rumaruka.thaumicbases.network.proxy.TBClient";

    //Logger

    @SidedProxy(serverSide = serverProxy, clientSide = clientProxy)
    public static TBServer proxy;

    public static SimpleNetworkWrapper network;
    static {
        FluidRegistry.enableUniversalBucket(); // Must be called before preInit
    }

    public static TBCore instance;
    public static final TBConfig cfg = new TBConfig();
    public static ResearchCategory RES_CAT;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {

        instance = this;
        TBFluids.init.call();
        TBBlocks.init();
        TBBlocks.InGameRegister();
        TBItems.init();
        TBItems.InGameRegistr();


        MinecraftForge.EVENT_BUS.register(new KnowledgeTB());

        KnowledgeTB.clInit.call();
        TBTiles.setup();
        TBEnchant.setupEnchatments();
        proxy.preInit(e);
        FMLInterModComms.sendMessage("Wailla","register","thaumicbases");




    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new TBGuiHandler());
        RES_CAT = ResearchCategories.registerCategory(catName,null,null,icon,back,back2);
        proxy.registerRenderInformation();
        MinecraftForge.EVENT_BUS.register(new EnchatmentHandler());
        TBThaumonomicon.setup();

        KnowledgeTB.clInit.call();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("thaumbases");
        RegisterHandlers.init();
        TBOreDictionary.setup();
        proxy.init(e);

    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {

        proxy.Renders();


        KnowledgeTB.init.call();
        KnowledgeTB.insertAspects.call();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("thaumicbases");




    }



    public static final String catName ="THAUMICBASES";
    public static final ResourceLocation icon = new ResourceLocation("thaumicbases","textures/thaumonomicon/bases.png");
    public static final ResourceLocation back = new ResourceLocation("thaumicbases","textures/thaumonomicon/background.png");
    public static final ResourceLocation back2 = new ResourceLocation("thaumcraft","textures/gui/gui_research_back_over.png");
}