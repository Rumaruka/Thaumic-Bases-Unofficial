package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.common.TBMaterials;
import com.rumaruka.thaumicbases.common.item.TBArmorItem;
import com.rumaruka.thaumicbases.common.item.TBHerobrinesScytheItem;
import com.rumaruka.thaumicbases.common.item.TBMortarItem;
import com.rumaruka.thaumicbases.common.item.TBPyrobucketItem;
import com.rumaruka.thaumicbases.common.item.TBRevolverItem;
import com.rumaruka.thaumicbases.common.item.TBRosehipSyrupItem;
import com.rumaruka.thaumicbases.common.item.TBSmokingPipeItem;
import com.rumaruka.thaumicbases.common.item.TBTobaccoItem;
import com.rumaruka.thaumicbases.common.item.TBUkuleleItem;
import com.rumaruka.thaumicbases.common.item.TBVoidFlintAndSteelItem;
import com.rumaruka.thaumicbases.common.item.TBVoidShearsItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class TBItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumicBases.MODID);

    public static final List<DeferredItem<? extends Item>> CREATIVE_TAB_ITEMS = new ArrayList<>();

    // Materials
    public static final DeferredItem<Item> NUGGET_THAUMINITE = registerItem(ITEMS.registerSimpleItem("nuggetthauminite"));
    public static final DeferredItem<Item> THAUMINITE_INGOT = registerItem(ITEMS.registerSimpleItem("thauminite_ingot"));
    public static final DeferredItem<Item> THAUMINITE_PLATE = registerItem(ITEMS.registerSimpleItem("thauminite_plate"));
    public static final DeferredItem<Item> BLOODY_CLOTH = registerItem(ITEMS.registerSimpleItem("bloodycloth"));

    // Thauminite tools
    public static final DeferredItem<SwordItem> THAUMINITE_SWORD = registerItem(ITEMS.register("thauminitesword",
            () -> new SwordItem(TBMaterials.THAUMINITE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(TBMaterials.THAUMINITE, 3, -2.4F)))));
    public static final DeferredItem<DiggerItem> THAUMINITE_PICKAXE = registerItem(ITEMS.register("thauminitepickaxe",
            () -> new DiggerItem(TBMaterials.THAUMINITE, net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(TBMaterials.THAUMINITE, 1.0F, -2.8F)))));
    public static final DeferredItem<AxeItem> THAUMINITE_AXE = registerItem(ITEMS.register("thauminiteaxe",
            () -> new AxeItem(TBMaterials.THAUMINITE, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(TBMaterials.THAUMINITE, 5.0F, -3.0F)))));
    public static final DeferredItem<ShovelItem> THAUMINITE_SHOVEL = registerItem(ITEMS.register("thauminiteshovel",
            () -> new ShovelItem(TBMaterials.THAUMINITE, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(TBMaterials.THAUMINITE, 1.5F, -3.0F)))));
    public static final DeferredItem<HoeItem> THAUMINITE_HOE = registerItem(ITEMS.register("thauminitehoe",
            () -> new HoeItem(TBMaterials.THAUMINITE, new Item.Properties()
                    .attributes(DiggerItem.createAttributes(TBMaterials.THAUMINITE, 0.0F, -3.0F)))));

    // Armor
    public static final DeferredItem<TBArmorItem> THAUMINITE_HELMET = registerItem(ITEMS.register("thauminitehelmet",
            () -> new TBArmorItem(TBArmorMaterials.THAUMINITE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(27)))));
    public static final DeferredItem<TBArmorItem> THAUMINITE_CHEST = registerItem(ITEMS.register("thauminitechest",
            () -> new TBArmorItem(TBArmorMaterials.THAUMINITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(27)))));
    public static final DeferredItem<TBArmorItem> THAUMINITE_LEGS = registerItem(ITEMS.register("thauminitelegs",
            () -> new TBArmorItem(TBArmorMaterials.THAUMINITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(27)))));
    public static final DeferredItem<TBArmorItem> THAUMINITE_BOOTS = registerItem(ITEMS.register("thauminiteboots",
            () -> new TBArmorItem(TBArmorMaterials.THAUMINITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(27)))));

    public static final DeferredItem<TBArmorItem> BLOODY_CHEST = registerItem(ITEMS.register("bloodychest",
            () -> new TBArmorItem(TBArmorMaterials.BLOODY, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(21)))
                    .withBonus(Attributes.MAX_HEALTH,
                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "bloody_chest_health"),
                                    0.4D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                            EquipmentSlotGroup.CHEST)));
    public static final DeferredItem<TBArmorItem> BLOODY_LEGS = registerItem(ITEMS.register("bloodylegs",
            () -> new TBArmorItem(TBArmorMaterials.BLOODY, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(21)))
                    .withBonus(Attributes.KNOCKBACK_RESISTANCE,
                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "bloody_legs_knockback"),
                                    1.0D, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.LEGS)));
    public static final DeferredItem<TBArmorItem> BLOODY_BOOTS = registerItem(ITEMS.register("bloodyboots",
            () -> new TBArmorItem(TBArmorMaterials.BLOODY, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(21)))
                    .withBonus(Attributes.MOVEMENT_SPEED,
                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "bloody_boots_speed"),
                                    0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                            EquipmentSlotGroup.FEET)));

    // Seeds
    public static final DeferredItem<ItemNameBlockItem> PLAX_SEED = registerItem(ITEMS.register("plaxseed",
            () -> new ItemNameBlockItem(TBBlocks.PLAX.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> SWEED_SEED = registerItem(ITEMS.register("sweedseed",
            () -> new ItemNameBlockItem(TBBlocks.SWEED.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> METALLEAT_SEED = registerItem(ITEMS.register("metalleatseed",
            () -> new ItemNameBlockItem(TBBlocks.METALLEAT.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> LAZULLIA_SEED = registerItem(ITEMS.register("lazulliaseed",
            () -> new ItemNameBlockItem(TBBlocks.LAZULLIA.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> LUCRITE_SEED = registerItem(ITEMS.register("lucriteseed",
            () -> new ItemNameBlockItem(TBBlocks.LUCRITE.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> TOBACCO_SEED = registerItem(ITEMS.register("tobaccoseed",
            () -> new ItemNameBlockItem(TBBlocks.TOBACCO.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> GLIEONIA_SEED = registerItem(ITEMS.register("glieoniaseed",
            () -> new ItemNameBlockItem(TBBlocks.GLIEONIA.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> KNOZE_SEED = registerItem(ITEMS.register("knozeseed",
            () -> new ItemNameBlockItem(TBBlocks.KNOZE.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> VOID_SEED = registerItem(ITEMS.register("voidseed",
            () -> new ItemNameBlockItem(TBBlocks.VOID_PLANT.get(), new Item.Properties())));
    public static final DeferredItem<ItemNameBlockItem> REDLON_SEED = registerItem(ITEMS.register("redlonseed",
            () -> new ItemNameBlockItem(TBBlocks.REDLON_STEM.get(), new Item.Properties())));

    // Crop products / curio items
    public static final DeferredItem<Item> TOBACCO_LEAVES = registerItem(ITEMS.registerSimpleItem("tobacco_leaves"));
    public static final DeferredItem<Item> KNOWLEDGE_SHARD = registerItem(ITEMS.registerSimpleItem("knowledge_shard"));
    public static final DeferredItem<Item> LAPIS_SHARD = registerItem(ITEMS.registerSimpleItem("lapis_shard"));
    public static final DeferredItem<Item> AURELIA_PETAL = registerItem(ITEMS.registerSimpleItem("aureliapetal"));
    public static final DeferredItem<Item> BRIAR_SEEDBAG = registerItem(ITEMS.registerSimpleItem("briar_seedbag"));

    // Void metal tools
    public static final DeferredItem<TBVoidShearsItem> VOID_SHEARS = registerItem(ITEMS.register("voidshears",
            () -> new TBVoidShearsItem(new Item.Properties().durability(476))));
    public static final DeferredItem<TBVoidFlintAndSteelItem> VOID_FLINT_AND_STEEL = registerItem(ITEMS.register("voidfas",
            () -> new TBVoidFlintAndSteelItem(new Item.Properties().durability(476))));

    // Utility items
    public static final DeferredItem<TBMortarItem> MORTAR = registerItem(ITEMS.register("mortar",
            () -> new TBMortarItem(new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBRosehipSyrupItem> ROSEHIP_SYRUP = registerItem(ITEMS.register("rosehipsyrup",
            () -> new TBRosehipSyrupItem(new Item.Properties().stacksTo(16)
                    .food(new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).alwaysEdible().build()))));
    public static final DeferredItem<TBPyrobucketItem> PYROBUCKET = registerItem(ITEMS.register("pyrobucket",
            () -> new TBPyrobucketItem(new Item.Properties().stacksTo(1))));

    // Weapons
    public static final DeferredItem<TBHerobrinesScytheItem> HEROBRINES_SCYTHE = registerItem(ITEMS.register("herobrinesscythe",
            () -> new TBHerobrinesScytheItem(Tiers.NETHERITE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.NETHERITE, 10, -3.0F)))));
    public static final DeferredItem<TBRevolverItem> REVOLVER = registerItem(ITEMS.register("revolver",
            () -> new TBRevolverItem(new Item.Properties().stacksTo(1))));

    // Tobacco and pipes
    public static final DeferredItem<TBSmokingPipeItem> GREATWOOD_PIPE = registerItem(ITEMS.register("greatwoodpipe",
            () -> new TBSmokingPipeItem(false, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBSmokingPipeItem> SILVERWOOD_PIPE = registerItem(ITEMS.register("silverwoodpipe",
            () -> new TBSmokingPipeItem(true, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_PILE = registerItem(ITEMS.register("tobacco_pile",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.PILE, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_ELDRITCH = registerItem(ITEMS.register("tobacco_eldritch",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.ELDRITCH, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_FIGHTING = registerItem(ITEMS.register("tobacco_fighting",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.FIGHTING, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_HUNGER = registerItem(ITEMS.register("tobacco_hunger",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.HUNGER, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_KNOWLEDGE = registerItem(ITEMS.register("tobacco_knowledge",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.KNOWLEDGE, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_MINING = registerItem(ITEMS.register("tobacco_mining",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.MINING, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_SANITY = registerItem(ITEMS.register("tobacco_sanity",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.SANITY, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_TAINTED = registerItem(ITEMS.register("tobacco_tainted",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.TAINTED, new Item.Properties())));
    public static final DeferredItem<TBTobaccoItem> TOBACCO_WISPY = registerItem(ITEMS.register("tobacco_wispy",
            () -> new TBTobaccoItem(TBTobaccoItem.TobaccoType.WISPY, new Item.Properties())));

    // Ukuleles
    public static final DeferredItem<TBUkuleleItem> UKULELE = registerItem(ITEMS.register("ukulele",
            () -> new TBUkuleleItem(0, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_KNOWLEDGE = registerItem(ITEMS.register("ukulele_knowledge",
            () -> new TBUkuleleItem(1, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_CALMING = registerItem(ITEMS.register("ukulele_calming",
            () -> new TBUkuleleItem(2, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_ELECTRIC = registerItem(ITEMS.register("ukulele_electric",
            () -> new TBUkuleleItem(3, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_RESISTANCE = registerItem(ITEMS.register("ukulele_resistance",
            () -> new TBUkuleleItem(4, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_BUFFING = registerItem(ITEMS.register("ukulele_buffing",
            () -> new TBUkuleleItem(5, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_CONFUSION = registerItem(ITEMS.register("ukulele_confusion",
            () -> new TBUkuleleItem(6, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_GROWTH = registerItem(ITEMS.register("ukulele_growth",
            () -> new TBUkuleleItem(7, new Item.Properties().stacksTo(1))));
    public static final DeferredItem<TBUkuleleItem> UKULELE_LOVE = registerItem(ITEMS.register("ukulele_love",
            () -> new TBUkuleleItem(8, new Item.Properties().stacksTo(1))));

    // Flower / bush block items
    public static final DeferredItem<BlockItem> AURELIA = registerBlockItem(TBBlocks.AURELIA);
    public static final DeferredItem<BlockItem> ASHROOM = registerBlockItem(TBBlocks.ASHROOM);
    public static final DeferredItem<BlockItem> BRIAR = registerBlockItem(TBBlocks.BRIAR);
    public static final DeferredItem<BlockItem> RAINBOW_CACTUS = registerBlockItem(TBBlocks.RAINBOW_CACTUS);
    public static final DeferredItem<BlockItem> CAMPFIRE = registerBlockItem(TBBlocks.CAMPFIRE);
    public static final DeferredItem<BlockItem> BRAZIER = registerBlockItem(TBBlocks.BRAZIER);
    public static final DeferredItem<BlockItem> PYRO_SOLID = registerBlockItem(TBBlocks.PYRO_SOLID);
    public static final DeferredItem<BlockItem> OVERCHANTER = registerBlockItem(TBBlocks.OVERCHANTER);
    public static final DeferredItem<BlockItem> THAUMIC_ANVIL = registerBlockItem(TBBlocks.THAUMIC_ANVIL);
    public static final DeferredItem<BlockItem> VOID_ANVIL = registerBlockItem(TBBlocks.VOID_ANVIL);

    // Block items
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_AIR = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_AIR);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_FIRE = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_FIRE);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_WATER = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_WATER);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_EARTH = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_EARTH);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_ORDER = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_ORDER);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_ENTROPY = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_ENTROPY);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_MIXED = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_MIXED);
    public static final DeferredItem<BlockItem> CRYSTAL_BLOCK_TAINTED = registerBlockItem(TBBlocks.CRYSTAL_BLOCK_TAINTED);

    public static final DeferredItem<BlockItem> QUICKSILVER_BLOCK = registerBlockItem(TBBlocks.QUICKSILVER_BLOCK);
    public static final DeferredItem<BlockItem> QUICKSILVER_BRICK = registerBlockItem(TBBlocks.QUICKSILVER_BRICK);
    public static final DeferredItem<BlockItem> DUST_BLOCK = registerBlockItem(TBBlocks.DUST_BLOCK);
    public static final DeferredItem<BlockItem> BLOCK_THAUMINITE = registerBlockItem(TBBlocks.BLOCK_THAUMINITE);
    public static final DeferredItem<BlockItem> IRON_GREATWOOD = registerBlockItem(TBBlocks.IRON_GREATWOOD);
    public static final DeferredItem<BlockItem> ELDRITCH_ARK = registerBlockItem(TBBlocks.ELDRITCH_ARK);

    public static final DeferredItem<BlockItem> OLD_COBBLE = registerBlockItem(TBBlocks.OLD_COBBLE);
    public static final DeferredItem<BlockItem> OLD_COBBLE_MOSSY = registerBlockItem(TBBlocks.OLD_COBBLE_MOSSY);
    public static final DeferredItem<BlockItem> OLD_GRAVEL = registerBlockItem(TBBlocks.OLD_GRAVEL);
    public static final DeferredItem<BlockItem> OLD_BRICK = registerBlockItem(TBBlocks.OLD_BRICK);
    public static final DeferredItem<BlockItem> OLD_LAPIS = registerBlockItem(TBBlocks.OLD_LAPIS);
    public static final DeferredItem<BlockItem> OLD_IRON = registerBlockItem(TBBlocks.OLD_IRON);
    public static final DeferredItem<BlockItem> OLD_GOLD = registerBlockItem(TBBlocks.OLD_GOLD);
    public static final DeferredItem<BlockItem> OLD_DIAMOND = registerBlockItem(TBBlocks.OLD_DIAMOND);

    public static final DeferredItem<BlockItem> IRON_SPIKE = registerBlockItem(TBBlocks.IRON_SPIKE);
    public static final DeferredItem<BlockItem> THAUM_SPIKE = registerBlockItem(TBBlocks.THAUM_SPIKE);
    public static final DeferredItem<BlockItem> VOID_SPIKE = registerBlockItem(TBBlocks.VOID_SPIKE);

    public static final DeferredItem<BlockItem> GOLDEN_LOG = registerBlockItem(TBBlocks.GOLDEN_LOG);
    public static final DeferredItem<BlockItem> GOLDEN_LEAVES = registerBlockItem(TBBlocks.GOLDEN_LEAVES);
    public static final DeferredItem<BlockItem> GOLDEN_PLANKS = registerBlockItem(TBBlocks.GOLDEN_PLANKS);
    public static final DeferredItem<BlockItem> GOLDEN_SAPLING = registerBlockItem(TBBlocks.GOLDEN_SAPLING);

    public static final DeferredItem<BlockItem> NETHER_LOG = registerBlockItem(TBBlocks.NETHER_LOG);
    public static final DeferredItem<BlockItem> NETHER_LEAVES = registerBlockItem(TBBlocks.NETHER_LEAVES);
    public static final DeferredItem<BlockItem> NETHER_PLANKS = registerBlockItem(TBBlocks.NETHER_PLANKS);
    public static final DeferredItem<BlockItem> NETHER_SAPLING = registerBlockItem(TBBlocks.NETHER_SAPLING);

    public static final DeferredItem<BlockItem> ENDER_LOG = registerBlockItem(TBBlocks.ENDER_LOG);
    public static final DeferredItem<BlockItem> ENDER_LEAVES = registerBlockItem(TBBlocks.ENDER_LEAVES);
    public static final DeferredItem<BlockItem> ENDER_PLANKS = registerBlockItem(TBBlocks.ENDER_PLANKS);
    public static final DeferredItem<BlockItem> ENDER_SAPLING = registerBlockItem(TBBlocks.ENDER_SAPLING);

    public static final DeferredItem<BlockItem> PEACE_LOG = registerBlockItem(TBBlocks.PEACE_LOG);
    public static final DeferredItem<BlockItem> PEACE_LEAVES = registerBlockItem(TBBlocks.PEACE_LEAVES);
    public static final DeferredItem<BlockItem> PEACE_PLANKS = registerBlockItem(TBBlocks.PEACE_PLANKS);
    public static final DeferredItem<BlockItem> PEACE_SAPLING = registerBlockItem(TBBlocks.PEACE_SAPLING);

    private static <T extends Item> DeferredItem<T> registerItem(DeferredItem<T> item) {
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    private static <B extends Block> DeferredItem<BlockItem> registerBlockItem(DeferredBlock<B> block) {
        DeferredItem<BlockItem> item = ITEMS.registerSimpleBlockItem(block);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    private TBItems() {
    }
}
