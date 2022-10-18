package com.rumaruka.thaumicbases.api.dummycore_remove.utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
public abstract class ContainerInventory extends Container{
    public final IInventory inv;
    public final InventoryPlayer pInv;
    public final TileEntity tile;
    public final Entity entity;
    public final EntityPlayer player;

    public int sizeInventory;

    public int pInvOffsetX;
    public int pInvOffsetZ;

    /**
     * Constructs a new ContainerInventory object from the player's slots and the TileEntity's slots
     * @param playerInv - the player inventory
     * @param tileInv - the TileEntity. Must implement IInventory
     */
    public ContainerInventory(InventoryPlayer playerInv, TileEntity tileInv) {
        super();
        inv = (IInventory) tileInv;
        pInv = playerInv;
        tile = tileInv;
        player = playerInv.player;
        entity = null;

        sizeInventory = inv.getSizeInventory();

        inv.openInventory(playerInv.player);
        setupSlots();
    }

    public ContainerInventory(InventoryPlayer playerInv, Entity entityInv) {
        super();
        inv = (IInventory)entityInv;
        pInv = playerInv;
        entity = entityInv;
        player = playerInv.player;
        tile = null;

        sizeInventory = inv.getSizeInventory();

        inv.openInventory(playerInv.player);
        setupSlots();
    }

    public ContainerInventory(InventoryPlayer playerInv, IInventory inv) {
        super();
        this.inv = inv;
        pInv = playerInv;
        player = playerInv.player;
        tile = null;
        entity = null;

        sizeInventory = inv.getSizeInventory();

        inv.openInventory(playerInv.player);
        setupSlots();
    }

    public ContainerInventory(EntityPlayer player, TileEntity tileInv) {
        this(player.inventory, tileInv);
    }

    public ContainerInventory(EntityPlayer player, Entity entityInv) {
        this(player.inventory, entityInv);
    }

    public ContainerInventory(EntityPlayer player, IInventory inv) {
        this(player.inventory, inv);
    }

    /**
     * Override this in your classes. This allows you to setup your slots and positions of them.
     * <br>At the <b>END</b> of your setup you can call the {@linkplain #setupPlayerInventory()} to automatically create slots for the player's inventory
     */
    public abstract void setupSlots();

    /**
     * Automatically creates the slots for the player's inventory
     */
    public void setupPlayerInventory() {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(pInv, j + i * 9 + 9, 8 + j * 18 + pInvOffsetX, 84 + i * 18 + pInvOffsetZ));

        for (int i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(pInv, i, 8 + i * 18 + pInvOffsetX, 142 + pInvOffsetZ));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inv.isUsableByPlayer(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        inv.closeInventory(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {
        ItemStack newItemStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < sizeInventory)
            {
                if (!this.mergeItemStack(itemStack, sizeInventory, inventorySlots.size(), false))
                    return ItemStack.EMPTY;
            }
            else if (!this.mergeItemStack(itemStack, 0, sizeInventory, false))
                return ItemStack.EMPTY;

            if (itemStack.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return newItemStack;
    }

    //Credits to Pahimar for his awesome ItemStack merging code!
    @Override
    public boolean mergeItemStack(ItemStack itemStack, int slotMin, int slotMax, boolean ascending) {
        boolean slotFound = false;
        int currentSlotIndex = ascending ? slotMax - 1 : slotMin;
        Slot slot;
        ItemStack stackInSlot;
        if (itemStack.isStackable())
        {
            while (itemStack.getCount() > 0 && (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin))
            {
                slot = this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && equalsIgnoreStackSize(itemStack, stackInSlot))
                {
                    int combinedStackSize = stackInSlot.getCount() + itemStack.getCount();
                    int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());
                    if (combinedStackSize <= slotStackSizeLimit)
                    {
                        itemStack.setCount(0);
                        stackInSlot.setCount(combinedStackSize);
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                    else if (stackInSlot.getCount() < slotStackSizeLimit)
                    {
                        itemStack.shrink(slotStackSizeLimit - stackInSlot.getCount());
                        stackInSlot.setCount(slotStackSizeLimit);
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }
                currentSlotIndex += ascending ? -1 : 1;
            }
        }

        if (itemStack.getCount() > 0)
        {
            currentSlotIndex = ascending ? slotMax - 1 : slotMin;
            while (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin)
            {
                slot = this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && stackInSlot.isEmpty())
                {
                    slot.putStack(cloneItemStack(itemStack, Math.min(itemStack.getCount(), slot.getSlotStackLimit())));
                    slot.onSlotChanged();
                    if (!slot.getStack().isEmpty())
                    {
                        itemStack.shrink(slot.getStack().getCount());
                        slotFound = true;
                    }
                    break;
                }
                currentSlotIndex += ascending ? -1 : 1;
            }
        }
        return slotFound;
    }

    public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize)
    {
        ItemStack clonedItemStack = itemStack.copy();
        clonedItemStack.setCount(stackSize);
        return clonedItemStack;
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2)
    {
        if (!itemStack1.isEmpty() && !itemStack2.isEmpty())
            if (itemStack1.getItem().equals(itemStack2.getItem()))
                if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                    if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                    {
                        if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                            return true;
                    }
                    else if (!itemStack1.hasTagCompound() && !itemStack2.hasTagCompound())
                        return true;

        return false;
    }
}
