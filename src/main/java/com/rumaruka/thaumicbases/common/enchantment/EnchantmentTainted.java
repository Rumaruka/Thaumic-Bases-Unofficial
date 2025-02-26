package com.rumaruka.thaumicbases.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentTainted extends Enchantment {
    public EnchantmentTainted(Rarity rarityIn) {
        super(rarityIn, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean canApplyTogether(Enchantment ench) {
        return ench != this && !(ench instanceof EnchantmentElderKnowledge);
    }

    public boolean canApply(ItemStack stack)
    {
        return false;
    }
}