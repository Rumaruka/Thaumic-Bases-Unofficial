package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.api.dummycore_remove.utils.MathUtils;
import com.rumaruka.thaumicbases.common.tiles.TileCampfire;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.SoundsTC;

import javax.annotation.Nullable;
import java.util.Random;



public class BlockCampfire extends BlockContainer implements ITileEntityProvider{

    public static PropertyInteger STATE = PropertyInteger.create("state", 0, 2);

    public BlockCampfire( ) {
        super(Material.WOOD);
        setHardness(1F);
        setHarvestLevel("axe",2);
        setResistance(1F);
        setSoundType(SoundType.WOOD);
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCampfire();
    }

    public void randomDisplayTick(IBlockState state, World w, BlockPos pos, Random r)
    {
        if(this.getMetaFromState(state) > 1)
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            TileCampfire tc = (TileCampfire) w.getTileEntity(pos);
            if(tc.tainted)
                FXDispatcher.INSTANCE.drawPollutionParticles(pos);

            w.spawnParticle(EnumParticleTypes.FLAME, x+0.5D+MathUtils.randomDouble(r)/4, y, z+0.5D+MathUtils.randomDouble(r)/4, 0, 0.04D, 0);
            for(int i = 0; i < 10; ++i)
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x+0.5D+MathUtils.randomDouble(r)/4, y+0.1D, z+0.5D+MathUtils.randomDouble(r)/4, 0, r.nextDouble()/20, 0);

            w.playSound(x+0.5D, y+0.5D, z+0.5D, SoundsTC.pump, SoundCategory.BLOCKS, 0.1F, 0.1F, false);
        }
    }

    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(w.isRemote)
            return true;

        TileCampfire fire = (TileCampfire) w.getTileEntity(pos);
        ItemStack stk = p.getHeldItem(hand);
        boolean added = fire.addLog(stk);
        if(!added)
            added = fire.addFuel(stk);

        if(added)
        {
            p.inventory.decrStackSize(p.inventory.currentItem, 1);
            if(p.openContainer != null)
                p.openContainer.detectAndSendChanges();
            p.inventory.markDirty();
            return true;
        }

        return false;
    }

    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(STATE==null){
            STATE = PropertyInteger.create("state",0,2);
        }
        return new BlockStateContainer(this,STATE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STATE);
    }
}
