package com.rumaruka.thaumicbases.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

/**
 * Simplified 1.21 leaves for the custom Thaumic Bases trees.
 * Drops a sapling (and optionally a fruit) without requiring a loot table.
 */
public class TBLeavesBlock extends Block {
    private final Supplier<? extends Block> sapling;
    private final Supplier<? extends net.minecraft.world.item.Item> fruit;
    private final float fruitChance;

    public TBLeavesBlock(Properties properties, Supplier<? extends Block> sapling,
                         Supplier<? extends net.minecraft.world.item.Item> fruit, float fruitChance) {
        super(properties);
        this.sapling = sapling;
        this.fruit = fruit;
        this.fruitChance = fruitChance;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            RandomSource random = level.random;
            if (random.nextFloat() < 0.05F) {
                popResource(level, pos, new ItemStack(sapling.get()));
            }
            if (fruit != null && random.nextFloat() < fruitChance) {
                popResource(level, pos, new ItemStack(fruit.get()));
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
