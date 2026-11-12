package com.rumaruka.thaumicbases.api;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class RevolverUpgrade {
    public static final List<RevolverUpgrade> ALL = new ArrayList<>();

    public static final RevolverUpgrade POWER = reg(new RevolverUpgrade(0, "POWER", 5));
    public static final RevolverUpgrade ACCURACY = reg(new RevolverUpgrade(1, "ACCURACY", 3));
    public static final RevolverUpgrade SILVER = reg(new RevolverUpgrade(2, "SILVER", 5));
    public static final RevolverUpgrade POISONED = reg(new RevolverUpgrade(3, "POISONED", 3));
    public static final RevolverUpgrade ATROPODS_BANE = reg(new RevolverUpgrade(4, "BANE_OF_ATROPODS", 5));
    public static final RevolverUpgrade ELDRITCH_BANE = reg(new RevolverUpgrade(5, "BANE_OF_ELDRITCH", 5));
    public static final RevolverUpgrade PIERCING = reg(new RevolverUpgrade(6, "PIERCING", 1));
    public static final RevolverUpgrade DUELING = reg(new RevolverUpgrade(7, "DUELING", 5));
    public static final RevolverUpgrade TAINTED = reg(new RevolverUpgrade(8, "TAINTED", 3));
    public static final RevolverUpgrade PRIMAL = reg(new RevolverUpgrade(9, "PRIMAL", 1));
    public static final RevolverUpgrade SPEED = reg(new RevolverUpgrade(10, "SPEED", 5));
    public static final RevolverUpgrade EFFICIENCY = reg(new RevolverUpgrade(11, "EFFICIENCY", 5));
    public static final RevolverUpgrade ELDRITCH = reg(new RevolverUpgrade(12, "ELDRITCH", 2));
    public static final RevolverUpgrade VOID = reg(new RevolverUpgrade(13, "VOID", 3));
    public static final RevolverUpgrade KNOWLEDGE = reg(new RevolverUpgrade(14, "KNOWLEDGE", 5));
    public static final RevolverUpgrade HEAVY = reg(new RevolverUpgrade(15, "HEAVY", 5));

    static {
        conflicts(POWER, SILVER, ATROPODS_BANE, ELDRITCH_BANE, DUELING, ELDRITCH);
        conflicts(SILVER, ATROPODS_BANE, ELDRITCH_BANE, DUELING, ELDRITCH);
        conflicts(ATROPODS_BANE, ELDRITCH_BANE, DUELING, ELDRITCH);
        conflicts(ELDRITCH_BANE, DUELING, ELDRITCH);
        conflicts(DUELING, ELDRITCH);
        conflicts(ACCURACY, SPEED, EFFICIENCY);
        conflicts(SPEED, EFFICIENCY);
        conflicts(POISONED, PRIMAL, TAINTED, KNOWLEDGE);
        conflicts(PRIMAL, TAINTED, KNOWLEDGE);
        conflicts(TAINTED, KNOWLEDGE);
    }

    public final int id;
    public final String key;
    public final int maxLevel;
    private final Set<Integer> conflicts = new java.util.HashSet<>();

    public RevolverUpgrade(int id, String key, int maxLevel) {
        this.id = id;
        this.key = key;
        this.maxLevel = maxLevel;
    }

    private static RevolverUpgrade reg(RevolverUpgrade u) {
        ALL.add(u);
        return u;
    }

    private static void conflicts(RevolverUpgrade a, RevolverUpgrade... others) {
        for (RevolverUpgrade b : others) {
            a.conflicts.add(b.id);
            b.conflicts.add(a.id);
        }
    }

    public boolean conflictsWith(RevolverUpgrade other) {
        return other != null && conflicts.contains(other.id);
    }

    public boolean canApply(ItemStack stack) {
        for (RevolverUpgrade u : ALL) {
            if (u == this) continue;
            if (getUpgradeLevel(stack, u) > 0 && conflictsWith(u)) return false;
        }
        return true;
    }

    public java.util.List<RevolverUpgrade> getConflicts() {
        java.util.List<RevolverUpgrade> list = new java.util.ArrayList<>();
        for (RevolverUpgrade u : ALL) {
            if (u != this && conflictsWith(u)) list.add(u);
        }
        return list;
    }

    public net.minecraft.network.chat.Component getDisplayName() {
        return net.minecraft.network.chat.Component.translatable("revolverUpgrade." + key + ".name");
    }

    public net.minecraft.network.chat.Component getDescription() {
        return net.minecraft.network.chat.Component.translatable("revolverUpgrade." + key + ".desc");
    }

    public java.util.List<net.minecraft.network.chat.Component> getTooltip(int level) {
        java.util.List<net.minecraft.network.chat.Component> lines = new java.util.ArrayList<>();
        String lvlText = level > 0 ? " " + toRoman(level) : "";
        lines.add(net.minecraft.network.chat.Component.literal("")
                .append(getDisplayName())
                .append(net.minecraft.network.chat.Component.literal(lvlText + " (max " + maxLevel + ")"))
                .withStyle(net.minecraft.ChatFormatting.GOLD));

        net.minecraft.network.chat.Component desc = getDescription();
        if (!desc.getString().equals("revolverUpgrade." + key + ".desc")) {
            lines.add(net.minecraft.network.chat.Component.literal(" ").append(desc).withStyle(net.minecraft.ChatFormatting.GRAY));
        }

        java.util.List<RevolverUpgrade> conflictsList = getConflicts();
        if (!conflictsList.isEmpty()) {
            net.minecraft.network.chat.MutableComponent c = net.minecraft.network.chat.Component.translatable("tooltip.thaumicbases.revolver_upgrade.conflicts")
                    .append(": ").withStyle(net.minecraft.ChatFormatting.RED);
            for (int i = 0; i < conflictsList.size(); i++) {
                if (i > 0) c.append(net.minecraft.network.chat.Component.literal(", ").withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
                c.append(conflictsList.get(i).getDisplayName().copy().withStyle(net.minecraft.ChatFormatting.YELLOW));
            }
            lines.add(c);
        }
        return lines;
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

    public static RevolverUpgrade byId(int id) {
        for (RevolverUpgrade u : ALL) if (u.id == id) return u;
        return null;
    }

    public static int getUpgradeLevel(ItemStack stack, RevolverUpgrade upgrade) {
        net.minecraft.world.item.component.CustomData data = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
        if (data == null) return 0;
        var tag = data.copyTag();
        if (!tag.contains("tb_upgrades", net.minecraft.nbt.Tag.TAG_COMPOUND)) return 0;
        var ups = tag.getCompound("tb_upgrades");
        return Math.max(0, ups.getInt(upgrade.key));
    }

    public static boolean canSetUpgrade(ItemStack stack, RevolverUpgrade upgrade, int level) {
        if (upgrade == null) return false;
        int lvl = Math.max(0, Math.min(upgrade.maxLevel, level));
        if (lvl <= 0) return true;
        for (RevolverUpgrade u : ALL) {
            if (u == upgrade) continue;
            if (getUpgradeLevel(stack, u) > 0 && upgrade.conflictsWith(u)) {
                return false;
            }
        }
        return true;
    }

    public static boolean setUpgradeLevel(ItemStack stack, RevolverUpgrade upgrade, int level) {
        if (upgrade == null) return false;
        int lvl = Math.max(0, Math.min(upgrade.maxLevel, level));
        if (lvl > 0 && !canSetUpgrade(stack, upgrade, lvl)) {
            return false;
        }
        net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack, tag -> {
            var ups = tag.getCompound("tb_upgrades");
            if (lvl <= 0) ups.remove(upgrade.key); else ups.putInt(upgrade.key, lvl);
            tag.put("tb_upgrades", ups);
        });
        return true;
    }

    public static boolean addUpgrade(ItemStack stack, RevolverUpgrade upgrade, int level) {
        return setUpgradeLevel(stack, upgrade, getUpgradeLevel(stack, upgrade) + level);
    }

    public static List<RevolverUpgrade> getInstalled(ItemStack stack) {
        List<RevolverUpgrade> list = new ArrayList<>();
        for (RevolverUpgrade u : ALL) if (getUpgradeLevel(stack, u) > 0) list.add(u);
        return list;
    }

    public static int modifyShots(ItemStack revolver, int baseShots) {
        boolean hasPrimal = getUpgradeLevel(revolver, PRIMAL) > 0;
        int eff = getUpgradeLevel(revolver, EFFICIENCY);
        int shots = hasPrimal ? 1 : baseShots;
        if (eff > 0) {
            // 1.12-like: efficiency adds shots; with primal level 5 gives 2, otherwise primal stays 1
            shots = hasPrimal ? (eff >= 5 ? 2 : 1) : (shots + eff);
        }
        return Math.max(1, Math.min(6, shots));
    }

    public static float modifyDamage(ItemStack revolver, LivingEntity target, float damage) {
        float d = damage;
        int power = getUpgradeLevel(revolver, POWER);
        if (power > 0) d *= (float) Math.pow(1.1F, power);
        int heavy = getUpgradeLevel(revolver, HEAVY);
        if (heavy > 0) d += heavy * 1.0F;

        if (target != null) {
            int silver = getUpgradeLevel(revolver, SILVER);
            if (silver > 0 && target.getType().is(EntityTypeTags.UNDEAD)) d *= (float) Math.pow(1.2F, silver);
            int baneA = getUpgradeLevel(revolver, ATROPODS_BANE);
            if (baneA > 0 && target.getType().is(EntityTypeTags.ARTHROPOD)) d *= (float) Math.pow(1.2F, baneA);
            int baneE = getUpgradeLevel(revolver, ELDRITCH_BANE);
            if (baneE > 0 && (target instanceof EnderMan || isEldritchLike(target))) d *= (float) Math.pow(1.2F, baneE);
            int dueling = getUpgradeLevel(revolver, DUELING);
            if (dueling > 0 && target instanceof Player) d *= (float) Math.pow(1.2F, dueling);
            int eld = getUpgradeLevel(revolver, ELDRITCH);
            if (eld > 0) d *= 0.85F;
            int tainted = getUpgradeLevel(revolver, TAINTED);
            if (tainted > 0) d *= (float) Math.pow(1.2F, tainted);
            int piercing = getUpgradeLevel(revolver, PIERCING);
            if (piercing > 0) d *= 1.1F;
        }
        int primal = getUpgradeLevel(revolver, PRIMAL);
        if (primal > 0) d *= 2.0F;
        return d;
    }

    public static boolean isEldritchLike(LivingEntity target) {
        String id = String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()));
        return id.contains("eldritch") || id.contains("crimson");
    }

    public static double modifySpeed(ItemStack revolver, double base) {
        int speed = getUpgradeLevel(revolver, SPEED);
        if (speed > 0) return base * Math.pow(1.09D, speed);
        return base;
    }

    public static float modifyAccuracyInaccuracy(ItemStack revolver, float base) {
        int acc = getUpgradeLevel(revolver, ACCURACY);
        if (acc > 0) return Math.max(0.0F, base - acc * 0.25F);
        return base;
    }

    public static void onBulletHit(ItemStack revolver, LivingEntity target, Player shooter, float damage) {
        int tainted = getUpgradeLevel(revolver, TAINTED);
        if (tainted > 0 && target.getRandom().nextDouble() <= 0.1D * tainted) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, Math.max(0, tainted - 1)));
        }
        int poisoned = getUpgradeLevel(revolver, POISONED);
        if (poisoned > 0) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, poisoned - 1));
        }
        int knowledge = getUpgradeLevel(revolver, KNOWLEDGE);
        if (knowledge > 0 && shooter != null) {
            // 1.12 granted XP orbs on kill based on target aspects; adapted to direct XP with a chance
            if (target.getRandom().nextInt(Math.max(1, 7 - knowledge)) == 0) {
                int xp = 1 + target.getRandom().nextInt(2 + knowledge);
                shooter.giveExperiencePoints(xp);
            }
        }
    }
}
