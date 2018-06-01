package com.rumaruka.tb.common.block;

import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;
import com.rumaruka.tb.init.TBBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.world.plants.BlockLeavesTC;
import thaumcraft.common.world.aura.AuraHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockTBLeaves extends Block implements IShearable {
    int leafType;

    public static final PropertyEnum<LeafGrowth> GROWTH = PropertyEnum.create("growth", LeafGrowth.class);

    public enum LeafGrowth implements IStringSerializable {
        FRESH(0), GROWING(1), READY(2), MATURED(3);
        private int meta;

        LeafGrowth(int meta) {
            this.meta = meta;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public boolean canGrow() {
            return this != MATURED;
        }

        public int getMeta() {
            return meta;
        }

    }





    public BlockTBLeaves(int type ) {
        super(Material.LEAVES);
        this.leafType = type;
        setTickRandomly(true);
        setHardness(0.2F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, LeafGrowth.FRESH));


    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 60;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 30;
    }



    public boolean isOpaqueCube(IBlockState state) {
        return Blocks.LEAVES.isOpaqueCube(state);
    }



    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getBlock() == TBBlocks.goldenleaves ? MapColor.LIGHT_BLUE : super.getMapColor(state, worldIn, pos);
    }



    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack((Block)this));
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack((Block)this);
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int blocks = 0;
        for (int i = 0; i < EnumFacing.VALUES.length; i++) {
            EnumFacing dir = EnumFacing.VALUES[i];
            IBlockState neighbour = world.getBlockState(pos.offset(dir));
            Block block = neighbour.getBlock();
            if (!block.isAir(neighbour, world, pos)) {
                blocks++;
            }
        }
        if (blocks != 6) {
            int randInt;
            if (leafType == 3) {
                randInt = rand.nextInt(10);
            } else if (leafType < 2) {
                randInt = rand.nextInt(8);
            } else {
                randInt = rand.nextInt(9);
            }
            if (randInt <= 2) {
                LeafGrowth leaf = state.getValue(GROWTH);
                if (leaf.canGrow()) {
                    world.setBlockState(pos, state.cycleProperty(GROWTH));
                }
            }
        }
    }
    public void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {


        if (leafType==0 && worldIn.rand.nextInt(chance)<60)
            spawnAsEntity(worldIn, pos, new ItemStack(Items.GOLDEN_APPLE, 1));
    }

    public void dropMagmacream(World worldIn, BlockPos pos, IBlockState state, int chance)
    {

        if (leafType==1&& worldIn.rand.nextInt(chance)<60)
            spawnAsEntity(worldIn, pos, new ItemStack(Items.MAGMA_CREAM, 1));
    }
    public void dropEnderpearl(World world,BlockPos pos, IBlockState state, int chance){
        if(leafType==2&& world.rand.nextInt(chance)<60){
            spawnAsEntity(world,pos,new ItemStack(Items.ENDER_PEARL,1));
        }
    }
    @Override
    public int tickRate(World world) {
        return 8;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }



    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, EnumFacing face) {
        return 150;
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos posz) {
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        switch (leafType) {
            case 0:
                ret.add(new ItemStack(TBBlocks.goldenleaves, 1, 0));
                break;
            case 1:
                ret.add(new ItemStack(TBBlocks.netherleaves, 1, 0));
                break;
            case 2:
                ret.add(new ItemStack(TBBlocks.enderleaves, 1, 0));
                break;

        }
        return ret;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    public LeafGrowth getVariant(int metadata) {
        for (LeafGrowth colour : LeafGrowth.values()) {
            if (colour.meta == metadata) {
                return colour;
            }
        }
        return LeafGrowth.FRESH;
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(GROWTH, getVariant(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWTH).getMeta();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWTH);
    }

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return Minecraft.isFancyGraphicsEnabled() ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }


    public boolean isVisuallyOpaque() {
        return false;
    }

    public int getSaplingDropChance(IBlockState state)
    {
        return leafType == 0 ?  50 : 30;
    }




    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new ArrayList<ItemStack>();
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int chance = this.getSaplingDropChance(state);

        if (fortune > 0)
        {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (leafType==0&&rand.nextInt(chance) == 0)
            ret.add(new ItemStack(TBBlocks.goldensapling));
        if(leafType==1&&rand.nextInt(chance)==0)
            ret.add(new ItemStack(TBBlocks.nethersapling));
        if(leafType==2&&rand.nextInt(chance)==0)
            ret.add(new ItemStack(TBBlocks.endersapling));

        chance = 200;
        if (fortune > 0)
        {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        if (world instanceof World)
            this.dropApple((World)world, pos, state, chance);
            this.dropEnderpearl((World) world,pos,state,chance);
            this.dropMagmacream((World) world,pos,state,chance);
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}

