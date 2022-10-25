package com.rumaruka.thaumicbases.common.item;


import com.google.common.collect.Multimap;
import com.rumaruka.thaumicbases.api.IRevolver;
import com.rumaruka.thaumicbases.common.enchantment.EnumInfusionEnchantmentGun;
import com.rumaruka.thaumicbases.common.entity.EntityRevolverBullet;
import com.rumaruka.thaumicbases.common.libs.TBSounds;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import thaumcraft.common.lib.SoundsTC;

import java.util.Random;
import java.util.UUID;

public class ItemRevolver extends Item implements IRevolver {
    protected float attackSpeed;

    public ItemRevolver() {
        super();
        this.maxStackSize=1;
        this.attackSpeed = -2.2F;

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = this.findAmmo(player);
        boolean flag = this.findAmmo(player).isEmpty();
        Random r=world.rand;

        if (flag) {
            return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
        } else {
            player.playSound(TBSounds.revolver_shot, 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);

            if (!world.isRemote) {
                world.spawnEntity(new EntityRevolverBullet(world, player));
                player.getCooldownTracker().setCooldown(TBItems.revolver, 40 / (EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(player.getHeldItem(hand), EnumInfusionEnchantmentGun.SPEED) + 1 ));
                if(!player.isCreative() && r.nextDouble()<= (1 - 0.1 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(player.getHeldItem(hand), EnumInfusionEnchantmentGun.EFFICIENCY)))
                stack.shrink(1);
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
    }

    //查找子弹是否在背包函数
    protected ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isBullet(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isBullet(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isBullet(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

@Override
public EnumRarity getRarity(final ItemStack stack){return EnumRarity.UNCOMMON;}

    protected boolean isBullet(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBullet;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> retMap = super.getAttributeModifiers(slot, stack);
        if (stack.getItem() == TBItems.revolver && slot == EntityEquipmentSlot.MAINHAND)
            retMap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "ATTACK DAMAGE", 5.0 + EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(stack, EnumInfusionEnchantmentGun.HEAVY) * 2, 0));
        return retMap;
    }

    public EnumAction getItemUseAction(ItemStack stack) {return EnumAction.BOW;}

    @Override
    public float getZoom(ItemStack item) {
        return 0.75F;
    }
}