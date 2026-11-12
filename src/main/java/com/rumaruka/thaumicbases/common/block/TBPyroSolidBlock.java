package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Crystallized pyrofluid. Harmful on contact and drops blaze powder when broken.
 */
public class TBPyroSolidBlock extends Block {
    public TBPyroSolidBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return MapCodec.unit(this);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide) {
            entity.igniteForSeconds(4.0F);
            entity.hurt(level.damageSources().inFire(), 2.0F);
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide) {
            entity.igniteForSeconds(2.0F);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            popResource(level, pos, new ItemStack(Items.BLAZE_POWDER, 20 + level.random.nextInt(2)));
        }
        // No default loot table: only blaze powder is dropped.
    }

    @Override
    public ItemStack getCloneItemStack(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(TBItems.PYRO_SOLID.get());
    }
}
