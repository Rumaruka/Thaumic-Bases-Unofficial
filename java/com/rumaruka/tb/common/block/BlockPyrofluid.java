package com.rumaruka.tb.common.block;

import DummyCore.Utils.BlockStateMetadata;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;
import com.rumaruka.tb.init.TBBlocks;
import com.rumaruka.tb.init.TBFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import thaumcraft.api.blocks.BlocksTC;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockPyrofluid extends BlockFluidClassic   {
    public BlockPyrofluid( ) {
        super(TBFluids.FLUID_PYRO, Material.LAVA);
        setUnlocalizedName("pyro");

        tickRate = 20;
    }

    @Override
    public Vec3d getFlowVector(IBlockAccess world, BlockPos pos) {
        return super.getFlowVector(world, pos);
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {



       if(!world.isRemote&&rand.nextInt(500)==0){
           world.setBlockState(pos,TBBlocks.pyrosolid.getDefaultState());
           world.neighborChanged(pos,TBBlocks.pyrosolid,pos);

       }

}




    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighbourPos) {
           if(!this.tryTouchWater(world,pos,state)){
               super.neighborChanged(state,world,pos,neighborBlock,neighbourPos);
           }
    }

    private boolean tryTouchWater(World world, BlockPos pos, IBlockState state) {

        boolean flag = false;
        for(EnumFacing enumFacing : EnumFacing.values()){
            if(enumFacing != EnumFacing.DOWN){
                BlockPos blockPos = pos.offset(enumFacing);
                if(world.getBlockState(blockPos).getBlock()== BlocksTC.purifyingFluid){
                    flag = true;
                    break;
                }
            }
        }
        if(flag){
            world.setBlockState(pos,TBBlocks.pyrosolid.getDefaultState());
        }
        return flag;
    }
}




