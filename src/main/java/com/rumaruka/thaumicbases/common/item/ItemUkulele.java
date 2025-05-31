package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.api.dummycore_remove.utils.MathUtils;
import com.rumaruka.thaumicbases.common.handlers.TBEventHandler;
import com.rumaruka.thaumicbases.core.TBCore;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.RechargeHelper;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.potions.PotionWarpWard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemUkulele extends Item implements IRechargable {

    public static final String[] types = new String[]{
            "simple",
            "knowledge",
            "calming",
            "electric",
            "resistance",
            "buffing",
            "confusion",
            "growth",
            "love"
    };

    public static final int[] costs = new int[]{
            0,
            1,
            1,
            0,
            0,
            20,
            1,
            0,
            0
    };

    public static final int[] soundDelays = new int[]{
            25 * 20,
            4 * 20,
            20 * 20,
            8 * 20,
            9 * 20,
            10 * 20,
            16 * 20,
            8 * 20,
            10 * 20
    };

    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name." + this.getDamage(stack));
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        {
            Vec3d lookVec = player.getLookVec();
            int cost = (int) (costs[Math.min(stack.getItemDamage(), costs.length - 1)] * 0.75);
            player.world.spawnParticle(EnumParticleTypes.NOTE, player.posX + lookVec.x / 5 + MathUtils.randomDouble(itemRand) / 2, player.posY + 1 + lookVec.y / 2 + MathUtils.randomDouble(itemRand) / 10 + 0.1D, player.posZ + lookVec.z / 2 + MathUtils.randomDouble(itemRand) / 5, itemRand.nextDouble(), itemRand.nextDouble(), itemRand.nextDouble());
            if (player.world.isRemote && TBEventHandler.clientUkuleleSoundPlayDelay <= 0) {
                TBEventHandler.clientUkuleleSoundPlayDelay = soundDelays[Math.min(stack.getItemDamage(), soundDelays.length - 1)];
                player.world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation(TBCore.modid, "guitar." + types[Math.min(stack.getItemDamage(), types.length - 1)])), SoundCategory.PLAYERS, 1.0F, 0.5F);
            }
            if (stack.getItemDamage() == 1 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 80 == 0) {
                    AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 8, player.posY - 8, player.posZ - 8, player.posX + 8, player.posY + 8, player.posZ + 8);
                    List<EntityPlayer> players = player.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
                    for (EntityPlayer p : players) {
                        boolean addAspect = p == player ? true : p.world.rand.nextBoolean();

                        if (!addAspect)
                            continue;

                        int amount = p.world.rand.nextInt(3) + 1;
                        if (!player.world.isRemote && RechargeHelper.consumeCharge(stack, player, cost)) {
                            EntityXPOrb xp = new EntityXPOrb(player.world, player.posX, player.posY, player.posZ, amount);
                            player.world.spawnEntity(xp);
                        }
                    }
                }
            }
            if (stack.getItemDamage() == 2 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 20 == 0) {
                    AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 4, player.posY - 4, player.posZ - 4, player.posX + 4, player.posY + 4, player.posZ + 4);
                    List<EntityPlayer> players = player.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
                    for (EntityPlayer p : players) {
                        if (RechargeHelper.consumeCharge(stack, player, cost)) {
                            boolean hasEffect = p.getActivePotionEffect(PotionWarpWard.instance) != null;
                            if (!hasEffect) {
                                if (!p.world.isRemote)
                                    p.addPotionEffect(new PotionEffect(PotionWarpWard.instance, 200, 0, true, false));
                            } else {
                                PotionEffect effect = p.getActivePotionEffect(PotionWarpWard.instance);
                                try {
                                    Field dur = PotionEffect.class.getDeclaredFields()[2];
                                    dur.setAccessible(true);
                                    dur.setInt(effect, dur.getInt(effect) + 120);
                                    dur.setAccessible(false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            if (stack.getItemDamage() == 3 && RechargeHelper.getCharge(stack) > 0) {
                double dx = player.posX + MathUtils.randomDouble(itemRand) * 16;
                double dy = player.posY + MathUtils.randomDouble(itemRand) * 16;
                double dz = player.posZ + MathUtils.randomDouble(itemRand) * 16;
                if (player.world.isRemote && player.world.rand.nextDouble() <= 0.1D)
                    new FXDispatcher().arcLightning(player.posX, player.posY - 1, player.posZ, dx, dy, dz, 0.2F, 0.5F, 1, 1);

                f:
                for (int i = 1; i <= 16; ++i) {
                    double px = lookVec.x * i + player.posX;
                    double py = lookVec.y * i + player.posY + player.getEyeHeight();
                    double pz = lookVec.z * i + player.posZ;
                    AxisAlignedBB aabb = new AxisAlignedBB(px - 0.5D, py - 0.5D, pz - 0.5D, px + 0.5D, py + 0.5D, pz + 0.5D);
                    List<EntityLivingBase> mobs = player.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                    for (EntityLivingBase e : mobs) {
                        if (e == player)
                            continue;

                        if (e.isDead)
                            continue;

                        if (e.hurtTime > 0)
                            continue;

                        boolean attack = true;

                        if (!RechargeHelper.consumeCharge(stack, player, cost)) {
                            attack = false;
                        }

                        if (attack) {
                            e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 6);
                            if (player.world.isRemote)
                                new FXDispatcher().arcLightning(player.posX, player.posY - 1, player.posZ, e.posX, e.posY, e.posZ, 0.2F, 0.5F, 1, 1);

                            player.world.playSound(player.posX, player.posY, player.posZ, SoundsTC.jacobs, SoundCategory.PLAYERS, 1, player.world.rand.nextFloat() * 2, false);
                            player.world.playSound(e.posX, e.posY, e.posZ, SoundsTC.jacobs, SoundCategory.PLAYERS, 1, player.world.rand.nextFloat() * 2, false);
                        }

                        break f;
                    }
                }
            }
            if (stack.getItemDamage() == 4 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 10 == 0) {
                    if (stack.hasTagCompound() && stack.getTagCompound().hasKey("playerhealth")) {
                        if (RechargeHelper.consumeCharge(stack, player, cost)) {
                            if (player.getHealth() < stack.getTagCompound().getDouble("playerhealth")) {
                                player.setHealth((float) stack.getTagCompound().getDouble("playerhealth"));
                            } else {
                                stack.getTagCompound().setDouble("playerhealth", player.getHealth());
                            }
                        }
                    } else {
                        if (!stack.hasTagCompound())
                            stack.setTagCompound(new NBTTagCompound());

                        stack.getTagCompound().setDouble("playerhealth", player.getHealth());
                    }

                }
            }
            if (stack.getItemDamage() == 5 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 100 == 0) {
                    AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 4, player.posY - 4, player.posZ - 4, player.posX + 4, player.posY + 4, player.posZ + 4);
                    List<EntityPlayer> players = player.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
                    for (EntityPlayer p : players) {
                        if (RechargeHelper.consumeCharge(stack, player, cost)) {
                            Potion[] pots = new Potion[]{MobEffects.STRENGTH, MobEffects.SPEED, MobEffects.HASTE, MobEffects.NIGHT_VISION, MobEffects.WATER_BREATHING, MobEffects.REGENERATION};
                            for (Potion pp : pots) {
                                boolean hasEffect = p.getActivePotionEffect(pp) != null;
                                if (!hasEffect) {
                                    if (!p.world.isRemote)
                                        p.addPotionEffect(new PotionEffect(pp, 600, 0, true, false));
                                } else {
                                    PotionEffect effect = p.getActivePotionEffect(pp);
                                    try {
                                        Field dur = PotionEffect.class.getDeclaredFields()[2];
                                        dur.setAccessible(true);
                                        dur.setInt(effect, dur.getInt(effect) + 600);
                                        dur.setAccessible(false);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (stack.getItemDamage() == 6 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 20 == 0)
                    f:for (int i = 1; i <= 8; ++i) {
                        double px = lookVec.x * i + player.posX;
                        double py = lookVec.y * i + player.posY + player.getEyeHeight();
                        double pz = lookVec.z * i + player.posZ;
                        AxisAlignedBB aabb = new AxisAlignedBB(px - 0.5D, py - 0.5D, pz - 0.5D, px + 0.5D, py + 0.5D, pz + 0.5D);
                        List<EntityLivingBase> mobs = player.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                        for (EntityLivingBase e : mobs) {
                            if (e == player)
                                continue;

                            if (e.isDead)
                                continue;

                            boolean attack = true;

                            if (!RechargeHelper.consumeCharge(stack, player, cost)) {
                                attack = false;
                            }

                            if (attack) {
                                if (e instanceof EntityAnimal) {
                                    EntityAnimal.class.cast(e).attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 0);
                                }
                                if (e instanceof IMob) {
                                    AxisAlignedBB nearbyMobs = new AxisAlignedBB(e.posX - 16, e.posY - 6, e.posZ - 16, e.posX + 16, e.posY + 6, e.posZ + 16);
                                    List<EntityLivingBase> nMobs = player.world.getEntitiesWithinAABB(EntityLivingBase.class, nearbyMobs);

                                    if (nMobs.contains(player))
                                        nMobs.remove(player);
                                    if (nMobs.contains(e))
                                        nMobs.remove(e);

                                    if (!nMobs.isEmpty()) {
                                        EntityLivingBase base = nMobs.get(player.world.rand.nextInt(nMobs.size()));
                                        e.setRevengeTarget(base);
                                        e.setLastAttackedEntity(base);
                                    }
                                }

                                if (e instanceof EntityPlayer) {
                                    e.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0, true, false));
                                    e.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0, true, false));
                                }
                            }

                            break f;
                        }
                    }
            }
            if (stack.getItemDamage() == 7 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 10 == 0) {
                    if (RechargeHelper.consumeCharge(stack, player, cost)) {
                        for (int i = 0; i < 64; ++i) {
                            int dx = MathHelper.floor(player.posX + MathUtils.randomDouble(itemRand) * 6);
                            int dy = MathHelper.floor(player.posY);
                            int dz = MathHelper.floor(player.posZ + MathUtils.randomDouble(itemRand) * 6);
                            Block b = player.world.getBlockState(new BlockPos(dx, dy, dz)).getBlock();
                            if (!b.isAir(player.world.getBlockState(new BlockPos(dx, dy, dz)), player.world, player.getPosition()) && b instanceof IPlantable)
                                b.randomTick(player.world, new BlockPos(dx, dy, dz), player.world.getBlockState(new BlockPos(dx, dy, dz)), player.world.rand);
                        }
                    }
                }
            }
            if (stack.getItemDamage() == 8 && RechargeHelper.getCharge(stack) > 0) {
                if (count % 10 == 0) {
                    if (RechargeHelper.consumeCharge(stack, player, cost)) {
                        AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 16, player.posY - 4, player.posZ - 16, player.posX + 16, player.posY + 4, player.posZ + 16);
                        List<EntityAnimal> animals = player.world.getEntitiesWithinAABB(EntityAnimal.class, aabb);
                        List<EntityAnimal> toRemove = new ArrayList<EntityAnimal>();
                        for (EntityAnimal a : animals) {
                            if (a.isInLove())
                                toRemove.add(a);
                            if (a.isDead)
                                toRemove.add(a);
                        }
                        animals.removeAll(toRemove);

                        if (!animals.isEmpty())
                            animals.get(player.world.rand.nextInt(animals.size())).setInLove((EntityPlayer) player);
                    }
                }
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityLivingBase player, int count) {
        if (!itemstack.hasTagCompound())
            itemstack.setTagCompound(new NBTTagCompound());
        else
            itemstack.getTagCompound().removeTag("playerhealth");
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.NONE;
    }

    public int getMaxItemUseDuration(ItemStack itemstack) {
        return Integer.MAX_VALUE;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag par4) {
        list.add(I18n.translateToLocal("tb.ukulele.type." + types[Math.min(stack.getItemDamage(), types.length - 1)]));
        list.add("");
    }

    public void onUpdate(ItemStack is, World w, Entity e, int slot, boolean currentItem) {
        if (!w.isRemote && e instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e;

            if (player.ticksExisted % 5 == 0) {
                int cr = (int) AuraHelper.getVis(w, player.getPosition());
                if (cr > 0)
                    RechargeHelper.rechargeItem(player.world, is, new BlockPos(player), player, 1);
            }
        }
    }

    @Override
    public int getMaxCharge(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return 300;
    }

    @Override
    public EnumChargeDisplay showInHud(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return EnumChargeDisplay.NORMAL;
    }
}