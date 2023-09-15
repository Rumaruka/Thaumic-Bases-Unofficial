package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;

public class BlockThaumSpike extends Block {

    public static PropertyInteger BLOODY = PropertyInteger.create("bloody",0,1);
    public BlockThaumSpike( ) {
        super(Material.IRON);
        setHardness(2.0f);
        setHarvestLevel("pickaxe",2);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        float f = 0.0625F;
        return new AxisAlignedBB(0, 0, 0, 1, 1-f, 1);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.damageDropped(state.getValue(BLOODY));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BLOODY,meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BLOODY);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos)
    {
        if(world.isAirBlock(observerPos.down()))
        {
            this.dropBlockAsItem(world, observerPos, observerState, 0);
            world.setBlockToAir(observerPos);
        }
    }



    @Override
    public void onEntityCollidedWithBlock(World w, BlockPos pos, IBlockState state, Entity collider) {
        int meta = state.getValue(BLOODY);
        if(meta == 0 || meta ==1){
            if(w.isBlockIndirectlyGettingPowered(pos)>0|| w.isBlockIndirectlyGettingPowered(pos.down())>0||w.isBlockPowered(pos))
                return;
            if(!(collider instanceof EntityItem))
                 collider.attackEntityFrom(DamageSource.CACTUS,14);
            if(meta == 0 && collider instanceof EntityLivingBase && ((EntityLivingBase) collider).getHealth() <= 0)
                w.setBlockState(pos, this.getStateFromMeta(1));
        }
    }

    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int meta = state.getValue(BLOODY);

        if(meta!=1)
            return false;
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if(itemstack.isEmpty()){
            return false;
        }

        if(itemstack.getItem() != ItemsTC.fabric){
            return false;
        }

        playerIn.inventory.decrStackSize(playerIn.inventory.currentItem,1);
        if(!playerIn.inventory.addItemStackToInventory(new ItemStack(TBItems.bloodycloth)))
            playerIn.dropItem(new ItemStack(TBItems.bloodycloth),false);
        w.setBlockState(pos, this.getStateFromMeta(meta-1));
        return true;
    }

    public int damageDropped(int par1)
    {
        return par1 == 1 ? 0 : par1 == 3 ? 2 : par1 == 5 ? 4 : par1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(BLOODY ==null){
            BLOODY = PropertyInteger.create("bloody",0,1);
        }
        return new BlockStateContainer(this,BLOODY);
    }
}
