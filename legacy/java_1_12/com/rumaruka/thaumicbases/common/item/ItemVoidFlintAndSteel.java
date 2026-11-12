package com.rumaruka.thaumicbases.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.items.IWarpingGear;

public class ItemVoidFlintAndSteel extends ItemFlintAndSteel implements IWarpingGear {

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if ((stack.isItemDamaged()) && (entityIn != null) && (entityIn.ticksExisted % 20 == 0) && ((entityIn instanceof EntityLivingBase)))
            stack.damageItem(-1, (EntityLivingBase)entityIn);
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 1;
    }
}
