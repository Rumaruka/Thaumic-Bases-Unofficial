package com.rumaruka.tb.common.item;

import com.rumaruka.tb.api.ITobacco;
import com.rumaruka.tb.init.TBItems;
import com.sun.org.apache.regexp.internal.RE;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.devices.BlockPedestal;
import thaumcraft.common.items.casters.ItemCaster;
import thaumcraft.common.items.resources.ItemMagicDust;

import javax.annotation.Nullable;

public class ItemSmokingPipe extends Item {
    public boolean isSilverwood;

    public ItemSmokingPipe(boolean silverwood) {

        super();
        isSilverwood = silverwood;

        this.setFull3D();
        this.setMaxStackSize(1);

        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }



    public ItemStack findTobacco(EntityPlayer smoker)
    {
        if (this.isTobacco(smoker.getHeldItem(EnumHand.OFF_HAND)))
        {
            return smoker.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isTobacco(smoker.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return smoker.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < smoker.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = smoker.inventory.getStackInSlot(i);

                if (this.isTobacco(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }


    protected boolean isTobacco(ItemStack stack)
    {
        return stack.getItem() instanceof TBTobacco;
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findTobacco(playerIn).isEmpty();
        if (!flag)
        {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
            else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }




    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 64;
    }



    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
            Vec3d look = player.getForward();
            double x = player.posX + look.x / 5;
            double y = player.posY + player.getEyeHeight() + look.y / 5;
            double z = player.posZ + look.z / 5 ;
            if (count < 32)
                player.world.spawnParticle(isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10, look.y / 10, look.z / 10);
        }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack tobacco = findTobacco(player);
        if(tobacco.isEmpty())
            return EnumActionResult.FAIL;
        ITobacco t = ITobacco.class.cast(tobacco.getItem());
        t.performTobaccoEffect(player, tobacco, isSilverwood);
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
        {
            ItemStack stk = player.inventory.getStackInSlot(i);
            if(stk != ItemStack.EMPTY && new ItemStack(stk.getItem()) != ItemStack.EMPTY && stk.getItem() instanceof ITobacco)
            {
                    getMaxItemUseDuration(stk);
                    player.inventory.decrStackSize(i, 1);
                break;
            }
        }
        Vec3d look = player.getForward();
        for(int i = 0; i < 100; ++i)
        {
            double x = player.posX+look.x / 5;
            double y = player.posY+player.getEyeHeight()+look.y / 5;
            double z = player.posZ+look.z / 5;

            player.world.spawnParticle(isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x/10, look.y/10, look.z/10);


        }

        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }


}



