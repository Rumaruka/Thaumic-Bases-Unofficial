package com.rumaruka.thaumicbases.client;

import com.rumaruka.thaumicbases.core.TBCore;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TBCore.modid)
public class ModelRegistrationEvents {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(TBCore.modid);
    }
}
