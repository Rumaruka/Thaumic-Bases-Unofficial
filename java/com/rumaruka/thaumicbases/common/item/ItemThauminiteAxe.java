package com.rumaruka.thaumicbases.common.item;

import com.google.common.collect.ImmutableSet;
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
