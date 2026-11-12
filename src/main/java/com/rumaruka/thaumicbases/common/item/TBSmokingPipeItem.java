package com.rumaruka.thaumicbases.common.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TBSmokingPipeItem extends Item {
    private final boolean silverwood;

    public TBSmokingPipeItem(boolean silverwood, Properties properties) {
        super(properties);
        this.silverwood = silverwood;
    }

    public boolean isSilverwood() {
        return silverwood;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 64;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack tobacco = findTobacco(player);
        if (tobacco.isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingUseDuration) {
        if (!(living instanceof Player)) return;
        if (remainingUseDuration < 32) {
            spawnSmoke(level, (Player) living, 1);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        if (living instanceof Player player) {
            ItemStack tobacco = findTobacco(player);
            if (!tobacco.isEmpty() && tobacco.getItem() instanceof TBTobaccoItem tobaccoItem) {
                tobaccoItem.performEffect(player, tobacco, silverwood);
                if (!player.getAbilities().instabuild) {
                    tobacco.shrink(1);
                }
                spawnSmoke(level, player, 24);
            }
        }
        return stack;
    }

    private void spawnSmoke(Level level, Player player, int count) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Vec3 look = player.getLookAngle();
        double x = player.getX() + look.x / 5.0D;
        double y = player.getY() + player.getEyeHeight() + look.y / 5.0D;
        double z = player.getZ() + look.z / 5.0D;
        for (int j = 0; j < count; j++) {
            serverLevel.sendParticles(silverwood ? ParticleTypes.EXPLOSION : ParticleTypes.SMOKE,
                    x, y, z, 1, look.x / 20.0D, look.y / 20.0D, look.z / 20.0D, 0.02D);
        }
    }

    private static ItemStack findTobacco(Player player) {
        if (isTobacco(player.getOffhandItem())) return player.getOffhandItem();
        if (isTobacco(player.getMainHandItem())) return player.getMainHandItem();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (isTobacco(s)) return s;
        }
        return ItemStack.EMPTY;
    }

    private static boolean isTobacco(ItemStack stack) {
        return stack.getItem() instanceof TBTobaccoItem;
    }
}
