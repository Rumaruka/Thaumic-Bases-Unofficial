package com.rumaruka.thaumicbases.common.item;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.warp.WarpHelper;
import com.leclowndu93150.thaumaturge.api.warp.WarpType;
import com.leclowndu93150.thaumaturge.content.entity.WispEntity;
import com.leclowndu93150.thaumaturge.registry.TTEntities;
import com.leclowndu93150.thaumaturge.registry.TTMobEffects;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TBTobaccoItem extends Item {
    public enum TobaccoType {
        PILE, ELDRITCH, FIGHTING, HUNGER, KNOWLEDGE, MINING, SANITY, TAINTED, WISPY
    }

    private final TobaccoType type;

    public TBTobaccoItem(TobaccoType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public TobaccoType getType() {
        return type;
    }

    public void performEffect(Player smoker, ItemStack tobacco, boolean isSilverwood) {
        Level level = smoker.level();
        switch (type) {
            case PILE -> {
                if (level.random.nextFloat() <= (isSilverwood ? 0.3f : 0.1f) && smoker instanceof ServerPlayer sp) {
                    WarpHelper.addWarp(sp, -1, WarpType.TEMPORARY);
                }
            }
            case ELDRITCH -> {
                if (!level.isClientSide) {
                    smoker.addEffect(new MobEffectInstance(TTMobEffects.DEATH_GAZE, 2000, 0, true, false));
                    if (!isSilverwood && smoker instanceof ServerPlayer sp) {
                        WarpHelper.addWarp(sp, 3, WarpType.TEMPORARY);
                    }
                }
            }
            case FIGHTING -> {
                if (!level.isClientSide) {
                    smoker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 8000, 1, true, false));
                    if (isSilverwood) {
                        smoker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 8000, 0, true, false));
                    }
                }
            }
            case HUNGER -> {
                if (!level.isClientSide) {
                    smoker.getFoodData().eat(3, 0.3F);
                    if (!isSilverwood) {
                        smoker.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 500, 0, true, false));
                    }
                }
            }
            case KNOWLEDGE -> {
                if (!level.isClientSide) {
                    smoker.giveExperiencePoints(isSilverwood ? 25 : 10);
                    if (smoker instanceof ServerPlayer sp && !isSilverwood) {
                        WarpHelper.addWarp(sp, 2, WarpType.TEMPORARY);
                        WarpHelper.addWarp(sp, 1, WarpType.NORMAL);
                    }
                }
            }
            case MINING -> {
                if (!level.isClientSide) {
                    smoker.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 8000, 1, true, false));
                    if (isSilverwood) {
                        smoker.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 8000, 0, true, false));
                    }
                }
            }
            case SANITY -> {
                if (!level.isClientSide && smoker instanceof ServerPlayer sp) {
                    WarpHelper.addWarp(sp, -1, WarpType.TEMPORARY);
                    if (isSilverwood) {
                        WarpHelper.addWarp(sp, -1, WarpType.NORMAL);
                        WarpHelper.addWarp(sp, -1, WarpType.TEMPORARY);
                    }
                }
            }
            case TAINTED -> {
                if (!level.isClientSide && smoker instanceof ServerPlayer sp && !isSilverwood) {
                    WarpHelper.addWarp(sp, 1 + level.random.nextInt(3), WarpType.TEMPORARY);
                    if (level.random.nextFloat() <= 0.4F) {
                        WarpHelper.addWarp(sp, 1, WarpType.NORMAL);
                    }
                }
            }
            case WISPY -> {
                if (!level.isClientSide) {
                    spawnWisp(level, smoker);
                    if (isSilverwood) {
                        spawnWisp(level, smoker);
                    }
                }
            }
        }
    }

    private static void spawnWisp(Level level, Player smoker) {
        WispEntity wisp = TTEntities.WISP.get().create(level);
        if (wisp == null) return;
        wisp.moveTo(smoker.getX(), smoker.getY() + 1.0D, smoker.getZ(), 0.0F, 0.0F);
        if (level instanceof ServerLevel serverLevel) {
            Registry<IAspect> registry = serverLevel.registryAccess().registryOrThrow(IAspect.REGISTRY_KEY);
            List<ResourceLocation> aspects = registry.keySet().stream().toList();
            if (!aspects.isEmpty()) {
                ResourceLocation aspect = aspects.get(level.random.nextInt(aspects.size()));
                wisp.setAspect(aspect);
            }
        }
        level.addFreshEntity(wisp);
    }
}
