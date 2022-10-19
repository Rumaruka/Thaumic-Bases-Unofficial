package com.rumaruka.thaumicbases.api.dummycore_remove.utils;
import com.rumaruka.thaumicbases.api.dummycore_remove.event.DummyEvent_OnPacketRecieved;
import com.rumaruka.thaumicbases.core.TBCore;
import io.netty.channel.ChannelHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

//Internal
@ChannelHandler.Sharable
public class DummyPacketHandler implements IMessageHandler<DummyPacketIMSG, IMessage> {

    @Override
    public IMessage onMessage(DummyPacketIMSG message, MessageContext ctx)  {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
            handleMessage(message, ctx);
        });
        return null;
    }

    public void handleMessage(DummyPacketIMSG message, MessageContext ctx)
    {
        Side s = ctx.side;
        if(s == Side.CLIENT)
            MinecraftForge.EVENT_BUS.post(new DummyEvent_OnPacketRecieved(s, message.dataStr, TBCore.proxy.getPlayerOnSide(ctx.getClientHandler())));
        else
            MinecraftForge.EVENT_BUS.post(new DummyEvent_OnPacketRecieved(s, message.dataStr, TBCore.proxy.getPlayerOnSide(ctx.getServerHandler())));
    }

    public static void sendToAll(DummyPacketIMSG message)
    {
        TBCore.network.sendToAll(message);
    }

    public static void sendToAllAround(DummyPacketIMSG message, TargetPoint pnt)
    {
        TBCore.network.sendToAllAround(message, pnt);
    }

    public static void sendToAllAround(DummyPacketIMSG message, int dim)
    {
        TBCore.network.sendToDimension(message, dim);
    }

    public static void sendToPlayer(DummyPacketIMSG message, EntityPlayerMP player)
    {
        TBCore.network.sendTo(message, player);
    }

    public static void sendToServer(DummyPacketIMSG message)
    {
        TBCore.network.sendToServer(message);
    }

}