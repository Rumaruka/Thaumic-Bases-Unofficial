package com.rumaruka.tb.init;

import com.rumaruka.tb.common.enchantment.EnchantmentElderKnowledge;
import com.rumaruka.tb.common.enchantment.EnchantmentEldritchBane;
import com.rumaruka.tb.common.enchantment.EnchantmentTainted;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TBEnchant {

    public static void setupEnchatments(){
        eldritchBane = new EnchantmentEldritchBane(Enchantment.Rarity.RARE, TBConfig.eldritchBaneID,7).setName("eldritchBane");
        eldritchBane.setRegistryName(new ResourceLocation("tb.eldritchBane"));
        ForgeRegistries.ENCHANTMENTS.register(eldritchBane);
        elderKnowledge = new EnchantmentElderKnowledge(Enchantment.Rarity.RARE, TBConfig.eldritchBaneID,12).setName("elderKnowledge");
        elderKnowledge.setRegistryName(new ResourceLocation("tb.elderKnowledge"));
        ForgeRegistries.ENCHANTMENTS.register(elderKnowledge);
        tainted = new EnchantmentTainted(Enchantment.Rarity.RARE,TBConfig.taintedID,9).setName("tainted");
        tainted.setRegistryName(new ResourceLocation("tb.tainted"));
        ForgeRegistries.ENCHANTMENTS.register(tainted);


    }

    public static Enchantment
            tainted,
            elderKnowledge,
            eldritchBane;
}
