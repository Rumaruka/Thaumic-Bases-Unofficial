package com.rumaruka.tb.init;

import com.rumaruka.tb.common.block.FluidPyro;
import com.rumaruka.tb.utils.OnetimeCaller;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TBFluids {

    public static final FluidPyro FLUID_PYRO = new FluidPyro();

    public static final OnetimeCaller init = new OnetimeCaller(TBFluids::$init);

    private static void $init()
    {

        addFluid(FLUID_PYRO);
    }

    private static void addFluid(Fluid fl)
    {
        FluidRegistry.registerFluid(fl);
        FluidRegistry.addBucketForFluid(fl);


    }
}
