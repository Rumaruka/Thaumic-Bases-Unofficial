package com.rumaruka.tb.init;


import com.rumaruka.tb.common.block.TBBlock;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.*;
import thaumcraft.common.blocks.crafting.BlockThaumatorium;
import thaumcraft.common.config.ConfigItems;

import java.util.Random;


public class TBThaumonomicon {
    static ResourceLocation defaultGroup = new ResourceLocation("");
    public ResearchStage.Knowledge THAUMICBASES = new ResearchStage.Knowledge(IPlayerKnowledge.EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("THAUMICBASES"),5);
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

        CrucibleRecipe thauminiteRec = new CrucibleRecipe("TB.Thauminite",new ItemStack(TBItems.thauminite_ingot),new ItemStack(ItemsTC.ingots,1,0),new AspectList().add(Aspect.MAGIC, 10).add(Aspect.EARTH, 15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Thauminite"),thauminiteRec);

        CrucibleRecipe airingotRec = new CrucibleRecipe("TB.Air_Ingot",new ItemStack(TBItems.airingot),ConfigItems.AIR_CRYSTAL,new AspectList().add(Aspect.AIR, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Air_Ingot"),airingotRec);

        CrucibleRecipe fireingotRec = new CrucibleRecipe("TB.Fire_Ingot",new ItemStack(TBItems.fireingot),ConfigItems.FIRE_CRYSTAL,new AspectList().add(Aspect.FIRE, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Fire_Ingot"),fireingotRec);

        CrucibleRecipe wateringotRec = new CrucibleRecipe("TB.Water_Ingot",new ItemStack(TBItems.wateringot),ConfigItems.WATER_CRYSTAL,new AspectList().add(Aspect.WATER, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Water_Ingot"),wateringotRec);

        CrucibleRecipe earthingotRec = new CrucibleRecipe("TB.Earth_Ingot",new ItemStack(TBItems.earthingot),ConfigItems.EARTH_CRYSTAL,new AspectList().add(Aspect.EARTH, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Earth_Ingot"),earthingotRec);

        CrucibleRecipe orgeringotRec = new CrucibleRecipe("TB.Order_Ingot",new ItemStack(TBItems.orderingot),ConfigItems.ORDER_CRYSTAL,new AspectList().add(Aspect.ORDER, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Order_Ingot"),orgeringotRec);

        CrucibleRecipe entropyingotRec = new CrucibleRecipe("TB.Entropy_Ingot",new ItemStack(TBItems.entropyingot), ConfigItems.ENTROPY_CRYSTAL,new AspectList().add(Aspect.ENTROPY, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Entropy_Ingot"),entropyingotRec);

        CrucibleRecipe mixedingotRec = new CrucibleRecipe("TB.Mixed_Ingot",new ItemStack(TBItems.mixedingot),new ItemStack(Items.IRON_INGOT),new AspectList().add(Aspect.EARTH, 16).add(Aspect.AIR,16).add(Aspect.WATER,16).add(Aspect.FIRE,16).add(Aspect.ENTROPY,5).add(Aspect.ORDER,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Mixed_Ingot"),mixedingotRec);

        CrucibleRecipe taintedingotRec = new CrucibleRecipe("TB.Tain_Ingot",new ItemStack(TBItems.taintedingot), ConfigItems.FLUX_CRYSTAL,new AspectList().add(Aspect.FLUX, TBConfig.aspectCountForIngotCrucible));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Tain_Ingot"),taintedingotRec);

        CrucibleRecipe gravelToFlint = new CrucibleRecipe("TB.gravelToFlint",new ItemStack(Items.FLINT,2,0),new ItemStack(Blocks.GRAVEL),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.gravelToFlint"),gravelToFlint);

        CrucibleRecipe cobbleToSand = new CrucibleRecipe("TB.cobbleToSand",new ItemStack(Blocks.SAND,2,0),new ItemStack(Blocks.COBBLESTONE),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.cobbleToSand"),cobbleToSand);

        CrucibleRecipe sandToNuggetGold = new CrucibleRecipe("TB.sandToNuggetGold",new ItemStack(Items.GOLD_NUGGET,2,0),new ItemStack(Blocks.SAND),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.sandToNuggetGold"),sandToNuggetGold);



        CrucibleRecipe randomWoolToString = new CrucibleRecipe("TB.randomWoolToString",new ItemStack(Items.STRING,4,0),new ItemStack(Blocks.WOOL),new AspectList().add(Aspect.ENTROPY, 25));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.randomWoolToString"),randomWoolToString);















        }


    public static final String catName ="THAUMICBASES";
    public static final ResourceLocation icon = new ResourceLocation("thaumicbases","textures/thaumonomicon/bases.png");
    public static final ResourceLocation back = new ResourceLocation("thaumicbases","textures/thaumonomicon/background.png");
    public static final ResourceLocation back2 = new ResourceLocation("thaumcraft","textures/gui/gui_research_back_over.png");

    }






