package com.rumaruka.thaumicbases.common.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemThauminitePickaxe extends ItemPickaxe {
    public ItemThauminitePickaxe(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {

        return ImmutableSet.of("pickaxe");
    }
}
