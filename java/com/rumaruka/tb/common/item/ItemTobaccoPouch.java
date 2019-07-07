package com.rumaruka.tb.common.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import thaumcraft.Thaumcraft;
import thaumcraft.common.items.casters.ItemFocusPouch;

public class ItemTobaccoPouch extends Item implements IBauble {
public ItemTobaccoPouch (){

}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote){
            playerIn.openGui(Thaumcraft.instance,5,worldIn, MathHelper.floor(playerIn.posX),MathHelper.floor(playerIn.posY),MathHelper.floor(playerIn.posZ));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public NonNullList<ItemStack> getInventory(ItemStack item) {
        NonNullList<ItemStack> stackList = NonNullList.withSize(18, ItemStack.EMPTY);
        if (item.hasTagCompound()) {
            ItemStackHelper.loadAllItems(item.getTagCompound(), stackList);
        }

        return stackList;
    }

    public void setInventory(ItemStack item, NonNullList<ItemStack> stackList) {
        if (item.getTagCompound() == null) {
            item.setTagCompound(new NBTTagCompound());
        }

        ItemStackHelper.saveAllItems(item.getTagCompound(), stackList);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BELT;
    }
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
    }

    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    }

    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    }

    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
