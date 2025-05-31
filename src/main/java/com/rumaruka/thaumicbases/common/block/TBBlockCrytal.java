package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.client.creativetabs.TBCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

public class TBBlockCrytal extends TBBlock implements IInfusionStabiliserExt {

    public TBBlockCrytal(Material m, boolean b) {
        super(m, b);

        setSoundType(SoundType.GLASS);

        setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        setHardness(0.5F);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
        return true;
    }

    @Override
    public boolean hasSymmetryPenalty(World world, BlockPos pos1, BlockPos pos2) {
        return false;
    }

    @Override
    public float getSymmetryPenalty(World world, BlockPos pos) {
        return 0.0F;
    }
}

