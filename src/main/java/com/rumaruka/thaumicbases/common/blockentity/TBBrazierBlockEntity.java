package com.rumaruka.thaumicbases.common.blockentity;

import com.rumaruka.thaumicbases.common.block.TBBrazierBlock;
import com.rumaruka.thaumicbases.init.TBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TBBrazierBlockEntity extends BlockEntity {
    public int burnTime;
    public int uptime;

    public TBBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(TBBlockEntities.BRAZIER.get(), pos, state);
    }

    public void tick() {
        if (level == null || level.isClientSide) {
            return;
        }
        uptime++;
        if (burnTime > 0) {
            burnTime--;
        }

        BlockState state = getBlockState();
        if (burnTime <= 0) {
            if (state.getValue(TBBrazierBlock.STATE) == 1) {
                level.setBlockAndUpdate(worldPosition, state.setValue(TBBrazierBlock.STATE, Integer.valueOf(0)));
            }
            if (uptime % 20 == 0) {
                setChanged();
            }
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
            if (entity instanceof Player) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2500, 0, true, false));
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2500, 0, true, false));
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
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        burnTime = tag.getInt("burnTime");
    }
}
