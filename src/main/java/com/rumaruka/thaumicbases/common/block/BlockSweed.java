package com.rumaruka.thaumicbases.common.block;


import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
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

public class BlockSweed extends BlockBush implements IGrowable {

    public int growthStages;
    public int growthDelay;
    public boolean requiresFarmland;
    public  PropertyInteger AGE;
    public ItemStack dropItem;
    public ItemStack dropSeed;


    public  BlockSweed(int stages, int delay,boolean isCrop) {
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
    protected BlockStateContainer createBlockState() {
        if(AGE==null){
            AGE = PropertyInteger.create("age",0,4);
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
    protected boolean canPlaceBlockOn(Block b)
    {
        return b != null && (b == Blocks.GRASS || b == Blocks.DIRT || b instanceof BlockGrass || b instanceof BlockDirt);
    }


    protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
    {
        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)blockIn))
                {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j)))
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();

            if (flag2)
            {
                f /= 2.0F;
            }
        }

        return f;
    }

    private float calculateGrowth(IBlockState state, World w, BlockPos pos)
    {
        float f = 1.0F;
        Block block = w.getBlockState(pos.north()).getBlock();
        Block block1 = w.getBlockState(pos.south()).getBlock();
        Block block2 = w.getBlockState(pos.west()).getBlock();
        Block block3 = w.getBlockState(pos.east()).getBlock();
        Block block4 = w.getBlockState(pos.east().north()).getBlock();
        Block block5 = w.getBlockState(pos.east().south()).getBlock();
        Block block6 = w.getBlockState(pos.west().north()).getBlock();
        Block block7 = w.getBlockState(pos.west().south()).getBlock();
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int l = x - 1; l <= x + 1; ++l)
        {
            for (int i1 = z - 1; i1 <= z + 1; ++i1)
            {
                float f1 = 0.0F;

                if (w.getBlockState(new BlockPos(l, y - 1, i1)).getBlock().canSustainPlant(state, w, new BlockPos(l, y - 1, i1), EnumFacing.UP, this))
                {
                    f1 = 1.0F;

                    if (w.getBlockState(new BlockPos(l, y - 1, i1)).getBlock().isFertile(w, new BlockPos(l, y - 1, i1)))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != x || i1 != z)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
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


    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w,pos, state, rnd);
        if (w.getLight(pos.up()) >= 9)
        {
            int l = getMetaFromState(state);

            if (l < growthStages)
            {
                float f = this.calculateGrowth(state, w, pos);

                if (rnd.nextInt((int)(growthDelay / f) + 1) == 0)
                {
                    ++l;
                    if(l >= growthStages)
                        l = growthStages-1;
                    w.setBlockState(pos, this.getStateFromMeta(l));
                }
            }
        }

        if(getMetaFromState(state) == growthStages-1 && !w.isRemote)
        {
            EnumFacing dir = EnumFacing.getFront(2+w.rand.nextInt(4));

            int newX = pos.getX()+dir.getFrontOffsetX();
            int newZ = pos.getZ()+dir.getFrontOffsetZ();
            int newY = findSutableY(w,newX,pos.getY(),newZ);
            if(canPlaceBlockOn(w.getBlockState(new BlockPos(newX, newY-1, newZ)).getBlock()) && w.isAirBlock(new BlockPos(newX, newY, newZ))) //fix for the Sweeds destroying blocks
                w.setBlockState(new BlockPos(newX, newY, newZ), this.getDefaultState());
        }
    }

    public int findSutableY(World w, int x, int y, int z)
    {
        int bY = y;
        y += 1;
        while(!canPlaceBlockOn(w.getBlockState(new BlockPos(x, y, z)).getBlock()) && y > bY - 2)
            --y;

        return y+1;
    }

    public int getGrowthStages() {
        return 3;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
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
                for (int i = 0; i < 1 + fortune; ++i)
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != null)
                            ret.add(dropSeed.copy());

                for (int i = 0; i < 4 + fortune; ++i)
                    if (world.rand.nextBoolean())
                        ret.add(new ItemStack(Items.SUGAR));
                for (int i = 0; i < 1 + fortune; ++i)
                    if (world.rand.nextBoolean())
                        ret.add(new ItemStack(Items.REEDS));



            }
        }
        return ret;
    }



}