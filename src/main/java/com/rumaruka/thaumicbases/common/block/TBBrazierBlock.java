package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.common.blockentity.TBBrazierBlockEntity;
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
import net.minecraft.world.item.Items;
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
 * Brazier port. Burn time is normally fed by fire essentia; until the Thaumaturge API hook
 * is wired in it can be lit with flint and steel or a fire charge.
 */
public class TBBrazierBlock extends BaseEntityBlock {
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);

    public TBBrazierBlock(Properties properties) {
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
        return new TBBrazierBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, be) -> {
            if (be instanceof TBBrazierBlockEntity brazier) {
                brazier.tick();
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(STATE) == 0 && (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE))) {
            if (!level.isClientSide && level.getBlockEntity(pos) instanceof TBBrazierBlockEntity brazier) {
                brazier.burnTime = 1600;
                level.setBlockAndUpdate(pos, state.setValue(STATE, Integer.valueOf(1)));
                if (!player.getAbilities().instabuild) {
                    if (stack.is(Items.FLINT_AND_STEEL)) {
                        stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                    } else {
                        stack.shrink(1);
                    }
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(STATE) != 1) {
            return;
        }
        double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) / 4.0D;
        double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) / 4.0D;
        level.addParticle(ParticleTypes.FLAME, x, pos.getY() + 0.6D, z, 0.0D, 0.04D, 0.0D);
        for (int i = 0; i < 2; i++) {
            level.addParticle(ParticleTypes.SMOKE, x, pos.getY() + 0.7D, z,
                    0.0D, random.nextDouble() / 20.0D, 0.0D);
        }
        if (random.nextInt(40) == 0) {
            level.playLocalSound(x, pos.getY() + 0.5D, z, SoundEvents.FIRE_AMBIENT,
                    SoundSource.BLOCKS, 0.1F, 0.1F, false);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            popResource(level, pos, new ItemStack(TBItems.BRAZIER.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
