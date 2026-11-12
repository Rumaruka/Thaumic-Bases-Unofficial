package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

/**
 * Simple sapling that builds a small tree out of the supplied log/leaves blocks.
 */
public class TBSaplingBlock extends BushBlock implements BonemealableBlock {
    private final Supplier<? extends Block> log;
    private final Supplier<? extends Block> leaves;

    public TBSaplingBlock(Properties properties, Supplier<? extends Block> log, Supplier<? extends Block> leaves) {
        super(properties);
        this.log = log;
        this.leaves = leaves;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos.above(), 0) >= 9 && random.nextInt(7) == 0) {
            growTree(level, pos, random);
        }
    }

    private void growTree(ServerLevel level, BlockPos pos, RandomSource random) {
        int height = 4 + random.nextInt(3);
        BlockState logState = log.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y);

        if (!level.isEmptyBlock(pos.above(height - 1))) {
            return;
        }

        for (int y = 0; y < height; y++) {
            level.setBlockAndUpdate(pos.above(y), logState);
        }

        for (int dy = -2; dy <= 1; dy++) {
            int radius = dy <= -1 ? 2 : 1;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) == radius && Math.abs(dz) == radius && random.nextBoolean()) {
                        continue;
                    }
                    BlockPos leafPos = pos.above(height).offset(dx, dy, dz);
                    if (level.getBlockState(leafPos).canBeReplaced()) {
                        level.setBlockAndUpdate(leafPos, leaves.get().defaultBlockState());
                    }
                }
            }
        }
        level.setBlockAndUpdate(pos.above(height), leaves.get().defaultBlockState());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.45F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        growTree(level, pos, random);
    }
}
