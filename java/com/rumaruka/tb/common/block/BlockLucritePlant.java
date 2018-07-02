package com.rumaruka.tb.common.block;

import com.rumaruka.tb.init.TBBlocks;
import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockLucritePlant extends BlockTBPlant {
    public BlockLucritePlant(int stages, int delay, boolean isCrop) {
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
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return super.canBlockStay(worldIn,pos,state) && worldIn.getBlockState(pos.down(2)).getBlock() == TBBlocks.dustblock;

    }



    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (w instanceof World) {
            World world = World.class.cast(w);
            int metadata = state.getValue(AGE);
            if (metadata >= growthStages - 1) {
                for (int i = 0; i < 1 + fortune; ++i)
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != null)
                            ret.add(dropSeed.copy());

                for (int i = 0; i < TBConfig.firstDropItemInSeeeds + fortune; ++i)
                    if (world.rand.nextBoolean())
                        ret.add(new ItemStack(Items.GOLD_NUGGET,3));
                for (int i = 0; i < TBConfig.extraDropItemInSeeeds + fortune; ++i)
                if (world.rand.nextBoolean())
                    ret.add(new ItemStack(Items.GOLDEN_CARROT,1));



            }
        }
        return ret;
    }
}
