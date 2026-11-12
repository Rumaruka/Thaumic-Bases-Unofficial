package com.rumaruka.thaumicbases.common.block;

import com.rumaruka.thaumicbases.api.dummycore_remove.utils.MathUtils;
import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AeXiaohu modified
 * 修复树叶不会自然衰退，同时完善树叶应有的特性
 * 注意：本类还含有以下更多`modified`
 */
//public class BlockTBLeaves extends Block implements IShearable {
public class BlockTBLeaves extends BlockLeaves implements IShearable { // End of modification

    int leafType;

    public static final PropertyEnum<LeafGrowth> GROWTH = PropertyEnum.create("growth", LeafGrowth.class);

    int[] surroundings; // AeXiaohu modified

    public enum LeafGrowth implements IStringSerializable {
        FRESH(0), GROWING(1), READY(2), MATURED(3);

        private final int meta;

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
//        super(Material.LEAVES); // AeXiaohu modified
        this.leafType = type;
        setTickRandomly(true);
        setHardness(0.2F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, LeafGrowth.FRESH));
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherleaves)
            return 0;

        return super.getFlammability(world, pos, face);
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherleaves)
            return 0;

        return super.getFireSpreadSpeed(world, pos, face);
    }

    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        if(this == TBBlocks.netherleaves)
            return true;

        return super.isFireSource(world, pos, side);
    }

    public boolean isOpaqueCube(IBlockState state) {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getBlock() == TBBlocks.goldenleaves ? MapColor.LIGHT_BLUE : super.getMapColor(state, worldIn, pos);
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this));
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }

    public void spawnAction(World w, Random rnd, BlockPos pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if(this == TBBlocks.peaceleaves)// TODO
        {
            if(rnd.nextDouble() > 0.03D)
                return;

            int dy = y;
            Biome base = w.getBiome(new BlockPos(x,0, z));
            if(base != null)
            {
                List<Biome.SpawnListEntry> l = base.getSpawnableList(EnumCreatureType.CREATURE);
                if(l != null && !l.isEmpty())
                {
                    Biome.SpawnListEntry entry = l.get(rnd.nextInt(l.size()));
                    if(entry != null && entry.entityClass != null)
                    {
                        Class<?> c = entry.entityClass;
                        if(EntityLiving.class.isAssignableFrom(c))
                        {
                            try
                            {
                                EntityLiving el = (EntityLiving) c.getConstructor(World.class).newInstance(w);
                                while(--y >= dy-6)
                                {
                                    el.setPositionAndRotation(x+0.5D, y, z+0.5D, 0, 0);
                                    if(el.getCanSpawnHere())
                                    {
                                        w.spawnEntity(el);
                                        break;
                                    }
                                    continue;
                                }
                            }catch(Exception e)
                            {
                                FMLLog.warning("[TB]Tried to create an entity of class "+c+" but failed! The exception is listed below:");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if(this == TBBlocks.netherleaves)
        {
            int dy = y;
            while(--y >= dy-8)
            {
                Block b = w.getBlockState(new BlockPos(x,y,z)).getBlock();
                if(!b.isAir(w.getBlockState(pos), w, new BlockPos(x, y, z)))
                {
                    boolean netheric = b instanceof BlockNetherrack || b instanceof BlockSoulSand || b == Blocks.QUARTZ_BLOCK;
                    if(netheric && rnd.nextDouble() <= 0.05D)
                    {
                        Biome hellBiome = Biomes.HELL;
                        List<Biome.SpawnListEntry> l = rnd.nextBoolean() ? hellBiome.getSpawnableList(EnumCreatureType.CREATURE) : hellBiome.getSpawnableList(EnumCreatureType.MONSTER);
                        if(l != null && !l.isEmpty())
                        {
                            Biome.SpawnListEntry entry = l.get(rnd.nextInt(l.size()));
                            if(entry != null && entry.entityClass != null)
                            {
                                Class<?> c = entry.entityClass;
                                if(EntityLiving.class.isAssignableFrom(c))
                                {
                                    try
                                    {
                                        EntityLiving el = (EntityLiving) c.getConstructor(World.class).newInstance(w);

                                        el.setPositionAndRotation(x+0.5D, y+1, z+0.5D, 0, 0);
                                        el.onInitialSpawn(w.getDifficultyForLocation(new BlockPos(el)), null);

                                        if(el.getCanSpawnHere())
                                        {
                                            w.spawnEntity(el);
                                            break;
                                        }

                                    }catch(Exception e)
                                    {
                                        FMLLog.warning("[TB]Tried to create an entity of class "+c+" but failed! The exception is listed below:");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        break;
                    }
                    boolean flag = b instanceof BlockDirt || b instanceof BlockGrass || b instanceof BlockGravel || b instanceof BlockSand || b instanceof BlockStone;
                    if(!flag)
                    {
                        ItemStack stk = new ItemStack(b,1,getMetaFromState(w.getBlockState(pos)));
                        if(!stk.isEmpty()&&stk.getItem()!=null)
                            if(OreDictionary.getOreIDs(stk) != null && OreDictionary.getOreIDs(stk).length > 0)
                            {
                                OreDict:for(int i = 0; i < OreDictionary.getOreIDs(stk).length; ++i)
                                {
                                    int id = OreDictionary.getOreIDs(stk)[i];
                                    if(id != -1)
                                    {
                                        String ore = OreDictionary.getOreName(id);
                                        if(ore != null && ! ore.isEmpty())
                                        {
                                            flag = ore.contains("dirt") || ore.contains("grass") || ore.contains("sand") || ore.contains("gravel") || ore.contains("stone");
                                            if(flag)
                                                break;
                                        }
                                    }
                                }
                            }
                    }
                    if(flag)
                    {
                        double random = rnd.nextDouble();
                        Block setTo = random <= 0.6D ? Blocks.NETHERRACK : random <= 0.9D ? Blocks.SOUL_SAND : Blocks.QUARTZ_ORE;
                        w.setBlockState(new BlockPos(x, y, z), setTo.getDefaultState());
                        break;
                    }
                }
            }
        }
        if(this == TBBlocks.enderleaves)
        {
            int dy = y;
            while(--y >= dy-11)
            {
                Block b = w.getBlockState(new BlockPos(x, y, z)).getBlock();
                if(!b.isAir(w.getBlockState(pos), w, new BlockPos(x, y, z)))
                {
                    boolean end = b == Blocks.END_STONE || b instanceof BlockObsidian;
                    if(end && rnd.nextDouble() <= 0.02D)
                    {
                        Biome hellBiome = Biomes.SKY;
                        List<Biome.SpawnListEntry> l = rnd.nextBoolean() ? hellBiome.getSpawnableList(EnumCreatureType.CREATURE) : hellBiome.getSpawnableList(EnumCreatureType.MONSTER);
                        if(l != null && !l.isEmpty())
                        {
                            Biome.SpawnListEntry entry = l.get(rnd.nextInt(l.size()));
                            if(entry != null && entry.entityClass != null)
                            {
                                Class<?> c = entry.entityClass;
                                if(EntityLiving.class.isAssignableFrom(c))
                                {
                                    try
                                    {
                                        EntityLiving el = (EntityLiving) c.getConstructor(World.class).newInstance(w);

                                        el.setPositionAndRotation(x+0.5D, y+1, z+0.5D, 0, 0);
                                        el.onInitialSpawn(w.getDifficultyForLocation(new BlockPos(el)), null);

                                        if(w.isAirBlock(new BlockPos(x, y+1, z)))
                                        {
                                            w.spawnEntity(el);
                                            break;
                                        }

                                    }catch(Exception e)
                                    {
                                        FMLLog.warning("[TB]Tried to create an entity of class "+c+" but failed! The exception is listed below:");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        break;
                    }
                    boolean flag = b instanceof BlockDirt || b instanceof BlockGrass || b instanceof BlockGravel || b instanceof BlockSand || b instanceof BlockStone && !(b instanceof BlockObsidian) && !(b == Blocks.END_STONE);
                    if(!flag)
                    {
                        ItemStack stk = new ItemStack(b,1,getMetaFromState(w.getBlockState(pos)));
                        if(!stk.isEmpty()&&stk.getItem()!=null)
                            if(OreDictionary.getOreIDs(stk) != null && OreDictionary.getOreIDs(stk).length > 0)
                            {
                                OreDict:for(int i = 0; i < OreDictionary.getOreIDs(stk).length; ++i)
                                {
                                    int id = OreDictionary.getOreIDs(stk)[i];
                                    if(id != -1)
                                    {
                                        String ore = OreDictionary.getOreName(id);
                                        if(ore != null && ! ore.isEmpty())
                                        {
                                            flag = ore.contains("dirt") || ore.contains("grass") || ore.contains("sand") || ore.contains("gravel") || ore.contains("stone");
                                            if(flag)
                                                break;
                                        }
                                    }
                                }
                            }
                    }
                    if(flag)
                    {
                        double random = rnd.nextDouble();
                        Block setTo = random <= 0.9D ? Blocks.END_STONE : Blocks.OBSIDIAN;
                        w.setBlockState(new BlockPos(x, y, z), setTo.getDefaultState());
                        break;
                    }
                }
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {


        /**
         * AeXiaohu modified
         * 修复树叶会自然衰退等树叶应有的特性
         */
        if (!worldIn.isRemote) {
            if (state.getValue(GROWTH) == LeafGrowth.MATURED) {
                int i = 4;
                int j = 5;
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();
                int j1 = 32;
                int k1 = 1024;
                int l1 = 16;

                if (this.surroundings == null) {
                    this.surroundings = new int[32768];
                }

                if (!worldIn.isAreaLoaded(pos, 1))
                    return; // Forge: prevent decaying leaves from updating neighbors and loading unloaded chunks
                if (worldIn.isAreaLoaded(pos, 6)) // Forge: extend range from 5 to 6 to account for neighbor checks in world.markAndNotifyBlock -> world.updateObservingBlocksAt
                {
                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                    for (int i2 = -4; i2 <= 4; ++i2) {
                        for (int j2 = -4; j2 <= 4; ++j2) {
                            for (int k2 = -4; k2 <= 4; ++k2) {
                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
                                Block block = iblockstate.getBlock();

                                if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
                                    if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
                                    } else {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
                                    }
                                } else {
                                    this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
                                }
                            }
                        }
                    }

                    for (int i3 = 1; i3 <= 4; ++i3) {
                        for (int j3 = -4; j3 <= 4; ++j3) {
                            for (int k3 = -4; k3 <= 4; ++k3) {
                                for (int l3 = -4; l3 <= 4; ++l3) {
                                    if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1) {
                                        if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = this.surroundings[16912];

                if (l2 >= 0) {
                    worldIn.setBlockState(pos, state.withProperty(GROWTH, LeafGrowth.MATURED), 4);
                } else {
                    this.destroy(worldIn, pos);
                }

                spawnAction(worldIn, rand, pos);
            }
        } // End of modification


        int blocks = 0;
        for (int i = 0; i < EnumFacing.VALUES.length; i++) {
            EnumFacing dir = EnumFacing.VALUES[i];
            IBlockState neighbour = worldIn.getBlockState(pos.offset(dir));
            Block block = neighbour.getBlock();
            if (!block.isAir(neighbour, worldIn, pos)) {
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
                    worldIn.setBlockState(pos, state.cycleProperty(GROWTH));
                }
            }
        }
    }

    private void destroy(World worldIn, BlockPos pos) {
        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockToAir(pos);
    }

    public void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {
        if (leafType==0 && worldIn.rand.nextInt(chance)<60)
            spawnAsEntity(worldIn, pos, new ItemStack(Items.GOLDEN_APPLE, 1));
    }

    @Override
    public int tickRate(World world) {
        return 8;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (worldIn.isRaining() && !worldIn.canBlockSeeSky(pos.down()) && rand.nextInt(15) == 1)
        {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() - 0.05D;
            double d2 = pos.getZ() + rand.nextFloat();
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if(this == TBBlocks.goldenleaves)
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, x+ rand.nextDouble(), y+ rand.nextDouble(), z+ rand.nextDouble(), 1, 1, 0);

        if(this == TBBlocks.peaceleaves && rand.nextFloat() <= 0.01F)
            worldIn.spawnParticle(EnumParticleTypes.HEART, x+ rand.nextDouble(), y+ rand.nextDouble(), z+ rand.nextDouble(), 0, 10, 0);

        if(this == TBBlocks.netherleaves)
        {
            if(worldIn.isAirBlock(new BlockPos(x, y-1, z)))
                worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, x+ rand.nextDouble(), y, z+ rand.nextDouble(), 0, 0, 0);
        }

        if(this == TBBlocks.enderleaves)
            worldIn.spawnParticle(EnumParticleTypes.PORTAL, x+ rand.nextDouble(), y+ rand.nextDouble(), z+ rand.nextDouble(), MathUtils.randomDouble(rand), MathUtils.randomDouble(rand), MathUtils.randomDouble(rand));
    }

    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if(this == TBBlocks.netherleaves)
            return true;

        return super.isFlammable(world, pos, face);
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
        // AeXiaohu modified
        if (state.getValue(GROWTH) != LeafGrowth.MATURED) {
            world.setBlockState(pos, state.withProperty(GROWTH, LeafGrowth.MATURED), 4);
        } // End of modification
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
            case 3:
                ret.add(new ItemStack(TBBlocks.peaceleaves, 1, 0));
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

    /**
     * AeXiaohu modified
     * 修复树叶会自然衰退等树叶应有的特性
     */
    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    } // End of modification


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
        if(leafType==3&&rand.nextInt(chance)==0)
            ret.add(new ItemStack(TBBlocks.peacesapling));

        chance = 200;
        if (fortune > 0)
        {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        if (world instanceof World) {
            this.dropApple((World) world, pos, state, chance);
        }
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}

