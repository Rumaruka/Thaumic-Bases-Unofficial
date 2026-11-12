package com.rumaruka.thaumicbases;

import com.mojang.logging.LogUtils;
import com.rumaruka.thaumicbases.client.ClientModEvents;
import com.rumaruka.thaumicbases.init.TBArmorMaterials;
import com.rumaruka.thaumicbases.init.TBBlockEntities;
import com.rumaruka.thaumicbases.init.TBBlocks;
import com.rumaruka.thaumicbases.init.TBCreativeTabs;
import com.rumaruka.thaumicbases.init.TBEntities;
import com.rumaruka.thaumicbases.init.TBFluids;
import com.rumaruka.thaumicbases.init.TBItems;
import com.rumaruka.thaumicbases.init.TBMenuTypes;
import com.rumaruka.thaumicbases.init.TBSounds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(ThaumicBases.MODID)
public class ThaumicBases {
    public static final String MODID = "thaumicbases";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThaumicBases(IEventBus modEventBus) {
        TBFluids.FLUID_TYPES.register(modEventBus);
        TBFluids.FLUIDS.register(modEventBus);
        TBBlocks.BLOCKS.register(modEventBus);
        TBItems.ITEMS.register(modEventBus);
        TBEntities.ENTITY_TYPES.register(modEventBus);
        TBBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        TBMenuTypes.MENU_TYPES.register(modEventBus);
        TBArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        TBSounds.SOUND_EVENTS.register(modEventBus);
        TBCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientModEvents::registerClientExtensions);
        }

        LOGGER.info("Thaumic Bases (NeoForge 1.21.1 port) initialized.");
    }
}
