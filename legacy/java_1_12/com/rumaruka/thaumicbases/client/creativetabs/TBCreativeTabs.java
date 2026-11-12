package com.rumaruka.thaumicbases.client.creativetabs;

import com.rumaruka.thaumicbases.core.TBCore;
import com.rumaruka.thaumicbases.init.TBBlocks;


public class TBCreativeTabs extends CreativeTabs {
    public static TBCreativeTabs TB_CREATIVEtabs = new TBCreativeTabs();

    public TBCreativeTabs() {
        super(TBCore.modid);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(TBBlocks.overchanter);
    }




    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> itemStacks) {
        super.displayAllRelevantItems(itemStacks);
    }
}
