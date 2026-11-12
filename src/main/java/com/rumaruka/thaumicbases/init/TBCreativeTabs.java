package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TBCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ThaumicBases.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TB_TAB = CREATIVE_MODE_TABS.register("thaumicbases",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + ThaumicBases.MODID))
                    .icon(() -> TBItems.OVERCHANTER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> TBItems.CREATIVE_TAB_ITEMS.forEach(item -> output.accept(item.get())))
                    .build());

    private TBCreativeTabs() {
    }
}
