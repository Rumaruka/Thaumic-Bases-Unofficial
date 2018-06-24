package com.rumaruka.tb.common.item;

import com.rumaruka.tb.api.ITobacco;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSmokingPipe extends Item {
    public boolean isSilverwood;
    public ItemSmokingPipe(boolean silverwood)
    {
        super();
        isSilverwood = silverwood;
        this.setFull3D();
        this.setMaxStackSize(1);
    }
    public ItemStack getTobacco(EntityPlayer p){
        for(int i = 0; i < p.inventory.getSizeInventory(); ++i)
        {
            ItemStack stk = p.inventory.getStackInSlot(i);
            if(stk != null && stk.getItem() != null && stk.getItem() instanceof ITobacco)
                return stk;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(this.getTobacco(playerIn)!=ItemStack.EMPTY){
           playerIn.setActiveHand(EnumHand.MAIN_HAND);

        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        Vec3d look = player.getLookVec();
        double x = player.posX+look.x/5;
        double y = player.posY+player.getEyeHeight()+look.y/5;
        double z = player.posZ+look.z/5;
        if(count < 32)
            player.world.spawnParticle(isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x/10, look.y/10, look.z/10);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

        ItemStack tobacco = getTobacco(player);
        if(tobacco ==  ItemStack.EMPTY)
            return EnumActionResult.PASS;
        ITobacco t = ITobacco.class.cast(tobacco.getItem());
        t.performTobaccoEffect(player, tobacco, isSilverwood);
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
        {
            ItemStack stk = player.inventory.getStackInSlot(i);
            if(stk != ItemStack.EMPTY && new ItemStack(stk.getItem()) != ItemStack.EMPTY && stk.getItem() instanceof ITobacco)
            {
                player.inventory.decrStackSize(i, 1);
                break;
            }
        }
        Vec3d look = player.getLookVec();
        for(int i = 0; i < 100; ++i)
        {
            double x = player.posX+look.x/5;
            double y = player.posY+player.getEyeHeight()+look.y/5;
            double z = player.posZ+look.z/5;

            player.world.spawnParticle(isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x/10, look.y/10, look.z/10);
        }

        return EnumActionResult.SUCCESS;
    }
}
