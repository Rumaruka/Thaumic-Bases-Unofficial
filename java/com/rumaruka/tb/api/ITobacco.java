package com.rumaruka.tb.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public  interface ITobacco {

   void performTobaccoEffect(EntityPlayer smoker, ItemStack tobbaco, boolean isSilverwood);

}
