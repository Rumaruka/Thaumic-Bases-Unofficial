package com.rumaruka.tb.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.blocks.world.taint.BlockFluxGoo;

import java.util.Random;

public class BlockFlaxium extends BlockBush {

    protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.25F, 0, 0.25F, 0.75F, 0.85F, 0.75F);



    public BlockFlaxium() {
        super(Material.PLANTS);
        setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BUSH_AABB;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w, pos, state, rnd);
        if(w.isAirBlock(pos.up()))
            AuraHelper.polluteAura(w, pos, 1, true);

        if(!canGrowOn(w,pos.down()))
            w.setBlockToAir(pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        if(!canGrowOn(world,pos.down())){
            world.isAirBlock(pos);

        }
    }


    private boolean canGrowOn(IBlockAccess w, BlockPos pos) {
        return !w.isAirBlock(pos) && (w.getBlockState(pos).getBlock().isReplaceableOreGen(w.getBlockState(pos),w, pos,BlockMatcher.forBlock(Blocks.GRASS)) || w.getBlockState(pos).getBlock().isReplaceableOreGen(w.getBlockState(pos),w, pos, BlockMatcher.forBlock(Blocks.DIRT)));

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
 }
