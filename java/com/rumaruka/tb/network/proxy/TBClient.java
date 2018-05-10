package com.rumaruka.tb.network.proxy;

import DummyCore.Utils.ASMManager;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;
import com.rumaruka.tb.common.block.TBBlock;
import com.rumaruka.tb.init.TBBlocks;
import com.rumaruka.tb.init.TBItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.beams.FXArc;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TBClient extends TBServer {


    @Override
    public void Renders() {
        TBBlocks.Render();
        TBItems.Renders();
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public void registerRenderInformation()
    {
      /*  ClientRegistry.bindTileEntitySpecialRenderer(TileOverchanter.class, new RenderOverchanter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAuraLinker.class, new RenderAuraLinker());
        ClientRegistry.bindTileEntitySpecialRenderer(TileNodeManipulator.class, new RenderNodeManipulator());
        MinecraftForge.EVENT_BUS.register(new RevolverEvents());*/

      /*  RenderAccessLibrary.registerItemRenderingHandler(TBItems.ukulele, new UkuleleRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(TBItems.revolver, new RenderRevolver());
        RenderAccessLibrary.registerItemRenderingHandler(Item.getItemFromBlock(TBBlocks.nodeManipulator), new NodeManipulatorItemRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(Item.getItemFromBlock(TBBlocks.auraLinker), new AuraLinkerItemRenderer());
        RenderAccessLibrary.registerRenderingHandler(new BraizerRenderer());
        RenderAccessLibrary.registerRenderingHandler(new CampfireRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(TBItems.nodeFoci, new NodeFociRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(TBItems.spawnerCompass, new SpawnerCompassRenderer());
        RenderAccessLibrary.registerItemRenderingHandler(TBItems.castingBracelet, new CastingBraceletRenderer());
        RenderAccessLibrary.registerItemMatrixHandler(TBItems.herobrinesScythe, new HerobrinesScytheMH());
        RenderingRegistry.registerEntityRenderingHandler(EntityAspectOrb.class, new RenderAspectOrb());
        RenderingRegistry.registerEntityRenderingHandler(EntityRevolverBullet.class, new RenderBullet());*/
    }
    @Override
    public void lightning(World world, double sx, double sy, double sz, double ex, double ey, double ez, int dur, float curve, int speed, int type)
    {
        FXArc efa = new FXArc(world, sx, sy, sz, ex, ey, ez, 0.1F, 0.0F, 0.1F, 0.1F);
        try
        {
            Field f = FXDispatcher.class.getDeclaredField(ASMManager.chooseByEnvironment("particleMaxAge", "field_70547_e"));
            f.setAccessible(true);
            f.setInt(efa, dur);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(efa);
    }

    @Override
    public void addEnchantmentToCreativeTab() {
        EnumEnchantmentType[] enchantmentTypes = TBCreativeTabs.TB_CREATIVEtabs.getRelevantEnchantmentTypes();
        enchantmentTypes = Arrays.copyOf(enchantmentTypes,enchantmentTypes.length + 1);
        TBCreativeTabs.TB_CREATIVEtabs.setRelevantEnchantmentTypes(enchantmentTypes);
    }
}
