package com.rumaruka.thaumicbases.common.item;

import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.Aspects;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.items.IWarpingGear;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.common.entity.RevolverBulletEntity;
import com.rumaruka.thaumicbases.init.TBSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.List;

public class TBRevolverItem extends Item implements IWarpingGear {
    private static final String SHOTS_TAG = "tb_shots";
    private static final String JAR_TAG = "tb_jar";
    private static final ResourceKey<IAspect> AVERSIO = ResourceKey.create(IAspect.REGISTRY_KEY,
            ResourceLocation.fromNamespaceAndPath("thaumaturge", "aversio"));

    public TBRevolverItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                player.openMenu(new net.minecraft.world.SimpleMenuProvider(
                        (id, inv, p) -> new com.rumaruka.thaumicbases.common.menu.RevolverMenu(id, inv, hand),
                        Component.translatable("item.thaumicbases.revolver")),
                        buf -> buf.writeVarInt(hand.ordinal()));
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        int shots = getShots(stack);
        if (shots > 0) {
            if (!level.isClientSide) {
                setShots(stack, shots - 1);
                float damage = RevolverUpgrade.modifyDamage(stack, null, 10.0F);
                RevolverBulletEntity bullet = new RevolverBulletEntity(level, player, damage, stack.copy());
                double speed = RevolverUpgrade.modifySpeed(stack, 4.0D);
                float inaccuracy = RevolverUpgrade.modifyAccuracyInaccuracy(stack, 1.0F);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, (float) speed, inaccuracy);
                level.addFreshEntity(bullet);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), TBSounds.REVOLVER_SHOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(this, 8);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        // Try reload from jar (Aversio)
        boolean reloaded = tryReload(level, player, stack);
        if (reloaded) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), TBSounds.REVOLVER_RELOAD.get(), SoundSource.PLAYERS, 0.9F, 1.0F);
            player.getCooldowns().addCooldown(this, 20);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), TBSounds.REVOLVER_NO_AMMO.get(), SoundSource.PLAYERS, 0.8F, 1.0F);
        return InteractionResultHolder.fail(stack);
    }

    private boolean tryReload(Level level, Player player, ItemStack revolver) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return canReload(level, player, revolver);
        }
        ItemStack jar = getJar(revolver, serverLevel.registryAccess());
        if (jar.isEmpty()) return false;
        Holder<IAspect> aversio = Aspects.resolve(serverLevel, AVERSIO);
        if (aversio == null) return false;

        AspectList list = jar.getOrDefault(TTDataComponents.ASPECTS.get(), AspectList.EMPTY);
        int current = getShots(revolver);
        int capacity = 6;
        if (current >= capacity) return false;

        boolean hasPrimal = RevolverUpgrade.getUpgradeLevel(revolver, RevolverUpgrade.PRIMAL) > 0;
        int shotsPerUnit = RevolverUpgrade.modifyShots(revolver, hasPrimal ? 1 : 2);
        if (shotsPerUnit <= 0) shotsPerUnit = 1;

        int needShots = capacity - current;
        int available = list.amountOf(aversio);
        if (available < 1) return false;

        int unitsToConsume = (int) Math.ceil(needShots / (double) shotsPerUnit);
        unitsToConsume = Math.min(unitsToConsume, available);
        if (unitsToConsume <= 0) return false;

        int gained = Math.min(needShots, unitsToConsume * shotsPerUnit);
        AspectList reduced = list.remove(aversio, unitsToConsume);
        jar.set(TTDataComponents.ASPECTS.get(), reduced);
        setShots(revolver, current + gained);
        setJar(revolver, jar, serverLevel.registryAccess());
        return true;
    }

    private boolean canReload(Level level, Player player, ItemStack revolver) {
        // client-side optimistic check
        CustomData data = revolver.get(DataComponents.CUSTOM_DATA);
        if (data == null) return false;
        CompoundTag tag = data.copyTag();
        return tag.contains(JAR_TAG, Tag.TAG_COMPOUND);
    }

    public static int getShots(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return 0;
        return Math.max(0, Math.min(6, data.copyTag().getInt(SHOTS_TAG)));
    }

    public static void setShots(ItemStack stack, int shots) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(SHOTS_TAG, Math.max(0, Math.min(6, shots))));
    }

    public static ItemStack getJar(ItemStack revolver, net.minecraft.core.HolderLookup.Provider registries) {
        CustomData data = revolver.get(DataComponents.CUSTOM_DATA);
        if (data == null) return ItemStack.EMPTY;
        CompoundTag tag = data.copyTag();
        if (!tag.contains(JAR_TAG, Tag.TAG_COMPOUND)) return ItemStack.EMPTY;
        return ItemStack.parseOptional(registries, tag.getCompound(JAR_TAG));
    }

    public static void setJar(ItemStack revolver, ItemStack jar, net.minecraft.core.HolderLookup.Provider registries) {
        CustomData.update(DataComponents.CUSTOM_DATA, revolver, tag -> {
            if (jar == null || jar.isEmpty()) {
                tag.remove(JAR_TAG);
            } else {
                tag.put(JAR_TAG, jar.save(registries));
            }
        });
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * getShots(stack) / 6.0F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xC0C0C0;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.shots", getShots(stack), 6).withStyle(net.minecraft.ChatFormatting.GRAY));
        // Jar contents
        try {
            if (context.level() != null) {
                ItemStack jar = getJar(stack, context.registries());
                if (!jar.isEmpty()) {
                    AspectList list = jar.getOrDefault(TTDataComponents.ASPECTS.get(), AspectList.EMPTY);
                    if (!list.isEmpty()) {
                        tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.jar", jar.getHoverName()).withStyle(net.minecraft.ChatFormatting.GRAY));
                        int av = 0;
                        Holder<IAspect> aversio = Aspects.resolve(context.level(), AVERSIO);
                        if (aversio != null) av = list.amountOf(aversio);
                        tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.aversio", av).withStyle(net.minecraft.ChatFormatting.DARK_AQUA));
                    } else {
                        tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.jar_empty", jar.getHoverName()).withStyle(net.minecraft.ChatFormatting.GRAY));
                    }
                } else {
                    tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.jar_none").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
                }
            }
        } catch (Throwable ignored) {
        }

        // Upgrades
        List<RevolverUpgrade> installed = RevolverUpgrade.getInstalled(stack);
        boolean shift = isShiftDownClient();
        if (!installed.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.upgrades").withStyle(net.minecraft.ChatFormatting.GOLD));
            for (RevolverUpgrade u : installed) {
                int lvl = RevolverUpgrade.getUpgradeLevel(stack, u);
                tooltip.add(Component.literal(" - ")
                        .append(u.getDisplayName())
                        .append(Component.literal(lvl > 0 ? " " + toRoman(lvl) : ""))
                        .append(Component.literal(" [" + lvl + "/" + u.maxLevel + "]").withStyle(net.minecraft.ChatFormatting.DARK_GRAY))
                        .withStyle(net.minecraft.ChatFormatting.GRAY));

                Component desc = u.getDescription();
                String descKey = "revolverUpgrade." + u.key + ".desc";
                if (!desc.getString().equals(descKey)) {
                    tooltip.add(Component.literal("   ").append(desc).withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
                }

                var conflictsList = u.getConflicts();
                if (!conflictsList.isEmpty()) {
                    var c = Component.translatable("tooltip.thaumicbases.revolver_upgrade.conflicts").append(": ").withStyle(net.minecraft.ChatFormatting.DARK_RED);
                    for (int i = 0; i < conflictsList.size(); i++) {
                        if (i > 0) c.append(Component.literal(", ").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
                        c.append(conflictsList.get(i).getDisplayName().copy().withStyle(net.minecraft.ChatFormatting.RED));
                    }
                    tooltip.add(Component.literal("   ").append(c));
                }
            }
        }

        if (shift) {
            tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.controls").withStyle(net.minecraft.ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.controls_jar").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.controls_reload").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.thaumicbases.revolver.shift_details").withStyle(net.minecraft.ChatFormatting.DARK_GRAY, net.minecraft.ChatFormatting.ITALIC));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide) return;
        int uvoid = RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.VOID);
        if (uvoid > 0 && stack.isDamaged() && entity instanceof LivingEntity living && entity.tickCount % Math.max(20, 100 / uvoid) == 0) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    @Override
    public int warp(ItemStack stack, LivingEntity entity) {
        return RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.VOID);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers base = super.getDefaultAttributeModifiers(stack);
        int heavy = RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.HEAVY);
        if (heavy <= 0) return base;
        ItemAttributeModifiers.Builder b = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry e : base.modifiers()) b.add(e.attribute(), e.modifier(), e.slot());
        b.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.fromNamespaceAndPath("thaumicbases", "revolver_heavy"), 5.0D + heavy * 2.0D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return b.build();
    }

    public static float getZoom(ItemStack stack) {
        int acc = RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.ACCURACY);
        return 0.9F / (acc + 1);
    }

    private static String toRoman(int value) {
        return switch (Math.max(1, Math.min(10, value))) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> String.valueOf(value);
        };
    }

    private static boolean isShiftDownClient() {
        try {
            if (net.neoforged.fml.loading.FMLEnvironment.dist.isClient()) {
                return net.minecraft.client.gui.screens.Screen.hasShiftDown();
            }
        } catch (Throwable ignored) {
        }
        return false;
    }
}
