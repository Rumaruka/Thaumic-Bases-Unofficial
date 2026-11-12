package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.common.menu.OverchanterMenu;
import com.rumaruka.thaumicbases.common.menu.ThaumicAnvilMenu;
import com.rumaruka.thaumicbases.common.menu.VoidAnvilMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TBMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, ThaumicBases.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<OverchanterMenu>> OVERCHANTER =
            MENU_TYPES.register("overchanter", () -> IMenuTypeExtension.create(OverchanterMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ThaumicAnvilMenu>> THAUMIC_ANVIL =
            MENU_TYPES.register("thaumic_anvil", () -> new MenuType<>(ThaumicAnvilMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final DeferredHolder<MenuType<?>, MenuType<VoidAnvilMenu>> VOID_ANVIL =
            MENU_TYPES.register("void_anvil", () -> new MenuType<>(VoidAnvilMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final DeferredHolder<MenuType<?>, MenuType<com.rumaruka.thaumicbases.common.menu.RevolverMenu>> REVOLVER =
            MENU_TYPES.register("revolver", () -> IMenuTypeExtension.create(com.rumaruka.thaumicbases.common.menu.RevolverMenu::new));

    private TBMenuTypes() {
    }
}
