package com.rumaruka.thaumicbases.common.blockentity;

import com.leclowndu93150.thaumaturge.api.aspect.Aspects;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.essentia.EssentiaCapabilities;
import com.leclowndu93150.thaumaturge.api.essentia.IEssentiaConduit;
import com.leclowndu93150.thaumaturge.api.essentia.IEssentiaStorage;
import com.rumaruka.thaumicbases.init.TBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class TBOverchanterBlockEntity extends BlockEntity {
    private static final ResourceKey<IAspect> MAGIC = ResourceKey.create(IAspect.REGISTRY_KEY,
            ResourceLocation.fromNamespaceAndPath("thaumaturge", "praecantatio"));
    private static final String OVERCHANTS_TAG = "overchants";

    private ItemStack inventory = ItemStack.EMPTY;
    private int enchantingTime;
    private boolean xpAbsorbed;
    private boolean enchantingStarted;
    private int ticksExisted;

    public TBOverchanterBlockEntity(BlockPos pos, BlockState state) {
        super(TBBlockEntities.OVERCHANTER.get(), pos, state);
    }

    public ItemStack getInventory() {
        return inventory;
    }

    public void clearInventory() {
        inventory = ItemStack.EMPTY;
        resetEnchanting();
        setChanged();
    }

    public void setInventoryDirect(ItemStack stack) {
        this.inventory = stack == null ? ItemStack.EMPTY : stack;
        resetEnchanting();
        setChanged();
    }

    public void insertFromPlayer(Player player, ItemStack stack) {
        if (!inventory.isEmpty() || stack.isEmpty()) {
            return;
        }
        inventory = stack.copyWithCount(1);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        enchantingTime = 0;
        xpAbsorbed = false;
        enchantingStarted = false;
        setChanged();
    }

    public void giveToPlayer(Player player) {
        if (inventory.isEmpty()) {
            return;
        }
        if (!player.getInventory().add(inventory)) {
            player.drop(inventory, false);
        }
        inventory = ItemStack.EMPTY;
        resetEnchanting();
        setChanged();
    }

    public boolean startEnchanting() {
        if (!enchantingStarted && !inventory.isEmpty() && findEnchantment() != null) {
            enchantingStarted = true;
            enchantingTime = 0;
            xpAbsorbed = false;
            setChanged();
            return true;
        }
        return false;
    }

    public void tick() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        ticksExisted++;

        if (inventory.isEmpty()) {
            if (enchantingStarted || enchantingTime != 0 || xpAbsorbed) {
                resetEnchanting();
                setChanged();
            }
            return;
        }
        if (!enchantingStarted) {
            return;
        }

        if (ticksExisted % 20 == 0) {
            Holder<IAspect> magic = Aspects.resolve(serverLevel, MAGIC);
            if (magic != null && drainEssentia(serverLevel, magic)) {
                if (enchantingTime < 16) {
                    enchantingTime++;
                } else if (!xpAbsorbed) {
                    // Hold progress at the XP absorption stage until a valid player is found.
                    enchantingTime = 16;
                    absorbExperience(serverLevel);
                    if (xpAbsorbed) {
                        enchantingTime = 17;
                    }
                } else {
                    enchantingTime++;
                }

                // Working sound while process is active.
                serverLevel.playSound(null, worldPosition, net.minecraft.sounds.SoundEvents.BEACON_AMBIENT,
                        net.minecraft.sounds.SoundSource.BLOCKS, 0.4F, 1.2F + (enchantingTime * 0.02F));

                if (xpAbsorbed && enchantingTime >= 32) {
                    boostEnchantment(serverLevel);
                    resetEnchanting();
                    serverLevel.playSound(null, worldPosition, net.minecraft.sounds.SoundEvents.PLAYER_LEVELUP,
                            net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
                    serverLevel.playSound(null, worldPosition, net.minecraft.sounds.SoundEvents.ENCHANTMENT_TABLE_USE,
                            net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            } else if (enchantingTime > 0) {
                enchantingTime--;
            }
            setChanged();
        }
    }

    private void resetEnchanting() {
        enchantingStarted = false;
        enchantingTime = 0;
        xpAbsorbed = false;
    }

    private boolean drainEssentia(ServerLevel serverLevel, Holder<IAspect> magic) {
        BlockPos min = worldPosition.offset(-3, -3, -3);
        BlockPos max = worldPosition.offset(3, 3, 3);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            IEssentiaStorage storage = serverLevel.getCapability(EssentiaCapabilities.STORAGE, pos, null);
            if (storage != null) {
                if (storage.extract(magic, 1, true) > 0) {
                    storage.extract(magic, 1, false);
                    return true;
                }
            }
            IEssentiaConduit conduit = serverLevel.getCapability(EssentiaCapabilities.CONDUIT, pos, null);
            if (conduit != null) {
                for (Direction side : Direction.values()) {
                    if (conduit.extract(magic, 1, side, false) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void absorbExperience(ServerLevel serverLevel) {
        List<Player> players = serverLevel.getEntitiesOfClass(Player.class,
                new AABB(worldPosition).inflate(6.0D, 3.0D, 6.0D));
        for (Player player : players) {
            if (player.experienceLevel >= 30) {
                player.giveExperienceLevels(-30);
                player.hurt(serverLevel.damageSources().magic(), 8.0F);
                xpAbsorbed = true;
                return;
            }
        }
    }

    @Nullable
    private Holder<Enchantment> findEnchantment() {
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(inventory);
        for (Holder<Enchantment> holder : enchantments.keySet()) {
            ResourceLocation id = enchantmentId(holder);
            if (id != null && !isOverchanted(id)) {
                return holder;
            }
        }
        return null;
    }

    private void boostEnchantment(ServerLevel serverLevel) {
        Holder<Enchantment> holder = findEnchantment();
        if (holder == null) {
            return;
        }
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(inventory);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);
        mutable.set(holder, enchantments.getLevel(holder) + 1);
        EnchantmentHelper.setEnchantments(inventory, mutable.toImmutable());
        ResourceLocation id = enchantmentId(holder);
        if (id != null) {
            addOverchant(id);
        }
    }

    @Nullable
    private ResourceLocation enchantmentId(Holder<Enchantment> holder) {
        if (level == null) {
            return null;
        }
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getKey(holder.value());
    }

    private boolean isOverchanted(ResourceLocation id) {
        CustomData data = inventory.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return false;
        }
        ListTag list = data.copyTag().getList(OVERCHANTS_TAG, Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) {
            if (list.getString(i).equals(id.toString())) {
                return true;
            }
        }
        return false;
    }

    private void addOverchant(ResourceLocation id) {
        CustomData.update(DataComponents.CUSTOM_DATA, inventory,
                tag -> {
                    ListTag list = tag.getList(OVERCHANTS_TAG, Tag.TAG_STRING);
                    list.add(net.minecraft.nbt.StringTag.valueOf(id.toString()));
                    tag.put(OVERCHANTS_TAG, list);
                });
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!inventory.isEmpty()) {
            tag.put("item", inventory.save(registries));
        }
        tag.putInt("enchTime", enchantingTime);
        tag.putBoolean("xpAbsorbed", xpAbsorbed);
        tag.putBoolean("enchStarted", enchantingStarted);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("item", Tag.TAG_COMPOUND)) {
            inventory = ItemStack.parseOptional(registries, tag.getCompound("item"));
        } else {
            inventory = ItemStack.EMPTY;
        }
        enchantingTime = tag.getInt("enchTime");
        xpAbsorbed = tag.getBoolean("xpAbsorbed");
        enchantingStarted = tag.getBoolean("enchStarted");
    }


    public Component getDisplayName() {
        return Component.translatable("tile.overchanter.name");
    }
}
