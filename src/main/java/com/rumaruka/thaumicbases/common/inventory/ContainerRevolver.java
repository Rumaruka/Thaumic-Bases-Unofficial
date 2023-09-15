package com.rumaruka.thaumicbases.common.inventory;


import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ContainerRevolver extends Container
{
    public World worldObj;
    public int x;
    public int y;
    public int z;
    public IInventory input = new InventoryRevolver(this);
    public ItemStack stack = null;
    public EntityPlayer player = null;

    public int blockedSlot;

    public ContainerRevolver(InventoryPlayer inv, World w, int ix, int iy, int iz)
    {
        this.worldObj = w;
        this.x = ix;
        this.y = iy;
        this.z = iz;
        this.player = inv.player;
        this.stack = inv.getCurrentItem();
        this.blockedSlot = inv.currentItem + 28;

        addSlotToContainer(new Slot(this.input, 0, 80, 32));

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));

        if (!w.isRemote)
        {
            try
            {
                ItemStack bullet = new ItemStack(TBItems.bullet);
                this.input.setInventorySlotContents(0, bullet);
            }
            catch (Exception e)
            {
                //...
            }
        }

        onCraftMatrixChanged(this.input);
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
    {
        if (slot == this.blockedSlot)
            return null;

        ItemStack stack = null;
        Slot slotObject = (Slot)this.inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if (slot == 0)
            {
                if (!this.input.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true, 64))
                    return null;
            }
            else if (!this.input.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 0, 1, false, 1))
                return null;

            if (stackInSlot.getCount() == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();
        }

        return stack;
    }

    public ItemStack slotClick(int par1, int par2, ClickType par3, EntityPlayer par4EntityPlayer)
    {
        if (par1 == this.blockedSlot)
            return null;

        InventoryPlayer inventoryplayer = par4EntityPlayer.inventory;
        if (par1 != 0 || this.input.isItemValidForSlot(par1, inventoryplayer.getItemStack()) || (par1 == 0 && inventoryplayer.getItemStack() == null))
            return super.slotClick(par1, par2, par3, par4EntityPlayer);

        return null;
    }

    public boolean canInteractWith(EntityPlayer p)
    {
        return true;
    }

    public void putStackInSlot(int slot, ItemStack stk)
    {
        if (this.input.isItemValidForSlot(slot, stk))
            super.putStackInSlot(slot, stk);
    }

    public void onContainerClosed(EntityPlayer p)
    {
        if (!this.worldObj.isRemote)
        {
            ItemStack var3 = this.input.removeStackFromSlot(0);
            if (var3 != ItemStack.EMPTY)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var3.writeToNBT(var4);
                this.stack.setTagInfo("bullet", var4);
                this.stack.getTagCompound().setBoolean("hasBullet", true);
            }
            else
            {
                this.stack.setTagInfo("bullet", new NBTTagCompound());
                this.stack.getTagCompound().setBoolean("hasBullet", false);
            }

            if (this.player == null)
                return;

            this.player.inventory.markDirty();
        }
    }

    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4, int limit)
    {
        boolean var5 = false;
        int var6 = par2;

        if (par4)
        {
            var6 = par3 - 1;
        }

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.getCount() > 0 && ((!par4 && var6 < par3) || (par4 && var6 >= par2)))
            {
                Slot var7 = (Slot)this.inventorySlots.get(var6);
                ItemStack var8 = var7.getStack();

                if (var8 != null && var8.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == var8.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, var8))
                {
                    int var9 = var8.getCount() + par1ItemStack.getCount();

                    if (var9 <= Math.min(par1ItemStack.getMaxStackSize(), limit))
                    {
                        par1ItemStack.setCount(0);
                        var8.setCount(var9);
                        var7.onSlotChanged();
                        var5 = true;
                    }
                    else if (var8.getCount() < Math.min(par1ItemStack.getMaxStackSize(), limit))
                    {
                        par1ItemStack.setCount(par1ItemStack.getCount() - Math.min(par1ItemStack.getMaxStackSize(), limit) + var8.getCount());
                        var8.setCount(Math.min(par1ItemStack.getMaxStackSize(), limit));
                        var7.onSlotChanged();
                        var5 = true;
                    }
                }
                if (par4)
                {
                    var6--;
                }
                else
                {
                    var6++;
                }
            }
        }

        if (par1ItemStack.getCount() > 0)
        {
            if (par4)
                var6 = par3 - 1;
            else
                var6 = par2;

            while ((!par4 && var6 < par3) || (par4 && var6 >= par2))
            {
                Slot var7 = (Slot)this.inventorySlots.get(var6);
                ItemStack var8 = var7.getStack();

                if (var8 == ItemStack.EMPTY)
                {
                    ItemStack res = par1ItemStack.copy();
                    res.setCount(Math.min(res.getCount(), limit));
                    var7.putStack(res);
                    var7.onSlotChanged();
                    par1ItemStack.setCount(par1ItemStack.getCount() - res.getCount());
                    var5 = true;
                    break;
                }

                if (par4)
                    var6--;
                else
                    var6++;
            }
        }
        return var5;
    }
}
