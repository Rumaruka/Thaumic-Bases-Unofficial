package com.rumaruka.thaumicbases.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.blocks.essentia.BlockJarItem;

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
        return 1;
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
        if (inventory[0] != ItemStack.EMPTY)
        {
            if (inventory[0].getCount() <= amount)
            {
                ItemStack stk = inventory[0];
                inventory[0] = ItemStack.EMPTY;
                this.markDirty();
                return stk;
            }

            ItemStack var3 = this.inventory[0].splitStack(amount);

            if (inventory[0].getCount() == 0)
            {
                inventory[0] = ItemStack.EMPTY;
            }

            this.markDirty();
            return var3;
        }
        return ItemStack.EMPTY;
    }

    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[0] = stack;
    }

    public int getInventoryStackLimit()
    {
        return 1;
    }

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    public boolean isItemValidForSlot(int slot, ItemStack jar)
    {
        if (!jar.isEmpty() && jar.getItem() instanceof BlockJarItem && jar.hasTagCompound())
        {
            AspectList aspects = ((BlockJarItem)jar.getItem()).getAspects(jar);
            if (aspects != null && aspects.size() > 0 && aspects.getAmount(Aspect.AVERSION) > 0)
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
    public void markDirty(){
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
        if (this.inventory[0] != ItemStack.EMPTY)
        {
            ItemStack stk = this.inventory[0];
            this.inventory[0] = ItemStack.EMPTY;
            return stk;
        }
        return ItemStack.EMPTY;
    }
}