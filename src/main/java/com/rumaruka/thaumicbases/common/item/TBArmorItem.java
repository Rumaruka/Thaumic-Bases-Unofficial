package com.rumaruka.thaumicbases.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.ArrayList;
import java.util.List;

public class TBArmorItem extends ArmorItem {
    private final List<BonusModifier> bonusModifiers = new ArrayList<>();

    public TBArmorItem(Holder<ArmorMaterial> material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    public TBArmorItem withBonus(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
        bonusModifiers.add(new BonusModifier(attribute, modifier, slot));
        return this;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers();
        for (BonusModifier bonus : bonusModifiers) {
            modifiers = modifiers.withModifierAdded(bonus.attribute(), bonus.modifier(), bonus.slot());
        }
        return modifiers;
    }

    private record BonusModifier(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
    }
}
