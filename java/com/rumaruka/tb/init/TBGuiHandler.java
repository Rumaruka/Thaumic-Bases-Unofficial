package com.rumaruka.tb.init;

import DummyCore.Client.GuiCommon;


import com.rumaruka.tb.common.inventory.ContainerOverchanter;
import com.rumaruka.tb.common.tiles.TileOverchanter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TBGuiHandler implements IGuiHandler {

    public static int OVERCHANTER = 0;


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == OVERCHANTER) {
            return new ContainerOverchanter(player.inventory, (TileOverchanter) world.getTileEntity(new BlockPos(x, y, z)));

        }
        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == OVERCHANTER) {
            return new GuiCommon(new ContainerOverchanter(player.inventory, (TileOverchanter) world.getTileEntity(new BlockPos(x, y, z))));

        }
        return null;
    }


}
