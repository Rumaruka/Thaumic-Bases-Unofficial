package com.rumaruka.thaumicbases.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentEldritchBane extends net.minecraft.enchantment.Enchantment {
    public EnchantmentEldritchBane(Rarity rarityIn) {
        super(rarityIn, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1+enchantmentLevel*5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return this !=ench&&ench!= Enchantments.SMITE&&ench!=Enchantments.BANE_OF_ARTHROPODS&&ench!=Enchantments.SHARPNESS&&!(ench instanceof EnchantmentDamage);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
