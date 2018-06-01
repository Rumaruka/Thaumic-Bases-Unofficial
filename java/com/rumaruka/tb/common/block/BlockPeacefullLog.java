package com.rumaruka.tb.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockPeacefullLog extends net.minecraft.block.Block {
    public BlockPeacefullLog( ) {

            super(Material.WOOD);
            this.setHarvestLevel("axe", 0);
            this.setHardness(2.0F);
            this.setSoundType(SoundType.WOOD);
    }


}
