package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.client.creativetabs.TBCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class TBBlockCrytal extends TBBlock implements IInfusionStabiliser {







    public TBBlockCrytal(Material m, boolean b) {
        super(m,b);

        setSoundType(SoundType.GLASS);

        setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        setHardness(0.5F);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean canStabaliseInfusion(World world, BlockPos paramBlockPos) {
        return true;
    }
}

