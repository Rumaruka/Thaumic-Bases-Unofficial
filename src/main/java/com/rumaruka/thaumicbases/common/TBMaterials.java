package com.rumaruka.thaumicbases.common;

import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public final class TBMaterials {
    // Legacy values: harvest level 2, 964 uses, speed 8, damage 4.8, enchantability 25
    public static final Tier THAUMINITE = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            964,
            8.0F,
            4.8F,
            25,
            () -> Ingredient.of(TBItems.THAUMINITE_INGOT.get())
    );

    private TBMaterials() {
    }
}
