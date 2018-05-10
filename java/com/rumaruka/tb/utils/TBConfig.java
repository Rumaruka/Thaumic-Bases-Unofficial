package com.rumaruka.tb.utils;

import DummyCore.Utils.IDummyConfig;
import DummyCore.Utils.MiscUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;

public class TBConfig implements IDummyConfig {
    @Override
    public void load(Configuration config) {
        cfg = config;
        Enchantment[]eArray = MiscUtils.enchantmentList();
      //  eldritchBaneID = cfg.getInt("eldritchBaneEnchantmentID", "Enchantments", 100, 0, eArray.length, "");





        visoldBlocks = cfg.getInt("visCastOldStyle","Count Vis",50,5,Integer.MAX_VALUE,"Count Vis for Crafting Old Style Vanilla Blocks");

    }
    static Configuration cfg;



    public static int visoldBlocks = 0;

    public static int eldritchBaneID = 0;

    public static int getVisoldBlocks(){
        return visoldBlocks;
    }
}
