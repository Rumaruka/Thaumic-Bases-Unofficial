package com.rumaruka.tb.common.block;


import DummyCore.Utils.BlockStateMetadata;

import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BlockTBPlant  extends BlockBush implements IGrowable {


    public int growthStages;
    public int growthDelay;
    public boolean requiresFarmland;
    public  PropertyInteger AGE;
    public ItemStack dropItem;
    public ItemStack dropSeed;


    public BlockTBPlant(int stages, int delay, boolean isCrop) {
        super();
        growthStages = stages;
        growthDelay = delay;
        requiresFarmland = isCrop;

        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
    }
    public abstract int getGrowthStages();

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
                                    ret.add(new ItemStack(Items.STRING,3));
                                    ret.add(new ItemStack(TBItems.plaxseed, 1));
                                }
                            }

                        }

            }
        }
        return ret;
    }

}