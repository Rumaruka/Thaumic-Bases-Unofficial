package com.rumaruka.thaumicbases.api.dummycore_remove.utils;

import com.google.common.collect.Streams;
import com.rumaruka.thaumicbases.core.TBCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

public class AllUtils {

    public static void createNBTTag(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            return;
        }
        NBTTagCompound itemTag = new NBTTagCompound();
        stack.setTagCompound(itemTag);
    }

    /**
     * used to get the ItemStack's tag compound.
     * @version From DummyCore 1.0
     * @param stack - the ItemStack to work with.
     * @return NBTTagCompound of the ItemStack
     */
    public static NBTTagCompound getStackTag(ItemStack stack)
    {
        createNBTTag(stack);
        return stack.getTagCompound();
    }

    @Deprecated
    public static Enchantment[] enchantmentList()
    {

        return Streams.stream(Enchantment.REGISTRY).toArray(Enchantment[]::new);
    }

    public static void syncTileEntity(ITEHasGameData t, Side s)
    {
        String dataString = "||mod:DummyCore.TileSync"+t.getPosition()+t.getData();
        DummyPacketIMSG simplePacket = new DummyPacketIMSG(dataString);
        if(s == Side.CLIENT)
        {
            DummyPacketHandler.sendToAll(simplePacket);
        }
        if(s == Side.SERVER)
        {
            DummyPacketHandler.sendToServer(simplePacket);
        }
    }

    /**
     * Sends a given NBT as a packet
     * @param tileTag - the NBT to send
     * @param packetID - the ID of the packet
     */
    public static void syncTileEntity(NBTTagCompound tileTag, int packetID)
    {
        DummyPacketIMSG_Tile simplePacket = new DummyPacketIMSG_Tile(tileTag);
        TBCore.network.sendToAll(simplePacket);
    }

}
