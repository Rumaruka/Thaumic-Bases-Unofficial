package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockTBPlanks extends net.minecraft.block.Block {



    public BlockTBPlanks( ) {
        super(Material.WOOD);
        this.setHarvestLevel("axe", 0);
        this.setHardness(2.0f);
        this.setSoundType(SoundType.WOOD);
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherplanks)
            return 0;

        return super.getFireSpreadSpeed(world, pos, face);
    }

    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherplanks)
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
        if(this == TBBlocks.netherplanks)
            return true;

        return super.isFireSource(world, pos, side);
    }
}
