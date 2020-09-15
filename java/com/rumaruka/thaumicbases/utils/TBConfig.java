package com.rumaruka.thaumicbases.utils;

import DummyCore.Utils.IDummyConfig;
import DummyCore.Utils.MiscUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;

public class TBConfig implements IDummyConfig {



    @Override
    public void load(Configuration config) {
        cfg = config;
        Enchantment[] eArray = MiscUtils.enchantmentList();
        elderKnowledgeID = cfg.getInt("elderKnowledgeEnchantmentID", "Enchantments", 98, 0, eArray.length, "");
        taintedID = cfg.getInt("taintedEnchantmentID", "Enchantments", 101, 0, eArray.length, "");

        allowTobacco = cfg.getBoolean("allowTobacco", "General", true, "If set to falso the tobacco will be disabled - there will be no recipes/no entries in Thaumonomicon");
        allowcraftprimordar = cfg.getBoolean("allowcraftprimordar", "General", false, "More easy version craft primordar pearl");

        eldritchBaneID = cfg.getInt("eldritchBaneEnchantmentID", "Enchantments", 99, 0, eArray.length, "");
        speedMultiplierForFurnace = cfg.getInt("speedMultiplierForFurnace", "Advanced Alchemy Smeltery", 4, 0, Integer.MAX_VALUE, "This is the speed of the Advanced Alchamical Smeltery. TC's basic has 1.");
        makeReguireAlumentium = cfg.getBoolean("makeRequireAlumentium", "Advanced Alchemy Smeltery", true, "Does the Advanced Alchemical Smeltery requires Alumentium to work faster");


        visoldBlocks = cfg.getInt("visCastOldStyle","Count Vis",50,5,250,"Count Vis for Crafting Old Style Vanilla Blocks");
        aspectCountForIngotCrucible = cfg.getInt("aspectCountForIngotCrucible","Count Aspect", 30,5,64,"Count Aspect For Crucible");
        minBlazePowderFromPyrofluid = cfg.getInt("minBlazePowderFromPyrofluid", "Pyrofluid", 5, 0, Integer.MAX_VALUE, "");
        maxBlazePowderFromPyrofluid = cfg.getInt("maxBlazePowderFromPyrofluid", "Pyrofluid", 5+32, 0, Integer.MAX_VALUE, "");
        firstDropItemInSeeeds = cfg.getInt("firstDropItemSeeds","Seeds Droped Chance",4, 1,20,"chance droped");
        extraDropItemInSeeeds = cfg.getInt("extraDropItemSeeds","Seeds Droped Chance",1, 1,10,"chance droped");
    }
    static Configuration cfg;
    public static int eldritchBaneID;
    public static int elderKnowledgeID;
    public static int taintedID;

    public static boolean allowTobacco;
    public static boolean allowcraftprimordar;
    public static boolean makeReguireAlumentium;
    public static int visoldBlocks = 0;
    public static int aspectCountForIngotCrucible = 0;
    public static int minBlazePowderFromPyrofluid = 0;
    public static int maxBlazePowderFromPyrofluid = 0;
    public static int firstDropItemInSeeeds = 0;
    public static int extraDropItemInSeeeds = 0;
    public static int speedMultiplierForFurnace;
    public static int getMaxBlazePowderFromPyrofluid() {
        return maxBlazePowderFromPyrofluid;
    }

    public static int getExtraDropItemInSeeeds() {
        return extraDropItemInSeeeds;
    }

    public static int getMinBlazePowderFromPyrofluid() {
        return minBlazePowderFromPyrofluid;
    }

    public static int getFirstDropItemInSeeeds() {
        return firstDropItemInSeeeds;
    }

    public static int getVisoldBlocks(){
        return visoldBlocks;
    }
    public static int getAspectCountForIngotCrucible(){
        return aspectCountForIngotCrucible;
    }

    public static int getEldritchBaneID() {
        return eldritchBaneID;
    }

    public static int getElderKnowledgeID() {
        return elderKnowledgeID;
    }

    public static int getTaintedID() {
        return taintedID;
    }
}
