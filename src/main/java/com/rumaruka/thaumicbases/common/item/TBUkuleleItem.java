package com.rumaruka.thaumicbases.common.item;

import com.leclowndu93150.thaumaturge.api.items.ChargeDisplay;
import com.leclowndu93150.thaumaturge.api.items.ChargeProfile;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import com.rumaruka.thaumicbases.init.TBSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TBUkuleleItem extends Item {
    public static final int TYPE_COUNT = 9;
    private final int type;

    public TBUkuleleItem(int type, Properties properties) {
        super(properties.component(TTDataComponents.CHARGE.get(), 0)
                .component(TTDataComponents.RECHARGEABLE.get(), new ChargeProfile(300, ChargeDisplay.ALWAYS)));
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseDuration) {
        if (!(living instanceof Player player)) return;
        int used = getUseDuration(stack, living) - remainingUseDuration;
        if (used != 1 && used % 40 != 0) return;

        Integer charge = stack.get(TTDataComponents.CHARGE.get());
        if (charge == null || charge <= 0) {
            // Rechargeable profile handles refill in Thaumaturge systems; stop active effects when empty.
            if (used == 1 || used % 80 == 0) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), getSound(), SoundSource.PLAYERS, 0.4F, 0.9F);
            }
            return;
        }

        if (!level.isClientSide) {
            stack.set(TTDataComponents.CHARGE.get(), Math.max(0, charge - 1));
            applyEffect((ServerLevel) level, player);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), getSound(), SoundSource.PLAYERS, 0.8F, 1.0F);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (level.isClientSide) {
            try {
                Class.forName("com.rumaruka.thaumicbases.client.UkuleleSoundHelper")
                        .getMethod("stopUkuleleSounds")
                        .invoke(null);
            } catch (Throwable ignored) {
            }
        }
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    private void applyEffect(ServerLevel level, Player player) {
        AABB area = player.getBoundingBox().inflate(8.0D);
        switch (type) {
            case 1 -> { // knowledge
                player.giveExperiencePoints(1);
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 60, 0, true, true));
            }
            case 2 -> { // calming
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, true, true));
                for (Monster mob : level.getEntitiesOfClass(Monster.class, area)) {
                    mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, true, true));
                }
            }
            case 3 -> { // electric
                for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player && e.isAlive())) {
                    if (player.getRandom().nextInt(3) == 0) {
                        target.hurt(level.damageSources().lightningBolt(), 2.0F);
                        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY() + 1.0D, target.getZ(), 8, 0.2D, 0.4D, 0.2D, 0.02D);
                    }
                }
            }
            case 4 -> { // resistance
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 0, true, true));
                for (Player other : level.getEntitiesOfClass(Player.class, area)) {
                    other.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0, true, true));
                }
            }
            case 5 -> { // buffing
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, 0, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 80, 0, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 80, 0, true, true));
            }
            case 6 -> { // confusion
                for (Monster mob : level.getEntitiesOfClass(Monster.class, area)) {
                    mob.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, true, true));
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, true, true));
                }
            }
            case 7 -> { // growth
                BlockPos base = player.blockPosition();
                for (int i = 0; i < 3; i++) {
                    BlockPos pos = base.offset(player.getRandom().nextInt(7) - 3, player.getRandom().nextInt(3) - 1, player.getRandom().nextInt(7) - 3);
                    BlockState state = level.getBlockState(pos);
                    if (state.getBlock() instanceof BonemealableBlock bonemealable && bonemealable.isValidBonemealTarget(level, pos, state)) {
                        if (bonemealable.isBonemealSuccess(level, player.getRandom(), pos, state)) {
                            bonemealable.performBonemeal(level, player.getRandom(), pos, state);
                            level.levelEvent(1505, pos, 0);
                            break;
                        }
                    }
                }
            }
            case 8 -> { // love
                for (Animal animal : level.getEntitiesOfClass(Animal.class, area)) {
                    if (animal.getAge() == 0 && !animal.isInLove()) {
                        animal.setInLove(player);
                    }
                }
                level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 1.0D, player.getZ(), 4, 0.5D, 0.5D, 0.5D, 0.0D);
            }
            default -> { // simple
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, true, true));
            }
        }
    }

    private SoundEvent getSound() {
        return switch (type) {
            case 1 -> TBSounds.GUITAR_KNOWLEDGE.get();
            case 2 -> TBSounds.GUITAR_CALMING.get();
            case 3 -> TBSounds.GUITAR_ELECTRIC.get();
            case 4 -> TBSounds.GUITAR_RESISTANCE.get();
            case 5 -> TBSounds.GUITAR_BUFFING.get();
            case 6 -> TBSounds.GUITAR_CONFUSION.get();
            case 7 -> TBSounds.GUITAR_GROWTH.get();
            case 8 -> TBSounds.GUITAR_LOVE.get();
            default -> TBSounds.GUITAR_SIMPLE.get();
        };
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        String key = typeKey(type);
        tooltip.add(Component.translatable("tooltip.thaumicbases.ukulele." + key).withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tb.ukulele.type." + key).withStyle(net.minecraft.ChatFormatting.DARK_AQUA, net.minecraft.ChatFormatting.ITALIC));
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.ukulele.name." + type);
    }

    private static String typeKey(int type) {
        return switch (type) {
            case 1 -> "knowledge";
            case 2 -> "calming";
            case 3 -> "electric";
            case 4 -> "resistance";
            case 5 -> "buffing";
            case 6 -> "confusion";
            case 7 -> "growth";
            case 8 -> "love";
            default -> "simple";
        };
    }
}
