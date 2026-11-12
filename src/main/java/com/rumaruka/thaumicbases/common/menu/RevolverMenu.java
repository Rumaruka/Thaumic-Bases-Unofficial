package com.rumaruka.thaumicbases.common.menu;

import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import com.rumaruka.thaumicbases.common.item.TBRevolverItem;
import com.rumaruka.thaumicbases.init.TBMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RevolverMenu extends AbstractContainerMenu {
    private final InteractionHand hand;
    private final Player player;
    private final SimpleContainer jarContainer = new SimpleContainer(1) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return isJarLike(stack);
        }
    };

    public RevolverMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf) {
        this(containerId, playerInv, InteractionHand.values()[buf.readVarInt()]);
    }

    public RevolverMenu(int containerId, Inventory playerInv, InteractionHand hand) {
        super(TBMenuTypes.REVOLVER.get(), containerId);
        this.hand = hand;
        this.player = playerInv.player;

        ItemStack revolver = getRevolver();
        if (revolver.getItem() instanceof TBRevolverItem) {
            ItemStack jar = TBRevolverItem.getJar(revolver, player.level().registryAccess());
            if (!jar.isEmpty()) jarContainer.setItem(0, jar.copy());
        }

        this.addSlot(new Slot(jarContainer, 0, 80, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isJarLike(stack);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                syncJarToRevolver();
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    private static boolean isJarLike(ItemStack stack) {
        if (stack.isEmpty()) return false;
        // Accept items carrying aspect list component (Thaumaturge jars)
        return stack.has(TTDataComponents.ASPECTS.get()) || stack.getItem() instanceof com.leclowndu93150.thaumaturge.content.essentia.jar.JarItem;
    }

    private ItemStack getRevolver() {
        return player.getItemInHand(hand);
    }

    private void syncJarToRevolver() {
        ItemStack revolver = getRevolver();
        if (!(revolver.getItem() instanceof TBRevolverItem)) return;
        ItemStack jar = jarContainer.getItem(0);
        TBRevolverItem.setJar(revolver, jar.isEmpty() ? ItemStack.EMPTY : jar.copy(), player.level().registryAccess());
    }

    public int getAmmoCount() {
        ItemStack revolver = getRevolver();
        return revolver.getItem() instanceof TBRevolverItem ? TBRevolverItem.getShots(revolver) : 0;
    }

    public String getJarInfo() {
        ItemStack jar = jarContainer.getItem(0);
        if (jar.isEmpty()) return "No Jar";
        AspectList list = jar.getOrDefault(TTDataComponents.ASPECTS.get(), AspectList.EMPTY);
        if (list.isEmpty()) return jar.getHoverName().getString() + ": empty";
        int total = list.totalAmount();
        return jar.getHoverName().getString() + ": " + total;
    }

    @Override
    public boolean stillValid(Player player) {
        ItemStack stack = player.getItemInHand(hand);
        return stack.getItem() instanceof TBRevolverItem;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack empty = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return empty;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index == 0) {
            if (!this.moveItemStackTo(stack, 1, this.slots.size(), true)) return empty;
        } else {
            if (!isJarLike(stack) || !this.moveItemStackTo(stack, 0, 1, false)) {
                return empty;
            }
        }
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        syncJarToRevolver();
        return copy;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        // jar stays stored in revolver; don't drop from temp container
        jarContainer.setItem(0, ItemStack.EMPTY);
    }
}
