package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.core.TBCore;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public  class BlockTBBush extends Block implements IPlantable, IGrowable
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

    private ItemStack itemDrop = new ItemStack(TBItems.briar_seedbag);

    //@formatter:off
    public static final AxisAlignedBB SMALL_BUSH_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);
    public static final AxisAlignedBB MEDIUM_BUSH_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D);
    public static final AxisAlignedBB FULL_BUSH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    //@formatter:on

    public BlockTBBush()
    {
        super(Material.LEAVES);

         this.setTickRandomly(true);
        this.setHardness(0.3F);
        this.setSoundType(SoundType.PLANT);
    }

    public boolean isMaxAge(IBlockState state)
    {
        return state.getValue(AGE).intValue() >= 3;
    }

    public void setItemDrop(ItemStack itemIn)
    {
        this.itemDrop = itemIn;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        int age = state.getValue(AGE).intValue();

        if (age == 0)
        {
            return SMALL_BUSH_AABB;
        }
        if (age == 1)
        {
            return MEDIUM_BUSH_AABB;
        }
        if (age == 2)
        {
            return FULL_BUSH_AABB;
        }
        if (age == 3)
        {
            return FULL_BUSH_AABB;
        }

        return FULL_BUSH_AABB;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() != this)
        {
            return this.getDefaultState();
        }

        return state;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        int meta = state.getValue(AGE);
        if(meta<2) {
            worldIn.setBlockState(pos,state.withProperty(AGE,meta+1));
            return true;




        }
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {

    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { AGE });
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
        return TBCore.proxy.fancyGraphicsEnable() ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
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
        return meta > 3;
    }
    @Override
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd) {
        super.updateTick(w, pos, state, rnd);

        if(w.getBlockState(pos).getBlock()==this)
        {
            int meta = w.getBlockState(pos).getValue(AGE).intValue();
            if(isTopBlock(meta))
            {
                if(meta-3 < 3 && w.rand.nextInt(4) == 0)
                {
                    w.setBlockState(pos, this.getStateFromMeta(meta+1));
                }
            }else
            {
                if(meta < 3 && w.rand.nextInt(4) == 0)
                {
                    w.setBlockState(pos, this.getStateFromMeta(meta+1));
                }
            }
        }
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        int age = state.getValue(AGE).intValue();

        if (age == 0)
        {
            return false;
        }
        if (age == 1)
        {
            return false;
        }
        if (age == 2)
        {
            return true;
        }
        if (age == 3)
        {
            return true;
        }

        return false;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        if (worldIn.isRemote)
        {
            return;
        }

        int age = worldIn.getBlockState(pos).getValue(AGE).intValue();

        if (age == 3)
        {
            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(2)), 2);

            ItemStack itemDrop = new ItemStack(TBItems.briar_seedbag, 1 );
            EntityItem entityitem = new EntityItem(worldIn, playerIn.posX, playerIn.posY - 1.0D, playerIn.posZ, itemDrop);

            worldIn.spawnEntity(entityitem);

            if (!(playerIn instanceof FakePlayer))
            {
                entityitem.onCollideWithPlayer(playerIn);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        int age = state.getValue(AGE).intValue();

        if (age == 3)
        {
            if (worldIn.isRemote)
            {
                return true;
            }

            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(2)), 2);

            ItemStack itemDrop = new ItemStack(TBItems.briar_seedbag, 1 );
            EntityItem entityitem = new EntityItem(worldIn, playerIn.posX, playerIn.posY - 1.0D, playerIn.posZ, itemDrop);

            worldIn.spawnEntity(entityitem);

            if (!(playerIn instanceof FakePlayer))
            {
                entityitem.onCollideWithPlayer(playerIn);
            }

            return true;
        }

        return false;
    }
}