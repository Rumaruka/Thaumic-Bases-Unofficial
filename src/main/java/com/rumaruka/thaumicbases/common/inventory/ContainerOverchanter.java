package com.rumaruka.thaumicbases.common.inventory;

import com.rumaruka.thaumicbases.api.dummycore_remove.utils.ContainerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerOverchanter extends ContainerInventory {

    public ContainerOverchanter(InventoryPlayer playerInv, TileEntity tileInv) {
        super(playerInv, tileInv);
    }

    @Override
    public void setupSlots() {
        this.addSlotToContainer(new Slot(this.inv,0,80,20));
        this.setupPlayerInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.inv.isUsableByPlayer(playerIn);
    }
}
