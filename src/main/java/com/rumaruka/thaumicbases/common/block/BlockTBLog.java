package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class BlockTBLog extends BlockLog {


    public BlockTBLog() {


        setHarvestLevel("axe", 0);
        setHardness(0.7F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        switch (meta & 12) {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        switch (state.getValue(LOG_AXIS)) {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
            default:
                break;
        }

        return i;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherlogs)
            return 0;

        return super.getFireSpreadSpeed(world, pos, face);
    }

    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherlogs)
            return true;

        return super.isFlammable(world, pos, face);
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherleaves)
            return 0;

        return super.getFlammability(world, pos, face);
    }

    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        if(this == TBBlocks.netherlogs)
            return true;

        return super.isFireSource(world, pos, side);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }
}