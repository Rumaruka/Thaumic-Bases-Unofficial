package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockLazullia extends BlockCrops implements IGrowable { // AeXiaohu modified

    public int growthStages;
    public int growthDelay;
    public boolean requiresFarmland;
    public PropertyInteger AGE;
    public ItemStack dropItem;
    public ItemStack dropSeed;

    public BlockLazullia(int stages, int delay, boolean isCrop) {
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
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    protected Item getCrop() {
        return TBItems.lapis_shard;
    }

    @Override
    protected Item getSeed() {
        return TBItems.lazulliaseed;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE, Math.min(growthStages, meta));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (AGE == null) {
            AGE = PropertyInteger.create("age", 0, 7);
        }
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public int tickRate(World worldIn) {
        return 1200 + worldIn.rand.nextInt(1200);
    }


    public int getGrowthStages() {
        return growthStages;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j) {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);

    }

    public int getMaxAge() {
        return growthStages;
    }

    protected int getAge(IBlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

    public IBlockState withAge(int age) {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (w instanceof World) {
            World world = (World) w;
            int metadata = state.getValue(AGE);
            if (metadata < growthStages) {
                ret.add(new ItemStack(TBItems.lazulliaseed, 1));
            } else if (metadata >= growthStages - 1) {
                if (world.rand.nextInt(growthStages) <= metadata)
                    if (dropSeed != ItemStack.EMPTY) {
                        for (int j = 0; j < 4 + fortune; ++j) {
                            if (world.rand.nextBoolean()) {
                                ret.add(new ItemStack(TBItems.lapis_shard, 7));
                                ret.add(new ItemStack(TBItems.lazulliaseed, 1));
                            }
                        }

                    }

            }
        }
        return ret;
    }
}
