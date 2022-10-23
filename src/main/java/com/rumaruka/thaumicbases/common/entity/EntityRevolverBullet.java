package com.rumaruka.thaumicbases.common.entity;

import com.rumaruka.thaumicbases.common.enchantment.EnumInfusionEnchantmentGun;
import com.rumaruka.thaumicbases.common.item.ItemRevolver;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;

import java.util.Objects;


public class EntityRevolverBullet extends EntityThrowable {
    public EntityRevolverBullet(World world) {
        super(world);
    }

    public EntityRevolverBullet(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityRevolverBullet(World world, EntityLivingBase thrower) {
        super(world, thrower);
        shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0, 1.5F, 1.0F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        makeTrail();
    }

    private void makeTrail() {
        for (int i = 0; i < 100; i++) {
            double dx = posX + world.rand.nextDouble();
            double dy = posY + world.rand.nextDouble();
            double dz = posZ + world.rand.nextDouble();

            double s1 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.17F;  // color
            double s2 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.80F;  // color
            double s3 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.69F;  // color

            this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            world.playSound(posX + 0.5D,
                    posY + .5D,
                    posZ + 0.5D, SoundsTC.zap, SoundCategory.BLOCKS, 0.1F, 0.1F, false);
        }
    }

    @Override
    protected void onImpact(RayTraceResult object) {
        if (this.isDead) return;

        if (object.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity e = object.entityHit;
            if (e instanceof EntityLivingBase && e != this.thrower && thrower instanceof EntityPlayer) {
                EntityLivingBase elb = (EntityLivingBase) e;
                float initialDamage = 14;

                if (((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.SMITE) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.SMITE);
                }

                if (((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.SMITE) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.SMITE);
                }

                if (((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.BOART) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.BOART);
                }

                if (((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(Objects.requireNonNull(this.getThrower()).getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.BOART) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.BOART);
                }

                if (e instanceof IEldritchMob && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.BOE) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.BOE);
                }

                if (e instanceof IEldritchMob && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.BOE) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.BOE);
                }


                if (EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.POWER) > 0)
                {
                    initialDamage = (float) (14 + 1.5 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.POWER));
                }

                if (EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.POWER) > 0)
                {
                    initialDamage = (float) (14 + 1.5 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.POWER));
                }

                elb.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), initialDamage);

                boolean destroy = true;
int leveltaint = 0;
                if(this.thrower.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemRevolver)
                leveltaint = EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.MAIN_HAND), EnumInfusionEnchantmentGun.TAINT);

                if(this.thrower.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemRevolver)
                    leveltaint = EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.thrower.getHeldItem(EnumHand.OFF_HAND), EnumInfusionEnchantmentGun.TAINT);

                if(leveltaint > 0)
                    ((EntityLivingBase) e).addPotionEffect(new PotionEffect(PotionFluxTaint.instance, leveltaint * 100, leveltaint - 1, true, false));

                this.world.setEntityState(this, (byte) 3);
                this.setDead();
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        super.attackEntityFrom(source, amount);

        if (!this.world.isRemote && source.getTrueSource() != null) {
            Vec3d vec3d = source.getTrueSource().getLookVec();
            // reflect faster and more accurately
            this.shoot(vec3d.x, vec3d.y, vec3d.z, 1.5F, 0.1F);  // reflect faster and more accurately

            if (source.getImmediateSource() instanceof EntityLivingBase) {
                this.thrower = (EntityLivingBase) source.getImmediateSource();
            }
            return true;
        }

        return false;
    }
}