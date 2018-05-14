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

        visoldBlocks = cfg.getInt("visCastOldStyle","Count Vis",50,5,250,"Count Vis for Crafting Old Style Vanilla Blocks");
        aspectCountForIngotCrucible = cfg.getInt("aspectCountForIngotCrucible","Count Aspect", 30,5,64,"Count Aspect For Crucible");
        minBlazePowderFromPyrofluid = cfg.getInt("minBlazePowderFromPyrofluid", "Pyrofluid", 5, 0, Integer.MAX_VALUE, "");
        maxBlazePowderFromPyrofluid = cfg.getInt("maxBlazePowderFromPyrofluid", "Pyrofluid", 5+32, 0, Integer.MAX_VALUE, "");
    }
    static Configuration cfg;



    public static int visoldBlocks = 0;
    public static int aspectCountForIngotCrucible = 0;
    public static int minBlazePowderFromPyrofluid = 0;
    public static int maxBlazePowderFromPyrofluid = 0;

    public static int getVisoldBlocks(){
        return visoldBlocks;
    }
    public static int getAspectCountForIngotCrucible(){
        return aspectCountForIngotCrucible;
    }
}
