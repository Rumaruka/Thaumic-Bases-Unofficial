package com.rumaruka.thaumicbases.init;

import net.minecraftforge.oredict.OreDictionary;

public class TBOreDictionary {

    public static void setup(){
        OreDictionary.registerOre("plankWood", TBBlocks.netherplanks);
        OreDictionary.registerOre("plankWood", TBBlocks.goldenplanks);
        OreDictionary.registerOre("plankWood", TBBlocks.enderplanks);
    }
}
