package com.rumaruka.thaumicbases.init;


import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.common.handlers.RevolverInfusionRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.crafting.*;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.List;

import static com.rumaruka.thaumicbases.init.TBFluids.FLUID_PYRO;


public class TBThaumonomicon {




    static ResourceLocation defaultGroup = new ResourceLocation("");
    public ResearchStage.Knowledge THAUMICBASES = new ResearchStage.Knowledge(IPlayerKnowledge.EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory("THAUMICBASES"),5);



    public static void setup(){

        ResearchCategories.registerCategory(catName,null,null,icon,back,back2);










/*==============================================ARCANE CRAFTING BEGIN=============================================================================*/
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldCobble"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldcobble,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.COBBLESTONE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldCobbleMossy"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldcobblemossy,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.MOSSY_COBBLESTONE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldGravel"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldgravel,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.GRAVEL)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldBrick"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldbrick,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.BRICK_BLOCK)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldLapis"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                , new ItemStack(TBBlocks.oldlapis,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.LAPIS_BLOCK)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldIron"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                .add(Aspect.ENTROPY,1)
                .add(Aspect.EARTH,1)
                .add(Aspect.ORDER, 1), new ItemStack(TBBlocks.oldiron,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.IRON_BLOCK)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldGold"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                .add(Aspect.ENTROPY,1)
                .add(Aspect.EARTH,1)
                .add(Aspect.ORDER, 1), new ItemStack(TBBlocks.oldgold,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.GOLD_BLOCK)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.DecoOldDiamond"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO", 50, new AspectList()
                .add(Aspect.ENTROPY,1)
                .add(Aspect.EARTH,1)
                .add(Aspect.ORDER, 1), new ItemStack(TBBlocks.olddiamond,6), "# #", "# #", "# #",
                '#', new ItemStack(Blocks.DIAMOND_BLOCK)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.SMB"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO2", 65, new AspectList()
,

                new ItemStack(TBBlocks.dustblock), "sss", "sss", "sss",
                's', new ItemStack(ItemsTC.salisMundus)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.SMBReturn"), new ShapedArcaneRecipe(defaultGroup, "TB.DECO2", 50, new AspectList()
,

                new ItemStack(ItemsTC.salisMundus,9,0), "   ", " s ", "   ",
                's', new ItemStack(TBBlocks.dustblock)));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.IronSpike"), new ShapedArcaneRecipe(defaultGroup, "TB.SPIKE",20, new AspectList()
                .add(Aspect.FIRE,1)
                .add(Aspect.ENTROPY, 1), new ItemStack(TBBlocks.ironspike,1), " i ", "iIi", "IpI",
                'i', "nuggetIron", 'I',new ItemStack(Items.IRON_INGOT), 'p',new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.ThaumSpike"), new ShapedArcaneRecipe(defaultGroup, "TB.SPIKE1",20, new AspectList()
                .add(Aspect.FIRE,1)
                .add(Aspect.ENTROPY, 1), new ItemStack(TBBlocks.thaumspike,1), " t ", "tTt", "TsT",
                't', new ItemStack(ItemsTC.nuggets,1,6), 'T',new ItemStack(ItemsTC.ingots,1,0), 's',new ItemStack(TBBlocks.ironspike)));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.VoidSpike"), new ShapedArcaneRecipe(defaultGroup, "TB.SPIKE2",20, new AspectList()
                .add(Aspect.FIRE,1)
                .add(Aspect.ENTROPY, 1), new ItemStack(TBBlocks.voidspike,1), " v ", "vVv", "VsV",
                'v', new ItemStack(ItemsTC.nuggets,1,7), 'V',new ItemStack(ItemsTC.ingots,1,1), 's',new ItemStack(TBBlocks.thaumspike)));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.BCHEST"), new ShapedArcaneRecipe(defaultGroup, "TB.BloodFabric",50, new AspectList()
                .add(Aspect.AIR,2)
                    , new ItemStack(TBItems.bloodychest,1), "# #", "###", "###",
                '#', new ItemStack(TBItems.bloodycloth)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.BLEGS"), new ShapedArcaneRecipe(defaultGroup, "TB.BloodFabric",50, new AspectList()
                .add(Aspect.WATER,2)
                , new ItemStack(TBItems.bloodylegs,1), "###",
                "# #", "# #",
                '#', new ItemStack(TBItems.bloodycloth)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.BBOOTS"), new ShapedArcaneRecipe(defaultGroup, "TB.BloodFabric",50, new AspectList()
                .add(Aspect.EARTH,1)
                , new ItemStack(TBItems.bloodyboots,1), "# #", "# #", "   ",
                '#', new ItemStack(TBItems.bloodycloth)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.thaumAnvil"), new ShapedArcaneRecipe(defaultGroup, "TB.THAUMANVIL",50, new AspectList()
                .add(primals(3))
                , new ItemStack(TBBlocks.thaumicAnvil,1)
                , "BBB"
                , " B "
                , "III",
                'B', new ItemStack(BlocksTC.metalBlockThaumium),
                'I', new ItemStack(ItemsTC.ingots)));
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
                "SSS",
                "CUC",
                "GGG",
                'S', new ItemStack(Items.STICK), 'U', new ItemStack(Items.COAL), 'C', new ItemStack(Blocks.COBBLESTONE), 'G', new ItemStack(Blocks.GRAVEL)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.braizer"), new ShapedArcaneRecipe(defaultGroup, "TB.UseFire",50, new AspectList()
                .add(Aspect.ENTROPY,3)
                .add(Aspect.FIRE,3)
                , new ItemStack(TBBlocks.braizer,1),
                "SUS",
                " A ",
                "SSS",
                'S', new ItemStack(BlocksTC.slabArcaneStone), 'U', new ItemStack(Items.COAL), 'A', new ItemStack(BlocksTC.stoneArcane)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.greatpipe"), new ShapedArcaneRecipe(defaultGroup, "TB.TOBACCO",50, new AspectList()
                .add(primals(1))

                , new ItemStack(TBItems.greatwoodpipe,1),
                "gs ",
                " gg",
                's', new ItemStack(ItemsTC.salisMundus), 'g', new ItemStack(BlocksTC.plankGreatwood)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.syrop"), new ShapedArcaneRecipe(defaultGroup, "TB.PLANT1",50, new AspectList()
                , new ItemStack(TBItems.rosehipsyrup,1),
                "pSs",
                's', new ItemStack(Items.SUGAR), 'p', new ItemStack(ItemsTC.phial), 'S',new ItemStack(TBItems.briar_seedbag)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.bullet"), new ShapedArcaneRecipe(defaultGroup, "TB.REVOLVER",80, new AspectList()
                , new ItemStack(TBItems.bullet,16),
                "m",
                "c",
                "b",
                'm', new ItemStack(TBItems.nuggetthauminite), 'c', new IngredientNBTTC(ThaumcraftApiHelper.makeCrystal(Aspect.AVERSION)), 'b',new ItemStack(ItemsTC.plate,1,0)));



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
        CrucibleRecipe lucrite = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.lucriteseed),new ItemStack(Items.GOLDEN_CARROT),new AspectList().add(Aspect.MAGIC,20).add(Aspect.DESIRE,18).add(Aspect.LIFE,14));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.lucrite"), lucrite);
        CrucibleRecipe knoze = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBItems.knozeseed),new ItemStack(Blocks.DOUBLE_PLANT,1,4),new AspectList().add(Aspect.MIND,60).add(Aspect.ENERGY,20).add(Aspect.LIFE,30));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.knoze"), knoze);
        CrucibleRecipe ashroom = new CrucibleRecipe("TB.PLANT1",new ItemStack(TBBlocks.ashroom),new ItemStack(BlocksTC.vishroom),new AspectList().add(Aspect.ENTROPY,10).add(Aspect.AURA,5));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.ashroom"), ashroom);

        CrucibleRecipe curio0 = new CrucibleRecipe("TB.KNOWLEDGE",new ItemStack(ItemsTC.curio, 1, 0),new ItemStack(TBItems.knowledge_shard),new AspectList().add(Aspect.MAGIC,30).add(Aspect.CRAFT, 10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.c0"), curio0);
        CrucibleRecipe curio1 = new CrucibleRecipe("TB.KNOWLEDGE",new ItemStack(ItemsTC.curio, 1, 1),new ItemStack(TBItems.knowledge_shard),new AspectList().add(Aspect.BEAST,30).add(Aspect.CRAFT,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.c1"), curio1);
        CrucibleRecipe curio2 = new CrucibleRecipe("TB.KNOWLEDGE",new ItemStack(ItemsTC.curio, 1, 2),new ItemStack(TBItems.knowledge_shard),new AspectList().add(Aspect.DEATH,30).add(Aspect.CRAFT,10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.c2"), curio2);
        CrucibleRecipe curio3 = new CrucibleRecipe("TB.KNOWLEDGE&&BASEELDRITCH",new ItemStack(ItemsTC.curio, 1, 3),new ItemStack(TBItems.knowledge_shard),new AspectList().add(Aspect.ELDRITCH,30).add(Aspect.DESIRE, 10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.c3"), curio3);
        CrucibleRecipe curio4 = new CrucibleRecipe("TB.KNOWLEDGE",new ItemStack(ItemsTC.curio, 1, 4),new ItemStack(TBItems.knowledge_shard),new AspectList().add(Aspect.MIND,30).add(Aspect.CRAFT, 10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.c4"), curio4);
        CrucibleRecipe curio5 = new CrucibleRecipe("TB.KNOWLEDGE",new ItemStack(ItemsTC.curio, 1, 5),new ItemStack(TBItems.knowledge_shard),new AspectList().add(Aspect.FLUX,30).add(Aspect.CRAFT, 10));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("TB.c5"), curio5);
        /*==============================================ALCHEMY END===============================================================================*/



        /*==============================================INFUSING BEGIN=============================================================================*/
        InfusionRecipe goldenSapling = new InfusionRecipe("TB.TREE", new ItemStack(TBBlocks.goldensapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.DESIRE,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(Items.GOLDEN_APPLE),new ItemStack(Items.GOLDEN_APPLE),new ItemStack(Items.APPLE),new ItemStack(Items.APPLE));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.goldenSapling"),goldenSapling);
        InfusionRecipe netherSapling = new InfusionRecipe("TB.TREE2", new ItemStack(TBBlocks.nethersapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.FIRE,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(Items.NETHER_WART),new ItemStack(Items.NETHER_WART),new ItemStack(Items.BLAZE_POWDER),new ItemStack(Items.BLAZE_POWDER));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.netherSapling"),netherSapling);
        InfusionRecipe enderSapling = new InfusionRecipe("TB.TREE1", new ItemStack(TBBlocks.endersapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.AURA,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.ENDER_EYE),new ItemStack(Items.ENDER_EYE));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.enderSapling"),enderSapling);
        InfusionRecipe peaceSapling = new InfusionRecipe("TB.TREE3", new ItemStack(TBBlocks.peacesapling),4,new AspectList().add(Aspect.PLANT,150).add(Aspect.LIFE,250), new ItemStack(Blocks.SAPLING,1,0),new ItemStack(BlocksTC.lampFertility),new ItemStack(BlocksTC.lampFertility),new ItemStack(Items.WHEAT),new ItemStack(Items.BEETROOT));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.peaceSapling"),peaceSapling);
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

        InfusionRecipe voidAnvil = new InfusionRecipe("TB.VOIDANVIL", new ItemStack(TBBlocks.voidAnvil),6,new AspectList().add(Aspect.TOOL,125).add(Aspect.MAGIC,125).add(Aspect.ELDRITCH,75).add(Aspect.METAL,75) , new ItemStack(TBBlocks.thaumicAnvil),new ItemStack(BlocksTC.metalBlockVoid),new ItemStack(BlocksTC.metalBlockVoid),new ItemStack(BlocksTC.metalBlockVoid),new ItemStack(BlocksTC.metalBlockVoid),new ItemStack(BlocksTC.metalBlockVoid),new ItemStack(BlocksTC.metalBlockVoid));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.voidAnvil"),voidAnvil);

        RevolverInfusionRecipe accuracyRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.accuracy, 1, new AspectList().add(Aspect.ORDER, 8).add(Aspect.SENSES,8), new ItemStack[]{
                new ItemStack(Blocks.GLASS_PANE,1,0),
                new ItemStack(Items.CARROT,1,0),
                new ItemStack(Blocks.GLASS_PANE,1,0),
                new ItemStack(Blocks.GLASS_PANE,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Blocks.GLASS_PANE,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.accuracy"),accuracyRec);

        RevolverInfusionRecipe atropodsRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.atropodsBane, 1, new AspectList().add(Aspect.DEATH, 8).add(Aspect.BEAST, 8).add(Aspect.CRAFT, 8), new ItemStack[]{
                new ItemStack(Items.SPIDER_EYE,1,0),
                new ItemStack(Items.FERMENTED_SPIDER_EYE,1,0),
                new ItemStack(Items.SPIDER_EYE,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.atropodsBane"),atropodsRec);

        RevolverInfusionRecipe eldritchBRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.eldritchBane, 2, new AspectList().add(Aspect.DEATH, 8).add(Aspect.ELDRITCH, 8), new ItemStack[]{
                new ItemStack(Items.ENDER_EYE,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.ENDER_PEARL,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.eldritchBRec"),eldritchBRec);

        RevolverInfusionRecipe duelingRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.dueling, 2, new AspectList().add(Aspect.DEATH, 8).add(Aspect.MAN, 8), new ItemStack[]{
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.BED,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.GHAST_TEAR,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.duelingRec"),duelingRec);

        RevolverInfusionRecipe efficiencyRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.efficiency, 0, new AspectList().add(Aspect.ORDER, 8).add(Aspect.EXCHANGE, 8), new ItemStack[]{
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.BLAZE_POWDER,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.ENCHANTED_BOOK,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.efficiencyRec"),efficiencyRec);

        RevolverInfusionRecipe eldritchRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.eldritch, 5, new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.VOID, 8), new ItemStack[]{
                new ItemStack(ItemsTC.eldritchEye,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(ItemsTC.eldritchEye,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.eldritchRec"),eldritchRec);

        RevolverInfusionRecipe heavyRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.heavy, 0, new AspectList().add(Aspect.AVERSION, 8).add(Aspect.METAL, 8), new ItemStack[]{
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.IRON_SWORD,1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ItemsTC.salisMundus,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.heavyRec"),heavyRec);

        RevolverInfusionRecipe knowledgeRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.knowledge, 0, new AspectList().add(Aspect.MIND, 8).add(Aspect.AURA, 8), new ItemStack[]{
                new ItemStack(ItemsTC.scribingTools,1,OreDictionary.WILDCARD_VALUE),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.BOOK,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.PAPER,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.knowledgeRec"),knowledgeRec);

        RevolverInfusionRecipe piercingRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.piercig, 12, new AspectList().add(Aspect.AVERSION, 32).add(Aspect.AIR, 64), new ItemStack[]{
                new ItemStack(TBBlocks.crystalblockfire,1,0),
                new ItemStack(TBBlocks.crystalblockwater,1,0),
                new ItemStack(TBBlocks.crystalblockair,1,0),
                new ItemStack(Items.DIAMOND_SWORD,1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(TBBlocks.crystalblockearth,1,0),
                new ItemStack(TBBlocks.crystalblockentropy,1,0),
                new ItemStack(TBBlocks.crystalblockorder,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.piercingRec"),piercingRec);

        RevolverInfusionRecipe powerRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.power, 2, new AspectList().add(Aspect.AVERSION, 16), new ItemStack[]{
                new ItemStack(Items.GUNPOWDER),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.FIRE_CHARGE),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.FIREWORKS,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.powerRec"),powerRec);

        RevolverInfusionRecipe primalRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.primal, 12, new AspectList().add(Aspect.AVERSION, 64).add(primals(64)).add(Aspect.ENERGY, 64), new ItemStack[]{
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(ItemsTC.primordialPearl,1,0),
                new ItemStack(Items.DIAMOND_SWORD),
                new ItemStack(ItemsTC.primordialPearl,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.primalRec"),primalRec);

        RevolverInfusionRecipe silverRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.silver, 0, new AspectList().add(Aspect.DEATH, 8).add(Aspect.BEAST, 8).add(Aspect.UNDEAD, 8), new ItemStack[]{
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(ItemsTC.quicksilver,1,0),
                new ItemStack(ItemsTC.quicksilver,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.silverRec"),silverRec);

        RevolverInfusionRecipe speedRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.speed, 2, new AspectList().add(Aspect.MOTION, 8).add(Aspect.AIR, 8), new ItemStack[]{
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(Items.FEATHER),
                new ItemStack(ItemsTC.salisMundus,1,0),
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.speedRec"),speedRec);

        RevolverInfusionRecipe taintedRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.tainted, 4, new AspectList().add(Aspect.FLUX, 8).add(Aspect.AVERSION, 8), new ItemStack[]{
                new ItemStack(BlocksTC.crystalTaint,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0),
                new ItemStack(BlocksTC.crystalTaint,1,0),
                new ItemStack(ItemsTC.salisMundus,1,0)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.taintedRec"),taintedRec);

        RevolverInfusionRecipe voidRec = new RevolverInfusionRecipe("TB.REVOLVER.2", RevolverUpgrade.uvoid, 4, new AspectList().add(Aspect.VOID, 8).add(Aspect.TOOL, 8), new ItemStack[]{
                new ItemStack(ItemsTC.ingots,1,1),
                new ItemStack(ItemsTC.ingots,1,1),
                new ItemStack(ItemsTC.ingots,1,1),
                new ItemStack(ItemsTC.ingots,1,1),
                new ItemStack(ItemsTC.ingots,1,1)
        });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("TB.REVOLVER.voidRec"),voidRec);
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
                "P  ",
                "   ",
                "   "
                , 'P', new ItemStack(TBItems.tobacco_pile)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("TB.hunger"), new ShapedArcaneRecipe(defaultGroup, "TB.TOBACCO",10, new AspectList()
                .add(Aspect.ORDER,1)
                .add(Aspect.AIR,1)
                .add(Aspect.ENTROPY,1)

                , new ItemStack(TBItems.tobacco_hunger,1),
                "Ps ",
                "   ",
                "   "
                , 'P', new ItemStack(TBItems.tobacco_pile),'s', "seedsStems"));

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






