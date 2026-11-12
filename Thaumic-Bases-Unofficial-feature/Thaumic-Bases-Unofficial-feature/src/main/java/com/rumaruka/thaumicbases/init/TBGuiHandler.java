package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.api.dummycore_remove.client.GuiCommon;
import com.rumaruka.thaumicbases.client.GuiRevolver;
import com.rumaruka.thaumicbases.client.GuiThaumicAnvil;
import com.rumaruka.thaumicbases.client.GuiVoidAnvil;
import com.rumaruka.thaumicbases.common.inventory.ContainerOverchanter;
import com.rumaruka.thaumicbases.common.inventory.ContainerRevolver;
import com.rumaruka.thaumicbases.common.inventory.ContainerThaumicAnvil;
import com.rumaruka.thaumicbases.common.inventory.ContainerVoidAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TBGuiHandler implements IGuiHandler {

    public static int OVERCHANTER = 0;
    public static int THAUM_ANVIL = 1;
    public static int REVOLVER = 2;
    public static int VOID_ANVIL = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == OVERCHANTER) {
            return new ContainerOverchanter(player.inventory, world.getTileEntity(new BlockPos(x, y, z)));

        }
        if (ID == THAUM_ANVIL) {
            return new ContainerThaumicAnvil(player.inventory, world, new BlockPos(x, y, z), player);

        }
        if(ID == REVOLVER)
            return new ContainerRevolver(player.inventory, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        if(ID == VOID_ANVIL)
            return new ContainerVoidAnvil(player.inventory, world, new BlockPos(x, y, z), player);


        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == OVERCHANTER) {
            return new GuiCommon(new ContainerOverchanter(player.inventory, world.getTileEntity(new BlockPos(x, y, z))));

        }
        if (ID == THAUM_ANVIL) {
            return new GuiThaumicAnvil(player.inventory, world, x, y, z);

        }
        if(ID == REVOLVER)
            return new GuiRevolver(player.inventory, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

        if(ID == VOID_ANVIL)
            return new GuiVoidAnvil(player.inventory, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

        return null;
    }


}
