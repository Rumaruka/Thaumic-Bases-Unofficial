package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.common.enchantment.EnchantmentElderKnowledge;
import com.rumaruka.thaumicbases.common.enchantment.EnchantmentEldritchBane;
import com.rumaruka.thaumicbases.common.enchantment.EnchantmentTainted;
import com.rumaruka.thaumicbases.utils.TBConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TBEnchant {

    public static void setupEnchatments() {
        eldritchBane = new EnchantmentEldritchBane(Enchantment.Rarity.RARE, TBConfig.eldritchBaneID, 7).setName("eldritchBane");
        eldritchBane.setRegistryName(new ResourceLocation("thaumicbases.eldritchBane"));
        ForgeRegistries.ENCHANTMENTS.register(eldritchBane);
        elderKnowledge = new EnchantmentElderKnowledge(Enchantment.Rarity.RARE, TBConfig.eldritchBaneID, 12).setName("elderKnowledge");
        elderKnowledge.setRegistryName(new ResourceLocation("thaumicbases.elderKnowledge"));
        ForgeRegistries.ENCHANTMENTS.register(elderKnowledge);
        tainted = new EnchantmentTainted(Enchantment.Rarity.RARE, TBConfig.taintedID, 9).setName("tainted");
        tainted.setRegistryName(new ResourceLocation("thaumicbases.tainted"));
        ForgeRegistries.ENCHANTMENTS.register(tainted);


    }

    public static Enchantment
            tainted,
            elderKnowledge,
            eldritchBane;
}
