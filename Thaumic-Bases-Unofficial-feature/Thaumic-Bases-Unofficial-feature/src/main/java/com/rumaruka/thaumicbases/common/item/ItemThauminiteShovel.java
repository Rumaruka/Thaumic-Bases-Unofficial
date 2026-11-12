package com.rumaruka.thaumicbases.common.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemThauminiteShovel extends ItemSpade {
    public ItemThauminiteShovel(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
    }
    @Override
    public Set<String> getToolClasses(ItemStack stack) {

        return ImmutableSet.of("shovel");
    }
}
