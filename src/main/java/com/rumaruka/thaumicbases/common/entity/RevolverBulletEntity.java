package com.rumaruka.thaumicbases.common.entity;

import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.init.TBEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class RevolverBulletEntity extends ThrowableItemProjectile {
    private float damage = 10.0F;
    private ItemStack revolver = ItemStack.EMPTY;
    private boolean piercing;

    public RevolverBulletEntity(EntityType<? extends RevolverBulletEntity> type, Level level) {
        super(type, level);
    }

    public RevolverBulletEntity(Level level, LivingEntity shooter, float damage, ItemStack revolver) {
        super(TBEntities.REVOLVER_BULLET.get(), shooter, level);
        this.damage = damage;
        this.revolver = revolver == null ? ItemStack.EMPTY : revolver.copy();
        this.piercing = RevolverUpgrade.getUpgradeLevel(this.revolver, RevolverUpgrade.PIERCING) > 0;
        boolean primal = RevolverUpgrade.getUpgradeLevel(this.revolver, RevolverUpgrade.PRIMAL) > 0;
        this.setNoGravity(primal);
        this.noPhysics = primal || piercing; // primal noclip through blocks, piercing passes through
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_NUGGET;
    }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            // handled in onHitEntity (allows piercing to continue)
            return;
        }
        if (noPhysics) {
            // primal/piercing can pass through blocks; discard only if hit world border-like cases handled elsewhere
            return;
        }
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide) return;
        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        if (target == owner) return;
        float finalDamage = damage;
        if (target instanceof LivingEntity living && !revolver.isEmpty()) {
            finalDamage = RevolverUpgrade.modifyDamage(revolver, living, damage);
        }
        if (owner instanceof Player playerOwner) {
            target.hurt(this.damageSources().playerAttack(playerOwner), finalDamage);
            if (target instanceof LivingEntity living) {
                RevolverUpgrade.onBulletHit(revolver, living, playerOwner, finalDamage);
            }
        } else if (owner instanceof LivingEntity livingOwner) {
            target.hurt(this.damageSources().mobProjectile(this, livingOwner), finalDamage);
        } else {
            target.hurt(this.damageSources().generic(), finalDamage);
        }
        if (!piercing) {
            this.discard();
        }
    }

    @Override
    protected double getDefaultGravity() {
        return RevolverUpgrade.getUpgradeLevel(revolver, RevolverUpgrade.PRIMAL) > 0 ? 0.0D : 0.01D;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount > 200) {
            this.discard();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", damage);
        tag.putBoolean("Piercing", piercing);
        if (!revolver.isEmpty()) {
            tag.put("Revolver", revolver.save(registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Damage")) damage = tag.getFloat("Damage");
        piercing = tag.getBoolean("Piercing");
        if (tag.contains("Revolver", CompoundTag.TAG_COMPOUND)) {
            revolver = ItemStack.parseOptional(registryAccess(), tag.getCompound("Revolver"));
        }
    }
}
