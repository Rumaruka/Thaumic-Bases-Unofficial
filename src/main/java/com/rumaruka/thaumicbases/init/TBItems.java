package com.rumaruka.thaumicbases.init;


import com.google.common.base.Strings;
import com.rumaruka.thaumicbases.client.creativetabs.TBCreativeTabs;
import com.rumaruka.thaumicbases.common.TBMaterial;
import com.rumaruka.thaumicbases.common.item.TBBloodyCloth;
import com.rumaruka.thaumicbases.common.item.*;
import com.rumaruka.thaumicbases.common.item.recourse.*;
import com.rumaruka.thaumicbases.core.TBCore;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class TBItems {

    public static ArmorMaterial thauminiteA = EnumHelper.addArmorMaterial("ATHAUMINITE", "thaumicbases:textures/items/armor/thauminite/thauminite", 27, new int[]{3, 8, 6, 3}, 17,SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F).setRepairItem(new ItemStack(TBItems.thauminite_ingot));
    public static ArmorMaterial bloodyA = EnumHelper.addArmorMaterial("TBBLOODY", "thaumicbases:textures/items/armor/bloody/bloody" ,21, new int[]{2, 6, 5, 2}, 21, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F).setRepairItem(new ItemStack(TBItems.bloodycloth));




    //Metall and Material
    public static Item nuggetthauminite;
    public static Item thauminite_ingot;
    public static Item thauminite_plate;

    public static Item bloodycloth;

    //Tools
    public static Item thauminiteaxe;
    public static Item thauminitepickaxe;
    public static Item thauminitehoe;
    public static Item thauminiteshovel;
    public static Item thauminitesword;



    public static Item voidshears;
    public static Item voidfas;

    //Pyr
    public static Item pyrobucket;

    //Seeds
    public static Item plaxseed;
    public static Item sweedseed;
    public static Item aureliapetal;
    public static Item metalleatseed;
    public static Item lucriteseed;
    public static Item lazulliaseed;
    public static Item redlonseed;
    public static Item glieoniaseed;
    public static Item briar_seedbag;
    public static Item voidseed;
    //Armor
    public static Item bloodychest;
    public static Item bloodylegs;
    public static Item bloodyboots;

    public static Item thauminitehelmet;
    public static Item thauminitechest;
    public static Item thauminitelegs;
    public static Item thauminiteboots;

    //Tools
    public static Item herobrinesscythe;
    //Tobacco
    public static Item tobaccoseed;
    public static Item tobacco_pile;
    public  static Item tobacco_eldritch;
    public  static Item tobacco_fighting;
    public  static Item tobacco_hunger;
    public  static Item tobacco_knowledge;
    public  static Item tobacco_mining;
    public  static Item tobacco_sanity;
    public  static Item tobacco_tainted;
    public  static Item tobacco_wispy;
    public  static Item tobacco_leaves;
    //Pipe
    public static Item greatwoodpipe;
    public static Item silverwoodpipe;
    //Syrup
    public static Item rosehipsyrup;


    //Mortar
    public  static  Item mortar;
    public static Item revolver;
    public static Item bullet;
public static Item knozeseed;
    public static Item knowledge_shard;

    public static Block[] crystalBlock = new Block[]{TBBlocks.crystalblocktainted, TBBlocks.crystalblockentropy, TBBlocks.crystalblockorder, TBBlocks.crystalblockearth, TBBlocks.crystalblockfire, TBBlocks.crystalblockair, TBBlocks.crystalblockwater, TBBlocks.crystalblockmixed};

    public static void init(){

        nuggetthauminite = new TBItemNuggetThauminite().setUnlocalizedName("nuggetthauminite").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminite_ingot = new TBItemThauminiteIngot().setUnlocalizedName("thauminite_ingot").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminite_plate = new TBItemThauminitePlate().setUnlocalizedName("thauminite_plate").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        bloodycloth = new TBBloodyCloth().setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs).setUnlocalizedName("bloodycloth");

        thauminiteaxe =  new ItemThauminiteAxe(TBMaterial.thauminite).setUnlocalizedName("thauminiteaxe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminitehoe =  new ItemThauminiteHoe(TBMaterial.thauminite).setUnlocalizedName("thauminitehoe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminitepickaxe =  new ItemThauminitePickaxe(TBMaterial.thauminite).setUnlocalizedName("thauminitepickaxe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminiteshovel =  new ItemThauminiteShovel(TBMaterial.thauminite).setUnlocalizedName("thauminiteshovel").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminitesword =  new ItemThauminiteSword(TBMaterial.thauminite).setUnlocalizedName("thauminitesword").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        voidshears = new ItemVoidSheard().setUnlocalizedName("voidshears").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        voidfas = new ItemVoidFlintAndSteel().setUnlocalizedName("voidfas").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);


        plaxseed = new ItemSeeds(TBBlocks.plax,Blocks.FARMLAND).setUnlocalizedName("plaxseed");
        sweedseed = new ItemGrassSeeds(TBBlocks.sweed).setUnlocalizedName("sweedseed");
        tobaccoseed = new ItemSeeds(TBBlocks.tobacco,Blocks.FARMLAND).setUnlocalizedName("tobaccoseed");
        aureliapetal = new ItemAureliaPetal().setUnlocalizedName("aureliapetal");
        metalleatseed = new ItemSeeds(TBBlocks.metalleat,Blocks.FARMLAND).setUnlocalizedName("metalleatseed");
        lazulliaseed = new ItemSeeds(TBBlocks.lazullia,Blocks.FARMLAND).setUnlocalizedName("lazulliaseed");
        redlonseed = new ItemSeeds(TBBlocks.redlonstem,Blocks.FARMLAND).setUnlocalizedName("redlonseed");
        lucriteseed = new ItemSeeds(TBBlocks.lucrite,Blocks.FARMLAND).setUnlocalizedName("lucriteseed");

        glieoniaseed = new ItemGrassSeeds(TBBlocks.glieonia).setUnlocalizedName("glieoniaseed"); // AeXiaohu modified 修复荧粉花只能种在耕地上，现在只能种在草地上

        knozeseed = new ItemKnozeSeeds(TBBlocks.knoze).setUnlocalizedName("knozeseed");

        voidseed = new ItemSeeds(TBBlocks.voidplant,Blocks.FARMLAND).setUnlocalizedName("voidseed");
        briar_seedbag = new ItemBriarSeedbag().setUnlocalizedName("briar_seedbag").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        knowledge_shard = new ItemKnowledgeShard().setUnlocalizedName("knowledge_shard").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        bloodychest = new ItemBloodyArmor(bloodyA,0,1,EntityEquipmentSlot.CHEST).setUnlocalizedName("bloodychest").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        bloodylegs = new ItemBloodyArmor(bloodyA,0,2, EntityEquipmentSlot.LEGS).setUnlocalizedName("bloodylegs").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        bloodyboots = new ItemBloodyArmor(bloodyA,0,3, EntityEquipmentSlot.FEET).setUnlocalizedName("bloodyboots").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        thauminitehelmet = new ItemThauminiteArmor(thauminiteA,0,0,EntityEquipmentSlot.HEAD).setUnlocalizedName("thauminitehelmet").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminitechest = new ItemThauminiteArmor(thauminiteA,0,1,EntityEquipmentSlot.CHEST).setUnlocalizedName("thauminitechest").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminitelegs = new ItemThauminiteArmor(thauminiteA,0,2,EntityEquipmentSlot.LEGS).setUnlocalizedName("thauminitelegs").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        thauminiteboots = new ItemThauminiteArmor(thauminiteA,0,3,EntityEquipmentSlot.FEET).setUnlocalizedName("thauminiteboots").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);



        herobrinesscythe = new ItemHerobrinesScythe().setUnlocalizedName("herobrinesscythe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        tobacco_pile = new TBTobacco().setUnlocalizedName("tobacco_pile").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_eldritch = new TBTobacco().setUnlocalizedName("tobacco_eldritch").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_fighting = new TBTobacco().setUnlocalizedName("tobacco_fighting").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_hunger = new TBTobacco().setUnlocalizedName("tobacco_hunger").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_knowledge = new TBTobacco().setUnlocalizedName("tobacco_knowledge").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_mining = new TBTobacco().setUnlocalizedName("tobacco_mining").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_sanity = new TBTobacco().setUnlocalizedName("tobacco_sanity").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_tainted = new TBTobacco().setUnlocalizedName("tobacco_tainted").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_wispy = new TBTobacco().setUnlocalizedName("tobacco_wispy").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        tobacco_leaves = new ItemTobaccoLeaves().setUnlocalizedName("tobacco_leaves").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        greatwoodpipe = new ItemSmokingPipe(false).setUnlocalizedName("greatwoodpipe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        silverwoodpipe = new ItemSmokingPipe(true).setUnlocalizedName("silverwoodpipe").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        mortar = new ItemMortarAndPesle().setUnlocalizedName("mortar").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        rosehipsyrup = new ItemRosehipSyrup().setUnlocalizedName("rosehipsyrup").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

        revolver = new ItemRevolver().setUnlocalizedName("revolver").setMaxStackSize(1).setMaxDamage(1561).setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
        bullet = new ItemBullet().setUnlocalizedName("bullet").setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);
    }



    public static void InGameRegistr(){
        TBItems.registerItem(nuggetthauminite, nuggetthauminite.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminite_ingot,thauminite_ingot.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminite_plate,thauminite_plate.getUnlocalizedName().substring(5));
        TBItems.registerItem(bloodycloth,bloodycloth.getUnlocalizedName().substring(5));

        TBItems.registerItem(thauminiteaxe,thauminiteaxe.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminitepickaxe,thauminitepickaxe.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminitesword,thauminitesword.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminiteshovel,thauminiteshovel.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminitehoe,thauminitehoe.getUnlocalizedName().substring(5));

        TBItems.registerItem(voidshears,voidshears.getUnlocalizedName().substring(5));
        TBItems.registerItem(voidfas,voidfas.getUnlocalizedName().substring(5));

        TBItems.registerItem(plaxseed,plaxseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(sweedseed,sweedseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(aureliapetal,aureliapetal.getUnlocalizedName().substring(5));
        TBItems.registerItem(metalleatseed,metalleatseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(lazulliaseed,lazulliaseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(briar_seedbag,briar_seedbag.getUnlocalizedName().substring(5));
        TBItems.registerItem(redlonseed,redlonseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(lucriteseed,lucriteseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(glieoniaseed,glieoniaseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(voidseed,voidseed.getUnlocalizedName().substring(5));

        TBItems.registerItem(knozeseed,knozeseed.getUnlocalizedName().substring(5));

        TBItems.registerItem(bloodychest,bloodychest.getUnlocalizedName().substring(5));
        TBItems.registerItem(bloodylegs,bloodylegs.getUnlocalizedName().substring(5));
        TBItems.registerItem(bloodyboots,bloodyboots.getUnlocalizedName().substring(5));

        TBItems.registerItem(thauminitehelmet,thauminitehelmet.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminitechest,thauminitechest.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminitelegs,thauminitelegs.getUnlocalizedName().substring(5));
        TBItems.registerItem(thauminiteboots,thauminiteboots.getUnlocalizedName().substring(5));

        TBItems.registerItem(herobrinesscythe,herobrinesscythe.getUnlocalizedName().substring(5));

        TBItems.registerItem(tobacco_eldritch,tobacco_eldritch.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_fighting,tobacco_fighting.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_hunger,tobacco_hunger.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_knowledge,tobacco_knowledge.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_mining,tobacco_mining.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_pile,tobacco_pile.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_sanity,tobacco_sanity.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_tainted,tobacco_tainted.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_wispy,tobacco_wispy.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobaccoseed,tobaccoseed.getUnlocalizedName().substring(5));
        TBItems.registerItem(tobacco_leaves,tobacco_leaves.getUnlocalizedName().substring(5));
        TBItems.registerItem(mortar,mortar.getUnlocalizedName().substring(5));

        TBItems.registerItem(greatwoodpipe,greatwoodpipe.getUnlocalizedName().substring(5));
        TBItems.registerItem(silverwoodpipe,silverwoodpipe.getUnlocalizedName().substring(5));

        TBItems.registerItem(rosehipsyrup,rosehipsyrup.getUnlocalizedName().substring(5));

        TBItems.registerItem(revolver,revolver.getUnlocalizedName().substring(5));
        TBItems.registerItem(bullet,bullet.getUnlocalizedName().substring(5));
        TBItems.registerItem(knowledge_shard,knowledge_shard.getUnlocalizedName().substring(5));
    }










    @Deprecated
    public static void registerItem(Item item, String name)
    {
        if (item.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a item with no name: " + item);
        if (item.getRegistryName() != null && !item.getRegistryName().toString().equals(name))
            throw new IllegalArgumentException("Attempted to register a item with conflicting names. Old: " + item.getRegistryName() + " New: " + name);
        ForgeRegistries.ITEMS.register(item.getRegistryName() == null ? item.setRegistryName(name) : item);
    }

    public static void Renders(){

        renderItems(nuggetthauminite);
        renderItems(thauminite_ingot);
        renderItems(thauminite_plate);
        renderItems(bloodycloth);

        renderItems(thauminiteaxe);
        renderItems(thauminitepickaxe);
        renderItems(thauminiteshovel);
        renderItems(thauminitesword);
        renderItems(thauminitehoe);

        renderItems(plaxseed);
        renderItems(sweedseed);
        renderItems(aureliapetal);
        renderItems(briar_seedbag);
        renderItems(metalleatseed);
        renderItems(lucriteseed);
        renderItems(lazulliaseed);
        renderItems(glieoniaseed);
        renderItems(voidseed);
        renderItems(redlonseed);

        renderItems(knozeseed);

        renderItems(bloodychest);
        renderItems(bloodylegs);
        renderItems(bloodyboots);

        renderItems(thauminitehelmet);
        renderItems(thauminitechest);
        renderItems(thauminitelegs);
        renderItems(thauminiteboots);
        renderItems(herobrinesscythe);

        renderItems(tobacco_eldritch);
        renderItems(tobacco_fighting);
        renderItems(tobacco_hunger);
        renderItems(tobacco_knowledge);
        renderItems(tobacco_mining);
        renderItems(tobacco_pile);
        renderItems(tobacco_sanity);
        renderItems(tobacco_tainted);
        renderItems(tobacco_wispy);
        renderItems(tobaccoseed);
        renderItems(tobacco_leaves);
        renderItems(mortar);

        renderItems(greatwoodpipe);
        renderItems(silverwoodpipe);

        renderItems(rosehipsyrup);

        renderItems(voidfas);
        renderItems(voidshears);

        renderItems(bullet);

        renderItems(knowledge_shard);
        renderItems(revolver);
    }

    public static void renderItems(Item i){

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(
                TBCore.modid + ":" + i.getUnlocalizedName().substring(5), "inventory"));

    }


}
