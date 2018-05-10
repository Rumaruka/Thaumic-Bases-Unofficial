package com.rumaruka.tb.common.block;

import DummyCore.Utils.CustomStepSound;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

import java.util.Arrays;
import java.util.List;

public class TBBlockCrytal extends TBBlock implements IInfusionStabiliser {





    public TBBlockCrytal(Material m, boolean b) {
        super(m,b);
        setSoundType(SoundType.GLASS);
    }

    @Override
    public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
        return true;
    }
}

