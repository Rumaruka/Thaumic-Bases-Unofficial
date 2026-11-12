package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.common.enchantment.EnchantmentElderKnowledge;
import com.rumaruka.thaumicbases.common.enchantment.EnchantmentEldritchBane;
import com.rumaruka.thaumicbases.common.enchantment.EnchantmentTainted;
import com.rumaruka.thaumicbases.core.TBCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TBEnchant {

    public static Enchantment tainted = new EnchantmentTainted(Enchantment.Rarity.RARE).setName("tainted").setRegistryName(new ResourceLocation(TBCore.modid, "thaumicbases.tainted"));
    public static Enchantment elderKnowledge = new EnchantmentElderKnowledge(Enchantment.Rarity.RARE).setName("elderKnowledge").setRegistryName(new ResourceLocation(TBCore.modid, "thaumicbases.elderKnowledge"));
    public static Enchantment eldritchBane = new EnchantmentEldritchBane(Enchantment.Rarity.RARE).setName("eldritchBane").setRegistryName(new ResourceLocation(TBCore.modid, "thaumicbases.eldritchBane"));

    public static void setupEnchatments() {
        ForgeRegistries.ENCHANTMENTS.register(eldritchBane);
        ForgeRegistries.ENCHANTMENTS.register(elderKnowledge);
        ForgeRegistries.ENCHANTMENTS.register(tainted);
    }
}
