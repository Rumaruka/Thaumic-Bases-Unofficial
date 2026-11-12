package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.init.TBFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class TBPyrobucketItem extends Item {
    public TBPyrobucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        }
        BlockHitResult bhr = (BlockHitResult) hit;
        BlockPos pos = bhr.getBlockPos().relative(bhr.getDirection());
        if (!level.mayInteract(player, pos)) {
            return InteractionResultHolder.fail(stack);
        }
        if (tryPlace(level, pos, player, stack, hand)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player != null && !level.mayInteract(player, pos)) {
            return InteractionResult.FAIL;
        }
        if (tryPlace(level, pos, player, stack, context.getHand())) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.FAIL;
    }

    private boolean tryPlace(Level level, BlockPos pos, Player player, ItemStack stack, InteractionHand hand) {
        BlockState state = level.getBlockState(pos);
        if (!state.canBeReplaced()) {
            return false;
        }
        if (!level.isClientSide) {
            // Place liquid pyrofluid; it will crystallize into pyrosolid after 10 seconds.
            level.setBlock(pos, TBFluids.PYRO_BLOCK.get().defaultBlockState(), 3);
            level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (player == null || !player.getAbilities().instabuild) {
                stack.shrink(1);
                ItemStack empty = new ItemStack(Items.BUCKET);
                if (player != null) {
                    if (stack.isEmpty()) {
                        player.setItemInHand(hand, empty);
                    } else if (!player.getInventory().add(empty)) {
                        player.drop(empty, false);
                    }
                }
            }
        }
        return true;
    }
}
