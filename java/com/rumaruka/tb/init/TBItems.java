package com.rumaruka.tb.init;
import com.google.common.base.Strings;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;


import com.rumaruka.tb.common.item.recourse.TBItemNuggetThauminite;
import com.rumaruka.tb.common.item.recourse.TBItemThauminiteIngot;
import com.rumaruka.tb.common.items.TBAirIngot;
import com.rumaruka.tb.common.items.TBFireIngot;
import com.rumaruka.tb.core.TBCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
public class TBItems {



    public static Item nuggetthauminite;
    public static Item thauminite_ingot;
    public static Item airingot;
    public static Item fireingot;

    public static void init(){

       nuggetthauminite = new TBItemNuggetThauminite().setUnlocalizedName("nuggetthauminite").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       thauminite_ingot = new TBItemThauminiteIngot().setUnlocalizedName("thauminite_ingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       airingot = new TBAirIngot().setUnlocalizedName("airingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
       fireingot = new TBFireIngot().setUnlocalizedName("fireingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);


    }

    public static void InGameRegistr(){
        TBItems.registerItem(nuggetthauminite, nuggetthauminite.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminite_ingot,thauminite_ingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(airingot, airingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(fireingot,fireingot.getUnlocalizedName().substring(5));


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

    }


    public static void renderItems(Item i){

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(
                TBCore.modid + ":" + i.getUnlocalizedName().substring(5), "inventory"));

    }


}
