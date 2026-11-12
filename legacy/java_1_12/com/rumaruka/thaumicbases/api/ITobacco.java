package com.rumaruka.thaumicbases.api;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public  interface ITobacco {

   void performTobaccoEffect(Player smoker, ItemStack tobbaco, boolean isSilverwood);

}
