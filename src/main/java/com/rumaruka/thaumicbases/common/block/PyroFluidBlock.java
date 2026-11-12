package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class PyroFluidBlock extends LiquidBlock {
    public PyroFluidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid.get(), properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide) {
            // Crystallize into crystalline pyrofluid (pyrosolid) after 10 seconds.
            level.scheduleTick(pos, this, 200);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getFluidState().isSource()) {
            level.setBlock(pos, TBBlocks.PYRO_SOLID.get().defaultBlockState(), 3);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (!level.isClientSide) {
            entity.igniteForSeconds(6.0F);
            entity.hurt(level.damageSources().lava(), 4.0F);
        }
    }
}
