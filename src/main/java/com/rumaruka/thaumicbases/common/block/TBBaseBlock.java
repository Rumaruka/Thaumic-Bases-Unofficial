package com.rumaruka.thaumicbases.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Base block for simple Thaumic Bases blocks.
 * Thaumcraft/Thaumaturge infusion stabilizer behavior can be layered in later.
 */
public class TBBaseBlock extends Block {
    public TBBaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
