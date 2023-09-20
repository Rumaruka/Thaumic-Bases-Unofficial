package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.core.TBCore;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.minecraft.util.BlockRenderLayer.CUTOUT_MIPPED;

public  class BlockTBBush extends BlockBush implements IPlantable, IGrowable
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
    public int growthStages = 8;
    public int growthDelay = 4;

    public BlockTBBush()
    {
        super(Material.LEAVES);

        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
    }

    public enum EnumBlockHalf implements IStringSerializable
    {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }
    }

    public boolean canPlaceBlockAt(World w, BlockPos pos)
    {
        return super.canPlaceBlockAt(w, pos) && w.isAirBlock(pos.up());
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), this.getStateFromMeta(8));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        int meta = getMetaFromState(state);
        if(isTopBlock(meta))
        {
            if(worldIn.getBlockState(pos.down()).getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
        }else
        {
            if(worldIn.getBlockState(pos.up()).getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
            if(!canBlockStay(worldIn, pos, state))
            {
                dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public boolean canBlockStay(World w, BlockPos pos, IBlockState state)
    {
        return !w.isAirBlock(pos) && (w.getBlockState(pos.down()).getBlock().isReplaceableOreGen(w.getBlockState(pos.down()), w, pos.down(), BlockStateMatcher.forBlock(Blocks.GRASS)) || w.getBlockState(pos.down()).getBlock().isReplaceableOreGen(w.getBlockState(pos.down()), w, pos.down(), BlockStateMatcher.forBlock(Blocks.DIRT)) || w.getBlockState(pos.down()).getBlock().canSustainPlant(w.getBlockState(pos.down()), w, pos.down(), EnumFacing.UP, this));
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return worldIn.getLight(pos.up()) >= 9;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return getMetaFromState(state) % 8 < growthStages-1;
    }

    @Override
    public void grow(World w, Random rand, BlockPos pos, IBlockState state) {
        if(w.getBlockState(pos).getBlock()==this)
        {
            int meta = getMetaFromState(state);
            if(isTopBlock(meta))
            {
                if(meta-8 < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
                {
                    w.setBlockState(pos, this.getStateFromMeta(meta+1));
                    w.setBlockState(pos.down(), this.getStateFromMeta(meta-7));
                }
            }else
            {
                if(meta < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
                {
                    w.setBlockState(pos, this.getStateFromMeta(meta+1));
                    w.setBlockState(pos.up(), this.getStateFromMeta(meta+9));
                }
            }
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return CUTOUT_MIPPED;
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return !TBCore.proxy.fancyGraphicsEnable() && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
    public boolean isTopBlock(int meta)
    {
        return meta > 7;
    }

    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w, pos, state, rnd);

        if(w.getBlockState(pos).getBlock()==this)
        {
            int meta = getMetaFromState(state);
            if(isTopBlock(meta))
            {
                if(meta-8 < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
                {
                    w.setBlockState(pos, this.getStateFromMeta(meta+1));
                    w.setBlockState(pos.down(), this.getStateFromMeta(meta-7));
                }
            }else
            {
                if(meta < growthStages-1 && w.rand.nextInt(growthDelay) == 0)
                {
                    w.setBlockState(pos, this.getStateFromMeta(meta+1));
                    w.setBlockState(pos.up(), this.getStateFromMeta(meta+9));
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune)
    {
        if(w instanceof World)
        {
            World world = World.class.cast(w);
            int metadata = getMetaFromState(state);
            ArrayList<ItemStack> ret = new ArrayList<>();

            if(!isTopBlock(metadata))
            {
                ret.add(new ItemStack(this,1,0));
                return ret;
            }

            metadata -= 8;
            if(metadata >= this.growthStages - 1)
            {
                ret.add(new ItemStack(TBItems.briar_seedbag,1+world.rand.nextInt(4),0));
                return ret;
            }
            return ret;
        }
        return super.getDrops(w, pos, state, fortune);
    }
}