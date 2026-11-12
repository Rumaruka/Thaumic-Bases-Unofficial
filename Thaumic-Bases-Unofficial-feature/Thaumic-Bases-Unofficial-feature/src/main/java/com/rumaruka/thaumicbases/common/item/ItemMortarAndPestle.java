package com.rumaruka.thaumicbases.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item {
    public ItemMortarAndPestle() {
        //setMaxDamage(256);
        setMaxStackSize(1);

    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        itemStack = itemStack.copy();
        //itemStack.grow(1);
        return itemStack;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
