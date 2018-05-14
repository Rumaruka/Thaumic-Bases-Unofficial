package com.rumaruka.tb.init;


import com.rumaruka.tb.common.block.TBBlock;
import com.rumaruka.tb.utils.OnetimeCaller;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.*;
import thaumcraft.common.blocks.crafting.BlockThaumatorium;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.List;
import java.util.Random;


public class TBThaumonomicon {
    static ResourceLocation defaultGroup = new ResourceLocation("");
    public ResearchStage.Knowledge THAUMICBASES = new ResearchStage.Knowledge(IPlayerKnowledge.EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("THAUMICBASES"),5);


    public static final OnetimeCaller insertAspects = new OnetimeCaller(TBThaumonomicon::$insertAspects);
    public static void setup(){



        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumicbases:research/thaumicbases.json"));


        ResearchCategories.registerCategory(catName,null,null,icon,back,back2);






        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldCobble"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
               .add(Aspect.EARTH, 5), new ItemStack(TBBlocks.oldcobble), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.COBBLESTONE)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldCobbleMossy"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH, 5), new ItemStack(TBBlocks.oldcobblemossy), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.MOSSY_COBBLESTONE)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldGravel"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH, 5), new ItemStack(TBBlocks.oldgravel), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.GRAVEL)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldBrick"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH, 5), new ItemStack(TBBlocks.oldbrick), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.BRICK_BLOCK)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldLapis"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH, 5), new ItemStack(TBBlocks.oldlapis), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.LAPIS_BLOCK)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldIron"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH,5)
                .add(Aspect.ORDER, 5), new ItemStack(TBBlocks.oldiron), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.IRON_BLOCK)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldGold"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH,5)
                .add(Aspect.ORDER, 5), new ItemStack(TBBlocks.oldgold), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.GOLD_BLOCK)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldDiamond"), new ShapedArcaneRecipe(defaultGroup, "OLDBLOCK", TBConfig.visoldBlocks, new AspectList()
                .add(Aspect.ENTROPY,5)
                .add(Aspect.EARTH,5)
                .add(Aspect.ORDER, 5), new ItemStack(TBBlocks.olddiamond), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.DIAMOND_BLOCK)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.SMB"), new ShapedArcaneRecipe(defaultGroup, "SMB", 65, new AspectList()
                .add(Aspect.ORDER,2)
                .add(Aspect.AIR, 2)
                .add(Aspect.WATER, 2)
                .add(Aspect.FIRE, 2)
                .add(Aspect.EARTH, 2),

                new ItemStack(TBBlocks.dustblock), new Object[] { "sss", "sss", "sss",
                Character.valueOf('s'), new ItemStack(ItemsTC.salisMundus)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.SMBReturn"), new ShapedArcaneRecipe(defaultGroup, "SMB", 50, new AspectList()
                .add(Aspect.ENTROPY,2)
                .add(Aspect.AIR, 2)
                .add(Aspect.WATER, 2)
                .add(Aspect.FIRE, 2)
                .add(Aspect.EARTH, 2),

                new ItemStack(ItemsTC.salisMundus,9,0), new Object[] { "   ", " s ", "   ",
                Character.valueOf('s'), new ItemStack(TBBlocks.dustblock)}));



        CrucibleRecipe thauminiteRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.thauminite_ingot),new ItemStack(ItemsTC.ingots,1,0),new AspectList().add(Aspect.MAGIC, 10).add(Aspect.EARTH, 15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Thauminite"),thauminiteRec);

        CrucibleRecipe airingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.airingot),ConfigItems.AIR_CRYSTAL,new AspectList().add(Aspect.AIR, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Air_Ingot"),airingotRec);

        CrucibleRecipe fireingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.fireingot),ConfigItems.FIRE_CRYSTAL,new AspectList().add(Aspect.FIRE, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Fire_Ingot"),fireingotRec);

        CrucibleRecipe wateringotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.wateringot),ConfigItems.WATER_CRYSTAL,new AspectList().add(Aspect.WATER, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Water_Ingot"),wateringotRec);

        CrucibleRecipe earthingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.earthingot),ConfigItems.EARTH_CRYSTAL,new AspectList().add(Aspect.EARTH, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Earth_Ingot"),earthingotRec);

        CrucibleRecipe orderingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.orderingot),ConfigItems.ORDER_CRYSTAL,new AspectList().add(Aspect.ORDER, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Order_Ingot"),orderingotRec);

        CrucibleRecipe entropyingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.entropyingot), ConfigItems.ENTROPY_CRYSTAL,new AspectList().add(Aspect.ENTROPY, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Entropy_Ingot"),entropyingotRec);

        CrucibleRecipe mixedingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.mixedingot),new ItemStack(Items.IRON_INGOT),new AspectList().add(Aspect.EARTH, 16).add(Aspect.AIR,16).add(Aspect.WATER,16).add(Aspect.FIRE,16).add(Aspect.ENTROPY,5).add(Aspect.ORDER,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Mixed_Ingot"),mixedingotRec);

        CrucibleRecipe taintedingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.taintedingot), ConfigItems.FLUX_CRYSTAL,new AspectList().add(Aspect.FLUX, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Tain_Ingot"),taintedingotRec);

        CrucibleRecipe gravelToFlint = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.FLINT,2,0),new ItemStack(Blocks.GRAVEL),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.gravelToFlint"),gravelToFlint);

        CrucibleRecipe cobbleToSand = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Blocks.SAND,2,0),new ItemStack(Blocks.COBBLESTONE),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.cobbleToSand"),cobbleToSand);

        CrucibleRecipe sandToNuggetGold = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.GOLD_NUGGET,2,0),new ItemStack(Blocks.SAND),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.sandToNuggetGold"),sandToNuggetGold);

        CrucibleRecipe woolToString = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.STRING,3,0),new ItemStack(Blocks.WOOL),new AspectList().add(Aspect.ENTROPY, 25));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.woolToString"),woolToString);

        CrucibleRecipe rodToPower = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.BLAZE_POWDER,3,0),new ItemStack(Items.BLAZE_ROD),new AspectList().add(Aspect.ENTROPY, 30));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.rodToPower"),rodToPower);

        CrucibleRecipe gravelToFlintAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.FLINT,4,0),new ItemStack(Blocks.GRAVEL),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,10).add(Aspect.CRAFT,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.gravelToFlintAdv"),gravelToFlintAdv);

        CrucibleRecipe cobbleToSandAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Blocks.SAND,4,0),new ItemStack(Blocks.COBBLESTONE),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,10).add(Aspect.CRAFT,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.gravelToFlintAdv"),cobbleToSandAdv);

        CrucibleRecipe sandToNuggetGoldAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.GOLD_NUGGET,4,0),new ItemStack(Blocks.SAND),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5).add(Aspect.CRAFT,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.sandToNuggetGoldAdv"),sandToNuggetGoldAdv);

        CrucibleRecipe woolToStringAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.STRING,4,0),new ItemStack(Blocks.WOOL),new AspectList().add(Aspect.ENTROPY, 25).add(Aspect.CRAFT,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.woolToStringAdv"),woolToStringAdv);

        CrucibleRecipe rodToPowerAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.BLAZE_POWDER,5,0),new ItemStack(Items.BLAZE_ROD),new AspectList().add(Aspect.ENTROPY, 30).add(Aspect.CRAFT,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.rodToPowerAdv"),rodToPowerAdv);

        CrucibleRecipe silverToQuicksilver = new CrucibleRecipe("TB.EXCHANG",new ItemStack(ItemsTC.quicksilver,3,0),new ItemStack(BlocksTC.logSilverwood),new AspectList().add(Aspect.ORDER, 8).add(Aspect.MAGIC,8));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.silverToQuicksilver"),silverToQuicksilver);

        CrucibleRecipe saplingToAmber= new CrucibleRecipe("TB.EXCHANG",new ItemStack(ItemsTC.amber,3,0),new ItemStack(Blocks.SAPLING,1,1),new AspectList().add(Aspect.TRAP, 8));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.saplingToAmber"),saplingToAmber);

        CrucibleRecipe powderToRod= new CrucibleRecipe("TB.EXCHANG",new ItemStack(Items.BLAZE_ROD,3,0),new ItemStack(Items.BLAZE_POWDER,6,0  ),new AspectList().add(Aspect.TRAP, 8));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.powderToRod"),powderToRod);

        CrucibleRecipe doubleMundus= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.salisMundus,2,0),new ItemStack(ItemsTC.salisMundus,1,0),new AspectList().add(primals(8)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleMundus"),doubleMundus);











        }
    private static void $insertAspects()
    {


        appendAspects(new ItemStack(BlocksTC.vishroom), new AspectList().add(Aspect.FLUX, 2));


    }

    public static AspectList primals(int amount)
    {
        return new AspectList().add(Aspect.AIR, amount).add(Aspect.WATER, amount).add(Aspect.EARTH, amount).add(Aspect.FIRE, amount).add(Aspect.ORDER, amount).add(Aspect.ENTROPY, amount);
    }
    private static void appendAspects(String oreDict, AspectList toAdd)
    {
        List<ItemStack> ores = ThaumcraftApiHelper.getOresWithWildCards(oreDict);
        if(toAdd == null)
            toAdd = new AspectList();
        if(ores != null && ores.size() > 0)
            for(ItemStack ore : ores)
                try
                {
                    ItemStack oc = ore.copy();
                    oc.setCount(1);
                    appendAspects(oc, toAdd);
                } catch(Exception oc)
                {
                }
    }

    private static void appendAspects(ItemStack stack, AspectList toAdd)
    {
        toAdd = toAdd.copy();

        // Finds item's aspects, and if there are any, adds them to appended
        // aspects
        {
            AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
            if(al != null)
                toAdd = toAdd.add(al);
        }

        CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), toAdd);
    }

    private static void removeAspects(ItemStack stack, Aspect... aspects)
    {
        AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
        if(al != null)
        {
            for(Aspect a : aspects)
                al.remove(a);
            CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), al);
        }
    }




    public static final String catName ="THAUMICBASES";
    public static final ResourceLocation icon = new ResourceLocation("thaumicbases","textures/thaumonomicon/bases.png");
    public static final ResourceLocation back = new ResourceLocation("thaumicbases","textures/thaumonomicon/background.png");
    public static final ResourceLocation back2 = new ResourceLocation("thaumcraft","textures/gui/gui_research_back_over.png");

    }






