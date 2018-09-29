package com.rumaruka.tb.common.block;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MathUtils;
import com.rumaruka.tb.common.libs.TBSounds;
import com.rumaruka.tb.common.tiles.TileCampfire;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import thaumcraft.Thaumcraft;
import thaumcraft.client.fx.FXDispatcher;

import javax.annotation.Nullable;
import java.util.Random;



public class BlockCampfire extends BlockContainer implements IModelRegisterer,ITileEntityProvider {

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

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        if(stateIn.getValue(STATE)>1) {

            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            TileCampfire tc = (TileCampfire) worldIn.getTileEntity(pos);
            if(tc.tainted)
                FXDispatcher.INSTANCE.drawPollutionParticles(pos);
                worldIn.spawnParticle(EnumParticleTypes.FLAME,x+0.5D+MathUtils.randomDouble(rand)/4,y,z+0.5D+MathUtils.randomDouble(rand)/4,0,0.4d,0);
            for(int i = 0 ; i<10;i++)
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,x+0.5D+MathUtils.randomDouble(rand)/4, y+0.1D, z+0.5D+MathUtils.randomDouble(rand)/4, 0, rand.nextDouble()/20, 0);
                worldIn.playSound(x + 0.5D, y + 0.5D, z + 0.5D, TBSounds.fire_loop, SoundCategory.BLOCKS, 0.1F, 0.1F, false);

                if(stateIn.getValue(STATE)==2){
                    setLightLevel(10f);
                }
        }

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)
            return true;
        TileCampfire fire = (TileCampfire) worldIn.getTileEntity(pos);
        ItemStack stk = playerIn.getHeldItemMainhand();
        boolean added = fire.addLog(stk);

        if(!added){
            added = fire.addFuel(stk);
        }
        if(fire.burnTime==0 && fire.logTime==0){
            state.withProperty(STATE,0);
        }
        if(added){
            playerIn.inventory.decrStackSize(playerIn.inventory.currentItem,1);
            if(playerIn.openContainer!=null){
                playerIn.openContainer.detectAndSendChanges();
                playerIn.inventory.markDirty();
                return true;

            }

        }
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

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0,new ModelResourceLocation("thaumicbases:campfire","inventory"));

    }
}
