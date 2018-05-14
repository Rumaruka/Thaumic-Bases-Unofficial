package com.rumaruka.tb.common.item;

import com.google.common.collect.ImmutableSet;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;
import com.rumaruka.tb.common.TBMaterial;
import com.rumaruka.tb.init.TBItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemThauminiteAxe extends ItemAxe  {


    public ItemThauminiteAxe(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial,8.0F, -3.0F);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {

            return ImmutableSet.of("axe");
    }
}
