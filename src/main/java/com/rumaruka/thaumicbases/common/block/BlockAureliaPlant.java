package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(TBBlocks.aurelia);
    }



    public void checkForMoonlight(World w, BlockPos pos,IBlockState state){

       boolean underSky = w.canBlockSeeSky(pos.up());
        boolean isFullMoon = w.provider.getMoonPhase(w.getWorldTime())==0;
        boolean isOpen =state.getValue(STATE)==1;

        if(isFullMoon&&underSky&&!w.isDaytime()){

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

    public int tickRate(World w)
    {
        return 10;
    }

    public int findSutableGround(World w, int x, int y, int z)
    {
        while(w.isAirBlock(new BlockPos(x,y,z)) && y > 0 && !w.isSideSolid(new BlockPos(x, y, z), EnumFacing.UP))
            --y;

        return y;
    }

    @Override
    public void updateTick(World w, BlockPos pos, IBlockState state, Random rand)
        {
            checkForMoonlight(w,pos,state);
            if(state.getValue(STATE)>0&&!w.isRemote)
            {
                int rndX = pos.getX() + w.rand.nextInt(8) - w.rand.nextInt(8);
                int rndZ = pos.getZ() + w.rand.nextInt(8) - w.rand.nextInt(8);
                int rndY = findSutableGround(w,rndX,pos.getY()+2,rndZ)+1;
                if(rndY > 2)
                    w.setBlockState(new BlockPos(rndX, rndY, rndZ), TBBlocks.aureliapetalb.getDefaultState());
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
