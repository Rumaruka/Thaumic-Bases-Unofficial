package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.common.block.TBAureliaBlock;
import com.rumaruka.thaumicbases.common.block.TBAureliaPetalsBlock;
import com.rumaruka.thaumicbases.common.block.TBAnvilBlock;
import com.rumaruka.thaumicbases.common.block.TBBriarBlock;
import com.rumaruka.thaumicbases.common.block.TBBrazierBlock;
import com.rumaruka.thaumicbases.common.block.TBCampfireBlock;
import com.rumaruka.thaumicbases.common.block.TBCropBlock;
import com.rumaruka.thaumicbases.common.block.TBLeavesBlock;
import com.rumaruka.thaumicbases.common.block.TBOverchanterBlock;
import com.rumaruka.thaumicbases.common.block.TBPyroSolidBlock;
import com.rumaruka.thaumicbases.common.block.TBRainbowCactusBlock;
import com.rumaruka.thaumicbases.common.block.TBRedlonStemBlock;
import com.rumaruka.thaumicbases.common.block.TBSaplingBlock;
import com.rumaruka.thaumicbases.common.block.TBSpikeBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class TBBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumicBases.MODID);

    // Crystal blocks
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_AIR = BLOCKS.registerSimpleBlock("crystalblockair",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_FIRE = BLOCKS.registerSimpleBlock("crystalblockfire",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_WATER = BLOCKS.registerSimpleBlock("crystalblockwater",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_EARTH = BLOCKS.registerSimpleBlock("crystalblockearth",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_ORDER = BLOCKS.registerSimpleBlock("crystalblockorder",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_ENTROPY = BLOCKS.registerSimpleBlock("crystalblockentropy",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_MIXED = BLOCKS.registerSimpleBlock("crystalblockmixed",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> CRYSTAL_BLOCK_TAINTED = BLOCKS.registerSimpleBlock("crystalblocktainted",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).noOcclusion());

    // Decorative / storage blocks
    public static final DeferredBlock<Block> QUICKSILVER_BLOCK = BLOCKS.registerSimpleBlock("quicksilverblock",
            BlockBehaviour.Properties.of().strength(0.5F));
    public static final DeferredBlock<Block> QUICKSILVER_BRICK = BLOCKS.registerSimpleBlock("quicksilverbrick",
            BlockBehaviour.Properties.of().strength(0.5F));
    public static final DeferredBlock<Block> DUST_BLOCK = BLOCKS.registerSimpleBlock("dustblock",
            BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.SAND));
    public static final DeferredBlock<Block> BLOCK_THAUMINITE = BLOCKS.registerSimpleBlock("blockthauminite",
            BlockBehaviour.Properties.of().strength(0.5F));
    public static final DeferredBlock<Block> IRON_GREATWOOD = BLOCKS.registerSimpleBlock("irongreatwood",
            BlockBehaviour.Properties.of().strength(0.5F));
    public static final DeferredBlock<Block> ELDRITCH_ARK = BLOCKS.registerSimpleBlock("eldritchark",
            BlockBehaviour.Properties.of().strength(0.5F));

    // Old-style blocks
    public static final DeferredBlock<Block> OLD_COBBLE = BLOCKS.registerSimpleBlock("oldcobble",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F));
    public static final DeferredBlock<Block> OLD_COBBLE_MOSSY = BLOCKS.registerSimpleBlock("oldcobblemossy",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F));
    public static final DeferredBlock<Block> OLD_GRAVEL = BLOCKS.registerSimpleBlock("oldgravel",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).sound(SoundType.GRAVEL));
    public static final DeferredBlock<Block> OLD_BRICK = BLOCKS.registerSimpleBlock("oldbrick",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F));
    public static final DeferredBlock<Block> OLD_LAPIS = BLOCKS.registerSimpleBlock("oldlapis",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F));
    public static final DeferredBlock<Block> OLD_IRON = BLOCKS.registerSimpleBlock("oldiron",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> OLD_GOLD = BLOCKS.registerSimpleBlock("oldgold",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> OLD_DIAMOND = BLOCKS.registerSimpleBlock("olddiamond",
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).sound(SoundType.METAL));

    // Crops
    public static final DeferredBlock<TBCropBlock> PLAX = BLOCKS.register("plax", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.PLAX_SEED.get(), 7, 4, true,
            List.of(
                    new TBCropBlock.DropEntry(() -> Items.STRING, 3, 3, 0.5F, 4),
                    new TBCropBlock.DropEntry(() -> TBItems.PLAX_SEED.get(), 1, 1, 0.5F, 4)
            )));

    public static final DeferredBlock<TBCropBlock> SWEED = BLOCKS.register("sweed", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.SWEED_SEED.get(), 3, 4, false,
            List.of(
                    new TBCropBlock.DropEntry(() -> Items.SUGAR, 1, 1, 0.5F, 4),
                    new TBCropBlock.DropEntry(() -> Items.SUGAR_CANE, 1, 1, 0.5F, 1)
            )));

    public static final DeferredBlock<TBCropBlock> METALLEAT = BLOCKS.register("metalleat", () -> new TBCropBlock(
            cropProperties(), TBItems.METALLEAT_SEED, 7, 8, true,
            List.of(
                    new TBCropBlock.DropEntry(() -> Items.IRON_NUGGET, 3, 3, 0.5F, 4),
                    new TBCropBlock.DropEntry(TBItems.METALLEAT_SEED, 1, 1, 0.5F, 4)
            )));

    public static final DeferredBlock<TBCropBlock> LAZULLIA = BLOCKS.register("lazullia", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.LAZULLIA_SEED.get(), 7, 16, true,
            List.of(
                    new TBCropBlock.DropEntry(() -> TBItems.LAPIS_SHARD.get(), 7, 7, 0.5F, 4),
                    new TBCropBlock.DropEntry(() -> TBItems.LAZULLIA_SEED.get(), 1, 1, 0.5F, 4)
            )));

    public static final DeferredBlock<TBCropBlock> LUCRITE = BLOCKS.register("lucrite", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.LUCRITE_SEED.get(), 7, 16, true,
            List.of(
                    new TBCropBlock.DropEntry(() -> Items.GOLD_NUGGET, 3, 3, 0.5F, 4),
                    new TBCropBlock.DropEntry(() -> Items.GOLDEN_CARROT, 1, 1, 0.1F, 1),
                    new TBCropBlock.DropEntry(() -> TBItems.LUCRITE_SEED.get(), 1, 1, 0.5F, 4)
            )));

    public static final DeferredBlock<TBCropBlock> TOBACCO = BLOCKS.register("tobacco", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.TOBACCO_SEED.get(), 7, 4, true,
            List.of(
                    new TBCropBlock.DropEntry(() -> TBItems.TOBACCO_LEAVES.get(), 1, 2, 0.5F, 4),
                    new TBCropBlock.DropEntry(() -> TBItems.TOBACCO_SEED.get(), 1, 1, 0.5F, 4)
            )));

    public static final DeferredBlock<TBCropBlock> GLIEONIA = BLOCKS.register("glieonia", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.GLIEONIA_SEED.get(), 3, 12, false,
            List.of(
                    new TBCropBlock.DropEntry(() -> Items.GLOWSTONE_DUST, 3, 3, 0.5F, 4),
                    new TBCropBlock.DropEntry(() -> TBItems.GLIEONIA_SEED.get(), 1, 1, 0.5F, 4)
            )));

    public static final DeferredBlock<TBCropBlock> KNOZE = BLOCKS.register("knoze", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.KNOZE_SEED.get(), 3, 12, false,
            List.of(
                    new TBCropBlock.DropEntry(() -> TBItems.KNOWLEDGE_SHARD.get(), 1, 1, 0.5F, 2),
                    new TBCropBlock.DropEntry(() -> TBItems.KNOZE_SEED.get(), 1, 1, 0.5F, 2)
            )));

    public static final DeferredBlock<TBCropBlock> VOID_PLANT = BLOCKS.register("voidplant", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.VOID_SEED.get(), 7, 32, true,
            List.of(
                    new TBCropBlock.DropEntry(() -> TBItems.VOID_SEED.get(), 1, 2, 0.5F, 2)
            )));

    // Flowers and bushes
    public static final DeferredBlock<TBAureliaBlock> AURELIA = BLOCKS.register("aurelia",
            () -> new TBAureliaBlock(BlockBehaviour.Properties.of().noCollission().randomTicks()
                    .instabreak().sound(SoundType.GRASS).lightLevel(state -> 8)));
    public static final DeferredBlock<TBAureliaPetalsBlock> AURELIA_PETALS = BLOCKS.register("aureliapetalb",
            () -> new TBAureliaPetalsBlock(BlockBehaviour.Properties.of().noCollission()
                    .instabreak().sound(SoundType.GRASS).lightLevel(state -> 5)));
    public static final DeferredBlock<TBBriarBlock> BRIAR = BLOCKS.register("briar",
            () -> new TBBriarBlock(BlockBehaviour.Properties.of().noCollission().randomTicks()
                    .instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<TBRainbowCactusBlock> RAINBOW_CACTUS = BLOCKS.register("rainbowcactus",
            () -> new TBRainbowCactusBlock(BlockBehaviour.Properties.of().strength(0.4F)
                    .sound(SoundType.WOOL).randomTicks()));

    public static final DeferredBlock<TBCropBlock> ASHROOM = BLOCKS.register("ashroom", () -> new TBCropBlock(
            cropProperties(), () -> TBItems.ASHROOM.get(), 3, 12, false,
            List.of(new TBCropBlock.DropEntry(() -> TBItems.ASHROOM.get(), 1, 1, 1.0F, 1))));

    // Heat sources
    public static final DeferredBlock<TBCampfireBlock> CAMPFIRE = BLOCKS.register("campfire",
            () -> new TBCampfireBlock(BlockBehaviour.Properties.of().strength(1.0F, 1.0F)
                    .sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<TBBrazierBlock> BRAZIER = BLOCKS.register("braizer",
            () -> new TBBrazierBlock(BlockBehaviour.Properties.of().strength(1.0F, 1.0F)
                    .sound(SoundType.STONE).noOcclusion()
                    .lightLevel(state -> state.getValue(TBBrazierBlock.STATE) == 1 ? 10 : 0)));

    // Redlon stem
    public static final DeferredBlock<TBRedlonStemBlock> REDLON_STEM = BLOCKS.register("redlonstem",
            () -> new TBRedlonStemBlock(BlockBehaviour.Properties.of().noCollission().randomTicks()
                    .instabreak().sound(SoundType.GRASS)));

    // Crystallized pyrofluid
    public static final DeferredBlock<TBPyroSolidBlock> PYRO_SOLID = BLOCKS.register("pyrosolid",
            () -> new TBPyroSolidBlock(BlockBehaviour.Properties.of().strength(1.0F)
                    .sound(SoundType.STONE)));

    // Overchanting table
    public static final DeferredBlock<TBOverchanterBlock> OVERCHANTER = BLOCKS.register("overchanter",
            () -> new TBOverchanterBlock(BlockBehaviour.Properties.of().strength(2.0F)
                    .sound(SoundType.STONE).noOcclusion().requiresCorrectToolForDrops()));

    // Anvils
    public static final DeferredBlock<TBAnvilBlock> THAUMIC_ANVIL = BLOCKS.register("thaumic_anvil",
            () -> new TBAnvilBlock(BlockBehaviour.Properties.of().strength(5.0F, 2000.0F)
                    .sound(SoundType.ANVIL).noOcclusion(), false));
    public static final DeferredBlock<TBAnvilBlock> VOID_ANVIL = BLOCKS.register("void_anvil",
            () -> new TBAnvilBlock(BlockBehaviour.Properties.of().strength(5.0F, 2000.0F)
                    .sound(SoundType.ANVIL).noOcclusion().randomTicks(), true));

    // Spikes
    public static final DeferredBlock<TBSpikeBlock> IRON_SPIKE = BLOCKS.register("ironspike",
            () -> new TBSpikeBlock(spikeProperties(), 8.0F, false));
    public static final DeferredBlock<TBSpikeBlock> THAUM_SPIKE = BLOCKS.register("thaumspike",
            () -> new TBSpikeBlock(spikeProperties(), 14.0F, true));
    public static final DeferredBlock<TBSpikeBlock> VOID_SPIKE = BLOCKS.register("voidspike",
            () -> new TBSpikeBlock(spikeProperties(), 20.0F, true));

    // Trees: golden apple
    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_LOG = BLOCKS.register("goldenlogs",
            () -> new RotatedPillarBlock(logProperties()));
    public static final DeferredBlock<TBLeavesBlock> GOLDEN_LEAVES = BLOCKS.register("goldenleaves",
            () -> new TBLeavesBlock(leavesProperties(), () -> TBBlocks.GOLDEN_SAPLING.get(), () -> Items.GOLDEN_APPLE, 0.05F));
    public static final DeferredBlock<Block> GOLDEN_PLANKS = BLOCKS.registerSimpleBlock("goldenplanks", plankProperties());
    public static final DeferredBlock<TBSaplingBlock> GOLDEN_SAPLING = BLOCKS.register("goldensapling",
            () -> new TBSaplingBlock(saplingProperties(), () -> GOLDEN_LOG.get(), () -> GOLDEN_LEAVES.get()));

    // Trees: infernal
    public static final DeferredBlock<RotatedPillarBlock> NETHER_LOG = BLOCKS.register("netherlogs",
            () -> new RotatedPillarBlock(logProperties()));
    public static final DeferredBlock<TBLeavesBlock> NETHER_LEAVES = BLOCKS.register("netherleaves",
            () -> new TBLeavesBlock(leavesProperties(), () -> TBBlocks.NETHER_SAPLING.get(), null, 0.0F));
    public static final DeferredBlock<Block> NETHER_PLANKS = BLOCKS.registerSimpleBlock("netherplanks", plankProperties());
    public static final DeferredBlock<TBSaplingBlock> NETHER_SAPLING = BLOCKS.register("nethersapling",
            () -> new TBSaplingBlock(saplingProperties(), () -> NETHER_LOG.get(), () -> NETHER_LEAVES.get()));

    // Trees: enden
    public static final DeferredBlock<RotatedPillarBlock> ENDER_LOG = BLOCKS.register("enderlogs",
            () -> new RotatedPillarBlock(logProperties()));
    public static final DeferredBlock<TBLeavesBlock> ENDER_LEAVES = BLOCKS.register("enderleaves",
            () -> new TBLeavesBlock(leavesProperties(), () -> TBBlocks.ENDER_SAPLING.get(), null, 0.0F));
    public static final DeferredBlock<Block> ENDER_PLANKS = BLOCKS.registerSimpleBlock("enderplanks", plankProperties());
    public static final DeferredBlock<TBSaplingBlock> ENDER_SAPLING = BLOCKS.register("endersapling",
            () -> new TBSaplingBlock(saplingProperties(), () -> ENDER_LOG.get(), () -> ENDER_LEAVES.get()));

    // Trees: peaceful
    public static final DeferredBlock<RotatedPillarBlock> PEACE_LOG = BLOCKS.register("peacelogs",
            () -> new RotatedPillarBlock(logProperties()));
    public static final DeferredBlock<TBLeavesBlock> PEACE_LEAVES = BLOCKS.register("peaceleaves",
            () -> new TBLeavesBlock(leavesProperties(), () -> TBBlocks.PEACE_SAPLING.get(), null, 0.0F));
    public static final DeferredBlock<Block> PEACE_PLANKS = BLOCKS.registerSimpleBlock("peaceplanks", plankProperties());
    public static final DeferredBlock<TBSaplingBlock> PEACE_SAPLING = BLOCKS.register("peacesapling",
            () -> new TBSaplingBlock(saplingProperties(), () -> PEACE_LOG.get(), () -> PEACE_LEAVES.get()));

    private static BlockBehaviour.Properties cropProperties() {
        return BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP);
    }

    private static BlockBehaviour.Properties spikeProperties() {
        return BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.METAL).noOcclusion();
    }

    private static BlockBehaviour.Properties logProperties() {
        return BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties plankProperties() {
        return BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties leavesProperties() {
        return BlockBehaviour.Properties.of().strength(0.2F).sound(SoundType.GRASS).noOcclusion();
    }

    private static BlockBehaviour.Properties saplingProperties() {
        return BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS);
    }

    private TBBlocks() {
    }
}
