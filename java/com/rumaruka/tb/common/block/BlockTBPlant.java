package com.rumaruka.tb.common.block;


import DummyCore.Utils.BlockStateMetadata;

import com.rumaruka.tb.init.TBItems;
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
        float f = 0.5F;


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
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack( dropSeed != ItemStack.EMPTY ? dropSeed.getItem() : Item.getItemFromBlock(this));
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = new Random(System.currentTimeMillis());
        int metadata = state.getValue(AGE);
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata >= growthStages-1)
        {
            for (int i = 0; i < (rand.nextDouble()*(fortune) > 0.75D ? 2 : 1); ++i) //Fix for the seed duplication
            {
                if (rand.nextInt(growthStages) <= metadata)
                {
                    if(dropSeed != null)
                    {
                        ret.add(dropSeed.copy());
                        if(dropSeed.getItem() instanceof ItemBlock) //Fix for the primal shroom duplication
                            break;
                    }
                }
            }

            for (int i = 0; i < 1 + rand.nextInt(fortune+1); ++i) //Change for the resource drop
            {
                if (rand.nextInt(growthStages) <= metadata)
                {
                    if(dropItem != null)
                        ret.add(dropItem.copy());
                }
            }
        }else
        if(dropSeed != null)
            ret.add(dropSeed.copy());

        return ret;
    }
    }