package com.rumaruka.thaumicbases.api.dummycore_remove.event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class DummyEvent_OnPacketRecieved extends Event{

    /**
     * The side the packet was accepted at
     */
    public final Side effectiveSide;

    /**
     * Packet data in a DummyData format
     */
    public final String recievedData;

    /**
     * The player that has recieved the packet. Can be null if the receiver is the server itself.
     */
    public final EntityPlayer recievedEntity;

    public DummyEvent_OnPacketRecieved(Side s, String str, EntityPlayer pl)
    {
        effectiveSide = s;
        recievedData = str;
        recievedEntity = pl;
    }
}
