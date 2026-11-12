package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.common.blockentity.TBCampfireBlockEntity;
import com.rumaruka.thaumicbases.init.TBBlockEntities;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Campfire port. Accepts logs and furnace fuel, then buffs nearby living entities while burning.
 */
public class TBCampfireBlock extends BaseEntityBlock {
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 2);

    public TBCampfireBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, Integer.valueOf(0)));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(STATE);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TBCampfireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, be) -> {
            if (be instanceof TBCampfireBlockEntity campfire) {
                campfire.tick();
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof TBCampfireBlockEntity campfire)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        boolean added = campfire.addLog(stack);
        if (!added) {
            added = campfire.addFuel(stack);
        }
        if (added) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(STATE) <= 1) {
            return;
        }
        double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) / 4.0D;
        double y = pos.getY();
        double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) / 4.0D;
        level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.04D, 0.0D);
        for (int i = 0; i < 10; i++) {
            level.addParticle(ParticleTypes.SMOKE,
                    pos.getX() + 0.5D + (random.nextDouble() - 0.5D) / 4.0D,
                    pos.getY() + 0.1D,
                    pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) / 4.0D,
                    0.0D, random.nextDouble() / 20.0D, 0.0D);
        }
        if (random.nextInt(20) == 0) {
            level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
                    0.1F, 0.1F, false);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            popResource(level, pos, new ItemStack(TBItems.CAMPFIRE.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
