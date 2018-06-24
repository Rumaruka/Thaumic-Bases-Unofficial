package com.rumaruka.tb.common.block;




import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

import java.util.Arrays;
import java.util.List;


public class TBBlock extends Block  {

    boolean isGlass;
    boolean stabilise;
    protected SoundType blockSoundType;
    String iconName;


    public TBBlock(Material m, boolean b) {
        super(m);
        isGlass = b;
        this.blockSoundType = SoundType.STONE;
    }


    public TBBlock stabilise(){
        stabilise = true;
        return this;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !isGlass;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return isGlass ? 7 : 15;
    }
    public int getRenderBlockPass(){
        return isGlass ? 1 : 0;
    }



    public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
        return stabilise;
    }





    public List<IBlockState> listPossibleStates(Block b) {
        return Arrays.asList(this.getDefaultState());
    }





    public TBBlock setBlockTextureName(String tex)
    {
        iconName = tex;
        return this;
    }

    public Block setSoundType(SoundType sound)
    {
        this.blockSoundType = sound;
        return this;
    }
    public SoundType getSoundType()
    {
        return this.blockSoundType;
    }
}
