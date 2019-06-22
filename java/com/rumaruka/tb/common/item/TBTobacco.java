package com.rumaruka.tb.common.item;

import com.rumaruka.tb.api.ITobacco;
import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.items.curios.ItemCurio;
import thaumcraft.common.lib.potions.PotionDeathGaze;

import java.util.ArrayList;
import java.util.Collection;

import static thaumcraft.api.capabilities.IPlayerWarp.EnumWarpType.*;

public class TBTobacco extends Item implements ITobacco {
    int oProg = IPlayerKnowledge.EnumKnowledgeType.OBSERVATION.getProgression();


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
            if (!smoker.world.isRemote) {

                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("GOLEMANCY"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ELDRITCH"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("INFUSION"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ARTIFICE"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ELDRITCH"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 5, IPlayerWarp.EnumWarpType.TEMPORARY);
                ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 1, IPlayerWarp.EnumWarpType.NORMAL);

            }

            for (int i = 0; i < (isSilverwood ? 20 : 10); ++i) {
                if (isSilverwood) {
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("GOLEMANCY"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("INFUSION"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ARTIFICE"), MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));
                    ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 0, IPlayerWarp.EnumWarpType.TEMPORARY);
                    ThaumcraftApi.internalMethods.addWarpToPlayer(smoker, 0, IPlayerWarp.EnumWarpType.NORMAL);
                    smoker.addExperience(20);
                    ResearchCategory[] rc = (ResearchCategory[]) ResearchCategories.researchCategories.values().toArray(new ResearchCategory[0]);
                    ThaumcraftApi.internalMethods.addKnowledge(smoker, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc[smoker.getRNG().nextInt(rc.length)], MathHelper.getInt(smoker.getRNG(), oProg / 3, oProg / 2));

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
            wisp.setPositionAndRotation(smoker.posX, smoker.posY, smoker.posZ, 0, 0);
            ArrayList<Aspect> aspects = new ArrayList<Aspect>();
            Collection<Aspect> pa = Aspect.aspects.values();
            for (Aspect aspect : pa) {
                aspects.add(aspect);
            }

            if (isSilverwood) {
                EntityWisp wisp1 = new EntityWisp(smoker.world);
                wisp.setPositionAndRotation(smoker.posX, smoker.posY, smoker.posZ, 0, 0);
                if (!smoker.world.isRemote) {
                    wisp.setType(aspects.get(smoker.world.rand.nextInt(aspects.size())).getTag());
                    smoker.world.spawnEntity(wisp1);
                    aspects.remove(Aspect.FLUX);

                }

                }

            }


        }


    }

