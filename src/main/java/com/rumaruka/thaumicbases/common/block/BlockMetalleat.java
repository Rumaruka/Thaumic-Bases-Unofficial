package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBItems;

import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//public class BlockMetalleat extends BlockBush implements IGrowable {
public class BlockMetalleat extends BlockCrops implements IGrowable { // AeXiaohu modified


    public int growthStages;
    public int growthDelay;
    public boolean requiresFarmland;
    public PropertyInteger AGE;
    public ItemStack dropItem;
    public ItemStack dropSeed;
    public BlockMetalleat(int stages, int delay,boolean isCrop ) {
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
    protected BlockStateContainer createBlockState() {
        if(AGE==null){
            AGE = PropertyInteger.create("age",0,7);
        }
        return new BlockStateContainer(this,AGE);
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE,Math.min(growthStages,meta));
    }
    protected boolean canPlaceBlockOn(Block b)
    {
        return b != null && (b == Blocks.GRASS || b == Blocks.DIRT || b instanceof BlockGrass || b instanceof BlockDirt);
    }
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w,pos, state, rnd);

        if (!w.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (w.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = state.getValue(AGE);

            if (i < getGrowthStages())
            {
                float f = getGrowthChance(this, w, pos);

                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(w, pos, state, rnd.nextInt((int)(25.0F / f) + 1) == 0))
                {
                    w.setBlockState(pos,state.withProperty(AGE,i+1));
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(w, pos, state, w.getBlockState(pos));
                }
            }
        }
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
    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        Block b = world.getBlockState(pos.down()).getBlock();
        return canPlaceBlockOn(b);
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
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
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
                ret.add(new ItemStack(TBItems.metalleatseed, 1));
            }
            if (metadata >= growthStages - 1) {
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != ItemStack.EMPTY){
                            for (int j = 0; j < 4 + fortune; ++j) {
                                if (world.rand.nextBoolean()) {
                                    ret.add(new ItemStack(Items.IRON_NUGGET,3));
                                    ret.add(new ItemStack(TBItems.metalleatseed, 1));
                                }
                            }

                        }

            }
        }
        return ret;
    }
}
