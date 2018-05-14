package com.rumaruka.tb.init;
import com.google.common.base.Strings;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;


import com.rumaruka.tb.common.TBMaterial;
import com.rumaruka.tb.common.item.*;
import com.rumaruka.tb.common.item.recourse.TBItemNuggetThauminite;
import com.rumaruka.tb.common.item.recourse.TBItemThauminiteIngot;

import com.rumaruka.tb.core.TBCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.common.items.tools.ItemElementalSword;
import thaumcraft.common.items.tools.ItemThaumiumAxe;

public class TBItems {




    public static ArmorMaterial thauminiteA = EnumHelper.addArmorMaterial("ATHAUMINITE", "thaumicbases:textures/items/armor/thauminite/thauminite", 27, new int[]{3, 8, 6, 3}, 17,SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static ArmorMaterial bloodyA = EnumHelper.addArmorMaterial("TBBLOODY", "thaumicbases:textures/items/armor/bloody/bloody" ,21, new int[]{2, 6, 5, 2}, 21, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);


    //Seeds
    public static Item plaxSeed;


    //Metall
    public static Item nuggetthauminite;
    public static Item thauminite_ingot;
    //Ex-Fragments
    public static Item airingot;
    public static Item fireingot;
    public static Item wateringot;
    public static Item earthingot;
    public static Item orderingot;
    public static Item entropyingot;
    public static Item mixedingot;
    public static Item taintedingot;
    //Tools
    public static Item thauminiteaxe;

    //Pyr
    public static Item pyrobucket;



    public static void init(){

       nuggetthauminite = new TBItemNuggetThauminite().setUnlocalizedName("nuggetthauminite").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       thauminite_ingot = new TBItemThauminiteIngot().setUnlocalizedName("thauminite_ingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

       airingot = new TBAirIngot().setUnlocalizedName("airingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       fireingot = new TBFireIngot().setUnlocalizedName("fireingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       wateringot = new TBWaterIngot().setUnlocalizedName("wateringot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       earthingot = new TBEarthIngot().setUnlocalizedName("earthingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       orderingot = new TBOrderIngot().setUnlocalizedName("orderingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       entropyingot = new TBEntropyIngot().setUnlocalizedName("entropyingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       mixedingot = new TBMixedIngot().setUnlocalizedName("mixedingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       taintedingot = new TBTaintedIngot().setUnlocalizedName("taintedingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);


       thauminiteaxe =  new ItemThauminiteAxe(TBMaterial.thauminite).setUnlocalizedName("thauminiteaxe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);


       plaxSeed = new ItemSeeds(TBBlocks.plax, Blocks.FARMLAND);

        pyrobucket = new ItemPyrofluidBucket().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("pyrobucket");
    }


    public static void InGameRegistr(){
        TBItems.registerItem(nuggetthauminite, nuggetthauminite.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminite_ingot,thauminite_ingot.getUnlocalizedName().substring(5));

        TBItems.registerItem(airingot, airingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(fireingot,fireingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(earthingot, earthingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(wateringot,wateringot.getUnlocalizedName().substring(5));
        TBItems.registerItem(orderingot, orderingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(entropyingot,entropyingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(mixedingot, mixedingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(taintedingot,taintedingot.getUnlocalizedName().substring(5));

        TBItems.registerItem(thauminiteaxe,thauminiteaxe.getUnlocalizedName().substring(5));

        TBItems.registerItem(pyrobucket,pyrobucket.getUnlocalizedName().substring(5));


    }










    @Deprecated
    public static void registerItem(Item item, String name)
    {
        if (item.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a item with no name: " + item);
        if (item.getRegistryName() != null && !item.getRegistryName().toString().equals(name))
            throw new IllegalArgumentException("Attempted to register a item with conflicting names. Old: " + item.getRegistryName() + " New: " + name);
        ForgeRegistries.ITEMS.register(item.getRegistryName() == null ? item.setRegistryName(name) : item);
    }

    public static void Renders(){

        renderItems(nuggetthauminite);
        renderItems(thauminite_ingot);

        renderItems(airingot);
        renderItems(fireingot);
        renderItems(earthingot);
        renderItems(wateringot);
        renderItems(orderingot);
        renderItems(entropyingot);
        renderItems(mixedingot);
        renderItems(taintedingot);

        renderItems(thauminiteaxe);

        renderItems(pyrobucket);

    }


    public static void renderItems(Item i){

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(
                TBCore.modid + ":" + i.getUnlocalizedName().substring(5), "inventory"));

    }


}
