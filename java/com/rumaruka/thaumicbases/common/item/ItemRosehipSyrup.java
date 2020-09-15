package com.rumaruka.thaumicbases.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.items.ItemsTC;

import java.util.ArrayList;
import java.util.List;

public class ItemRosehipSyrup extends Item
{
    public ItemRosehipSyrup()
    {
        this.setMaxStackSize(1);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote) entityLiving.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air
        if (entityLiving instanceof EntityPlayer)
        {
            this.removeNegativeEffect(entityLiving);
            ThaumcraftApi.internalMethods.addWarpToPlayer((EntityPlayer) entityLiving,-4,IPlayerWarp.EnumWarpType.TEMPORARY);



        }

        if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(ItemsTC.phial) : stack;
    }
    public static void removeNegativeEffect(EntityLivingBase entity)
    {
        List<Potion> potions = new ArrayList<>();
        potions.addAll(entity.getActivePotionMap().keySet());
        potions.stream().filter(potion -> potion.isBadEffect()).forEach(entity::removeActivePotionEffect);
    }



    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }


    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, net.minecraft.nbt.NBTTagCompound nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}