package com.rumaruka.tb.init;


import com.google.common.collect.ObjectArrays;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;
import com.rumaruka.tb.common.block.*;
import com.rumaruka.tb.common.itemblocks.*;
import com.rumaruka.tb.core.TBCore;
import joptsimple.internal.Strings;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TBBlocks {




    //Fluid
    public static Block pyrofluid;
    public static Block pyrosolid;
    //TB Blocks
    public static Block crystalblockair;
    public static Block crystalblockfire;
    public static Block crystalblockwater;
    public static Block crystalblockearth;
    public static Block crystalblockorder;
    public static Block crystalblockentropy;
    public static Block crystalblockmixed;
    public static Block crystalblocktainted;





    //ThaumcraftBlocks
    public static Block quicksilverblock;
    public static Block quicksilverbrick;
    public static Block dustblock;
    public static Block blockthauminite;
    public static Block irongreatwood;
    public static Block eldritchark;
    //Old Style
    public static Block oldcobble;
    public static Block oldcobblemossy;
    public static Block oldgravel;
    public static Block oldbrick;
    public static Block oldlapis;
    public static Block oldiron;
    public static Block oldgold;
    public static Block olddiamond;

    //Plant
    public static Block sweed;
    public static Block plax;
    public static Block rainbowcactus;
    public static Block aurelia;
    public static Block aureliapetalb;
    public static Block metalleat;
    public static Block lazullia;
    public static Block redlonstem;
    public static Block lucrite;
    public static Block flaxium;
    public static Block briar;
    public static Block glieonia;
    public static Block voidplant;
    public static Block tobacco;
    //Spike
    public static Block ironspike;
    public static Block thaumspike;
    public static Block voidspike;

    //Trees TB
    public static Block goldenlogs;
    public static Block goldenleaves;
    public static Block goldensapling;
    public static Block goldenplanks;


    public static Block netherlogs;
    public static Block netherleaves;
    public static Block nethersapling;
    public static Block netherplanks;

    public static Block enderlogs;
    public static Block enderleaves;
    public static Block endersapling;
    public static Block enderplanks;




    //Main Mechanism TB
    public static Block overchanter;
    public static Block campfire;
    public static Block braizer;





    public static void init() {

        //Old Style Blocks
        oldcobble = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldcobble").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldcobble.setHarvestLevel("piclaxe", 0);
        oldcobblemossy = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldcobblemossy").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldcobblemossy.setHarvestLevel("piclaxe", 0);
        oldgravel = new TBBlock(Material.SAND, false).setSoundType(SoundType.GROUND).setUnlocalizedName("oldgravel").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldgravel.setHarvestLevel("shovel", 0);
        oldbrick = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldbrick").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldbrick.setHarvestLevel("piclaxe", 0);
        oldlapis = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldlapis").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldlapis.setHarvestLevel("piclaxe", 0);
        oldiron = new TBSidedBlock(Material.IRON, false).setUnlocalizedName("oldiron").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldiron.setHarvestLevel("piclaxe", 0);
        oldgold = new TBSidedBlock(Material.IRON, false).setUnlocalizedName("oldgold").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldgold.setHarvestLevel("piclaxe", 0);
        olddiamond = new TBSidedBlock(Material.IRON, false).setUnlocalizedName("olddiamond").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        olddiamond.setHarvestLevel("piclaxe", 0);
        //Thaumcraft DecoBlock
        quicksilverblock = new TBBlock(Material.ROCK, false).stabilise().setUnlocalizedName("quicksilverBlock").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        quicksilverblock.setHarvestLevel("pickaxe", 0);
        quicksilverbrick = new TBBlock(Material.ROCK, false).stabilise().setUnlocalizedName("quicksilverBrick").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        quicksilverbrick.setHarvestLevel("pickaxe", 0);
        dustblock = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("dustBlock").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        dustblock.setHarvestLevel("pickaxe", 0);
        blockthauminite = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("blockthauminite").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        blockthauminite.setHarvestLevel("pickaxe", 0);
        irongreatwood = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("irongreatwood").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        irongreatwood.setHarvestLevel("pickaxe", 0);
        eldritchark = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("eldritchark").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        eldritchark.setHarvestLevel("pickaxe", 0);
        //TB Blocks
        crystalblockair = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockair").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockair.setHarvestLevel("pickaxe", 0);
        crystalblockfire = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockfire").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockfire.setHarvestLevel("pickaxe", 0);
        crystalblockwater = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockwater").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockwater.setHarvestLevel("pickaxe", 0);
        crystalblockearth = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockearth").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockearth.setHarvestLevel("pickaxe", 0);
        crystalblockorder = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockorder").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockorder.setHarvestLevel("pickaxe", 0);
        crystalblockentropy = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockentropy").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockentropy.setHarvestLevel("pickaxe", 0);
        crystalblockmixed = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblockmixed").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockmixed.setHarvestLevel("pickaxe", 0);
        crystalblocktainted = new TBBlockCrytal(Material.GLASS, false).setUnlocalizedName("crystalblocktainted").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblocktainted.setHarvestLevel("pickaxe", 0);
        pyrofluid = new BlockPyrofluid();
        pyrosolid = new BlockPyrosolid().setUnlocalizedName("pyrosolid");
        //Plant
        sweed = new BlockSweed(4,4,false).setUnlocalizedName("sweed");
        plax = new BlockPlax(7, 4, true).setUnlocalizedName("plax");
        aurelia = new BlockAureliaPlant().setUnlocalizedName("aurelia").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        aureliapetalb = new BlockAureliaLeaf().setUnlocalizedName("aureliapetalb");
        tobacco = new BlockTobacco(7,4,true).setUnlocalizedName("tobacco");
        metalleat = new BlockMetalleat(7,8,true).setUnlocalizedName("metalleat");
        lazullia = new BlockLazullia(7,16,true).setUnlocalizedName("lazullia");
        redlonstem = new BlockRedlonStem(Blocks.REDSTONE_BLOCK).setUnlocalizedName("redlonstem");
        lucrite = new BlockLucritePlant(7,16,true).setUnlocalizedName("lucrite");
        flaxium = new BlockFlaxium().setUnlocalizedName("flaxium");
        briar = new BlockTBBush().setUnlocalizedName("briar");
        glieonia = new BlockGlieonia(3,12,false).setUnlocalizedName("glieonia");
        voidplant = new BlockVoidPlant(3,32,true).setUnlocalizedName("voidplant");
        //Tree
        goldenlogs = new BlockTBLog().setUnlocalizedName("goldenlogs").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        goldenleaves = new BlockTBLeaves(0).setUnlocalizedName("goldenleaves").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        goldenplanks = new BlockTBPlanks().setUnlocalizedName("goldenplanks").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        goldensapling = new BlockTBSapling(0).setUnlocalizedName("goldensapling").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        nethersapling = new BlockTBSapling(1).setUnlocalizedName("nethersapling").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        netherleaves = new BlockTBLeaves(1).setUnlocalizedName("netherleaves").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        netherplanks = new BlockTBPlanks().setUnlocalizedName("netherplanks").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        netherlogs = new BlockTBLog().setUnlocalizedName("netherlogs").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        endersapling = new BlockTBSapling(2).setUnlocalizedName("endersapling").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        enderleaves = new BlockTBLeaves(2).setUnlocalizedName("enderleaves").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        enderplanks = new BlockTBPlanks().setUnlocalizedName("enderplanks").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        enderlogs = new BlockTBLog().setUnlocalizedName("enderlogs").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        //Spike
        ironspike = new BlockIronSpike().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("ironspike");
        thaumspike = new BlockThaumSpike().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("thaumspike");
        voidspike = new BlockVoidSpike().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("voidspike");
        //Main Mechanism TB
        overchanter = new BlockOverchanter().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("overchanter");
        campfire = new BlockCampfire().setUnlocalizedName("campfire").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        braizer = new BlockBraizer().setUnlocalizedName("braizer").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

       //Cactus ALONE :<
        rainbowcactus = new BlockRainbowCactus().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("rainbowcactus");
    }


    public static void InGameRegister() {


        TBBlocks.registerBlock(oldcobble, oldcobble.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(oldcobblemossy, oldcobblemossy.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(oldgravel, oldgravel.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(oldbrick, oldbrick.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(oldlapis, oldlapis.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(oldiron, oldiron.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(oldgold, oldgold.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(olddiamond, olddiamond.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(quicksilverblock, quicksilverblock.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(quicksilverbrick, quicksilverbrick.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(dustblock, dustblock.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(blockthauminite, blockthauminite.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(irongreatwood, irongreatwood.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(eldritchark, eldritchark.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(crystalblockair, ItemBlockCrystal.class, crystalblockair.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblockfire, ItemBlockCrystal.class, crystalblockfire.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblockwater, ItemBlockCrystal.class, crystalblockwater.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblockearth, ItemBlockCrystal.class, crystalblockearth.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblockorder, ItemBlockCrystal.class, crystalblockorder.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblockentropy, ItemBlockCrystal.class, crystalblockentropy.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblockmixed, ItemBlockCrystal.class, crystalblockmixed.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(crystalblocktainted, ItemBlockCrystal.class, crystalblocktainted.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(pyrofluid,pyrofluid.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(pyrosolid,pyrosolid.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(rainbowcactus,rainbowcactus.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(plax,plax.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(sweed,sweed.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(aurelia,aurelia.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(aureliapetalb,aureliapetalb.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(tobacco,tobacco.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(metalleat,metalleat.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(lazullia,lazullia.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(redlonstem,redlonstem.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(lucrite,lucrite.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(flaxium,flaxium.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(briar,briar.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(glieonia,glieonia.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(voidplant,voidplant.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(ironspike,ironspike.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(thaumspike,thaumspike.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(voidspike,voidspike.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(goldenlogs,goldenlogs.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(goldenleaves,goldenleaves.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(goldensapling,goldensapling.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(goldenplanks,goldenplanks.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(endersapling,endersapling.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(enderlogs,enderlogs.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(enderleaves,enderleaves.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(enderplanks,enderplanks.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(nethersapling,nethersapling.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(netherlogs,netherlogs.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(netherleaves,netherleaves.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(netherplanks,netherplanks.getUnlocalizedName().substring(5));


        TBBlocks.registerBlock(overchanter,overchanter.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(campfire,campfire.getUnlocalizedName().substring(5));
        TBBlocks.registerBlock(braizer,braizer.getUnlocalizedName().substring(5));

    }

    @Deprecated
    public static Block registerBlock(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        return block;
    }

    @Deprecated
    public static Block registerBlock(Block block, String name) {
        if (block.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a Block with no name: " + block);

        return registerBlock(block.getRegistryName() != null ? block : block.setRegistryName(name));
    }

    @Deprecated
    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, Object... itemCtorArgs) {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Attempted to register a block with no name: " + block);
        }
        if (Loader.instance().isInState(LoaderState.CONSTRUCTING)) {
            FMLLog.warning("The mod %s is attempting to register a block whilst it it being constructed. This is bad modding practice - please use a proper mod lifecycle event.", Loader.instance().activeModContainer());
        }
        try {
            assert block != null : "registerBlock: block cannot be null";
            if (block.getRegistryName() != null && !block.getRegistryName().toString().equals(name))
                throw new IllegalArgumentException("Attempted to register a Block with conflicting names. Old: " + block.getRegistryName() + " New: " + name);
            ItemBlock i = null;
            if (itemclass != null) {
                Class<?>[] ctorArgClasses = new Class<?>[itemCtorArgs.length + 1];
                ctorArgClasses[0] = Block.class;
                for (int idx = 1; idx < ctorArgClasses.length; idx++) {
                    ctorArgClasses[idx] = itemCtorArgs[idx - 1].getClass();
                }
                Constructor<? extends ItemBlock> itemCtor = itemclass.getConstructor(ctorArgClasses);
                i = itemCtor.newInstance(ObjectArrays.concat(block, itemCtorArgs));
            }
            // block registration has to happen first
            ForgeRegistries.BLOCKS.register(block.getRegistryName() == null ? block.setRegistryName(name) : block);
            if (i != null)
                ForgeRegistries.ITEMS.register(i.setRegistryName(name));
            return block;
        } catch (Exception e) {
            FMLLog.log(Level.ERROR, e, "Caught an exception during block registration");
            throw new LoaderException(e);
        }
    }

    public static void Render() {


        registerRender(oldcobble);
        registerRender(oldcobblemossy);
        registerRender(oldgravel);
        registerRender(oldbrick);
        registerRender(oldlapis);
        registerRender(oldiron);
        registerRender(oldgold);
        registerRender(olddiamond);



        registerRender(quicksilverblock);
        registerRender(quicksilverbrick);
        registerRender(dustblock);
        registerRender(blockthauminite);
        registerRender(irongreatwood);
        registerRender(eldritchark);


        registerRender(crystalblockair);
        registerRender(crystalblockfire);
        registerRender(crystalblockwater);
        registerRender(crystalblockearth);
        registerRender(crystalblockorder);
        registerRender(crystalblockentropy);
        registerRender(crystalblockmixed);
        registerRender(crystalblocktainted);


        registerRender(pyrofluid);
        registerRender(pyrosolid);


        registerRender(rainbowcactus);
        registerRender(plax);
        registerRender(sweed);
        registerRender(aurelia);
        registerRender(aureliapetalb);
        registerRender(tobacco);
        registerRender(metalleat);
        registerRender(lazullia);
        registerRender(lucrite);
        registerRender(redlonstem);
        registerRender(flaxium);
        registerRender(briar);
        registerRender(glieonia);
        registerRender(voidplant);


        registerRender(ironspike);
        registerRender(thaumspike);
        registerRender(voidspike);


        registerRender(goldensapling);
        renderBlockItems(Item.getItemFromBlock(goldensapling));
        registerRender(goldenplanks);
        registerRender(goldenleaves);
        registerRender(goldenlogs);


        registerRender(endersapling);
        renderBlockItems(Item.getItemFromBlock(endersapling));
        registerRender(enderlogs);
        registerRender(enderplanks);
        registerRender(enderleaves);


        registerRender(nethersapling);
        renderBlockItems(Item.getItemFromBlock(nethersapling));
        registerRender(netherlogs);
        registerRender(netherleaves);
        registerRender(netherplanks);


        registerRender(overchanter);
        registerRender(campfire);
        registerRender(braizer);
    }

    public static void registerRender(Block block) {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(TBCore.modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
    public static void renderBlockItems(Item i){

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(
                TBCore.modid + ":" + i.getUnlocalizedName().substring(5), "inventory"));
    }
}





