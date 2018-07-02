package com.rumaruka.tb.common.block;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MathUtils;
import com.rumaruka.tb.common.libs.TBSounds;
import com.rumaruka.tb.common.tiles.TileBraizer;
import com.rumaruka.tb.common.tiles.TileCampfire;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import thaumcraft.client.fx.FXDispatcher;

import javax.annotation.Nullable;
import java.util.Random;


public class BlockBraizer extends BlockContainer implements ITileEntityProvider,IModelRegisterer {
    public static PropertyInteger STATE = PropertyInteger.create("state", 0, 1);

    public BlockBraizer() {
        super(Material.ROCK);
        setHardness(1F);
        setResistance(1F);
    }






    @Override
    public void randomDisplayTick(IBlockState stateIn, World w, BlockPos pos, Random r) {

        if(stateIn.getValue(STATE)==1) {

            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            w.spawnParticle(EnumParticleTypes.FLAME, x+0.5D+MathUtils.randomDouble(r)/4, y+0.6D, z+0.5D+MathUtils.randomDouble(r)/4, 0, 0.04D, 0);
            for(int i = 0; i < 2; ++i)
                w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x+0.5D+MathUtils.randomDouble(r)/4, y+0.7D, z+0.5D+MathUtils.randomDouble(r)/4, 0, r.nextDouble()/20, 0);
            w.playSound(x + 0.5D, y + 0.5D, z + 0.5D, TBSounds.fire_loop, SoundCategory.BLOCKS, 0.1F, 0.1F, false);

            if(stateIn.getValue(STATE)==1){
                setLightLevel(10f);
            }
        }

    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBraizer();
    }
    @Override
    protected BlockStateContainer createBlockState() {
        if(STATE==null){
            STATE = PropertyInteger.create("state",0,1);
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
