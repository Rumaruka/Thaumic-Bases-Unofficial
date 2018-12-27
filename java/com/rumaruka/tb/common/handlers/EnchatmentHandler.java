package com.rumaruka.tb.common.handlers;

import com.rumaruka.tb.init.TBEnchant;
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
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.api.research.ResearchCategories;

public class EnchatmentHandler {

    @SubscribeEvent
    public void onMobDeath(LivingDeathEvent e) {
        if (e.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer) e.getSource().getTrueSource();
            ItemStack mainHund = attacker.getHeldItemMainhand();

            if (!mainHund.isEmpty() && mainHund.getItem() instanceof ItemSword) {


                if (EnchantmentHelper.getEnchantmentLevel(TBEnchant.elderKnowledge, mainHund) > 0) {
                    int enchLevel = EnchantmentHelper.getEnchantmentLevel(TBEnchant.elderKnowledge, mainHund);
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("GOLEMANCY"), MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ELDRITCH"), MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("INFUSION"), MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));
                    ThaumcraftApi.internalMethods.addKnowledge(attacker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ARTIFICE"), MathHelper.getInt(attacker.getRNG(), enchLevel, enchLevel + 1));

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
}
