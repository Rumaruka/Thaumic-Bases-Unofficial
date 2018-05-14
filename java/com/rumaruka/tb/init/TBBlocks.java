package com.rumaruka.tb.init;

import DummyCore.Registries.BlockRegistry;
import DummyCore.Utils.CustomStepSound;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;

import com.rumaruka.tb.common.block.*;
import com.rumaruka.tb.common.itemblocks.*;
import com.rumaruka.tb.core.TBCore;
import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IRegistryDelegate;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.IThaumcraftRecipe;
import thaumcraft.api.items.ItemsTC;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;


public class TBBlocks {

        //TB Blocks
        public static Block crystalblockair;
        public static Block crystalblockfire;
        public static Block crystalblockwater;
        public static Block crystalblockearth;
        public static Block crystalblockorder;
        public static Block crystalblockentropy;
        public static Block crystalblockmixed;
        public static Block crystalblocktainted;
        public static Block pyrofluid;

        //Trees TB

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
        public static Block plax;
        //Spike

        //Slabs


        //Main Mechanism TB


    public static void init(){
            //Old Style Blocks
        oldcobble = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldcobble").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldcobble.setHarvestLevel("piclaxe",0);
        oldcobblemossy = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldcobblemossy").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldcobblemossy.setHarvestLevel("piclaxe",0);
        oldgravel = new TBBlock(Material.SAND,false).setSoundType(SoundType.GROUND).setUnlocalizedName("oldgravel").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldgravel.setHarvestLevel("shovel",0);
        oldbrick = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldbrick").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldbrick.setHarvestLevel("piclaxe",0);
        oldlapis = new TBBlock(Material.ROCK, false).setUnlocalizedName("oldlapis").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldlapis.setHarvestLevel("piclaxe",0);

        oldiron = new TBSidedBlock(Material.IRON, false).setUnlocalizedName("oldiron").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldiron.setHarvestLevel("piclaxe",0);
        oldgold = new TBSidedBlock(Material.IRON, false).setUnlocalizedName("oldgold").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        oldgold.setHarvestLevel("piclaxe",0);
        olddiamond = new TBSidedBlock(Material.IRON,false).setUnlocalizedName("olddiamond").setHardness(1).setResistance(1).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        olddiamond.setHarvestLevel("piclaxe",0);


            //Thaumcraft DecoBlock
        quicksilverblock = new TBBlock(Material.ROCK, false).stabilise().setUnlocalizedName("quicksilverBlock").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        quicksilverblock.setHarvestLevel("pickaxe",0);
        quicksilverbrick = new TBBlock(Material.ROCK,false).stabilise().setUnlocalizedName("quicksilverBrick").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        quicksilverbrick.setHarvestLevel("pickaxe",0);
        dustblock = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("dustBlock").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        dustblock.setHarvestLevel("pickaxe",0);
        blockthauminite = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("blockthauminite").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        blockthauminite.setHarvestLevel("pickaxe",0);
        irongreatwood = new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("irongreatwood").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        irongreatwood.setHarvestLevel("pickaxe",0);
        eldritchark= new TBBlock(Material.SAND, false).stabilise().setUnlocalizedName("eldritchark").setHardness(0.5F).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        eldritchark.setHarvestLevel("pickaxe",0);
        //TB Blocks
        crystalblockair = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockair").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockair.setHarvestLevel("pickaxe",0);
        crystalblockfire = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockfire").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockfire.setHarvestLevel("pickaxe",0);
        crystalblockwater = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockwater").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockwater.setHarvestLevel("pickaxe",0);
        crystalblockearth = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockearth").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockearth.setHarvestLevel("pickaxe",0);
        crystalblockorder = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockorder").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockorder.setHarvestLevel("pickaxe",0);
        crystalblockentropy = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockentropy").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockentropy.setHarvestLevel("pickaxe",0);
        crystalblockmixed = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblockmixed").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblockmixed.setHarvestLevel("pickaxe",0);
        crystalblocktainted = new TBBlockCrytal(Material.GLASS,false).setUnlocalizedName("crystalblocktainted").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setHardness(0.5F);
        crystalblocktainted.setHarvestLevel("pickaxe",0);
        pyrofluid = new BlockPyrofluid().setLightLevel(1).setUnlocalizedName("pyrofluid");
        //Plant
        plax = new BlockTBPlant();

    }



        public static void InGameRegister(){
            TBBlocks.registerBlock(oldcobble,oldcobble.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(oldcobblemossy, oldcobblemossy.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(oldgravel, oldgravel.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(oldbrick,oldbrick.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(oldlapis,oldlapis.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(oldiron, oldiron.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(oldgold, oldgold.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(olddiamond, olddiamond.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(quicksilverblock, quicksilverblock.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(quicksilverbrick, quicksilverbrick.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(dustblock, dustblock.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(blockthauminite, blockthauminite.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(irongreatwood, irongreatwood.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(eldritchark, eldritchark.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockair, ItemBlockCrystal.class,crystalblockair.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockfire,ItemBlockCrystal.class ,crystalblockfire.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockwater,ItemBlockCrystal.class ,crystalblockwater.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockearth,ItemBlockCrystal.class ,crystalblockearth.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockorder,ItemBlockCrystal.class ,crystalblockorder.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockentropy,ItemBlockCrystal.class ,crystalblockentropy.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblockmixed,ItemBlockCrystal.class ,crystalblockmixed.getUnlocalizedName().substring(5));
            TBBlocks.registerBlock(crystalblocktainted,ItemBlockCrystal.class ,crystalblocktainted.getUnlocalizedName().substring(5));

            TBBlocks.registerBlock(pyrofluid, pyrofluid.getUnlocalizedName().substring(5));

    }

        @Deprecated
        public static Block registerBlock(Block block)
        {
            ForgeRegistries.BLOCKS.register(block);
            ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
            return block;
        }
        @Deprecated
        public static Block registerBlock(Block block, String name)
        {
            if (block.getRegistryName() == null && Strings.isNullOrEmpty(name))
                throw new IllegalArgumentException("Attempted to register a Block with no name: " + block);
            if (block.getRegistryName() != null && !block.getRegistryName().toString().equals(name))
                throw new IllegalArgumentException("Attempted to register a Block with conflicting names. Old: " + block.getRegistryName() + " New: " + name);
            return registerBlock(block.getRegistryName() != null ? block : block.setRegistryName(name));
        }
        @Deprecated
        public static Block registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, Object... itemCtorArgs)
        {
            if (Strings.isNullOrEmpty(name))
            {
                throw new IllegalArgumentException("Attempted to register a block with no name: " + block);
            }
            if (Loader.instance().isInState(LoaderState.CONSTRUCTING))
            {
                FMLLog.warning("The mod %s is attempting to register a block whilst it it being constructed. This is bad modding practice - please use a proper mod lifecycle event.", Loader.instance().activeModContainer());
            }
            try
            {
                assert block != null : "registerBlock: block cannot be null";
                if (block.getRegistryName() != null && !block.getRegistryName().toString().equals(name))
                    throw new IllegalArgumentException("Attempted to register a Block with conflicting names. Old: " + block.getRegistryName() + " New: " + name);
                ItemBlock i = null;
                if (itemclass != null)
                {
                    Class<?>[] ctorArgClasses = new Class<?>[itemCtorArgs.length + 1];
                    ctorArgClasses[0] = Block.class;
                    for (int idx = 1; idx < ctorArgClasses.length; idx++)
                    {
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
            } catch (Exception e)
            {
                FMLLog.log(Level.ERROR, e, "Caught an exception during block registration");
                throw new LoaderException(e);
            }
        }
        public static void Render(){
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



    }
        public static void registerRender(Block block)
        {
            Item item = Item.getItemFromBlock(block);
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(TBCore.modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
        }


}
