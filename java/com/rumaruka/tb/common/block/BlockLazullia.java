package com.rumaruka.tb.common.block;

import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockLazullia extends BlockTBPlant {
    public BlockLazullia(int stages, int delay, boolean isCrop) {
        super(stages, delay, isCrop);
        this.setTickRandomly(true);
    }
    protected boolean canPlaceBlockOn(Block b)
    {
        return b != null && (b == Blocks.GRASS || b == Blocks.DIRT || b instanceof BlockGrass || b instanceof BlockDirt);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        Block b = world.getBlockState(pos.down()).getBlock();
        return canPlaceBlockOn(b);
    }

    @Override
    public int tickRate(World worldIn) {
        return 1200+worldIn.rand.nextInt(1200);
    }


    @Override
    public int getGrowthStages() {
        return 7;
    }



    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (w instanceof World) {
            World world = World.class.cast(w);
            int metadata = state.getValue(AGE);
            if (metadata >= growthStages - 1) {
                for (int i = 0; i < 1; ++i)
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != ItemStack.EMPTY){
                       for (int j = 0; j < TBConfig.firstDropItemInSeeeds + fortune; ++j) {
                           if (world.rand.nextBoolean()) {
                               ret.add(new ItemStack(Items.DYE, 1, 4));
                               ret.add(new ItemStack(TBItems.lazulliaseed, 1));
                           }
                       }

                        }

            }
        }
        return ret;
    }
}
