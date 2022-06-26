package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.IVisDiscountGear;

import javax.annotation.Nullable;

public class ItemThauminiteArmor extends ItemArmor implements IVisDiscountGear {

    int aType;
    public ItemThauminiteArmor(ArmorMaterial mat, int renderIndexIn,int aType, EntityEquipmentSlot equipmentSlotIn) {
        super(mat, 0, equipmentSlotIn);
        this.aType = aType;
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer) {
        return discount[aType];
    }
    static  final int[] discount = new int[]{6,4,4,2};

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (stack.getItem() != TBItems.thauminitehelmet && stack.getItem() != TBItems.thauminitechest && stack.getItem() != TBItems.bloodyboots) {
            return stack.getItem() == TBItems.thauminitelegs ? "thaumicbases:textures/items/armor/thauminite/thauminite_2.png" : "thaumicbases:textures/items/armor/thauminite/thauminite_1.png";
        } else {
            return "thaumicbases:textures/items/armor/thauminite/thauminite_1.png";
        }
    }
}
