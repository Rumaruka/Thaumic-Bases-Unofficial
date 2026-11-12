package com.rumaruka.thaumicbases.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TBMortarItem extends Item {
    public TBMortarItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }
}
