package com.rumaruka.tb.init;

import com.rumaruka.tb.common.block.FluidPyro;
import com.rumaruka.tb.common.block.TBBlock;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.curios.ItemPrimordialPearl;

public class TBOreDirection {

    public static void setup(){

        //Vanilla
        OreDictionary.registerOre("flint", Items.FLINT);
        OreDictionary.registerOre("seedsStems",Items.MELON_SEEDS);
        OreDictionary.registerOre("seedsStems",Items.PUMPKIN_SEEDS);
        //TC Blocks
        OreDictionary.registerOre("plankGreatwood", BlocksTC.plankGreatwood);

        //Old Vanilla Blocks
        OreDictionary.registerOre("cobblestone", TBBlocks.oldcobble);
        OreDictionary.registerOre("mossyCobblestone", TBBlocks.oldcobblemossy);
        OreDictionary.registerOre("gravel",TBBlocks.oldgravel);
        OreDictionary.registerOre("brick",TBBlocks.oldbrick);
        OreDictionary.registerOre("blockLapis",TBBlocks.oldlapis);
        OreDictionary.registerOre("blockIron",TBBlocks.oldiron);
        OreDictionary.registerOre("blockGold",TBBlocks.oldgold);
        OreDictionary.registerOre("blockDiamond",TBBlocks.olddiamond);
        //Thaumcraft Decor Block
        OreDictionary.registerOre("blockQuicksilver",TBBlocks.quicksilverblock);
        OreDictionary.registerOre("blockQuicksilver",TBBlocks.quicksilverbrick);
        OreDictionary.registerOre("blockSalisMundus",TBBlocks.dustblock);
        OreDictionary.registerOre("blockThauminite", TBBlocks.blockthauminite);
        //Thaumcraft Item
        OreDictionary.registerOre("ingotThauminite", TBItems.thauminite_ingot);
        OreDictionary.registerOre("nuggetThauminite",TBItems.nuggetthauminite);
        OreDictionary.registerOre("plateThauminite",TBItems.thauminite_plate);
        OreDictionary.registerOre("ingotVoid", new ItemStack(ItemsTC.ingots,1,1));
        //Logs
        OreDictionary.registerOre("logGolden",TBBlocks.goldenlogs);
        OreDictionary.registerOre("logEnder",TBBlocks.enderlogs);
        OreDictionary.registerOre("logNether",TBBlocks.netherlogs);

        //Mortar
        OreDictionary.registerOre("pestleAndMortar", new ItemStack(TBItems.mortar));
        OreDictionary.registerOre("tobbacoLeaves", TBItems.tobacco_leaves);
        //Planks
        OreDictionary.registerOre("plankWood", TBBlocks.enderplanks);
        OreDictionary.registerOre("plankWood", TBBlocks.goldenplanks);
        OreDictionary.registerOre("plankWood", TBBlocks.netherplanks);


    }

}
