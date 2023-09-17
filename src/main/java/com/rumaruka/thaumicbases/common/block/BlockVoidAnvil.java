package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.core.TBCore;
import com.rumaruka.thaumicbases.init.TBGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.client.fx.FXDispatcher;

import java.util.Random;

public class BlockVoidAnvil extends Block implements IInfusionStabiliser
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);
	protected static final Logger LOGGER = LogManager.getLogger();

	public BlockVoidAnvil()
	{
		super(Material.ANVIL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DAMAGE, Integer.valueOf(0)));
		this.setLightOpacity(0);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setSoundType(SoundType.ANVIL);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		int meta = getMetaFromState(state);
		if(meta > 3)
			worldIn.setBlockState(pos, this.getStateFromMeta(meta-4));
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random r)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		FXDispatcher.INSTANCE.sparkle(x+r.nextFloat(), y+r.nextFloat(), z+r.nextFloat(), 2, 5, -0.1F);
	}

	@Override
	public boolean canStabaliseInfusion(World w, BlockPos pos)
	{
		return true;
	}
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();

		try
		{
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, enumfacing).withProperty(DAMAGE, meta >> 2);
		}
		catch (IllegalArgumentException var11)
		{
			if (!worldIn.isRemote)
			{
				LOGGER.warn(String.format("Invalid damage property for anvil at %s. Found %d, must be in [0, 1, 2]", pos, meta >> 2));

				if (placer instanceof EntityPlayer)
				{
					placer.sendMessage(new TextComponentTranslation("Invalid damage property. Please pick in [0, 1, 2]"));
				}
			}

			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, placer).withProperty(FACING, enumfacing).withProperty(DAMAGE, 0);
		}
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			playerIn.openGui(TBCore.instance, TBGuiHandler.VOID_ANVIL, worldIn, pos.getX(),pos.getY(),pos.getZ());
		}

		return true;
	}

	public int damageDropped(IBlockState state)
	{
		return state.getValue(DAMAGE);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing enumfacing = state.getValue(FACING);
		return enumfacing.getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
	}

	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		items.add(new ItemStack(this));
		items.add(new ItemStack(this, 1, 1));
		items.add(new ItemStack(this, 1, 2));
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(DAMAGE, Integer.valueOf((meta & 15) >> 2));
	}

	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		i = i | state.getValue(DAMAGE) << 2;
		return i;
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, DAMAGE);
	}
}