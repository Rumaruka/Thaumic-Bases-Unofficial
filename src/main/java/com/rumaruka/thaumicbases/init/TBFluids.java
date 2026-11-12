package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.common.block.PyroFluidBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TBFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ThaumicBases.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ThaumicBases.MODID);

    public static final DeferredHolder<FluidType, FluidType> PYRO_TYPE = FLUID_TYPES.register("pyro",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.thaumicbases.pyro")
                    .density(3000)
                    .viscosity(6000)
                    .temperature(1300)
                    .lightLevel(15)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)));

    public static final DeferredHolder<Fluid, FlowingFluid> PYRO_SOURCE = FLUIDS.register("pyro",
            () -> new BaseFlowingFluid.Source(TBFluids.PYRO_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> PYRO_FLOWING = FLUIDS.register("pyro_flowing",
            () -> new BaseFlowingFluid.Flowing(TBFluids.PYRO_PROPERTIES));

    public static final DeferredHolder<net.minecraft.world.level.block.Block, PyroFluidBlock> PYRO_BLOCK =
            TBBlocks.BLOCKS.register("pyro", () -> new PyroFluidBlock(PYRO_SOURCE,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.FIRE)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .noLootTable()
                            .liquid()
                            .lightLevel(s -> 15)));

    public static final BaseFlowingFluid.Properties PYRO_PROPERTIES = new BaseFlowingFluid.Properties(
            PYRO_TYPE, PYRO_SOURCE, PYRO_FLOWING)
            .block(PYRO_BLOCK)
            .bucket(TBItems.PYROBUCKET)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(30);

    private TBFluids() {
    }
}
