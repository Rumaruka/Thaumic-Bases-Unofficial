package com.rumaruka.tb.common.block;


import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;
import com.rumaruka.tb.init.TBBlocks;
import com.rumaruka.tb.utils.generation.TBTreeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.terraingen.TerrainGen;
import thaumcraft.common.blocks.world.plants.BlockLogsTC;
import thaumcraft.common.blocks.world.plants.BlockSaplingTC;
import thaumcraft.common.world.objects.WorldGenGreatwoodTrees;


import java.util.Random;

public class BlockTBSapling extends BlockBush implements IGrowable {




    public static final PropertyInteger STAGE = PropertyInteger.create((String) "stage", (int) 0, (int) 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552, 0.0, 0.09999999403953552, 0.8999999761581421, 0.800000011920929, 0.8999999761581421);
    public int type;
    public BlockTBSapling(int i) {
        this.type = i;
        this.setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE,0));
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return type !=3 ? super.canPlaceBlockAt(worldIn, pos) && soil.getBlock().canSustainPlant(soil,worldIn,pos.down(),EnumFacing.UP,this) : soil.getBlock()== Blocks.GRASS;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return type == 3 ? this.canSustainBush(worldIn.getBlockState(pos.down())) : super.canBlockStay(worldIn, pos, state);    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return type != 3 ? state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND:state.getBlock() == Blocks.GRASS ;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, rand, pos, state);
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (state.getValue(STAGE) == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }else {
            generateTree(worldIn, pos, state, rand);
        }
    }

    private void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!TerrainGen.saplingGrowTree(worldIn,rand,pos))
            return;
        WorldGenerator worldGenerator = rand.nextInt(10)==0? new WorldGenBigTree(false):new WorldGenTrees(true);
        int i = 0;
        int j = 0;
        boolean flag = false;
        switch (type) {
            case 0:
                worldGenerator = new TBTreeBuilder(true, TBBlocks.goldensapling, TBBlocks.goldenleaves, TBBlocks.goldenlogs);
                break;
            case 1:
                worldGenerator = new TBTreeBuilder(true, TBBlocks.nethersapling, TBBlocks.netherleaves, TBBlocks.netherlogs);
                break;
            case 2:
                worldGenerator = new TBTreeBuilder(true, TBBlocks.endersapling, TBBlocks.enderleaves, TBBlocks.enderlogs);
                break;


        }
        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        worldIn.setBlockState(pos, iblockstate2, 4);

        if (!worldGenerator.generate(worldIn, rand, pos.add(i, 0, j))) {
            worldIn.setBlockState(pos, state, 4);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE,meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,STAGE);
    }
}