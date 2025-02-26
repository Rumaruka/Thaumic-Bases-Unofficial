package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//public class BlockTobacco extends BlockBush implements IGrowable {
public class BlockTobacco extends BlockCrops implements IGrowable { // AeXiaohu modified 修复烟草为作物类型，使傀儡能正确识别
    public int growthStages;
    public int growthDelay;
    public boolean requiresFarmland;
    public PropertyInteger AGE;
    public ItemStack dropItem;
    public ItemStack dropSeed;
    public BlockTobacco(int stages, int delay,boolean isCrop){
        super();
        growthStages = stages;
        growthDelay = delay;
        requiresFarmland = isCrop;

        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();

}

    @Override
    protected Item getSeed()
    {
        return TBItems.tobaccoseed;
    }

    @Override
    protected Item getCrop()
    {
        return TBItems.tobacco_leaves;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        if(AGE==null){
            AGE = PropertyInteger.create("age",0,7);
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
    public int tickRate(World worldIn) {
        return 1200+worldIn.rand.nextInt(1200);
    }

    public int getGrowthStages() {
        return growthStages;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return !this.isMaxAge(state);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j)
        {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);

    }
    protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.getInt(worldIn.rand, 2, 5);
    }

    public int getMaxAge()
    {
        return growthStages;
    }

    protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (w instanceof World) {
            World world = World.class.cast(w);
            int metadata = state.getValue(AGE);
            if (metadata < growthStages) {
                ret.add(new ItemStack(TBItems.tobaccoseed, 1));
            }
            if (metadata >= growthStages - 1) {
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != ItemStack.EMPTY){
                            ret.add(new ItemStack(TBItems.tobacco_leaves));
                            ret.add(new ItemStack(TBItems.tobaccoseed));

                        }

                for (int i = 0; i < 2 + fortune; ++i)
                    if (world.rand.nextBoolean()) {
                        ret.add(new ItemStack(TBItems.tobacco_leaves));
                        ret.add(new ItemStack(TBItems.tobaccoseed));
                    }
            }
        }
        return ret;
    }
}
