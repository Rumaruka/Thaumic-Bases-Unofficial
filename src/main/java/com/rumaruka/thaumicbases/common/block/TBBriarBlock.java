package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Two-block-tall briar bush. Bottom ages 0-7, top ages 8-15; a fully grown top drops seed bags.
 */
public class TBBriarBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public TBBriarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    private static boolean isTop(BlockState state) {
        return state.getValue(AGE) >= 8;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isTop(state)) {
            return level.getBlockState(pos.below()).is(this);
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (isTop(state)) {
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }
        return level.getBlockState(pos.above()).is(this)
                ? super.updateShape(state, direction, neighborState, level, pos, neighborPos)
                : Blocks.AIR.defaultBlockState();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (level.isEmptyBlock(pos.above())) {
            level.setBlock(pos.above(), state.setValue(AGE, Integer.valueOf(8)), 2);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !isTop(state) && state.getValue(AGE) < 7;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1) || isTop(state)) {
            return;
        }
        int age = state.getValue(AGE);
        if (age >= 7 || random.nextInt(4) != 0) {
            return;
        }
        BlockPos abovePos = pos.above();
        BlockState above = level.getBlockState(abovePos);
        if (above.is(this) || above.canBeReplaced()) {
            level.setBlock(pos, state.setValue(AGE, Integer.valueOf(age + 1)), 2);
            level.setBlock(abovePos, state.setValue(AGE, Integer.valueOf(age + 9)), 2);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !isTop(state) && state.getValue(AGE) < 7;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age >= 7) {
            return;
        }
        BlockPos abovePos = pos.above();
        BlockState above = level.getBlockState(abovePos);
        if (above.is(this) || above.canBeReplaced()) {
            level.setBlock(pos, state.setValue(AGE, Integer.valueOf(age + 1)), 2);
            level.setBlock(abovePos, state.setValue(AGE, Integer.valueOf(age + 9)), 2);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (isTop(state)) {
                BlockPos below = pos.below();
                if (level.getBlockState(below).is(this)) {
                    level.destroyBlock(below, false);
                }
            } else {
                BlockPos above = pos.above();
                if (level.getBlockState(above).is(this)) {
                    level.destroyBlock(above, false);
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              net.minecraft.world.level.block.entity.BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            if (!isTop(state)) {
                popResource(level, pos, new ItemStack(TBItems.BRIAR.get()));
            } else if (state.getValue(AGE) >= 15) {
                popResource(level, pos, new ItemStack(TBItems.BRIAR_SEEDBAG.get(),
                        1 + level.random.nextInt(4)));
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(TBItems.BRIAR.get());
    }

    @SuppressWarnings("unused")
    private static boolean canGrowOn(BlockState below) {
        return below.is(BlockTags.DIRT) || below.getBlock() instanceof net.minecraft.world.level.block.FarmBlock;
    }
}
