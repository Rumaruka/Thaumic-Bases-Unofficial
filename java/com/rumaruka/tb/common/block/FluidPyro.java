package com.rumaruka.tb.common.block;

import com.rumaruka.tb.core.TBCore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class FluidPyro extends Fluid {



    public FluidPyro() {
        super("pyro",  new ResourceLocation("thaumicbases:blocks/fluid_pyro_still"), new ResourceLocation( "thaumicbases:blocks/fluid_pyro_flow"));
        setUnlocalizedName("pyro");



    }



}
