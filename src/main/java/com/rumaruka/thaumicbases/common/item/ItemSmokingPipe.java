package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.api.ITobacco;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

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
            return smoker.getHeldItem(EnumHand.OFF_HAND);
        if (isTobacco(smoker.getHeldItem(EnumHand.MAIN_HAND)))
            return smoker.getHeldItem(EnumHand.MAIN_HAND);
        for (int i = 0; i < smoker.inventory.getSizeInventory(); i++) {
            ItemStack itemstack = smoker.inventory.getStackInSlot(i);
            if (isTobacco(itemstack))
                return itemstack;
        }
        return ItemStack.EMPTY;
    }

    protected boolean isTobacco(ItemStack stack) {
        return stack.getItem() instanceof TBTobacco;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        boolean flag = !findTobacco(player).isEmpty();
        if (!flag)
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);

        player.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            ItemStack tobacco = findTobacco(player);
            if (!tobacco.isEmpty()) {
                ITobacco t = (ITobacco) tobacco.getItem();
                t.performTobaccoEffect(player, tobacco, this.isSilverwood);
                tobacco.shrink(1);
                Vec3d look = entityLiving.getLookVec();
                for (int j = 0; j < 100; j++) {
                    double x = player.posX + look.x / 5.0D;
                    double y = player.posY + player.getEyeHeight() + look.y / 5.0D;
                    double z = player.posZ + look.z / 5.0D;
                    player.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D);
                }
            }
        }
        return stack;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 64;
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
        Vec3d look = entityLiving.getLookVec();
        double x = entityLiving.posX + look.x / 5.0D;
        double y = entityLiving.posY + entityLiving.getEyeHeight() + look.y / 5.0D;
        double z = entityLiving.posZ + look.z / 5.0D;
        if (count < 32)
            entityLiving.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D);
    }
}