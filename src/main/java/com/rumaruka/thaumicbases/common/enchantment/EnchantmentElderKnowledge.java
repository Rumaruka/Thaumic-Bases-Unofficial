package com.rumaruka.thaumicbases.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentElderKnowledge extends Enchantment {
    public EnchantmentElderKnowledge(Rarity rarityIn) {
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
        return true;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
