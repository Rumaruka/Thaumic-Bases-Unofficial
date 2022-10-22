package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.common.entity.EntityRevolverBullet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBullet extends Item {
    public EntityRevolverBullet createBullet(World w, ItemStack itemStack, EntityLivingBase shooter) {
        return new EntityRevolverBullet(w, shooter);
    }
}