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
        /*  24 */     this.isSilverwood = silverwood;
        /*  26 */     setFull3D();
        /*  27 */     setMaxStackSize(1);
        /*  29 */     addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
                       @SideOnly(Side.CLIENT)
                       public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                /*  32 */             if (entityIn == null)
                    /*  33 */               return 0.0F;
                /*  35 */             return !(entityIn.getActiveItemStack().getItem() instanceof ItemSmokingPipe) ? 0.0F : ((stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F);
                           }
                     });
        /*  39 */     addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
                       @SideOnly(Side.CLIENT)
                       public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                /*  42 */             return (entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack) ? 1.0F : 0.0F;
                           }
                     });
           }
    
       public ItemStack findTobacco(EntityPlayer smoker) {
        /*  49 */     if (isTobacco(smoker.getHeldItem(EnumHand.OFF_HAND)))
            /*  50 */       return smoker.getHeldItem(EnumHand.OFF_HAND);
        /*  51 */     if (isTobacco(smoker.getHeldItem(EnumHand.MAIN_HAND)))
            /*  52 */       return smoker.getHeldItem(EnumHand.MAIN_HAND);
        /*  54 */     for (int i = 0; i < smoker.inventory.getSizeInventory(); i++) {
            /*  55 */       ItemStack itemstack = smoker.inventory.getStackInSlot(i);
            /*  57 */       if (isTobacco(itemstack))
                /*  58 */         return itemstack;
                 }
        /*  62 */     return ItemStack.EMPTY;
           }
    
       protected boolean isTobacco(ItemStack stack) {
        /*  68 */     return stack.getItem() instanceof TBTobacco;
           }
    
       public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        /*  74 */     ItemStack itemstack = player.getHeldItem(handIn);
        /*  76 */     boolean flag = !findTobacco(player).isEmpty();
        /*  78 */     if (!flag)
            /*  79 */       return flag ? new ActionResult<>(EnumActionResult.PASS, itemstack) : new ActionResult<>(EnumActionResult.FAIL, itemstack);
        /*  81 */     player.setActiveHand(handIn);
        /*  84 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
           }

       public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        /* 123 */     if (entityLiving instanceof EntityPlayer) {
            /* 124 */       EntityPlayer player = (EntityPlayer)entityLiving;
            /* 125 */       ItemStack tobacco = findTobacco(player);
            /* 126 */       if (tobacco.isEmpty())
                /* 127 */         return stack;
            /* 128 */       ITobacco t = (ITobacco)tobacco.getItem();
            /* 129 */       t.performTobaccoEffect(player, tobacco, this.isSilverwood);
            /* 130 */       for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                /* 131 */         ItemStack stk = player.inventory.getStackInSlot(i);
                /* 132 */         if (stk != ItemStack.EMPTY && !(new ItemStack(stk.getItem())).equals(ItemStack.EMPTY) && stk.getItem() instanceof ITobacco) {
                    /* 133 */           getMaxItemUseDuration(stk);
                    /* 134 */           player.inventory.decrStackSize(i, 1);
                               break;
                             }
                       }
            /* 138 */       Vec3d look = entityLiving.getLookVec();
            /* 139 */       for (int j = 0; j < 100; j++) {
                /* 140 */         double x = player.posX + look.x / 5.0D;
                /* 141 */         double y = player.posY + player.getEyeHeight() + look.y / 5.0D;
                /* 142 */         double z = player.posZ + look.z / 5.0D;
                /* 143 */         player.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D);
                       }
                 }
        /* 146 */     return stack;
           }
    
       public EnumAction getItemUseAction(ItemStack stack) {
        /* 150 */     return EnumAction.BOW;
           }
    
       public int getMaxItemUseDuration(ItemStack stack) {
        /* 154 */     return 64;
           }
    
       public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
        /* 161 */     Vec3d look = entityLiving.getLookVec();
        /* 163 */     double x = entityLiving.posX + look.x / 5.0D;
        /* 164 */     double y = entityLiving.posY + entityLiving.getEyeHeight() + look.y / 5.0D;
        /* 165 */     double z = entityLiving.posZ + look.z / 5.0D;
        /* 166 */     if (count < 32)
            /* 167 */       entityLiving.world.spawnParticle(this.isSilverwood ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, x, y, z, look.x / 10.0D, look.y / 10.0D, look.z / 10.0D);
           }
     }


/* Location:              E:\thaumicbases-3.3.500.6r-feature-1.3 - 副本\!\com\rumaruka\thaumicbases\common\item\ItemSmokingPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */