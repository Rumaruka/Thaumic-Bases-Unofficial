package com.rumaruka.thaumicbases.core;

import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.common.entity.EntityRevolverBullet;
import com.rumaruka.thaumicbases.common.handlers.EnchantmentHandler;
import com.rumaruka.thaumicbases.common.handlers.RegisterHandlers;
import com.rumaruka.thaumicbases.init.*;
import com.rumaruka.thaumicbases.network.proxy.TBServer;


@Mod(TBCore.modid)
public class TBCore {

    public static final String modid = "thaumicbases";
    public static final String name = "Thaumic Bases";
    public static final String version = "3.6.023.0";
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
    public static ResearchCategory RES_CAT;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {

        instance = this;
        TBFluids.init.call();
        TBBlocks.init();
        TBBlocks.InGameRegister();
        TBItems.init();
        TBItems.InGameRegistr();
        EntityRegistry.registerModEntity(new ResourceLocation(TBCore.modid + ":" + "bullet"), EntityRevolverBullet.class, "revolver_bullet", 0, TBCore.instance, 32, 1, true);
        TBTiles.setup();
        TBEnchant.setupEnchatments();
        proxy.preInit(e);
        FMLInterModComms.sendMessage("Wailla", "register", "thaumicbases");


    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new TBGuiHandler());
        MinecraftForge.EVENT_BUS.register(new EnchantmentHandler());
        TBThaumonomicon.setup();
        TBResearch.registerResearch();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("thaumbases");
        RegisterHandlers.init();
        TBOreDictionary.setup();
        RevolverUpgrade.initConflictingMappings();
        proxy.init(e);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.Renders();

        network = NetworkRegistry.INSTANCE.newSimpleChannel("thaumicbases");
        proxy.postInit(e);
    }

}