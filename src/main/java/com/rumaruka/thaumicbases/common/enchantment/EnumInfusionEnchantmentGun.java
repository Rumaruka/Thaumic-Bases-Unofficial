package com.rumaruka.thaumicbases.common.enchantment;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public enum EnumInfusionEnchantmentGun{
    TAINT(ImmutableSet.of("revolver_taint"), 3, "TB.REVOLVER.2"),
    HEAVY(ImmutableSet.of("revolver"), 5, "TB.REVOLVER.2"),
    POWER(ImmutableSet.of("revolver_power"), 5, "TB.REVOLVER.2"),
    BOART(ImmutableSet.of("revolver_boart"), 5, "TB.REVOLVER.2"),
    DUELING(ImmutableSet.of("revolver_dueling"), 5, "TB.REVOLVER.2"),
    SMITE(ImmutableSet.of("revolver_smite"), 5, "TB.REVOLVER.2"),
    BOE(ImmutableSet.of("revolver_boe"), 5, "TB.REVOLVER.2"),
    EFFICIENCY(ImmutableSet.of("revolver_effect"), 5, "TB.REVOLVER.2"),
    SPEED(ImmutableSet.of("revolver_speed"), 5, "TB.REVOLVER.2"),
    WISE(ImmutableSet.of("revolver_wise"), 5, "TB.REVOLVER.2"),
    ACCURACY(ImmutableSet.of("revolver_accuracy"), 3, "TB.REVOLVER.2");

    public Set<String> toolClasses;
    public int maxLevel;
    public String research;

    EnumInfusionEnchantmentGun(Set<String> toolClasses, int ml, String research){
        this.toolClasses = toolClasses;
        this.maxLevel = ml;
        this.research = research;
    }

    public static NBTTagList getInfusionEnchantmentTagList(ItemStack stack){
        return stack == null || stack.isEmpty() || stack.getTagCompound() == null ? null : stack.getTagCompound().getTagList("infenchrevolver", 10);
    }

    public static List<EnumInfusionEnchantmentGun> getInfusionEnchantments(ItemStack stack){
        NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
        ArrayList<EnumInfusionEnchantmentGun> list = new ArrayList<EnumInfusionEnchantmentGun>();
        if(nbttaglist != null){
            for(int j = 0; j < nbttaglist.tagCount(); ++j){
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if(k < 0 || k >= EnumInfusionEnchantmentGun.values().length)
                    continue;
                list.add(EnumInfusionEnchantmentGun.values()[k]);
            }
        }
        return list;
    }

    public static int getInfusionEnchantmentLevel(ItemStack stack, EnumInfusionEnchantmentGun enchantment){
        NBTTagList nbttaglist = EnumInfusionEnchantmentGun.getInfusionEnchantmentTagList(stack);
        if(nbttaglist != null){
            for(int j = 0; j < nbttaglist.tagCount(); ++j){
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if(k < 0 || k >= EnumInfusionEnchantmentGun.values().length || EnumInfusionEnchantmentGun.values()[k] != enchantment)
                    continue;
                return l;
            }
        }
        return 0;
    }

    public static void addInfusionEnchantment(ItemStack stack, EnumInfusionEnchantmentGun ie, int level){
        if(stack == null || stack.isEmpty() || level > ie.maxLevel){
            System.out.println("uwu");
            return;
        }
        NBTTagList nbttaglist = EnumInfusionEnchantmentGun.getInfusionEnchantmentTagList(stack);
        if(nbttaglist != null){
            for(int j = 0; j < nbttaglist.tagCount(); ++j){
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if(k != ie.ordinal())
                    continue;
                if(level <= l){ return; }
                nbttaglist.getCompoundTagAt(j).setShort("lvl", (short)level);
                stack.setTagInfo("infenchrevolver", nbttaglist);
                return;
            }
        }else{
            nbttaglist = new NBTTagList();
        }
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setShort("id", (short)ie.ordinal());
        nbttagcompound.setShort("lvl", (short)level);
        nbttaglist.appendTag(nbttagcompound);
        if(nbttaglist.tagCount() > 0){
            stack.setTagInfo("infenchrevolver", nbttaglist);
        }
    }

    private static void handleProjecting(ItemStack stack){
        // Add an nbt tag to give Projecting items extended reach.
        NBTTagCompound nbt = stack.getTagCompound();

        Multimap<String, AttributeModifier> map = stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
        Collection<AttributeModifier> reachCollection = map.get(EntityPlayer.REACH_DISTANCE.getName());
        Collection<AttributeModifier> damageCollection = map.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
        Collection<AttributeModifier> speedCollection = map.get(SharedMonsterAttributes.ATTACK_SPEED.getName());
        double reach;
        try{
            AttributeModifier reachModifier = (AttributeModifier)reachCollection.toArray()[0];
            System.out.println(reachModifier.getAmount());
            reach = reachModifier.getAmount() + 2;
        }catch(ArrayIndexOutOfBoundsException e){
            reach = 2;
            System.out.println("WHAT");
        }
        AttributeModifier reachDistance = new AttributeModifier("reachDistance", reach, 0);
        AttributeModifier attackDamage = (AttributeModifier)damageCollection.toArray()[0];
        AttributeModifier attackSpeed = (AttributeModifier)speedCollection.toArray()[0];
        NBTTagCompound reachNBT = writeAttributeModifierToNBT(EntityPlayer.REACH_DISTANCE, reachDistance, EntityEquipmentSlot.MAINHAND);
        NBTTagCompound damageNBT = writeAttributeModifierToNBT(SharedMonsterAttributes.ATTACK_DAMAGE, attackDamage, EntityEquipmentSlot.MAINHAND);
        NBTTagCompound speedNBT = writeAttributeModifierToNBT(SharedMonsterAttributes.ATTACK_SPEED, attackSpeed, EntityEquipmentSlot.MAINHAND);
        NBTTagList list = new NBTTagList();
        list.appendTag(reachNBT);
        list.appendTag(damageNBT);
        list.appendTag(speedNBT);
        nbt.setTag("AttributeModifiers", list);
    }

    private static NBTTagCompound writeAttributeModifierToNBT(IAttribute attribute, AttributeModifier modifier, EntityEquipmentSlot slot){
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setString("AttributeName", attribute.getName());
        nbt.setString("Name", modifier.getName());
        nbt.setString("Slot", slot.getName());
        nbt.setDouble("Amount", modifier.getAmount());
        nbt.setInteger("Operation", modifier.getOperation());
        nbt.setLong("UUIDMost", modifier.getID().getMostSignificantBits());
        nbt.setLong("UUIDLeast", modifier.getID().getLeastSignificantBits());

        return nbt;
    }
}