package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.api.ITobacco;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.lib.potions.PotionDeathGaze;

import java.util.ArrayList;
import java.util.Collection;

import static thaumcraft.api.capabilities.IPlayerWarp.EnumWarpType.*;

public class TBTobacco extends Item implements ITobacco {

    public TBTobacco() {

    }


    @Override
    public void performTobaccoEffect(EntityPlayer smoker, ItemStack tobbaco, boolean isSilverwood) {
        if (tobbaco.getItem() == TBItems.tobacco_pile) {
            if (isSilverwood && smoker.world.rand.nextFloat() <= 0.3f) {
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, -1, TEMPORARY);
            }
            if (!isSilverwood && smoker.world.rand.nextFloat() <= 0.1f) {
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, -1, TEMPORARY);
            }
        }
        if (tobbaco.getItem() == TBItems.tobacco_eldritch) {
            if (!smoker.world.isRemote) {
                smoker.addPotionEffect(new PotionEffect(PotionDeathGaze.instance, 2_000, 0, true, false));
                if (!isSilverwood) {
                    ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 3, TEMPORARY);
                }
            }
        }
        if (tobbaco.getItem() == TBItems.tobacco_fighting) {
            if (!smoker.world.isRemote) {
                smoker.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 8_000, 1, true, false));
                if (isSilverwood) {
                    smoker.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 8_000, 0, true, false));
                }
            }
        }

        if (tobbaco.getItem() == TBItems.tobacco_hunger) {
            smoker.getFoodStats().addStats(3, 3);

            if (!isSilverwood) {
                smoker.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 0, true, false));
            }
        }
        if (tobbaco.getItem() == TBItems.tobacco_knowledge) {

            int oProg = IPlayerKnowledge.EnumKnowledgeType.OBSERVATION.getProgression();
            int tProg = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();


            //code from thaumadditions:reconstructed
            ResearchCategory[] rc = ResearchCategories.researchCategories.values().toArray(new ResearchCategory[0]);

            if (!smoker.world.isRemote) {

                //code from thaumadditions:reconstructed
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc[smoker.getRNG().nextInt(rc.length)], MathHelper.getInt(smoker.getRNG(), oProg / 4, oProg / 3));
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[smoker.getRNG().nextInt(rc.length)], MathHelper.getInt(smoker.getRNG(), tProg / 8, tProg / 6));
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 5, TEMPORARY);
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 1, NORMAL);

            }

            for (int i = 0; i < (isSilverwood ? 20 : 10); ++i) {
                if (isSilverwood) {

                    //code from thaumadditions:reconstructed
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc[smoker.getRNG().nextInt(rc.length)], MathHelper.getInt(smoker.getRNG(), oProg / 4, oProg / 3));
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[smoker.getRNG().nextInt(rc.length)], MathHelper.getInt(smoker.getRNG(), tProg / 8, tProg / 6));
                    ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 0, TEMPORARY);
                    ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 0, NORMAL);
                    smoker.addExperience(20);

                }
            }

        }
        if (tobbaco.getItem() == TBItems.tobacco_mining) {
            if (!smoker.world.isRemote) {
                smoker.addPotionEffect(new PotionEffect(MobEffects.HASTE, 8_000, 1, true, false));
                if (isSilverwood) {
                    smoker.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 8_000, 0, true, false));
                }
            }
        }
        if (tobbaco.getItem() == TBItems.tobacco_sanity) {
            if (!smoker.world.isRemote) {

                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, -1, TEMPORARY);
                if (isSilverwood) ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, -1, NORMAL);
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, -1, TEMPORARY);
            }
        }
        if (tobbaco.getItem() == TBItems.tobacco_tainted) {
            if (!smoker.world.isRemote) {
                if (!isSilverwood) {
                    ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 1 + smoker.world.rand.nextInt(3), TEMPORARY);
                    if (smoker.world.rand.nextFloat() <= 0.4F) ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 1, NORMAL);
                } else {
                    ItemStack stk = smoker.getHeldItemMainhand();
                    if (stk != ItemStack.EMPTY) {
                        smoker.renderBrokenItemStack(stk);
                        smoker.inventory.setInventorySlotContents(smoker.inventory.currentItem, ItemStack.EMPTY);
                    }
                    return;
                }
            }

        }
        if (tobbaco.getItem() == TBItems.tobacco_wispy) {
            EntityWisp wisp = new EntityWisp(smoker.world);
            wisp.setPositionAndRotation(smoker.posX, smoker.posY + 1.0D, smoker.posZ, 0.0F, 0.0F);
            ArrayList<Aspect> aspects = new ArrayList<>();
            Collection<Aspect> pa = Aspect.aspects.values();
            for (Aspect aspect : pa)
                aspects.add(aspect);
            if (isSilverwood) {
                EntityWisp wisp1 = new EntityWisp(smoker.world);
                wisp1.setPositionAndRotation(smoker.posX, smoker.posY + 1.0D, smoker.posZ, 0.0F, 0.0F);
                if (!smoker.world.isRemote) {
                    wisp.setType(((Aspect)aspects.get(smoker.world.rand.nextInt(aspects.size()))).getTag());
                    smoker.world.spawnEntity((Entity)wisp1);
                    aspects.remove(Aspect.FLUX);
                } else {
                    smoker.world.spawnEntity((Entity)wisp1);
                }
            } else {
                smoker.world.spawnEntity((Entity) wisp);
            }

                }

            }


        }


