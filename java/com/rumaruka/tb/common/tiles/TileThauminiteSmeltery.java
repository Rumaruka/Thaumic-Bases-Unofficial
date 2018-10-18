package com.rumaruka.tb.common.tiles;

import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.essentia.BlockSmelter;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.tiles.essentia.TileSmelter;

import java.lang.reflect.Field;

public class TileThauminiteSmeltery extends TileSmelter {

    public boolean isFuelAlumentum;
    private static final int[] slots_bottom = new int[]{1};
    private static final int[] slots_top = new int[0];
    private static final int[] slots_sides = new int[]{0};
    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        isFuelAlumentum = compound.getBoolean("isAlumentium");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
         super.writeToNBT(compound);
         compound.setBoolean("isAlumentium",isFuelAlumentum);
        return compound;
    }
    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return slots_bottom;
        }
        else
        {
            return side == EnumFacing.UP ? slots_top : slots_sides;
        }
    }
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks.get(index);
    }


    @Override
    public void update() {
        if(furnaceBurnTime == 0){
            isFuelAlumentum = this.getStackInSlot(1) != ItemStack.EMPTY && this.getStackInSlot(1).getItem()== ItemsTC.alumentum && this.getStackInSlot(1).getItemDamage()==0;
        }

        try
        {
            if(this.isFuelAlumentum || !TBConfig.makeReguireAlumentium){
                Class<TileSmelter> smelter = TileSmelter.class;
                Field count = smelter.getDeclaredField("count");
                count.setAccessible(true);
                count.setAccessible(true);

                count.setInt(this, count.getInt(this)+TBConfig.speedMultiplierForFurnace-1);

                count.setAccessible(false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
