package com.rumaruka.thaumicbases.common.menu;

import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBMenuTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.state.BlockState;

public class ThaumicAnvilMenu extends ItemCombinerMenu {
    protected final DataSlot cost = DataSlot.standalone();
    protected String itemName;
    protected final Player player;

    public ThaumicAnvilMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ThaumicAnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(TBMenuTypes.THAUMIC_ANVIL.get(), containerId, playerInventory, access);
        this.player = playerInventory.player;
        this.addDataSlot(this.cost);
    }

    @Override
    public MenuType<?> getType() {
        return TBMenuTypes.THAUMIC_ANVIL.get();
    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, stack -> true)
                .withSlot(1, 76, 47, stack -> true)
                .withResultSlot(2, 134, 47)
                .build();
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(TBBlocks.THAUMIC_ANVIL.get());
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return hasStack && (player.getAbilities().instabuild || player.experienceLevel >= this.cost.get()) && this.cost.get() > 0;
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            player.giveExperienceLevels(-this.cost.get());
        }
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        ItemStack second = this.inputSlots.getItem(1);
        if (!second.isEmpty()) {
            second.shrink(1);
            this.inputSlots.setItem(1, second.isEmpty() ? ItemStack.EMPTY : second);
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }
        this.cost.set(0);
        this.access.execute((level, pos) -> {
            BlockState state = level.getBlockState(pos);
            if (!player.getAbilities().instabuild && state.is(TBBlocks.THAUMIC_ANVIL.get()) && player.getRandom().nextFloat() < 0.12F) {
                int dmg = state.getValue(com.rumaruka.thaumicbases.common.block.TBAnvilBlock.DAMAGE);
                if (dmg < 2) {
                    level.setBlock(pos, state.setValue(com.rumaruka.thaumicbases.common.block.TBAnvilBlock.DAMAGE, dmg + 1), 3);
                } else {
                    level.removeBlock(pos, false);
                }
                level.levelEvent(1029, pos, 0);
            } else {
                level.levelEvent(1030, pos, 0);
            }
        });
    }

    @Override
    public void createResult() {
        ItemStack left = this.inputSlots.getItem(0);
        if (left.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.cost.set(0);
            return;
        }
        ItemStack right = this.inputSlots.getItem(1);
        ItemStack out = left.copy();
        int c = 0;
        boolean changed = false;

        if (!right.isEmpty()) {
            if (left.isDamageableItem() && left.getItem().isValidRepairItem(left, right)) {
                int repair = Math.min(out.getDamageValue(), out.getMaxDamage() / 4);
                if (repair > 0) {
                    out.setDamageValue(out.getDamageValue() - repair);
                    c += 1;
                    changed = true;
                }
            } else if (left.getItem() == right.getItem() && left.isDamageableItem()) {
                int l = left.getMaxDamage() - left.getDamageValue();
                int r = right.getMaxDamage() - right.getDamageValue();
                int bonus = out.getMaxDamage() * 12 / 100;
                int repaired = Math.min(out.getMaxDamage(), l + r + bonus);
                out.setDamageValue(out.getMaxDamage() - repaired);
                c += 2;
                changed = true;
                // merge enchantments
                ItemEnchantments.Mutable m = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(left));
                for (var entry : EnchantmentHelper.getEnchantmentsForCrafting(right).entrySet()) {
                    m.set(entry.getKey(), Math.max(m.getLevel(entry.getKey()), entry.getIntValue()));
                }
                EnchantmentHelper.setEnchantments(out, m.toImmutable());
            } else if (right.is(Items.ENCHANTED_BOOK)) {
                ItemEnchantments.Mutable m = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(left));
                for (var entry : EnchantmentHelper.getEnchantmentsForCrafting(right).entrySet()) {
                    if (entry.getKey().value().canEnchant(left) || left.is(Items.ENCHANTED_BOOK)) {
                        m.set(entry.getKey(), Math.max(m.getLevel(entry.getKey()), entry.getIntValue()));
                        c += 1;
                        changed = true;
                    }
                }
                EnchantmentHelper.setEnchantments(out, m.toImmutable());
            }
        }

        // rename
        if (this.itemName != null && !StringUtil.isBlank(this.itemName)) {
            if (!this.itemName.equals(left.getHoverName().getString())) {
                out.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
                c += 1;
                changed = true;
            }
        } else if (left.has(DataComponents.CUSTOM_NAME)) {
            out.remove(DataComponents.CUSTOM_NAME);
            c += 1;
            changed = true;
        }

        if (!changed) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.cost.set(0);
        } else {
            // Thaumic anvil: higher cap (allow up to 59)
            c = Math.max(1, Math.min(c, 59));
            this.cost.set(c);
            this.resultSlots.setItem(0, out);
        }
        this.broadcastChanges();
    }

    public boolean setItemName(String name) {
        String s = StringUtil.filterText(name);
        if (s.length() > 50) s = s.substring(0, 50);
        if (s.equals(this.itemName)) return false;
        this.itemName = s;
        if (this.getSlot(2).hasItem()) {
            ItemStack out = this.getSlot(2).getItem();
            if (StringUtil.isBlank(s)) out.remove(DataComponents.CUSTOM_NAME);
            else out.set(DataComponents.CUSTOM_NAME, Component.literal(s));
        }
        this.createResult();
        return true;
    }

    public int getCost() {
        return this.cost.get();
    }
}
