package com.rumaruka.thaumicbases.common.handlers;

import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.common.item.ItemRevolver;
import com.rumaruka.thaumicbases.core.TBCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = TBCore.modid)
public class RevolverEvents
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void renderZoomEvent(FOVUpdateEvent event)
	{
		if(Minecraft.getMinecraft().player.isSneaking())
		{
			if(!Minecraft.getMinecraft().player.getHeldItemMainhand().isEmpty() && Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemRevolver)
			{
				event.setNewfov(0.9F / (ItemRevolver.getUpgradeLevel(Minecraft.getMinecraft().player.getHeldItemMainhand(), RevolverUpgrade.accuracy) + 1));
			}
		}
	}
}