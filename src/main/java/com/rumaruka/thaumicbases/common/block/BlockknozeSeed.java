package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.crafting.IngredientNBTTC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockknozeSeed extends BlockBush implements IGrowable {



    public int growthStages;
    public int growthDelay;
    public boolean requiresFarmland;
    public  PropertyInteger AGE;
    public ItemStack dropItem;
    public ItemStack dropSeed;

    public BlockknozeSeed(int stages, int delay, boolean isCrop) {
        super();
        growthStages = stages;
        growthDelay = delay;
        requiresFarmland = isCrop;

        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
    }


    public int getGrowthStages() {
        return growthStages;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        if(AGE==null){
            AGE = PropertyInteger.create("age",0,3);
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
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w,pos, state, rnd);

        if (!w.isAreaLoaded(pos, 1)) return;
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

    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return (Integer) state.getValue(AGE) != 3;
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

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return (state == TBBlocks.crystalblockmixed.getDefaultState()) || (state == TBBlocks.crystalblockair.getDefaultState()) || (state == TBBlocks.crystalblockfire.getDefaultState()) || (state == TBBlocks.crystalblockwater.getDefaultState()) || (state == TBBlocks.crystalblocktainted.getDefaultState()) || (state == TBBlocks.crystalblockorder.getDefaultState()) || (state == TBBlocks.crystalblockentropy.getDefaultState()) || (state == TBBlocks.crystalblockearth.getDefaultState());
    }


    protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.getInt(worldIn.rand, 1, 2);
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
            ret.add(new ItemStack(TBItems.knozeseed, 1));
            int metadata = ((Integer)state.getValue((IProperty)this.AGE)).intValue();
            if (metadata >= growthStages - 1) {
                ret.add(new ItemStack(TBItems.knowledge_shard));
                for (int i = 0; i < 1; ++i)
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != ItemStack.EMPTY){
                            for (int j = 0; j < 4 + fortune; ++j) {
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockfire) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.FIRE));
                                }
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockwater) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.WATER));
                                }
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockearth) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.EARTH));
                                }
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockair) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.AIR));
                                }
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockorder) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY));
                                }
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockentropy) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.ORDER));
                                }
                                if (world.rand.nextBoolean() && world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblocktainted) {
                                    ret.add(ThaumcraftApiHelper.makeCrystal(Aspect.FLUX));
                                }
                                if (world.getBlockState(pos.down(1)).getBlock() == TBBlocks.crystalblockmixed) {
                                    ret.add(new ItemStack(TBItems.knowledge_shard, 1));
                                }
                            }

                        }

            }
        }
        return ret;
    }
}
