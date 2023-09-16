package com.rumaruka.thaumicbases.common.item.recourse;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemKnowledgeShard extends net.minecraft.item.Item {
    public ItemKnowledgeShard(){

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (!world.isRemote) {
            ItemStack item = player.getHeldItem(handIn);
                world.spawnEntity(new EntityXPOrb(world, player.posX, player.posY, player.posZ, world.rand.nextInt(3) + 1));
                world.spawnEntity(new EntityXPOrb(world, player.posX, player.posY, player.posZ, world.rand.nextInt(3) + 1));
                world.spawnEntity(new EntityXPOrb(world, player.posX, player.posY, player.posZ, world.rand.nextInt(3) + 1));
                item.setCount(item.getCount() - 1);
        }
        return super.onItemRightClick(world, player, handIn);
    }
}
