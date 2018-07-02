package com.rumaruka.tb.common.block;

import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockVoidPlant extends BlockTBPlant {
    public BlockVoidPlant(int stages, int delay, boolean isCrop) {
        super(stages, delay, isCrop);
    }

    @Override
    public int getGrowthStages() {
        return 3;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        if(AGE==null){
            AGE = PropertyInteger.create("age",0,getGrowthStages());
        }
        return new BlockStateContainer(this,AGE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE,Math.min(growthStages,meta));
    }


    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int groM=state.getValue(AGE);

        if(groM<getGrowthStages()){
            worldIn.setBlockState(pos,state.withProperty(AGE,groM+1));
        }

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
                                    ret.add(new ItemStack(ItemsTC.voidSeed));
                                }
                            }

                        }

            }
        }
        return ret;
    }
}
