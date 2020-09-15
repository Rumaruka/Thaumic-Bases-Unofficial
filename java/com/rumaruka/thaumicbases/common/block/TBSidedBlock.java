package com.rumaruka.thaumicbases.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class TBSidedBlock extends TBBlock implements IInfusionStabiliser {


    boolean stabilise;


    public TBSidedBlock(Material m, boolean b) {
        super(m, b);
    }
    public TBSidedBlock stabilise(){
        stabilise = true;
        return this;
    }
    public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
        return stabilise;
    }
}
