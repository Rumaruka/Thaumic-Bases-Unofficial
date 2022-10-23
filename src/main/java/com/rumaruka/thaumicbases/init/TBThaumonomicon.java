package com.rumaruka.thaumicbases.init;


import com.rumaruka.thaumicbases.common.block.FluidPyro;
import com.rumaruka.thaumicbases.common.block.TBBlock;
import com.rumaruka.thaumicbases.common.enchantment.EnumInfusionEnchantmentGun;
import com.rumaruka.thaumicbases.common.enchantment.InfusionEnchantmentRecipeGun;
import com.rumaruka.thaumicbases.common.item.ItemRevolver;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.crafting.*;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.*;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.crafting.InfusionEnchantmentRecipe;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

import java.util.List;

import static com.rumaruka.thaumicbases.init.TBFluids.FLUID_PYRO;


public class TBThaumonomicon {




    static ResourceLocation defaultGroup = new ResourceLocation("");
    public ResearchStage.Knowledge THAUMICBASES = new ResearchStage.Knowledge(IPlayerKnowledge.EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("THAUMICBASES"),5);



    public static void setup(){

        ResearchCategories.registerCategory(catName,null,null,icon,back,back2);










/*==============================================ARCANE CRAFTING BEGIN=============================================================================*/
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldCobble"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldcobble,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.COBBLESTONE)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldCobbleMossy"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldcobblemossy,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.MOSSY_COBBLESTONE)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldGravel"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldgravel,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.GRAVEL)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldBrick"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldbrick,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.BRICK_BLOCK)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldLapis"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldlapis,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.LAPIS_BLOCK)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldIron"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                .add(Aspect.ENTROPY,1)
                .add(Aspect.EARTH,1)
                .add(Aspect.ORDER, 1), new ItemStack(TBBlocks.oldiron,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.IRON_BLOCK)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldGold"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                .add(Aspect.ENTROPY,1)
                .add(Aspect.EARTH,1)
                .add(Aspect.ORDER, 1), new ItemStack(TBBlocks.oldgold,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.GOLD_BLOCK)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldDiamond"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                .add(Aspect.ENTROPY,1)
                .add(Aspect.EARTH,1)
                .add(Aspect.ORDER, 1), new ItemStack(TBBlocks.olddiamond,6), new Object[] { "# #", "# #", "# #",
                Character.valueOf('#'), new ItemStack(Blocks.DIAMOND_BLOCK)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.SMB"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO2", 65, new AspectList()
,

                new ItemStack(TBBlocks.dustblock), new Object[] { "sss", "sss", "sss",
                Character.valueOf('s'), new ItemStack(ItemsTC.salisMundus)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.SMBReturn"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO2", 50, new AspectList()
,

                new ItemStack(ItemsTC.salisMundus,9,0), new Object[] { "   ", " s ", "   ",
                Character.valueOf('s'), new ItemStack(TBBlocks.dustblock)}));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.IronSpike"), new ShapedArcaneRecipe(defaultGroup, "TB.SPIKE",20, new AspectList()
                .add(Aspect.FIRE,1)
                .add(Aspect.ENTROPY, 1), new ItemStack(TBBlocks.ironspike,1), new Object[] { " i ", "iIi", "IpI",
                Character.valueOf('i'), "nuggetIron", Character.valueOf('I'),new ItemStack(Items.IRON_INGOT), Character.valueOf('p'),new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)  }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.ThaumSpike"), new ShapedArcaneRecipe(defaultGroup, "TB.SPIKE1",20, new AspectList()
                .add(Aspect.FIRE,1)
                .add(Aspect.ENTROPY, 1), new ItemStack(TBBlocks.thaumspike,1), new Object[] { " t ", "tTt", "TsT",
                Character.valueOf('t'), new ItemStack(ItemsTC.nuggets,1,6), Character.valueOf('T'),new ItemStack(ItemsTC.ingots,1,0), Character.valueOf('s'),new ItemStack(TBBlocks.ironspike)  }));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.VoidSpike"), new ShapedArcaneRecipe(defaultGroup, "TB.SPIKE2",20, new AspectList()
                .add(Aspect.FIRE,1)
                .add(Aspect.ENTROPY, 1), new ItemStack(TBBlocks.voidspike,1), new Object[] { " v ", "vVv", "VsV",
                Character.valueOf('v'), new ItemStack(ItemsTC.nuggets,1,7), Character.valueOf('V'),new ItemStack(ItemsTC.ingots,1,1), Character.valueOf('s'),new ItemStack(TBBlocks.thaumspike)  }));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.BCHEST"), new ShapedArcaneRecipe(defaultGroup, "TB.BloodFabric",50, new AspectList()
                .add(Aspect.AIR,2)
                    , new ItemStack(TBItems.bloodychest,1), new Object[] { "# #", "###", "###",
                Character.valueOf('#'), new ItemStack(TBItems.bloodycloth)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.BLEGS"), new ShapedArcaneRecipe(defaultGroup, "TB.BloodFabric",50, new AspectList()
                .add(Aspect.WATER,2)
                , new ItemStack(TBItems.bloodylegs,1), new Object[]
                { "###",
                        "# #", "# #",
                Character.valueOf('#'), new ItemStack(TBItems.bloodycloth)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.BBOOTS"), new ShapedArcaneRecipe(defaultGroup, "TB.BloodFabric",50, new AspectList()
                .add(Aspect.EARTH,1)
                , new ItemStack(TBItems.bloodyboots,1), new Object[] { "# #", "# #", "   ",
                Character.valueOf('#'), new ItemStack(TBItems.bloodycloth)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.mortal"), new ShapelessArcaneRecipe(defaultGroup, "TB.TOBACCO",25, new AspectList()
                , new ItemStack(TBItems.mortar,1),
                new Object[] {
                new ItemStack(Items.BOWL),
                new ItemStack(Items.STICK),
                new ItemStack(Blocks.WOODEN_SLAB),
                new ItemStack(Blocks.WOODEN_SLAB),
                new ItemStack(Blocks.WOODEN_SLAB)
 }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.camp"), new ShapedArcaneRecipe(defaultGroup, "TB.UseFire",50, new AspectList()
                .add(Aspect.ENTROPY,2)
                .add(Aspect.FIRE,2)
                , new ItemStack(TBBlocks.campfire,1),
                new Object[] {
                "SSS",
                "CUC",
                "GGG",
                Character.valueOf('S'), new ItemStack(Items.STICK),Character.valueOf('U'), new ItemStack(Items.COAL),Character.valueOf('C'), new ItemStack(Blocks.COBBLESTONE),Character.valueOf('G'), new ItemStack(Blocks.GRAVEL)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.braizer"), new ShapedArcaneRecipe(defaultGroup, "TB.UseFire",50, new AspectList()
                .add(Aspect.ENTROPY,3)
                .add(Aspect.FIRE,3)
                , new ItemStack(TBBlocks.braizer,1),
                new Object[] {
                        "SUS",
                        " A ",
                        "SSS",
                        Character.valueOf('S'), new ItemStack(BlocksTC.slabArcaneStone),Character.valueOf('U'), new ItemStack(Items.COAL),Character.valueOf('A'), new ItemStack(BlocksTC.stoneArcane)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.greatpipe"), new ShapedArcaneRecipe(defaultGroup, "TB.TOBACCO",50, new AspectList()
                .add(primals(1))

                , new ItemStack(TBItems.greatwoodpipe,1),
                new Object[] {
                        "gs ",
                        " gg",
                        Character.valueOf('s'), new ItemStack(ItemsTC.salisMundus),Character.valueOf('g'), new ItemStack(BlocksTC.plankGreatwood)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.syrop"), new ShapedArcaneRecipe(defaultGroup, "TB.PLANT1",50, new AspectList()
                , new ItemStack(TBItems.rosehipsyrup,1),
                new Object[] {
                        "pSs",
                        Character.valueOf('s'), new ItemStack(Items.SUGAR),Character.valueOf('p'), new ItemStack(ItemsTC.phial), Character.valueOf('S'),new ItemStack(TBItems.briar_seedbag)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.bullet"), new ShapedArcaneRecipe(defaultGroup, "TB.REVOLVER",80, new AspectList()
                , new ItemStack(TBItems.bullet,16),
                new Object[] {
                        "m",
                        "c",
                        "b",
                        Character.valueOf('m'), new ItemStack(TBItems.nuggetthauminite),Character.valueOf('c'), new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.AVERSION)), Character.valueOf('b'),new ItemStack(ItemsTC.plate,1,0)}));



        /*==============================================ARCANE CRAFTING ENG=============================================================================*/



        /*==============================================ALCHEMY BEGIN=============================================================================*/
        CrucibleRecipe thauminiteRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBItems.thauminite_ingot),new ItemStack(ItemsTC.ingots,1,0),new AspectList().add(Aspect.MAGIC, 10).add(Aspect.EARTH, 15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Thauminite"),thauminiteRec);

        CrucibleRecipe airingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockair),new ItemStack(BlocksTC.crystalAir,1,0),new AspectList().add(Aspect.AIR, 10).add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Air_Ingot"),airingotRec);

        CrucibleRecipe fireingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockfire),new ItemStack(BlocksTC.crystalFire,1,0),new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.FIRE, 10).add(Aspect.WATER, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Fire_Ingot"),fireingotRec);

        CrucibleRecipe wateringotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockwater),new ItemStack(BlocksTC.crystalWater,1,0),new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 10).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Water_Ingot"),wateringotRec);

        CrucibleRecipe earthingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockearth),new ItemStack(BlocksTC.crystalEarth,1,0),new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 10).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Earth_Ingot"),earthingotRec);

        CrucibleRecipe orderingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockorder),new ItemStack(BlocksTC.crystalOrder,1,0),new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 10).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Order_Ingot"),orderingotRec);

        CrucibleRecipe entropyingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockentropy),new ItemStack(BlocksTC.crystalEntropy,1,0),new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.ENTROPY, 10).add(Aspect.ORDER, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Entropy_Ingot"),entropyingotRec);

        CrucibleRecipe mixedingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblockmixed),new ItemStack(TBBlocks.dustblock),new AspectList().add(Aspect.EARTH, 6).add(Aspect.AIR,6).add(Aspect.WATER, 6).add(Aspect.FIRE,6).add(Aspect.ENTROPY, 6).add(Aspect.ORDER, 6).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Mixed_Ingot"),mixedingotRec);

        CrucibleRecipe taintedingotRec = new CrucibleRecipe("TB.ALCHEMY",new ItemStack(TBBlocks.crystalblocktainted), new ItemStack(BlocksTC.crystalTaint,1,0),new AspectList().add(Aspect.FLUX, 10).add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.Tain_Ingot"),taintedingotRec);

        CrucibleRecipe gravelToFlint = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.FLINT,2,0),new ItemStack(Blocks.GRAVEL),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.gravelToFlint"),gravelToFlint);

        CrucibleRecipe cobbleToSand = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Blocks.SAND,2,0),new ItemStack(Blocks.COBBLESTONE),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.cobbleToSand"),cobbleToSand);

        CrucibleRecipe sandToNuggetGold = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.GOLD_NUGGET,2,0),new ItemStack(Blocks.SAND),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,4).add(Aspect.DESIRE,2));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.sandToNuggetGold"),sandToNuggetGold);

        CrucibleRecipe woolToString = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.STRING,3,0),new ItemStack(Blocks.WOOL),new AspectList().add(Aspect.ENTROPY, 25));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.woolToString"),woolToString);

        CrucibleRecipe rodToPower = new CrucibleRecipe("TB.MACERATOR",new ItemStack(Items.BLAZE_POWDER,3,0),new ItemStack(Items.BLAZE_ROD),new AspectList().add(Aspect.ENTROPY, 30));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.rodToPower"),rodToPower);

        CrucibleRecipe gravelToFlintAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.FLINT,4,0),new ItemStack(Blocks.GRAVEL),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,10).add(Aspect.CRAFT,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.gravelToFlintAdv"),gravelToFlintAdv);

        CrucibleRecipe cobbleToSandAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Blocks.SAND,4,0),new ItemStack(Blocks.COBBLESTONE),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,10).add(Aspect.CRAFT,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.cobbleToSandAdv"),cobbleToSandAdv);

        CrucibleRecipe sandToNuggetGoldAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.GOLD_NUGGET,4,0),new ItemStack(Blocks.SAND),new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.EARTH,5).add(Aspect.CRAFT,8).add(Aspect.DESIRE, 5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.sandToNuggetGoldAdv"),sandToNuggetGoldAdv);

        CrucibleRecipe woolToStringAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.STRING,4,0),new ItemStack(Blocks.WOOL),new AspectList().add(Aspect.ENTROPY, 25).add(Aspect.CRAFT,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.woolToStringAdv"),woolToStringAdv);

        CrucibleRecipe rodToPowerAdv = new CrucibleRecipe("TB.AdvMACERATOR",new ItemStack(Items.BLAZE_POWDER,5,0),new ItemStack(Items.BLAZE_ROD),new AspectList().add(Aspect.ENTROPY, 30).add(Aspect.CRAFT,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.rodToPowerAdv"),rodToPowerAdv);

        CrucibleRecipe silverToQuicksilver = new CrucibleRecipe("TB.EXCHANG",new ItemStack(ItemsTC.quicksilver,3,0),new ItemStack(BlocksTC.logSilverwood),new AspectList().add(Aspect.ORDER, 8).add(Aspect.MAGIC,8));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.silverToQuicksilver"),silverToQuicksilver);

        CrucibleRecipe saplingToAmber= new CrucibleRecipe("TB.EXCHANG",new ItemStack(ItemsTC.amber,3,0),new ItemStack(Blocks.SAPLING,1,1),new AspectList().add(Aspect.TRAP, 8));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.saplingToAmber"),saplingToAmber);

        CrucibleRecipe powderToRod= new CrucibleRecipe("TB.EXCHANG",new ItemStack(Items.BLAZE_ROD,1,0),new ItemStack(Items.BLAZE_POWDER,1,0),new AspectList().add(Aspect.FIRE, 15).add(Aspect.ALCHEMY, 5).add(Aspect.ENERGY, 5).add(Aspect.ENTROPY, 1));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.powderToRod"),powderToRod);
        CrucibleRecipe bonemealToBONE= new CrucibleRecipe("TB.EXCHANG",new ItemStack(Items.BONE,1,0),new ItemStack(Items.DYE,1,15  ),new AspectList().add(Aspect.ORDER, 2).add(Aspect.SENSES,6).add(Aspect.CRAFT,2).add(Aspect.EXCHANGE,1).add(Aspect.DEATH,4));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.bonemealToBONE"),bonemealToBONE);

        CrucibleRecipe greenTOCactus= new CrucibleRecipe("TB.EXCHANG",new ItemStack(Blocks.CACTUS,1,0),new ItemStack(Items.DYE,1,2  ),new AspectList().add(Aspect.ORDER, 1).add(Aspect.LIFE,2).add(Aspect.EARTH,1));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.greenTOCactus"),greenTOCactus);

        CrucibleRecipe doubleMundus= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.salisMundus,2,0),new ItemStack(ItemsTC.salisMundus,1,0),new AspectList().add(primals(8)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleMundus"),doubleMundus);
        CrucibleRecipe doubleArrow= new CrucibleRecipe("TB.DOUBLE",new ItemStack(Items.ARROW,2,0),new ItemStack(Items.ARROW,1,0),new AspectList().add(Aspect.AVERSION,6));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleArrow"),doubleArrow);
        CrucibleRecipe doubleIronCluster= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.clusters,2,0),new ItemStack(Blocks.IRON_ORE,1,0),new AspectList().add(Aspect.METAL,10).add(Aspect.ORDER,10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleIronCluster"),doubleIronCluster);
        CrucibleRecipe doubleGoldCluster= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.clusters,2,1),new ItemStack(Blocks.GOLD_ORE,1,0),new AspectList().add(Aspect.METAL,10).add(Aspect.ORDER,10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleGoldCluster"),doubleGoldCluster);
        CrucibleRecipe doubleCinnabarCluster= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.clusters,2,6),new ItemStack(BlocksTC.oreCinnabar,1,0),new AspectList().add(Aspect.METAL,10).add(Aspect.ORDER,10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleCinnabarCluster"),doubleCinnabarCluster);
        CrucibleRecipe doubleQuartzCluster= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.clusters,2,7),new ItemStack(Blocks.QUARTZ_ORE,1,0),new AspectList().add(Aspect.METAL,10).add(Aspect.ORDER,10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleQuartzCluster"),doubleQuartzCluster);
        CrucibleRecipe doubleQuartzCluster1= new CrucibleRecipe("TB.DOUBLE",new ItemStack(ItemsTC.clusters,2,7),new ItemStack(BlocksTC.oreQuartz,1,0),new AspectList().add(Aspect.METAL,10).add(Aspect.ORDER,10).add(Aspect.EARTH,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.doubleQuartzCluster1"),doubleQuartzCluster1);
        CrucibleRecipe lavaToPyro= new CrucibleRecipe("TB.FLUID", FluidUtil.getFilledBucket(new FluidStack(FLUID_PYRO, 1000)),new ItemStack(Items.LAVA_BUCKET),new AspectList().add(Aspect.FIRE,40).add(Aspect.MAGIC,25));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.lavaToPyro"),lavaToPyro);

        CrucibleRecipe seedToSweed= new CrucibleRecipe("TB.PLANT",new ItemStack(TBItems.sweedseed),new ItemStack(Items.WHEAT_SEEDS),new AspectList().add(Aspect.PLANT,25).add(Aspect.MAGIC,15).add(Aspect.LIFE,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.seedToSweed"),seedToSweed);

        CrucibleRecipe plax= new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.plaxseed),new ItemStack(Items.WHEAT_SEEDS),new AspectList().add(Aspect.PLANT,25).add(Aspect.LIFE,20));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.sweedToPlax"),plax);



        CrucibleRecipe aurelia= new CrucibleRecipe("TB.PLANT1",new ItemStack(TBBlocks.aurelia),new ItemStack(Blocks.RED_FLOWER,1,7),new AspectList().add(Aspect.PLANT,25).add(Aspect.AURA,12).add(Aspect.DESIRE,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.aurelia"),aurelia);
        CrucibleRecipe briar = new CrucibleRecipe("TB.PLANT1", new ItemStack(TBBlocks.briar), new ItemStack(Blocks.DOUBLE_PLANT,1,4), new AspectList().add(Aspect.PLANT,20).add(Aspect.LIFE,35));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.briar"),briar);
        CrucibleRecipe redlonbrush= new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.redlonseed),new ItemStack(Items.MELON_SEEDS),new AspectList().add(Aspect.PLANT,20).add(Aspect.ENERGY,16));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.roshipbrush"),redlonbrush);
        CrucibleRecipe metallea = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.metalleatseed),new ItemStack(Items.WHEAT_SEEDS),new AspectList().add(Aspect.ORDER,25).add(Aspect.LIFE,10).add(Aspect.METAL,25));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.metallea"),metallea);
        CrucibleRecipe  lazullia = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.lazulliaseed),new ItemStack(Items.WHEAT_SEEDS),new AspectList().add(Aspect.SENSES,25).add(Aspect.DESIRE,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.lazullia"), lazullia);
        CrucibleRecipe  glieonia = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.glieoniaseed),new ItemStack(Blocks.RED_FLOWER,1,3),new AspectList().add(Aspect.PLANT,20).add(Aspect.MAGIC,15).add(Aspect.LIFE,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.glieonia"), glieonia);
        CrucibleRecipe fluxium = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBBlocks.flaxium),new ItemStack(Blocks.RED_FLOWER,1,2),new AspectList().add(Aspect.PLANT,20).add(Aspect.MAGIC,15).add(Aspect.ENTROPY,25));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.fluxium"), fluxium);
        CrucibleRecipe lucrite = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.lucriteseed),new ItemStack(Items.GOLDEN_CARROT),new AspectList().add(Aspect.MAGIC,20).add(Aspect.DESIRE,18).add(Aspect.LIFE,14));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.lucrite"), lucrite);
        /*==============================================ALCHEMY END===============================================================================*/



        /*==============================================INFUSING BEGIN=============================================================================*/
        InfusionRecipe goldenSapling = new InfusionRecipe("TB.TREE", new ItemStack(TBBlocks.goldensapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.DESIRE,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(Items.GOLDEN_APPLE),new ItemStack(Items.GOLDEN_APPLE),new ItemStack(Items.APPLE),new ItemStack(Items.APPLE));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.goldenSapling"),goldenSapling);
        InfusionRecipe netherSapling = new InfusionRecipe("TB.TREE2", new ItemStack(TBBlocks.nethersapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.FIRE,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(Items.NETHER_WART),new ItemStack(Items.NETHER_WART),new ItemStack(Items.BLAZE_POWDER),new ItemStack(Items.BLAZE_POWDER));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.netherSapling"),netherSapling);
        InfusionRecipe enderSapling = new InfusionRecipe("TB.TREE1", new ItemStack(TBBlocks.endersapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.AURA,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.ENDER_EYE),new ItemStack(Items.ENDER_EYE));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.enderSapling"),enderSapling);
        InfusionRecipe overchanter = new InfusionRecipe("TB.MACHANISM", new ItemStack(TBBlocks.overchanter),5,new AspectList().add(primals(100)).add(Aspect.MECHANISM,125).add(Aspect.MAGIC,125).add(Aspect.MIND,125), new ItemStack(Blocks.ENCHANTING_TABLE),new ItemStack(TBBlocks.dustblock),new ItemStack(TBBlocks.dustblock),new ItemStack(TBBlocks.crystalblockmixed),new ItemStack(TBBlocks.crystalblockmixed),new ItemStack(TBBlocks.crystalblockentropy),new ItemStack(TBBlocks.crystalblockorder),new ItemStack(TBBlocks.crystalblockearth),new ItemStack(TBBlocks.crystalblockwater),new ItemStack(TBBlocks.crystalblockfire),new ItemStack(TBBlocks.crystalblockair),new ItemStack(BlocksTC.metalBlockThaumium),new ItemStack(BlocksTC.metalBlockThaumium));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.overchanter"),overchanter);
        InfusionRecipe voidseed = new InfusionRecipe("TB.VOIDSEEDS", new ItemStack(TBItems.voidseed),5,new AspectList().add(Aspect.DARKNESS,135).add(Aspect.MAGIC,125).add(Aspect.MIND,125), new ItemStack(ItemsTC.voidSeed),new ItemStack(Items.NETHER_WART),new ItemStack(Items.CARROT),new ItemStack(Items.POISONOUS_POTATO),new ItemStack(Items.MELON_SEEDS),new ItemStack(Items.PUMPKIN_SEEDS),new ItemStack(Items.WHEAT_SEEDS),new ItemStack(Items.BEETROOT_SEEDS));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.voidseed"),voidseed);
        InfusionRecipe silverpipe = new InfusionRecipe("TB.TOBACCO", new ItemStack(TBItems.silverwoodpipe),5,new AspectList().add(Aspect.AURA,30).add(Aspect.ORDER,60), new ItemStack(TBItems.greatwoodpipe),new ItemStack(BlocksTC.shimmerleaf),new ItemStack(BlocksTC.plankSilverwood),new ItemStack(BlocksTC.plankSilverwood),new ItemStack(BlocksTC.plankSilverwood),new ItemStack(BlocksTC.plankSilverwood),new ItemStack(ItemsTC.salisMundus),new ItemStack(ItemsTC.salisMundus),new ItemStack(ItemsTC.salisMundus));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.silverpipe"),silverpipe);
        InfusionRecipe herobrine = new InfusionRecipe("TB.TOOLS", new ItemStack(TBItems.herobrinesscythe),8,new AspectList().add(Aspect.AVERSION,40).add(Aspect.ENERGY,75).add(Aspect.ENERGY,40).add(Aspect.ELDRITCH,25).add(Aspect.DEATH,25), new ItemStack(ItemsTC.voidHoe),FluidUtil.getFilledBucket(new FluidStack(ConfigBlocks.FluidDeath.instance,Fluid.BUCKET_VOLUME)),new ItemStack(Items.NETHER_STAR),FluidUtil.getFilledBucket(new FluidStack(ConfigBlocks.FluidDeath.instance,Fluid.BUCKET_VOLUME)),new ItemStack(Items.NETHER_STAR), new ItemStack(BlocksTC.metalBlockVoid));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.herobrine"),herobrine);
        InfusionRecipe revolver = new InfusionRecipe("TB.REVOLVER", new ItemStack(TBItems.revolver),8,new AspectList().add(Aspect.FIRE,100).add(Aspect.TOOL,30).add(Aspect.MECHANISM,30).add(Aspect.METAL,75).add(Aspect.ENERGY,100).add(Aspect.AVERSION,250)  , new ItemStack(BlocksTC.metalBlockBrass),new ItemStack(ItemsTC.turretPlacer, 1, 0),new ItemStack(Items.GUNPOWDER),new ItemStack(Blocks.REDSTONE_TORCH),new ItemStack(BlocksTC.logGreatwood), new ItemStack(Items.FIRE_CHARGE),new ItemStack(Items.GUNPOWDER), new ItemStack(TBItems.bullet),new ItemStack(Items.GUNPOWDER), new ItemStack(Items.FIRE_CHARGE), new ItemStack(BlocksTC.logGreatwood),new ItemStack(ItemsTC.mechanismComplex), new ItemStack(Items.GUNPOWDER));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.revolver"),revolver);

        ItemStack rev1 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev1, EnumInfusionEnchantmentGun.TAINT, 1);
        InfusionEnchantmentRecipeGun IEGTAINT = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.TAINT, (new AspectList()).add(Aspect.AVERSION, 30).add(Aspect.FLUX, 30), new IngredientNBTTC(new ItemStack(TBBlocks.crystalblocktainted)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGTAINT"), (InfusionRecipe)IEGTAINT);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGTAINTFAKE"), new InfusionEnchantmentRecipeGun(IEGTAINT, rev1));

        ItemStack rev2 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev2, EnumInfusionEnchantmentGun.HEAVY, 1);
        InfusionEnchantmentRecipeGun IEGHEAVY = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.HEAVY, (new AspectList()).add(Aspect.AVERSION, 30).add(Aspect.METAL, 30), new IngredientNBTTC(new ItemStack(Blocks.ANVIL)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGHEAVY"), (InfusionRecipe)IEGHEAVY);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGHEAVYFAKE"), new InfusionEnchantmentRecipeGun(IEGHEAVY, rev2));

        ItemStack rev3 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev3, EnumInfusionEnchantmentGun.POWER, 1);
        InfusionEnchantmentRecipeGun IEGPOWER = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.POWER, (new AspectList()).add(Aspect.AVERSION, 60), new IngredientNBTTC(new ItemStack(Items.FIRE_CHARGE)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGPOWER"), (InfusionRecipe)IEGPOWER);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGPOWERFAKE"), new InfusionEnchantmentRecipeGun(IEGPOWER, rev3));

        ItemStack rev4 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev4, EnumInfusionEnchantmentGun.SMITE, 1);
        InfusionEnchantmentRecipeGun IEGSMITE = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.SMITE, (new AspectList()).add(Aspect.AVERSION, 30).add(Aspect.UNDEAD, 15).add(Aspect.DEATH, 15), new IngredientNBTTC(new ItemStack(ItemsTC.quicksilver)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGSMITE"), (InfusionRecipe)IEGSMITE);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGSMITEFAKE"), new InfusionEnchantmentRecipeGun(IEGSMITE, rev4));

        ItemStack rev5 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev5, EnumInfusionEnchantmentGun.BOART, 1);
        InfusionEnchantmentRecipeGun IEGBOART = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.BOART, (new AspectList()).add(Aspect.AVERSION, 30).add(Aspect.UNDEAD, 15).add(Aspect.TRAP, 15), new IngredientNBTTC(new ItemStack(Items.FERMENTED_SPIDER_EYE)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGBOART"), (InfusionRecipe)IEGBOART);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGBOARTFAKE"), new InfusionEnchantmentRecipeGun(IEGBOART, rev5));

        ItemStack rev6 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev6, EnumInfusionEnchantmentGun.BOE, 1);
        InfusionEnchantmentRecipeGun IEGBOE = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.BOE, (new AspectList()).add(Aspect.AVERSION, 30).add(Aspect.ELDRITCH, 30), new IngredientNBTTC(new ItemStack(ItemsTC.voidSword)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGBOE"), (InfusionRecipe)IEGBOE);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGBOEFAKE"), new InfusionEnchantmentRecipeGun(IEGBOE, rev6));

        ItemStack rev7 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev7, EnumInfusionEnchantmentGun.DUELING, 1);
        InfusionEnchantmentRecipeGun IEGDUELING = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.DUELING, (new AspectList()).add(Aspect.DEATH, 30).add(Aspect.MAN, 30), new IngredientNBTTC(new ItemStack(Items.GHAST_TEAR)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGDUELING"), (InfusionRecipe)IEGDUELING);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGDUELINGFAKE"), new InfusionEnchantmentRecipeGun(IEGDUELING, rev7));

        ItemStack rev8 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev8, EnumInfusionEnchantmentGun.WISE, 1);
        InfusionEnchantmentRecipeGun IEGWISE = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.WISE, (new AspectList()).add(Aspect.AURA, 30).add(Aspect.MIND, 30), new IngredientNBTTC(new ItemStack(ItemsTC.brain)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGWISE"), (InfusionRecipe)IEGWISE);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGWISEFAKE"), new InfusionEnchantmentRecipeGun(IEGWISE, rev8));

        ItemStack rev9 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev9, EnumInfusionEnchantmentGun.EFFICIENCY, 1);
        InfusionEnchantmentRecipeGun IEGEFFICIENCY = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.EFFICIENCY, (new AspectList()).add(Aspect.TOOL, 30).add(Aspect.DESIRE, 30), new IngredientNBTTC(new ItemStack(Items.RABBIT_FOOT)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGEFFICIENCY"), (InfusionRecipe)IEGEFFICIENCY);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGEFFICIENCYFAKE"), new InfusionEnchantmentRecipeGun(IEGEFFICIENCY, rev9));

        ItemStack rev10 = new ItemStack(TBItems.revolver);
        EnumInfusionEnchantmentGun.addInfusionEnchantment(rev10, EnumInfusionEnchantmentGun.SPEED, 1);
        InfusionEnchantmentRecipeGun IEGSPEED = new InfusionEnchantmentRecipeGun(EnumInfusionEnchantmentGun.SPEED, (new AspectList()).add(Aspect.AIR, 30).add(Aspect.MOTION, 30), new IngredientNBTTC(new ItemStack(ItemsTC.mechanismSimple)), new ItemStack((Item)Items.ENCHANTED_BOOK));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicbases:IEGSPEED"), (InfusionRecipe)IEGSPEED);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumicbases:IEGSPEEDFAKE"), new InfusionEnchantmentRecipeGun(IEGSPEED, rev10));

        /*==============================================INFUSING END=============================================================================*/

        /*TOBACCO INIT*/
        CrucibleRecipe tobacco = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.tobaccoseed),new ItemStack(Items.WHEAT_SEEDS),new AspectList().add(Aspect.MIND,20).add(Aspect.WATER,25).add(Aspect.MAN,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.tobacco"), tobacco);
        CrucibleRecipe catusToRC= new CrucibleRecipe("TB.CACTUS",new ItemStack(TBBlocks.rainbowcactus),new ItemStack(Blocks.CACTUS),new AspectList().add(Aspect.PLANT,60).add(Aspect.MAGIC,30));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.catusToRC"),catusToRC);


        CrucibleRecipe angry= new CrucibleRecipe("TB.TOBACCO",new ItemStack(TBItems.tobacco_fighting),new ItemStack(TBItems.tobacco_pile),new AspectList().add(Aspect.AVERSION,20));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.angry"),angry);
        CrucibleRecipe wisdom = new CrucibleRecipe("TB.TOBACCO",new ItemStack(TBItems.tobacco_knowledge),new ItemStack(TBItems.tobacco_pile),new AspectList().add(Aspect.MIND,15).add(Aspect.AIR,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.wisdom"),wisdom);
        CrucibleRecipe miner = new CrucibleRecipe("TB.TOBACCO",new ItemStack(TBItems.tobacco_mining),new ItemStack(TBItems.tobacco_pile),new AspectList().add(Aspect.MOTION,15).add(Aspect.TOOL,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.miner"),miner);
        CrucibleRecipe tainted = new CrucibleRecipe("TB.TOBACCO",new ItemStack(TBItems.tobacco_tainted),new ItemStack(TBItems.tobacco_pile),new AspectList().add(Aspect.MIND,15).add(Aspect.FLUX,15));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.tainted"),tainted);
        InfusionRecipe eldritchtobacco = new InfusionRecipe("TB.TOBACCO", new ItemStack(TBItems.tobacco_eldritch),2,new AspectList().add(Aspect.ELDRITCH,15).add(Aspect.DEATH,5), new ItemStack(TBItems.tobacco_pile),new ItemStack(Items.ENDER_EYE),new ItemStack(Items.ARROW));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.eldritchtobacco"),eldritchtobacco);
        InfusionRecipe wispy = new InfusionRecipe("TB.TOBACCO", new ItemStack(TBItems.tobacco_wispy),2,new AspectList().add(Aspect.AURA,10).add(Aspect.MAGIC,10), new ItemStack(TBItems.tobacco_pile),new ItemStack(ItemsTC.salisMundus),ConfigItems.AIR_CRYSTAL);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.wispy"),wispy);

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.relax"), new ShapedArcaneRecipe(defaultGroup, "TB.TOBACCO",10, new AspectList()
                .add(Aspect.ORDER,1)
                .add(Aspect.AIR,1)
                .add(Aspect.ENTROPY,1)

                , new ItemStack(TBItems.tobacco_sanity,1),
                new Object[] {
                        "P  ",
                        "   ",
                        "   "
                        ,Character.valueOf('P'), new ItemStack(TBItems.tobacco_pile)}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.hunger"), new ShapedArcaneRecipe(defaultGroup, "TB.TOBACCO",10, new AspectList()
                .add(Aspect.ORDER,1)
                .add(Aspect.AIR,1)
                .add(Aspect.ENTROPY,1)

                , new ItemStack(TBItems.tobacco_hunger,1),
                new Object[] {
                        "Ps ",
                        "   ",
                        "   "
                        ,Character.valueOf('P'), new ItemStack(TBItems.tobacco_pile),Character.valueOf('s'), "seedsStems"}));

    }





    public static void $insertAspects()
    {


        appendAspects(new ItemStack(BlocksTC.vishroom), new AspectList().add(Aspect.FLUX, 2));
        appendAspects(new ItemStack(Items.LEATHER), new AspectList());
        appendAspects(new ItemStack(Items.RABBIT_HIDE), new AspectList());
        appendAspects(new ItemStack(Items.LEATHER_BOOTS), new AspectList());
        appendAspects(new ItemStack(Items.LEATHER_CHESTPLATE), new AspectList());
        appendAspects(new ItemStack(Items.LEATHER_HELMET), new AspectList());
        appendAspects(new ItemStack(Items.LEATHER_LEGGINGS), new AspectList());
        appendAspects(new ItemStack(TBItems.aureliapetal),new AspectList().add(Aspect.AURA,10));
        appendAspects(new ItemStack(TBBlocks.goldenleaves),new AspectList().add(Aspect.PLANT,5));
        appendAspects(new ItemStack(TBBlocks.enderleaves),new AspectList().add(Aspect.PLANT,5));
        appendAspects(new ItemStack(TBBlocks.netherleaves),new AspectList().add(Aspect.PLANT,5));

        appendAspects(new ItemStack(TBItems.bloodycloth), new AspectList().add(Aspect.BEAST,26).add(Aspect.CRAFT,6));
        appendAspects(new ItemStack(TBItems.briar_seedbag), new AspectList().add(Aspect.PLANT,5).add(Aspect.LIFE,5));
            appendAspects(new ItemStack(TBItems.tobacco_pile), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAN, 3).add(Aspect.ENTROPY, 1));
            appendAspects(new ItemStack(TBItems.tobacco_leaves), new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAN, 5));
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






