package com.rumaruka.thaumicbases.client;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.client.screen.OverchanterScreen;
import com.rumaruka.thaumicbases.client.screen.ThaumicAnvilScreen;
import com.rumaruka.thaumicbases.client.screen.VoidAnvilScreen;
import com.rumaruka.thaumicbases.init.TBEntities;
import com.rumaruka.thaumicbases.init.TBFluids;
import com.rumaruka.thaumicbases.init.TBItems;
import com.rumaruka.thaumicbases.init.TBMenuTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = ThaumicBases.MODID, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(TBMenuTypes.OVERCHANTER.get(), OverchanterScreen::new);
        event.register(TBMenuTypes.THAUMIC_ANVIL.get(), ThaumicAnvilScreen::new);
        event.register(TBMenuTypes.VOID_ANVIL.get(), VoidAnvilScreen::new);
        event.register(TBMenuTypes.REVOLVER.get(), com.rumaruka.thaumicbases.client.screen.RevolverScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TBEntities.REVOLVER_BULLET.get(), ctx -> new ThrownItemRenderer<>(ctx, 0.3F, true));
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(TBItems.REVOLVER.get(), ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "void"),
                    (stack, level, entity, seed) -> RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.VOID) > 0 ? 1.0F : 0.0F);
            ItemProperties.register(TBItems.REVOLVER.get(), ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "primal"),
                    (stack, level, entity, seed) -> RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.PRIMAL) > 0 ? 1.0F : 0.0F);
            ItemProperties.register(TBItems.REVOLVER.get(), ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "taint"),
                    (stack, level, entity, seed) -> RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.TAINTED) > 0 ? 1.0F : 0.0F);
            ItemProperties.register(TBItems.REVOLVER.get(), ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "eldritch"),
                    (stack, level, entity, seed) -> RevolverUpgrade.getUpgradeLevel(stack, RevolverUpgrade.ELDRITCH) > 0 ? 1.0F : 0.0F);
        });
    }

    @SubscribeEvent
    public static void onComputeFovModifier(ComputeFovModifierEvent event) {
        // Zoom removed per request
    }

    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "block/fluid_pyro_still");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "block/fluid_pyro_flow");
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return null;
            }

            @Override
            public int getTintColor() {
                return 0xFFFFFFFF;
            }
        }, TBFluids.PYRO_TYPE.get());
    }
}
