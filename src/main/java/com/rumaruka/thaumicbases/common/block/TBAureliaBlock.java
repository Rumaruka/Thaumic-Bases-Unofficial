package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Aurelia flower. Opens during a full moon at night and scatters petal blocks around itself.
 */
public class TBAureliaBlock extends BushBlock {
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);

    public TBAureliaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, Integer.valueOf(0)));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(STATE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean night = !level.isDay();
        boolean fullMoon = level.dimensionType().moonPhase(level.getDayTime()) == 0;
        boolean openSky = level.canSeeSky(pos.above());
        boolean open = state.getValue(STATE) == 1;
        boolean shouldOpen = night && fullMoon && openSky;

        if (shouldOpen && !open) {
            level.setBlockAndUpdate(pos, state.setValue(STATE, Integer.valueOf(1)));
        } else if (!shouldOpen && open) {
            level.setBlockAndUpdate(pos, state.setValue(STATE, Integer.valueOf(0)));
            open = false;
        }

        if (open && random.nextInt(3) == 0) {
            scatterPetal(level, pos, random);
        }
    }

    private void scatterPetal(ServerLevel level, BlockPos pos, RandomSource random) {
        int x = pos.getX() + random.nextInt(17) - 8;
        int z = pos.getZ() + random.nextInt(17) - 8;
        int y = pos.getY() + 2;
        int minY = level.getMinBuildHeight();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(x, y, z);

        while (cursor.getY() > minY && level.isEmptyBlock(cursor)) {
            cursor.move(Direction.DOWN);
        }
        if (cursor.getY() > minY) {
            BlockPos target = cursor.above();
            if (level.isEmptyBlock(target) && level.getBlockState(cursor).isFaceSturdy(level, cursor, Direction.UP)) {
                level.setBlockAndUpdate(target, TBBlocks.AURELIA_PETALS.get().defaultBlockState());
            }
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            popResource(level, pos, new ItemStack(TBItems.AURELIA.get()));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(TBItems.AURELIA.get());
    }
}
