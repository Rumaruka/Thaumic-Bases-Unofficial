package com.rumaruka.tb.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public  interface ITobacco {

    public abstract void performTobaccoEffect(EntityPlayer smoker, ItemStack tobbaco, boolean isSilverwood);

}
