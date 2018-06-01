package com.rumaruka.tb.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class BlockPyrosolid extends Block {
    public BlockPyrosolid() {
        super(Material.ROCK);
        setHardness(1.0f);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.BLAZE_POWDER;
    }
    public int quantityDropped(Random random)
    {
        return 20 + random.nextInt(2);
    }
}
