package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class TBArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, ThaumicBases.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> THAUMINITE = ARMOR_MATERIALS.register("thauminite",
            () -> new ArmorMaterial(
                    Map.of(
                            ArmorItem.Type.HELMET, 3,
                            ArmorItem.Type.CHESTPLATE, 8,
                            ArmorItem.Type.LEGGINGS, 6,
                            ArmorItem.Type.BOOTS, 3
                    ),
                    17,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.of(TBItems.THAUMINITE_INGOT.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "thauminite"))),
                    0.0F,
                    0.0F
            ));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BLOODY = ARMOR_MATERIALS.register("bloody",
            () -> new ArmorMaterial(
                    Map.of(
                            ArmorItem.Type.HELMET, 2,
                            ArmorItem.Type.CHESTPLATE, 6,
                            ArmorItem.Type.LEGGINGS, 5,
                            ArmorItem.Type.BOOTS, 2
                    ),
                    21,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.of(TBItems.BLOODY_CLOTH.get()),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, "bloody"))),
                    0.0F,
                    0.0F
            ));

    private TBArmorMaterials() {
    }
}
