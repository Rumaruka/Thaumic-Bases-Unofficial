package com.rumaruka.thaumicbases.api.dummycore_remove.utils;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

//Internal
public class DummyPacketIMSG implements IMessage{

    public String dataStr;

    public DummyPacketIMSG()
    {

    }

    public DummyPacketIMSG(String data)
    {
        dataStr = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dataStr = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, dataStr);

    }

}
