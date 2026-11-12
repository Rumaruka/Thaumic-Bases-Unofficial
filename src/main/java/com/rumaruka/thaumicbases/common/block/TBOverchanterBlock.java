package com.rumaruka.thaumicbases.common.block;

import com.leclowndu93150.thaumaturge.api.casters.ICaster;
import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.common.blockentity.TBOverchanterBlockEntity;
import com.rumaruka.thaumicbases.common.menu.OverchanterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * Overchanting table. Insert an enchanted item, supply Praecantatio essentia and 30 experience
 * levels, then sneak-use the table to raise one enchantment by one level.
 */
public class TBOverchanterBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public TBOverchanterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TBOverchanterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, be) -> {
            if (be instanceof TBOverchanterBlockEntity overchanter) {
                overchanter.tick();
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof TBOverchanterBlockEntity overchanter)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        // Right-click with wand/caster: start enchanting if item is present, otherwise open GUI.
        if (stack.getItem() instanceof ICaster) {
            if (!overchanter.getInventory().isEmpty()) {
                if (!level.isClientSide() && overchanter.startEnchanting()) {
                    level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 0.5F, 1.0F);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
            if (!level.isClientSide()) {
                player.openMenu(new net.minecraft.world.SimpleMenuProvider(
                        (id, inv, p) -> new com.rumaruka.thaumicbases.common.menu.OverchanterMenu(id, inv, overchanter),
                        Component.translatable("tile.overchanter.name")), pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        if (player.isShiftKeyDown() && !overchanter.getInventory().isEmpty()) {
            if (!level.isClientSide() && overchanter.startEnchanting()) {
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 0.5F, 1.0F);
                player.swing(hand);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        if (!level.isClientSide()) {
            player.openMenu(new net.minecraft.world.SimpleMenuProvider(
                    (id, inv, p) -> new OverchanterMenu(id, inv, overchanter),
                    Component.translatable("tile.overchanter.name")), pos);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof TBOverchanterBlockEntity overchanter)) {
            return InteractionResult.PASS;
        }
        if (player.isShiftKeyDown() && !overchanter.getInventory().isEmpty()) {
            if (!level.isClientSide && overchanter.startEnchanting()) {
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 0.5F, 1.0F);
                player.swing(InteractionHand.MAIN_HAND);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (!level.isClientSide) {
            player.openMenu(new net.minecraft.world.SimpleMenuProvider(
                    (id, inv, p) -> new OverchanterMenu(id, inv, overchanter),
                    Component.translatable("tile.overchanter.name")), pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TBOverchanterBlockEntity overchanter) {
                ItemStack stored = overchanter.getInventory();
                if (!stored.isEmpty()) {
                    Containers.dropItemStack(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stored);
                    overchanter.clearInventory();
                }
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
