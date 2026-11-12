package com.rumaruka.thaumicbases.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ContainerRevolver extends Container implements IInventoryChangedListener
{
	public World worldObj;
	public int x;
	public int y;
	public int z;
	public IInventory input = new InventoryRevolver(this);
	public ItemStack stack;
	public EntityPlayer player;
 	
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
			 ItemStack jar = new ItemStack(stack.getTagCompound().getCompoundTag("jar"));
			 this.input.setInventorySlotContents(0, jar);
			 input.markDirty();
 		}
	}

	public void onInventoryChanged(IInventory invBasic) {
		detectAndSendChanges();
	}

 	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
 	{
 		if (slot == this.blockedSlot)
 			return ItemStack.EMPTY;
 		
 		ItemStack stack = ItemStack.EMPTY;
 		Slot slotObject = this.inventorySlots.get(slot);

 		if (slotObject != null && slotObject.getHasStack())
 		{
 			ItemStack stackInSlot = slotObject.getStack();
 			stack = stackInSlot.copy();

 			if (slot == 0)
 			{
 				if (!this.input.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true, 64))
 					return ItemStack.EMPTY;
 			}
 			else if (!this.input.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 0, 1, false, 1))
 				return ItemStack.EMPTY;

 			if (stackInSlot.getCount() == 0)
 				slotObject.putStack(ItemStack.EMPTY);
 			else
 				slotObject.onSlotChanged();
 		}

 		return stack;
	}

	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId == blockedSlot) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
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
 			if (!var3.isEmpty())
 			{
 				NBTTagCompound var4 = new NBTTagCompound();
 				var3.writeToNBT(var4);
 				this.stack.setTagInfo("jar", var4);
 				this.stack.getTagCompound().setBoolean("hasJar", true);
 			} 
 			else 
 			{
 				this.stack.setTagInfo("jar", new NBTTagCompound());
 				this.stack.getTagCompound().setBoolean("hasJar", false);
 			}
 			
 			if (this.player == null) 
 				return;
 			
 			if (this.player.getHeldItemMainhand() != ItemStack.EMPTY && this.player.getHeldItemMainhand().isItemEqual(this.stack))
 				this.player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.stack);
 			
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
 				Slot var7 = this.inventorySlots.get(var6);
 				ItemStack var8 = var7.getStack();

 				if (!var8.isEmpty() && var8.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == var8.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, var8))
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
 						par1ItemStack.setCount(par1ItemStack.getCount() - Math.min(par1ItemStack.getMaxStackSize(), limit) - var8.getCount());
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
 				Slot var7 = this.inventorySlots.get(var6);
 				ItemStack var8 = var7.getStack();

 				if (var8.isEmpty())
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