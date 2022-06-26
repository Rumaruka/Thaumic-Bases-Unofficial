package com.rumaruka.thaumicbases.common.item;

import com.google.common.collect.Multimap;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.IVisDiscountGear;

import javax.annotation.Nullable;
import java.util.UUID;

public class ItemBloodyArmor extends ItemArmor implements IVisDiscountGear,IRechargable {


    public static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    int aType;
    public ItemStack repairMaterial = ItemStack.EMPTY;
    public ItemBloodyArmor(ArmorMaterial mat,int renderIndexIn,int aType ,EntityEquipmentSlot equipmentSlotIn) {


        super(mat, 0,equipmentSlotIn);
        TBItems.bloodyA=mat;
        this.aType = aType;
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer) {
        return discount[aType];
    }
    static final int[] discount = new int[]{6,5,4,3};

    @Override
    public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return 10;
    }

    @Override
    public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return EnumChargeDisplay.NORMAL;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (stack.getItem() != TBItems.bloodychest && stack.getItem() != TBItems.bloodyboots) {
            if (stack.getItem() == TBItems.bloodylegs) {
                return type == null ? "thaumicbases:textures/items/armor/bloody/bloody_2.png" : "thaumcraft:textures/entity/armor/robes_2_overlay.png";
            } else {
                return type == null ? "thaumicbases:textures/items/armor/bloody/bloody_1.png" : "thaumcraft:textures/entity/armor/robes_1_overlay.png";
            }
        } else {
            return type == null ? "thaumicbases:textures/items/armor/bloody/bloody_1.png" : "thaumcraft:textures/entity/armor/robes_1_overlay.png";
        }

    }



    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
       return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
       Multimap<String,AttributeModifier> map = super.getAttributeModifiers(slot,stack);
       if(stack.getItem()==TBItems.bloodychest && slot==EntityEquipmentSlot.CHEST){
           map.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(UUID.fromString("96042c45-dfe3-4366-b93b-84663c4d828d"), "MAX HEALTH", 0.4F, 2));
       }
       if(stack.getItem()==TBItems.bloodylegs&& slot==EntityEquipmentSlot.LEGS){
           map.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(UUID.fromString("e4e1d8b2-87f2-44f5-8f24-e1876060a04c"), "KNOCKBACK RESISTANCE", 1F, 2));
       }
       if(stack.getItem()==TBItems.bloodyboots&& slot==EntityEquipmentSlot.FEET){
           map.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(UUID.fromString("f6d1384c-74c3-4cce-9a80-11b91dbd4ff4"), "MOVEMENT SPEED", 0.5F, 2));

       }
       return map;
    }
}
