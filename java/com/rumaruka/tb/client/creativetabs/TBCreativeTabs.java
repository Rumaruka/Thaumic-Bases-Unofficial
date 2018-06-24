package com.rumaruka.tb.client.creativetabs;

import com.rumaruka.tb.common.block.TBBlock;
import com.rumaruka.tb.core.TBCore;
import com.rumaruka.tb.init.TBBlocks;
import com.rumaruka.tb.init.TBItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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
