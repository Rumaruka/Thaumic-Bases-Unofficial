package com.rumaruka.tb.common.block;

import net.minecraft.block.SoundType;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


public class BlockTBPlanks extends net.minecraft.block.Block {



    public BlockTBPlanks( ) {
        super(Material.WOOD);
        this.setHarvestLevel("axe", 0);
        this.setHardness(2.0f);
        this.setSoundType(SoundType.WOOD);
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 20;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 5;
    }

}
