package com.rumaruka.thaumicbases.common.item;


import com.rumaruka.thaumicbases.api.ITobacco;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSmokingPipe extends Item {
    public boolean isSilverwood;

    public ItemSmokingPipe(boolean silverwood) {
        this.isSilverwood = silverwood;
        setFull3D();
        setMaxStackSize(1);
        addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null)
                    return 0.0F;
                return !(entityIn.getActiveItemStack().getItem() instanceof ItemSmokingPipe) ? 0.0F : ((stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F);
            }
        });

        addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {

            @SideOnly(Side.CLIENT)
           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {

                return (entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack) ? 1.0F : 0.0F;

            }

        });

    }



    public ItemStack findTobacco(EntityPlayer smoker) {

        if (isTobacco(smoker.getHeldItem(EnumHand.OFF_HAND)))
            /*  50 */ return smoker.getHeldItem(EnumHand.OFF_HAND);

        if (isTobacco(smoker.getHeldItem(EnumHand.MAIN_HAND)))
            /*  52 */ return smoker.getHeldItem(EnumHand.MAIN_HAND);

        for (int i = 0; i < smoker.inventory.getSizeInventory(); i++) {

            ItemStack itemstack = smoker.inventory.getStackInSlot(i);
            /*  57 */
            if (isTobacco(itemstack))
                /*  58 */ return itemstack;
            /*     */
        }
        /*  62 */
        return ItemStack.EMPTY;
        /*     */
    }


    protected boolean isTobacco(ItemStack stack) {

        return stack.getItem() instanceof TBTobacco;

    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {

        ItemStack itemstack = player.getHeldItem(handIn);

        boolean flag = !findTobacco(player).isEmpty();

        if (!flag)

            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);

        player.setActiveHand(handIn);

        return new ActionResult(EnumActionResult.SUCCESS, itemstack);

    }

    public void onPlayerStoppedUsing(ItemStack itemstack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        /*  92 */
        if (getMaxItemUseDuration(itemstack) - timeLeft < 20)
            /*     */ return;
        /*  93 */
        if (entityLiving instanceof EntityPlayer) {
            /*  94 */
            EntityPlayer player = (EntityPlayer) entityLiving;
            /*  95 */
            ItemStack tobacco = findTobacco(player);
            /*  96 */
            if (tobacco.isEmpty())
                /*     */ return;
            /*  98 */
            ITobacco t = (ITobacco) tobacco.getItem();
            /*  99 */
            t.performTobaccoEffect(player, tobacco, this.isSilverwood);
            /* 100 */
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                /* 101 */
                ItemStack stk = player.inventory.getStackInSlot(i);
                /* 102 */
                if (stk != ItemStack.EMPTY && !(new ItemStack(stk.getItem())).equals(ItemStack.EMPTY) && stk.getItem() instanceof ITobacco) {
                    /* 103 */
                    getMaxItemUseDuration(stk);
                    /* 104 */
                    player.inventory.decrStackSize(i, 1);
                    /*     */
                    break;
                    /*     */
                }
                /*     */
            }
            /* 108 */
            Vec3d look = entityLiving.getLookVec();
            /* 109 */
            for (int j = 0; j < 100; j++) {
                /* 110 */
                double x = player.posX + look.x / 5.0D;
                /* 111 */
                double y = player.posY + player.getEyeHeight() + look.y / 5.0D;
                /* 112 */
                double z = player.posZ + look.z / 5.0D;
                /* 113 */
                player.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D, new int[0]);
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        /* 123 */
        if (entityLiving instanceof EntityPlayer) {
            /* 124 */
            EntityPlayer player = (EntityPlayer) entityLiving;
            /* 125 */
            ItemStack tobacco = findTobacco(player);
            /* 126 */
            if (tobacco.isEmpty())
                /* 127 */ return stack;
            /* 128 */
            ITobacco t = (ITobacco) tobacco.getItem();
            /* 129 */
            t.performTobaccoEffect(player, tobacco, this.isSilverwood);
            /* 130 */
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                /* 131 */
                ItemStack stk = player.inventory.getStackInSlot(i);
                /* 132 */
                if (stk != ItemStack.EMPTY && !(new ItemStack(stk.getItem())).equals(ItemStack.EMPTY) && stk.getItem() instanceof ITobacco) {
                    /* 133 */
                    getMaxItemUseDuration(stk);
                    /* 134 */
                    player.inventory.decrStackSize(i, 1);
                    /*     */
                    break;
                    /*     */
                }
                /*     */
            }
            /* 138 */
            Vec3d look = entityLiving.getLookVec();
            /* 139 */
            for (int j = 0; j < 100; j++) {
                /* 140 */
                double x = player.posX + look.x / 5.0D;
                /* 141 */
                double y = player.posY + player.getEyeHeight() + look.y / 5.0D;
                /* 142 */
                double z = player.posZ + look.z / 5.0D;
                /* 143 */
                player.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D, new int[0]);
                /*     */
            }
            /*     */
        }
        /* 146 */
        return stack;
        /*     */
    }

    /*     */
    /*     */
    public EnumAction getItemUseAction(ItemStack stack) {
        /* 150 */
        return EnumAction.BOW;
        /*     */
    }

    /*     */
    /*     */
    public int getMaxItemUseDuration(ItemStack stack) {
        /* 154 */
        return 64;
        /*     */
    }

    /*     */
    /*     */
    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
        /* 161 */
        Vec3d look = entityLiving.getLookVec();
        /* 163 */
        double x = entityLiving.posX + look.x / 5.0D;
        /* 164 */
        double y = entityLiving.posY + entityLiving.getEyeHeight() + look.y / 5.0D;
        /* 165 */
        double z = entityLiving.posZ + look.z / 5.0D;
        /* 166 */
        if (count < 32)
            /* 167 */
            entityLiving.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D, new int[0]);
        /*     */
    }

}