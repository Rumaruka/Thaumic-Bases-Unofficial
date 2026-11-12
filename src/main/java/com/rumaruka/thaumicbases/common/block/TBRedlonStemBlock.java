package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.Nullable;

/**
 * Redlon stem. Grows on farmland up to age 7 and then attempts to place a redstone block
 * on a neighbouring dirt block. Harvesting drops redlon seeds.
 */
public class TBRedlonStemBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 14.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D)
    };

    public TBRedlonStemBlock(Properties properties) {
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

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() instanceof net.minecraft.world.level.block.FarmBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(AGE)];
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 7;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) {
            return;
        }
        if (level.getRawBrightness(pos.above(), 0) < 9) {
            return;
        }
        float growth = getGrowthSpeed(level, pos);
        if (!CommonHooks.canCropGrow(level, pos, state, random.nextInt((int) (25.0F / growth) + 1) == 0)) {
            return;
        }
        int age = state.getValue(AGE);
        if (age < 7) {
            level.setBlock(pos, state.setValue(AGE, Integer.valueOf(age + 1)), 2);
        } else {
            growFruit(level, pos, random);
        }
        CommonHooks.fireCropGrowPost(level, pos, state);
    }

    private static float getGrowthSpeed(ServerLevel level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos below = pos.below();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                float local = 0.0F;
                BlockState soil = level.getBlockState(below.offset(dx, 0, dz));
                if (soil.getBlock() instanceof net.minecraft.world.level.block.FarmBlock) {
                    local = 1.0F;
                    if (soil.getValue(net.minecraft.world.level.block.FarmBlock.MOISTURE) > 0) {
                        local = 3.0F;
                    }
                }
                if (dx != 0 || dz != 0) {
                    local /= 4.0F;
                }
                speed += local;
            }
        }
        return speed;
    }

    private void growFruit(ServerLevel level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (level.getBlockState(pos.relative(direction)).is(Blocks.REDSTONE_BLOCK)) {
                return;
            }
        }
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos target = pos.relative(direction);
        BlockState soil = level.getBlockState(target.below());
        if (level.isEmptyBlock(target) && (soil.is(BlockTags.DIRT)
                || soil.getBlock() instanceof net.minecraft.world.level.block.FarmBlock)) {
            level.setBlockAndUpdate(target, Blocks.REDSTONE_BLOCK.defaultBlockState());
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < 7;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = Math.min(7, state.getValue(AGE) + 2 + random.nextInt(4));
        level.setBlock(pos, state.setValue(AGE, Integer.valueOf(age)), 2);
        if (age == 7) {
            growFruit(level, pos, random);
        }
    }

    @Override
    public void playerDestroy(Level level, net.minecraft.world.entity.player.Player player, BlockPos pos,
                              BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            int age = state.getValue(AGE);
            for (int roll = 0; roll < 3; roll++) {
                if (level.random.nextInt(15) <= age) {
                    popResource(level, pos, new ItemStack(TBItems.REDLON_SEED.get()));
                }
            }
        }
        // No default loot table: the stem only yields seeds.
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(TBItems.REDLON_SEED.get());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return state.canSurvive(level, pos)
                ? super.updateShape(state, direction, neighborState, level, pos, neighborPos)
                : Blocks.AIR.defaultBlockState();
    }
}
