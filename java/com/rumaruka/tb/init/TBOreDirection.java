package com.rumaruka.tb.init;

import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.blocks.BlocksTC;

public class TBOreDirection {

    public static void setup(){


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

    }
}
