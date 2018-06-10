package com.rumaruka.tb.network.proxy;

import DummyCore.Client.GuiCommon;
import DummyCore.Utils.ASMManager;
import com.rumaruka.tb.client.creativetabs.TBCreativeTabs;

import com.rumaruka.tb.client.render.RenderOverchanter;
import com.rumaruka.tb.common.tiles.TileOverchanter;
import com.rumaruka.tb.init.TBBlocks;
import com.rumaruka.tb.init.TBFluids;
import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.beams.FXArc;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class TBClient extends TBServer {


    @Override
    public void preInit() {
        ModelLoader.setCustomStateMapper(TBBlocks.pyrofluid,new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }

    @Override
    public void Renders() {
        TBBlocks.Render();
        TBItems.Renders();
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public void registerRenderInformation()
    {
       ClientRegistry.bindTileEntitySpecialRenderer(TileOverchanter.class, new RenderOverchanter());
      /*

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
       */
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
