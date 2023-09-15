package com.rumaruka.thaumicbases.common.inventory;


import com.rumaruka.thaumicbases.common.item.ItemBullet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class InventoryRevolver implements IInventory
{
    public ItemStack[] inventory;
    public Container revolverContainer;

    public InventoryRevolver(Container revolver)
    {
        this.inventory = new ItemStack[1];
        this.revolverContainer = revolver;
    }

    public int getSizeInventory()
    {
        return 64;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return inventory[0];
    }

    public ItemStack decrStackSize(int slot, int amount)
    {
        if (inventory[0] != null)
        {
            if (inventory[0].getCount() <= amount)
            {
                ItemStack stk = inventory[0];
                inventory[0] = null;
                revolverContainer.onCraftMatrixChanged(this);
                return stk;
            }

            ItemStack var3 = this.inventory[0].splitStack(amount);

            if (inventory[0].getCount() == 0)
            {
                inventory[0] = null;
            }

            revolverContainer.onCraftMatrixChanged(this);
            return var3;
        }
        return null;
    }

    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[0] = stack;
        revolverContainer.onCraftMatrixChanged(this);
    }

    public int getInventoryStackLimit()
    {
        return 1;
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    public boolean isItemValidForSlot(int slot, ItemStack bullet)
    {
        if (bullet != null && bullet.getItem() instanceof ItemBullet && bullet.hasTagCompound())
        {
            if (bullet.getCount() >= 0)
                return true;
        }
        return false;
    }

    public String getInventoryName()
    {
        return "container.revolver";
    }

    public boolean hasCustomInventoryName()
    {
        return false;
    }

    public void openInventory(){}

    public void closeInventory(){}

    @Override
    public void markDirty(){}

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public String getName() {
        return getInventoryName();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        inventory = new ItemStack[1];
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (this.inventory[0] != null)
        {
            ItemStack stk = this.inventory[0];
            this.inventory[0] = null;
            return stk;
        }
        return null;
    }

}