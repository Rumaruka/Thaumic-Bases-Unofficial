package com.rumaruka.tb.common.block;

import DummyCore.Utils.BlockStateMetadata;
import com.rumaruka.tb.init.TBItems;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockSweed extends BlockTBPlant {




    public  BlockSweed(int stages, int delay,boolean isCrop) {
        super(stages,delay,isCrop);
        this.setTickRandomly(true);

    }

    protected boolean canPlaceBlockOn(Block b)
    {
        return b != null && (b == Blocks.GRASS || b == Blocks.DIRT || b instanceof BlockGrass || b instanceof BlockDirt);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        Block b = world.getBlockState(pos.down()).getBlock();
        return canPlaceBlockOn(b);
    }

    @Override
    public int tickRate(World worldIn) {
        return 1200+worldIn.rand.nextInt(1200);
    }


    @Override
    public int getGrowthStages() {
        return 3;
    }

    public void updateTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
        super.updateTick(w,pos, state, rnd);
        if(state.getValue(AGE)<3&&!w.isRemote)
        {
            EnumFacing dir = EnumFacing.getFront(2+w.rand.nextInt(4));

           BlockPos posN = pos.offset(dir);


            if(canPlaceBlockOn(w.getBlockState(posN.down()).getBlock()) && w.isAirBlock(posN))
                w.setBlockState(posN, this.getStateFromMeta(Math.min(3,getMetaFromState(state)+1)));
        }

    }


    @Override
    public List<ItemStack> getDrops(IBlockAccess w, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (w instanceof World) {
            World world = World.class.cast(w);
            int metadata = state.getValue(AGE);
            if (metadata >= growthStages - 1) {
                for (int i = 0; i < 1 + fortune; ++i)
                    if (world.rand.nextInt(growthStages) <= metadata)
                        if (dropSeed != null)
                            ret.add(dropSeed.copy());

                for (int i = 0; i < 3 + fortune; ++i)
                    if (world.rand.nextBoolean())
                        ret.add(new ItemStack(Items.SUGAR));

                if (world.rand.nextBoolean())
                    ret.add(new ItemStack(Items.REEDS));



            }
        }
        return ret;
    }



}
