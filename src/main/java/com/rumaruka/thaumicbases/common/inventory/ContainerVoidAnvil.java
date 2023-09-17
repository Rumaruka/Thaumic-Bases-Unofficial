package com.rumaruka.thaumicbases.common.inventory;

import com.rumaruka.thaumicbases.common.block.BlockVoidAnvil;
import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static net.minecraftforge.common.ForgeHooks.onAnvilRepair;

public class ContainerVoidAnvil extends ContainerRepair{

	private final IInventory outputSlot = new InventoryCraftResult();
    IInventory inputSlots;
    private String repairedItemName;
    private final EntityPlayer thePlayer;
    private final World w;
    private final int x;
    private final int y;
    private final int z;

	public ContainerVoidAnvil(InventoryPlayer inv, final World w, final BlockPos pos, EntityPlayer p)
	{
		super(inv, w, pos, p);

        this.thePlayer = p;
        this.inventoryItemStacks.clear();
        this.inventorySlots.clear();

        this.inputSlots = new InventoryBasic("Repair", true, 2)
        {
            public void markDirty()
            {
                super.markDirty();
                ContainerVoidAnvil.this.onCraftMatrixChanged(this);
            }
        };
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.w = w;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47)
        {
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return (playerIn.capabilities.isCreativeMode || playerIn.experienceLevel >= ContainerVoidAnvil.this.maximumCost) && ContainerVoidAnvil.this.maximumCost > 0 && this.getHasStack();
            }
            public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
            {
                if (!thePlayer.capabilities.isCreativeMode)
                {
                    thePlayer.addExperienceLevel(-ContainerVoidAnvil.this.maximumCost);
                }

                float breakChance = onAnvilRepair(thePlayer, stack, ContainerVoidAnvil.this.inputSlots.getStackInSlot(0), ContainerVoidAnvil.this.inputSlots.getStackInSlot(1));

                ContainerVoidAnvil.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);

                if (ContainerVoidAnvil.this.materialCost > 0)
                {
                    ItemStack itemstack = ContainerVoidAnvil.this.inputSlots.getStackInSlot(1);

                    if (!itemstack.isEmpty() && itemstack.getCount() > ContainerVoidAnvil.this.materialCost)
                    {
                        itemstack.shrink(ContainerVoidAnvil.this.materialCost);
                        ContainerVoidAnvil.this.inputSlots.setInventorySlotContents(1, itemstack);
                    }
                    else
                    {
                        ContainerVoidAnvil.this.inputSlots.setInventorySlotContents(1, ItemStack.EMPTY);
                    }
                }
                else
                {
                    ContainerVoidAnvil.this.inputSlots.setInventorySlotContents(1, ItemStack.EMPTY);
                }

                ContainerVoidAnvil.this.maximumCost = 0;
                IBlockState iblockstate = w.getBlockState(pos);

                if (!thePlayer.capabilities.isCreativeMode && !w.isRemote && iblockstate.getBlock() == TBBlocks.voidAnvil && thePlayer.getRNG().nextFloat() < breakChance)
                {
                    int l = iblockstate.getValue(BlockVoidAnvil.DAMAGE);
                    ++l;

                    if (l > 2)
                    {
                        w.setBlockToAir(pos);
                        w.playEvent(1029, pos, 0);
                    }
                    else
                    {
                        w.setBlockState(pos, iblockstate.withProperty(BlockVoidAnvil.DAMAGE, l), 2);
                        w.playEvent(1030, pos, 0);
                    }
                }
                else if (!w.isRemote)
                {
                    w.playEvent(1030, pos, 0);
                }

                return stack;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(inv, k, 8 + k * 18, 142));
        }
    }

    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn == this.inputSlots)
        {
            this.updateRepairOutput();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void updateRepairOutput()
    {
        ItemStack itemstack = this.inputSlots.getStackInSlot(0);
        this.maximumCost = 1;
        int i = 0;
        int j = 0;
        int k = 0;

        if (itemstack.isEmpty())
        {
            this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
            this.maximumCost = 0;
        }
        else
        {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
            j = j + itemstack.getRepairCost() + (itemstack2.isEmpty() ? 0 : itemstack2.getRepairCost());
            this.materialCost = 0;
            boolean flag = false;

            if (!itemstack2.isEmpty())
            {
                if (!net.minecraftforge.common.ForgeHooks.onAnvilChange(this, itemstack, itemstack2, outputSlot, repairedItemName, j)) return;
                flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !ItemEnchantedBook.getEnchantments(itemstack2).hasNoTags();

                if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2))
                {
                    int l2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);

                    if (l2 <= 0)
                    {
                        this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.maximumCost = 0;
                        return;
                    }

                    int i3;

                    for (i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); ++i3)
                    {
                        int j3 = itemstack1.getItemDamage() - l2;
                        itemstack1.setItemDamage(j3);
                        ++i;
                        l2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
                    }

                    this.materialCost = i3;
                }
                else
                {
                    if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isItemStackDamageable()))
                    {
                        this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.maximumCost = 0;
                        return;
                    }

                    if (itemstack1.isItemStackDamageable() && !flag)
                    {
                        int l = itemstack.getMaxDamage() - itemstack.getItemDamage();
                        int i1 = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
                        int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
                        int k1 = l + j1;
                        int l1 = itemstack1.getMaxDamage() - k1;

                        if (l1 < 0)
                        {
                            l1 = 0;
                        }

                        if (l1 < itemstack1.getItemDamage()) // vanilla uses metadata here instead of damage.
                        {
                            itemstack1.setItemDamage(l1);
                            i += 2;
                        }
                    }

                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
                    boolean flag2 = false;
                    boolean flag3 = false;

                    for (Enchantment enchantment1 : map1.keySet())
                    {
                        if (enchantment1 != null)
                        {
                            int i2 = map.containsKey(enchantment1) ? map.get(enchantment1) : 0;
                            int j2 = map1.get(enchantment1);
                            j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                            boolean flag1 = enchantment1.canApply(itemstack);

                            if (this.thePlayer.capabilities.isCreativeMode || itemstack.getItem() == Items.ENCHANTED_BOOK)
                            {
                                flag1 = true;
                            }

                            for (Enchantment enchantment : map.keySet())
                            {
                                if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment))
                                {
                                    flag1 = false;
                                    ++i;
                                }
                            }

                            if (!flag1)
                            {
                                flag3 = true;
                            }
                            else
                            {
                                flag2 = true;

                                if (j2 > enchantment1.getMaxLevel())
                                {
                                    j2 = enchantment1.getMaxLevel();
                                }

                                map.put(enchantment1, j2);
                                int k3 = 0;

                                switch (enchantment1.getRarity())
                                {
                                    case COMMON:
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }

                                if (flag)
                                {
                                    k3 = Math.max(1, k3 / 2);
                                }

                                i += k3 * j2;

                                if (itemstack.getCount() > 1)
                                {
                                    i = 40;
                                }
                            }
                        }
                    }

                    if (flag3 && !flag2)
                    {
                        this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.maximumCost = 0;
                        return;
                    }
                }
            }

            if (StringUtils.isBlank(this.repairedItemName))
            {
                if (itemstack.hasDisplayName())
                {
                    k = itemstack.isItemStackDamageable() ? 7 : itemstack.getCount() * 5;
                    i += k;
                    itemstack1.clearCustomName();
                }
            }
            else if (!this.repairedItemName.equals(itemstack.getDisplayName()))
            {
                k = itemstack.isItemStackDamageable() ? 7 : itemstack.getCount() * 5;
                i += k;

                itemstack1.setStackDisplayName(this.repairedItemName);
            }
            if (flag && !itemstack1.getItem().isBookEnchantable(itemstack1, itemstack2)) itemstack1 = ItemStack.EMPTY;

            this.maximumCost = 7;

            if (i <= 0)
            {
                itemstack1 = ItemStack.EMPTY;
            }

            if (k == i && k > 0 && this.maximumCost >= 60)
            {
                this.maximumCost = 7;
            }

            if (this.maximumCost >= 60 && !this.thePlayer.capabilities.isCreativeMode)
            {
                itemstack1 = ItemStack.EMPTY;
            }

            if (!itemstack1.isEmpty())
            {
                int k2 = itemstack1.getRepairCost();

                if (!itemstack2.isEmpty() && k2 < itemstack2.getRepairCost())
                {
                    k2 = itemstack2.getRepairCost();
                }

                if (k != i || k == 0)
                {
                    k2 -= 9;
                }

                if (k2 < 0)
                {
                    k2 = 0;
                }

                k2 += 2;

                itemstack1.setRepairCost(Math.min(k2, 7));
                EnchantmentHelper.setEnchantments(map, itemstack1);
            }

            this.outputSlot.setInventorySlotContents(0, itemstack1);
            this.detectAndSendChanges();
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (this.w.getBlockState(new BlockPos(x, y, z)).getBlock() != TBBlocks.voidAnvil)
        {
            return false;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.x + 0.5D, (double)this.y + 0.5D, (double)this.z + 0.5D) <= 64.0D;
        }
    }
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.maximumCost);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        if (id == 0)
        {
            this.maximumCost = data;
        }
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.w.isRemote)
        {
            this.clearContainer(playerIn, this.w, this.inputSlots);
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 0 && index != 1)
            {
                if (index >= 3 && index < 39 && !this.mergeItemStack(itemstack1, 0, 2, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
    public void updateItemName(String newName)
    {
        this.repairedItemName = newName;

        if (this.getSlot(2).getHasStack())
        {
            ItemStack itemstack = this.getSlot(2).getStack();

            if (StringUtils.isBlank(newName))
            {
                itemstack.clearCustomName();
            }
            else
            {
                itemstack.setStackDisplayName(this.repairedItemName);
            }
        }

        this.updateRepairOutput();
    }
}