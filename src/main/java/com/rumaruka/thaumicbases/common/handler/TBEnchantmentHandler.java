package com.rumaruka.thaumicbases.common.handler;

import com.leclowndu93150.thaumaturge.api.capability.KnowledgeType;
import com.leclowndu93150.thaumaturge.api.research.TTResearchCategories;
import com.leclowndu93150.thaumaturge.api.research.scan.ScanningManager;
import com.rumaruka.thaumicbases.ThaumicBases;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

@EventBusSubscriber(modid = ThaumicBases.MODID)
public class TBEnchantmentHandler {
    private static final ResourceKey<Enchantment> ELDER_KNOWLEDGE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "elder_knowledge"));
    private static final ResourceKey<Enchantment> ELDRITCH_BANE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "eldritch_bane"));
    private static final ResourceKey<Enchantment> TAINTED = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "tainted"));

    private static final List<ResourceLocation> KNOWLEDGE_CATEGORIES = List.of(
            TTResearchCategories.BASICS.location(),
            TTResearchCategories.AUROMANCY.location(),
            TTResearchCategories.ALCHEMY.location(),
            TTResearchCategories.ARTIFICE.location(),
            TTResearchCategories.INFUSION.location(),
            TTResearchCategories.GOLEMANCY.location(),
            TTResearchCategories.ELDRITCH.location()
    );

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }
        ItemStack weapon = player.getMainHandItem();
        int level = getLevel(player, weapon, ELDER_KNOWLEDGE);
        if (level > 0) {
            // Adapted from 1.12: grant Thaumaturge observation knowledge on kill.
            ResourceLocation category = KNOWLEDGE_CATEGORIES.get(player.getRandom().nextInt(KNOWLEDGE_CATEGORIES.size()));
            int amount = Mth.nextInt(player.getRandom(), level, level + 1);
            ScanningManager.addKnowledge(player, KnowledgeType.OBSERVATION, category, amount);
            ScanningManager.addKnowledge(player, KnowledgeType.THEORY, category, amount);

        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }
        LivingEntity target = event.getEntity();
        ItemStack weapon = player.getMainHandItem();

        int bane = getLevel(player, weapon, ELDRITCH_BANE);
        if (bane > 0 && (target instanceof EnderMan || target instanceof EnderDragon || target.getType().toString().contains("eldritch"))) {
            event.setAmount(event.getAmount() + bane * 2.5F);
        }

        int tainted = getLevel(player, weapon, TAINTED);
        if (tainted > 0) {
            event.setAmount(event.getAmount() + tainted * 1.5F);
            if (target.getRandom().nextInt(Math.max(1, 4 - tainted)) == 0) {
                target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, Math.max(0, tainted - 1), true, false));
            }
        }
    }

    private static int getLevel(Player player, ItemStack stack, ResourceKey<Enchantment> key) {
        if (stack.isEmpty()) return 0;
        Holder<Enchantment> holder = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(key);
        return EnchantmentHelper.getTagEnchantmentLevel(holder, stack);
    }
}
