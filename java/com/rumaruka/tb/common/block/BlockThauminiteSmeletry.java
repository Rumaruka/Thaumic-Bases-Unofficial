package com.rumaruka.tb.common.block;

import DummyCore.Utils.MiscUtils;
import com.rumaruka.tb.common.tiles.TileThauminiteSmeltery;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.Thaumcraft;
import thaumcraft.common.lib.utils.BlockStateUtils;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockThauminiteSmeletry extends BlockContainer implements ITileEntityProvider {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private final boolean isBurning;
    public BlockThauminiteSmeletry(boolean isBurning) {
        super(Material.ROCK);
        this.isBurning = isBurning;
        this.setHardness(2);
        this.setResistance(3);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileThauminiteSmeltery();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory((IInventory) worldIn.getTileEntity(pos));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote){
            return true;
        }
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(!playerIn.isSneaking()&& tileEntity instanceof TileThauminiteSmeltery){
            playerIn.openGui(Thaumcraft.instance,9,worldIn,pos.getX(),pos.getY(),pos.getZ());

            return true;
        }
        return false;
    }
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }


    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileThauminiteSmeltery)
            {
                ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void randomDisplayTick(IBlockState state, World w, BlockPos pos, Random r) {
        if (this.isBurning) {
            float f = (float)pos.getX() + 0.5F;
            float f1 = (float)pos.getY() + 0.2F + r.nextFloat() * 5.0F / 16.0F;
            float f2 = (float)pos.getZ() + 0.5F;
            float f3 = 0.52F;
            float f4 = r.nextFloat() * 0.5F - 0.25F;
            if (BlockStateUtils.getFacing(state) == EnumFacing.WEST) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D, new int[0]);
                w.spawnParticle(EnumParticleTypes.FLAME, (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D, new int[0]);
            }

            if (BlockStateUtils.getFacing(state) == EnumFacing.EAST) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D, new int[0]);
                w.spawnParticle(EnumParticleTypes.FLAME, (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D, new int[0]);
            }

            if (BlockStateUtils.getFacing(state) == EnumFacing.NORTH) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D, new int[0]);
                w.spawnParticle(EnumParticleTypes.FLAME, (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D, new int[0]);
            }

            if (BlockStateUtils.getFacing(state) == EnumFacing.SOUTH) {
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D, new int[0]);
                w.spawnParticle(EnumParticleTypes.FLAME, (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }
}

