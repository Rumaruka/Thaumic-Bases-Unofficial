package com.rumaruka.thaumicbases.common.handlers;

import com.rumaruka.thaumicbases.core.TBCore;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class TBEventHandler {
    public static int clientUkuleleSoundPlayDelay = 0;

    @SubscribeEvent
    public void clientWorldTickEvent(TickEvent.ClientTickEvent event)
    {
        World world = TBCore.proxy.clientWorld();
        if(event.side == Side.CLIENT && event.phase == TickEvent.Phase.END && world != null && world.provider != null)
        {
            --clientUkuleleSoundPlayDelay;
        }
    }
}
