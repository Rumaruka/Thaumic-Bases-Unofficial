package com.rumaruka.tb.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SlotGeneric extends Slot {

    public  int slot;
    public SlotGeneric(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        slot=index;

    }
    public SlotGeneric(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ResourceLocation p_i1824_5_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        slot = p_i1824_2_;
        this.setBackgroundName(p_i1824_5_.toString());
    }

    public SlotGeneric(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, String p_i1824_5_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        slot = p_i1824_2_;
        this.setBackgroundName(p_i1824_5_);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return inventory.isItemValidForSlot(slot,stack);
    }
}
