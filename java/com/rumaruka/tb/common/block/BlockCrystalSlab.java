package com.rumaruka.tb.common.block;

import com.rumaruka.tb.init.TBBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrystalSlab extends BlockTBSlab {

    public static final String[] names = new String[]{"air","fire","water","earth","order","entropy","mixed","tainted"};
    public BlockCrystalSlab(boolean fullblock, Material materialIn) {
        super(materialIn,fullblock);
        setHardness(0.7F);
        setLightLevel(0.5F);
        setSoundType(SoundType.GLASS);
        this.isAStabiliser = true;
    }




    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName()+ BlockCrystalSlab.names[meta&7] ;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }
}
