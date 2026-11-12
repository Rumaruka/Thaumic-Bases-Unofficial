package com.rumaruka.thaumicbases.common.menu;

import com.rumaruka.thaumicbases.common.blockentity.TBOverchanterBlockEntity;
import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class OverchanterMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final ItemStackHandler handler;
    private final TBOverchanterBlockEntity blockEntity;

    // Client constructor
    public OverchanterMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf) {
        this(containerId, playerInv, getBlockEntity(playerInv, buf));
    }

    // Server constructor
    public OverchanterMenu(int containerId, Inventory playerInv, TBOverchanterBlockEntity be) {
        super(TBMenuTypes.OVERCHANTER.get(), containerId);
        this.blockEntity = be;
        this.access = ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());
        this.handler = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                be.setChanged();
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                ItemStack stored = be.getInventory();
                return stored == null ? ItemStack.EMPTY : stored;
            }

            @Override
            public void setStackInSlot(int slot, ItemStack stack) {
                if (stack.isEmpty()) {
                    be.clearInventory();
                } else {
                    // keep BE logic simple: store copy
                    be.setInventoryDirect(stack.copyWithCount(1));
                }
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (!be.getInventory().isEmpty() || stack.isEmpty() || !stack.isEnchanted()) {
                    return stack;
                }
                if (!simulate) {
                    be.setInventoryDirect(stack.copyWithCount(1));
                }
                ItemStack remainder = stack.copy();
                remainder.shrink(1);
                return remainder;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                ItemStack stored = be.getInventory();
                if (stored.isEmpty() || amount <= 0) return ItemStack.EMPTY;
                ItemStack out = stored.copy();
                if (!simulate) {
                    be.clearInventory();
                }
                return out;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.isEmpty() || stack.isEnchanted();
            }
        };

        this.addSlot(new SlotItemHandler(handler, 0, 80, 16) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.isEnchanted();
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                blockEntity.clearInventory();
                super.onTake(player, stack);
            }
        });

        // Player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 41 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 99));
        }
    }

    private static TBOverchanterBlockEntity getBlockEntity(Inventory playerInv, FriendlyByteBuf buf) {
        BlockEntity be = playerInv.player.level().getBlockEntity(buf.readBlockPos());
        if (be instanceof TBOverchanterBlockEntity overchanter) {
            return overchanter;
        }
        throw new IllegalStateException("Expected TBOverchanterBlockEntity");
    }

    public TBOverchanterBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, TBBlocks.OVERCHANTER.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack empty = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return empty;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        int beSlots = 1;
        if (index < beSlots) {
            if (!this.moveItemStackTo(stack, beSlots, this.slots.size(), true)) return empty;
            blockEntity.clearInventory();
        } else {
            if (!stack.isEnchanted() || !this.moveItemStackTo(stack, 0, beSlots, false)) return empty;
        }
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        return copy;
    }
}