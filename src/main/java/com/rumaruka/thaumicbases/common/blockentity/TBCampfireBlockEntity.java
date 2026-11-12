package com.rumaruka.thaumicbases.common.blockentity;

import com.rumaruka.thaumicbases.common.block.TBCampfireBlock;
import com.rumaruka.thaumicbases.init.TBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TBCampfireBlockEntity extends BlockEntity {
    public int burnTime;
    public int logTime;
    public int uptime;
    public boolean tainted;

    public TBCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(TBBlockEntities.CAMPFIRE.get(), pos, state);
    }

    public boolean addLog(ItemStack stack) {
        if (stack.isEmpty() || logTime > 0) {
            return false;
        }

        int time = 0;
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String path = id.getPath();
        if (id.getNamespace().equals("thaumaturge")) {
            if (path.contains("greatwood") || path.contains("silverwood")) {
                time = 1600 * 4;
            } else if (path.contains("taint")) {
                time = 1600 * 8;
                tainted = true;
            }
        }
        if (time == 0 && stack.is(ItemTags.LOGS)) {
            time = 1600;
        }
        if (time == 0) {
            return false;
        }

        logTime = time;
        if (level != null && !level.isClientSide) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(TBCampfireBlock.STATE, Integer.valueOf(1)));
        }
        setChanged();
        return true;
    }

    public boolean addFuel(ItemStack stack) {
        if (stack.isEmpty() || logTime <= 0 || burnTime > 0) {
            return false;
        }
        int time = stack.getBurnTime(null);
        if (time <= 0) {
            return false;
        }
        burnTime = time;
        if (level != null && !level.isClientSide) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(TBCampfireBlock.STATE, Integer.valueOf(2)));
        }
        setChanged();
        return true;
    }

    public void tick() {
        if (level == null || level.isClientSide) {
            return;
        }
        uptime++;
        if (burnTime > 0) {
            burnTime--;
            if (logTime > 0) {
                logTime--;
            }
        }

        BlockState state = getBlockState();
        int meta = state.getValue(TBCampfireBlock.STATE);

        if (burnTime <= 0) {
            if (meta == 2) {
                level.setBlockAndUpdate(worldPosition, state.setValue(TBCampfireBlock.STATE, Integer.valueOf(1)));
            }
            if (uptime % 20 == 0) {
                setChanged();
            }
            return;
        }

        if (logTime <= 0) {
            if (meta > 0) {
                level.setBlockAndUpdate(worldPosition, state.setValue(TBCampfireBlock.STATE, Integer.valueOf(0)));
            }
            tainted = false;
            setChanged();
            return;
        }

        if (uptime % 20 == 0) {
            applyEffects();
            setChanged();
        }
    }

    private void applyEffects() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class,
                new AABB(worldPosition).inflate(12.0D, 6.0D, 12.0D));
        for (LivingEntity entity : entities) {
            if (tainted) {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, true, false));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, true, false));
            }
            if (entity instanceof Player) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1500, 0, true, false));
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1500, 0, true, false));
            }
            if (entity instanceof Mob mob) {
                if (mob.isInvertedHealAndHarm()) {
                    entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 2, true, false));
                } else {
                    entity.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 2, true, false));
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("burnTime", burnTime);
        tag.putInt("logTime", logTime);
        tag.putBoolean("tainted", tainted);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        burnTime = tag.getInt("burnTime");
        logTime = tag.getInt("logTime");
        tainted = tag.getBoolean("tainted");
    }
}
