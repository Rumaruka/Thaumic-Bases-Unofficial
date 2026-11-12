package com.rumaruka.thaumicbases.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.List;
import java.util.function.Supplier;


public class TBCropBlock extends CropBlock {
    private final Supplier<? extends Item> seed;
    private final int maxAge;
    private final int growthDelay;
    private final boolean requiresFarmland;
    private final List<DropEntry> matureDrops;

    public TBCropBlock(Properties properties, Supplier<? extends Item> seed, int maxAge, int growthDelay,
                       boolean requiresFarmland, List<DropEntry> matureDrops) {
        super(properties);
        this.seed = seed;
        this.maxAge = maxAge;
        this.growthDelay = growthDelay;
        this.requiresFarmland = requiresFarmland;
        this.matureDrops = matureDrops;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    protected Item getBaseSeedId() {
        return seed.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        if (requiresFarmland) {
            return state.getBlock() instanceof net.minecraft.world.level.block.FarmBlock;
        }
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof net.minecraft.world.level.block.FarmBlock;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !isMaxAge(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) {
            return;
        }
        if (level.getRawBrightness(pos, 0) < 9) {
            return;
        }
        int age = getAge(state);
        if (age >= getMaxAge()) {
            return;
        }
        float growthSpeed = getGrowthSpeed(state, level, pos);
        boolean shouldGrow = random.nextInt((int) (growthDelay / growthSpeed) + 1) == 0;
        if (CommonHooks.canCropGrow(level, pos, state, shouldGrow)) {
            level.setBlock(pos, getStateForAge(age + 1), 2);
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }

    @Override
    public void playerDestroy(net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player,
                              BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity blockEntity,
                              ItemStack tool) {
        if (!level.isClientSide) {
            int age = getAge(state);
            RandomSource random = level.random;

            // Immature crops return the seed.
            if (age < getMaxAge()) {
                popResource(level, pos, new ItemStack(seed.get()));
            } else {
                for (DropEntry drop : matureDrops) {
                    for (int roll = 0; roll < drop.rolls(); roll++) {
                        if (random.nextFloat() < drop.chance()) {
                            int count = drop.minCount() + (drop.maxCount() > drop.minCount()
                                    ? random.nextInt(drop.maxCount() - drop.minCount() + 1)
                                    : 0);
                            popResource(level, pos, new ItemStack(drop.item().get(), count));
                        }
                    }
                }
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public ItemStack getCloneItemStack(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(seed.get());
    }

    @Override
    protected net.minecraft.world.level.block.state.properties.IntegerProperty getAgeProperty() {
        return BlockStateProperties.AGE_7;
    }

    public record DropEntry(Supplier<? extends Item> item, int minCount, int maxCount, float chance, int rolls) {
    }
}
