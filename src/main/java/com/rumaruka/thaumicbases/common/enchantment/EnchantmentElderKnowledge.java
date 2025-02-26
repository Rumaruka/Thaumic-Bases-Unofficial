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
    public int getMinEnchantability(int lvl) {
        return 1 + lvl * 5;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return !(ench instanceof EnchantmentElderKnowledge) && !(ench instanceof EnchantmentTainted);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
