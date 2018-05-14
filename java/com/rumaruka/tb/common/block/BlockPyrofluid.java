package com.rumaruka.tb.common.block;

import DummyCore.Utils.BlockStateMetadata;
import com.rumaruka.tb.utils.TBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockPyrofluid extends Block {

    public BlockPyrofluid() {
        super(Material.ROCK);

        float f = 0.0F;
        float f1 = 0.0F;
        AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
        this.setTickRandomly(true);
    }


    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        int meta = BlockStateMetadata.getBlockMetadata(worldIn, pos);
        double removed = 0;
        if(meta == 15)
            removed = 0.99D;
        else
            removed = 1-((8D - meta)/8D);

        if(removed < 0)
            removed = 0.95D;

        AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0, 0, 0, 1, (float) (1-removed), 1);
    }



    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return super.getCollisionBoundingBox(blockState, worldIn, pos);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.attackEntityFrom(DamageSource.LAVA, 5);
        entityIn.setFire(10);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    public int tickRate(World par1World)
    {
        return 5;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = pos.getX();
        double d1 = pos.getY();
        double d2 = pos.getZ();

        if(worldIn.getBlockState(pos.up()).getBlock().getMaterial(stateIn)==Material.AIR && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube(stateIn)){
            if(rand.nextInt(100)==0){
                double d8 = d0 + rand.nextFloat();
                double d4 = d1 + pos.getY();
                double d6 = d2 + rand.nextFloat();
                worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
                worldIn.playSound(d8, d4, d6, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS,0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);

            }
        }


    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        int meta = BlockStateMetadata.getMetaFromState(state) + 1;
        if(meta > 15)
            return;

        if(meta >= 8)
            meta = 15;

        worldIn.setBlockState(pos, this.getStateFromMeta(meta));
    }



    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for(int i = 0; i < 8; ++i)
            par3List.add(new ItemStack(par1,1,i));

        par3List.add(new ItemStack(par1,1,15));
    }





    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        ArrayList<ItemStack> retLst = new ArrayList<ItemStack>();

        retLst.add(new ItemStack(Items.BLAZE_POWDER,TBConfig.minBlazePowderFromPyrofluid+RANDOM.nextInt(TBConfig.maxBlazePowderFromPyrofluid-TBConfig.minBlazePowderFromPyrofluid),0));

        return retLst;
    }




    public List<IBlockState> listPossibleStates(Block b) {
        ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
        for(int i = 0; i < 9; ++i)
            retLst.add(getStateFromMeta(i));

        retLst.add(getStateFromMeta(15));

        return retLst;
    }


}
