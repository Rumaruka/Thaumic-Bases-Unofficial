package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Rainbow cactus: harmless to touch, upper segments drop assorted dyes when broken.
 */
public class TBRainbowCactusBlock extends CactusBlock {
    private static final Item[] DYES = new Item[]{
            Items.RED_DYE, Items.GREEN_DYE, Items.PURPLE_DYE, Items.GREEN_DYE,
            Items.CYAN_DYE, Items.LIGHT_GRAY_DYE, Items.GREEN_DYE, Items.GRAY_DYE,
            Items.PINK_DYE, Items.LIME_DYE, Items.GREEN_DYE, Items.YELLOW_DYE,
            Items.LIGHT_BLUE_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.GREEN_DYE
    };

    public TBRainbowCactusBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<CactusBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide) {
            if (!level.getBlockState(pos.below()).is(this)) {
                popResource(level, pos, new ItemStack(TBItems.RAINBOW_CACTUS.get()));
            } else {
                int count = 3 + level.random.nextInt(8);
                for (int i = 0; i < count; i++) {
                    popResource(level, pos, new ItemStack(DYES[level.random.nextInt(DYES.length)]));
                }
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(TBItems.RAINBOW_CACTUS.get());
    }
}
