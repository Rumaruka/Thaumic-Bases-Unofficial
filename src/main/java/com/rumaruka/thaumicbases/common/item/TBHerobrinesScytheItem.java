package com.rumaruka.thaumicbases.common.item;

import com.leclowndu93150.thaumaturge.api.items.IWarpingGear;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TBHerobrinesScytheItem extends SwordItem implements IWarpingGear {
    public TBHerobrinesScytheItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && stack.isDamaged() && entity.tickCount % 20 == 0 && entity instanceof LivingEntity) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && target instanceof Monster && player.getAttackStrengthScale(0.5F) > 0.9F) {
            chainAttack(player, target, new HashSet<>());
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private void chainAttack(Player attacker, LivingEntity attacked, Set<Integer> visited) {
        if (visited.size() > 6) return;
        AABB box = attacked.getBoundingBox().inflate(6.0D);
        List<LivingEntity> mobs = attacked.level().getEntitiesOfClass(LivingEntity.class, box,
                e -> e instanceof Monster && e.isAlive() && e != attacker && !visited.contains(e.getId()));
        if (mobs.isEmpty()) return;
        LivingEntity next = mobs.get(attacker.getRandom().nextInt(mobs.size()));
        visited.add(next.getId());
        next.hurt(attacker.damageSources().playerAttack(attacker), 6.0F);
        if (attacker.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, next.getX(), next.getY(0.5D), next.getZ(), 8, 0.3, 0.3, 0.3, 0.02);
        }
        chainAttack(attacker, next, visited);
    }

    @Override
    public int warp(ItemStack stack, LivingEntity entity) {
        return 3;
    }
}
