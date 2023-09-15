package com.rumaruka.thaumicbases.common.block;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidPyro extends Fluid {



    public FluidPyro() {
        super("pyro",  new ResourceLocation("thaumicbases:blocks/fluid_pyro_still"), new ResourceLocation( "thaumicbases:blocks/fluid_pyro_flow"));
        setUnlocalizedName("pyro");
    }

    @Override
    public String getUnlocalizedName() {
        return "fluid.pyro";
    }
}
