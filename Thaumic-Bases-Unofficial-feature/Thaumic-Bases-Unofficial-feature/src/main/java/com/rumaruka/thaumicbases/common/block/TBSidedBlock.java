package com.rumaruka.thaumicbases.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

public class TBSidedBlock extends TBBlock implements IInfusionStabiliserExt {

    boolean stabilise;

    public TBSidedBlock(Material m, boolean b) {
        super(m, b);
    }

    public TBSidedBlock stabilise() {
        stabilise = true;
        return this;
    }

    public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
        return stabilise;
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
