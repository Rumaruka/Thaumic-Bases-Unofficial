package com.rumaruka.tb.common.block;

import DummyCore.Utils.BlockStateMetadata;
import com.rumaruka.tb.init.TBBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAureliaPlant extends BlockBush {

    private PropertyInteger STATE = PropertyInteger.create("state",0,1);

    public BlockAureliaPlant(){
        super(Material.PLANTS);
        this.setTickRandomly(true);
        this.setLightLevel(0.5f);
        this.setSoundType(SoundType.PLANT);
    }


    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(TBBlocks.aurelia);
    }



    public void checkForMoonlight(World w, BlockPos pos,IBlockState state){

       boolean underSky = w.canBlockSeeSky(pos.up());
        boolean night = !w.isDaytime();
       // boolean isFullMoon = w.provider.getMoonPhase(w.getWorldTime())==0;
        boolean isOpen =state.getValue(STATE)==1;

        if(night&&underSky){

            w.setBlockState(pos,state.withProperty(STATE,1));
            w.markBlockRangeForRenderUpdate(pos.down().west().north(),pos.up().east().south());

        }
        else {
            if(isOpen){
                w.setBlockState(pos, state.withProperty(STATE,0));
                w.markBlockRangeForRenderUpdate(pos.down().west().north(), pos.up().east().south());
            }
        }


    }

    @Override
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rand) {

        checkForMoonlight(w,pos,state);


            if(state.getValue(STATE)>0&&!w.isRemote)
            {
                EnumFacing dir = EnumFacing.getFront(2+w.rand.nextInt(4));

                BlockPos posN = pos.offset(dir);
                w.setBlockState(posN, TBBlocks.aureliapetalb.getDefaultState());

        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }



    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STATE,Math.min(0,meta));

    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STATE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(STATE ==null){
            STATE = PropertyInteger.create("state",0,1);
        }
        return new BlockStateContainer(this,STATE);
    }
}
