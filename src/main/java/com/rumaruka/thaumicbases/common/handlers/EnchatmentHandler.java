package com.rumaruka.thaumicbases.common.handlers;

import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBEnchant;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;

public class EnchatmentHandler {

    @SubscribeEvent
    public void onMobDeath(LivingDeathEvent e) {
        if (e.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer) e.getSource().getTrueSource();
            ItemStack mainHund = attacker.getHeldItemMainhand();

            if (!mainHund.isEmpty() && (mainHund.getItem() instanceof ItemSword)) {


                if (EnchantmentHelper.getEnchantmentLevel(TBEnchant.elderKnowledge, mainHund) > 0) {
                    int enchLevel = 0;
                    if(mainHund.getItem() instanceof ItemSword)
                        enchLevel = EnchantmentHelper.getEnchantmentLevel(TBEnchant.elderKnowledge, mainHund);
                    ResearchCategory[] rc = ResearchCategories.researchCategories.values().toArray(new ResearchCategory[0]);
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc[attacker.getRNG().nextInt(rc.length)], MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[attacker.getRNG().nextInt(rc.length)], MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));

                }

            }
        }


    }


    @SubscribeEvent
    public void onModDamage(LivingHurtEvent e) {
        if (e.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer) e.getSource().getTrueSource();
            EntityLivingBase mob = e.getEntityLiving();
            ItemStack mainHund = attacker.getHeldItemMainhand();

            if (!mainHund.isEmpty() && mainHund.getItem() instanceof ItemSword) {
                if (mob instanceof EntityEnderman || mob instanceof IEldritchMob || mob instanceof EntityDragon) {

                    if (EnchantmentHelper.getEnchantmentLevel(TBEnchant.eldritchBane, mainHund) > 0) {
                        float enchLevel = EnchantmentHelper.getEnchantmentLevel(TBEnchant.eldritchBane, mainHund);
                        e.setAmount(enchLevel * 6.2f);

                    }
                }
                if (!(mob instanceof ITaintedMob)) {
                    if (EnchantmentHelper.getEnchantmentLevel(TBEnchant.tainted, mainHund) > 0) {
                        int enchLevel = EnchantmentHelper.getEnchantmentLevel(TBEnchant.tainted, mainHund);

                        e.setAmount(enchLevel * 3f);
                        if (attacker.world.rand.nextInt(Math.max(1, 4 - enchLevel)) == 0) {
                            mob.addPotionEffect(new PotionEffect(PotionFluxTaint.instance, 200, enchLevel - 1, true, false));
                        }

                    }

                }
            }


        }
    }

    @SubscribeEvent
    public void registerAspects(AspectRegistryEvent event) {
        AspectEventProxy proxy = event.register;
        proxy.registerComplexObjectTag(new ItemStack(TBItems.aureliapetal),new AspectList().add(Aspect.AURA,10));
        proxy.registerComplexObjectTag(new ItemStack(TBBlocks.goldenleaves),new AspectList().add(Aspect.PLANT,5));
        proxy.registerComplexObjectTag(new ItemStack(TBBlocks.enderleaves),new AspectList().add(Aspect.PLANT,5));
        proxy.registerComplexObjectTag(new ItemStack(TBBlocks.netherleaves),new AspectList().add(Aspect.PLANT,5));
        proxy.registerComplexObjectTag(new ItemStack(TBBlocks.peaceleaves),new AspectList().add(Aspect.PLANT,5));

        proxy.registerComplexObjectTag(new ItemStack(TBItems.bloodycloth), new AspectList().add(Aspect.BEAST,26).add(Aspect.CRAFT,6));
        proxy.registerComplexObjectTag(new ItemStack(TBItems.briar_seedbag), new AspectList().add(Aspect.PLANT,5).add(Aspect.LIFE,5));
        proxy.registerComplexObjectTag(new ItemStack(TBItems.tobacco_pile), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAN, 3).add(Aspect.ENTROPY, 1));
        proxy.registerComplexObjectTag(new ItemStack(TBItems.tobacco_leaves), new AspectList().add(Aspect.PLANT, 5).add(Aspect.MAN, 5));
        proxy.registerComplexObjectTag(new ItemStack(TBItems.knowledge_shard), new AspectList().add(Aspect.MIND, 15));
    }
}
