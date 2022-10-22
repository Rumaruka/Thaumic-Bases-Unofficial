package com.rumaruka.thaumicbases.common.entity;

import com.rumaruka.thaumicbases.common.enchantment.EnumInfusionEnchantmentGun;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.lib.SoundsTC;


public class EntityRevolverBullet extends EntityThrowable {
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
        for (int i = 0; i < 5; i++) {
            double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble());
            double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble());
            double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble());

            double s1 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.17F;
            double s2 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.80F;
            double s3 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.69F;

            world.spawnParticle(EnumParticleTypes.END_ROD, dx, dy, dz, s1, s2, s3);
        }
    }

    @Override
    protected void onImpact(RayTraceResult object) {
        if (this.isDead) return;

        if (!this.world.isRemote && object.typeOfHit == RayTraceResult.Type.BLOCK) {
            if (noClip) return;
            if (this.world.isBlockNormalCube(object.getBlockPos(), true))
                this.setDead();
            else {
                IBlockState b = this.world.getBlockState(new BlockPos(object.getBlockPos().getX(), object.getBlockPos().getY(), object.getBlockPos().getZ()));
                Block b1 = b.getBlock();
                int meta = this.world.getBlockState(new BlockPos(object.getBlockPos().getX(), object.getBlockPos().getY(), object.getBlockPos().getZ())).getBlock().getMetaFromState(b);

                for (int i = 0; i < 100; ++i)
                    this.world.spawnParticle(
                            EnumParticleTypes.END_ROD,
                            object.getBlockPos().getX() + world.rand.nextDouble(),
                            object.getBlockPos().getY() + world.rand.nextDouble(),
                            object.getBlockPos().getZ() + world.rand.nextDouble(),
                            0,
                            0,
                            0);

                world.playSound(object.getBlockPos().getX() + 0.5D,
                        object.getBlockPos().getY() + 0.5D,
                        object.getBlockPos().getZ() + 0.5D, SoundsTC.zap, SoundCategory.BLOCKS, 0.1F, 0.1F, false);
            }
        }
        if (object.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity e = object.entityHit;
            if (e instanceof EntityLivingBase && e != this.thrower && !(e instanceof EntityRevolverBullet)) {
                EntityLivingBase elb = (EntityLivingBase) e;
                float initialDamage = 14;

                if (((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.SMITE) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.SMITE);
                }

                if (((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.BOART) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.BOART);
                }

                if (e instanceof IEldritchMob && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.BOE) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.BOE);
                }

                if ((e instanceof EntityPlayer ||  ((EntityLivingBase) e).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) && EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.DUELING) > 0)
                {
                    initialDamage = 14 + 2 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.DUELING);
                }

                if (EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.POWER) > 0)
                {
                    initialDamage = (float) (14 + 1.5 * EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.POWER));
                }

                elb.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), initialDamage);
                ((EntityLivingBase) e).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100, 0, true, false));
                boolean destroy = true;

                int leveltaint = EnumInfusionEnchantmentGun.getInfusionEnchantmentLevel(this.getThrower().getHeldItem(this.getThrower().getActiveHand()), EnumInfusionEnchantmentGun.TAINT);

                if(leveltaint > 0)
                    ((EntityLivingBase) e).addPotionEffect(new PotionEffect(PotionFluxTaint.instance, leveltaint * 100, leveltaint - 1, true, false));
                if (destroy) this.setDead();
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