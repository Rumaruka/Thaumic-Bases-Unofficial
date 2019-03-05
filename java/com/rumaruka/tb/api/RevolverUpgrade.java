package com.rumaruka.tb.api;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;


import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.potions.PotionFluxTaint;

import java.util.ArrayList;
import java.util.Hashtable;

public class RevolverUpgrade {


    public static RevolverUpgrade[]allUpgrades = new RevolverUpgrade[24];

    public static Hashtable<String,Integer>textToIntMapping = new Hashtable<String, Integer>();

    public static Hashtable<Integer,String>intToStringMapping = new Hashtable<Integer, String>();



    //For upgrades
    public int maxLevel;
    public final int id;
    public final String name;
    public int instability;


    public static final ResourceLocation eldritchTextures = new ResourceLocation("thaumicbases","textures/items/revolver/revolverDarkMetalEldritch.png");
    public static final ResourceLocation primalTextures = new ResourceLocation("thaumicbases","textures/items/revolver/revolverGunPrimalUV.png");
    public static final ResourceLocation taintedTextures = new ResourceLocation("thaumicbases","textures/items/revolver/revolverGunTaintedUV.png");
    public static final ResourceLocation voidTextures = new ResourceLocation("thaumicbases","textures/items/revolver/revolverHandleVoidUV.png");


    public final ArrayList<RevolverUpgrade>conflicts = new ArrayList<RevolverUpgrade>();

    public RevolverUpgrade(int id,String name){
        this.name=name;
        this.id=id;

        if(id<allUpgrades.length&&allUpgrades[id]!=null){
            FMLLog.warning("[TB]Attempting to register revolver upgrade "+name+"["+id+"], but the ID is already occupied by "+allUpgrades[id].name+"["+allUpgrades[id].id+"], ignoring.", new Object[0]);
            return;
        }

        if(id>=allUpgrades.length){
            RevolverUpgrade[]copied = new RevolverUpgrade[allUpgrades.length];
            System.arraycopy(allUpgrades,0,copied,0,allUpgrades.length);
            allUpgrades=copied;
        }
        allUpgrades[id]=this;
    }

    public String getName() {
        return I18n.format("revolverUpgrade."+name+".name");
    }

    public int getId() {
        return this.id;
    }
    public String getTextID()
    {
        return this.name;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public RevolverUpgrade setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public boolean conflictsWith(RevolverUpgrade upgrade)
    {
        return conflicts.contains(upgrade);
    }
    public RevolverUpgrade addConflict(RevolverUpgrade... upgrade)
    {
        for(RevolverUpgrade rUpgr : upgrade)
        {
            if(!conflicts.contains(rUpgr))
                conflicts.add(rUpgr);

            if(!rUpgr.conflicts.contains(this))
                rUpgr.conflicts.add(this);
        }

        return this;
    }

    public int getInstabilityForLevel(int level)
    {
        return instability * level;
    }

    public RevolverUpgrade setInstabilityPerLevel(int newValue)
    {
        instability = newValue;
        return this;
    }
    public float modifyDamage_start(EntityLivingBase base, ItemStack revolver, float currentModification, int modLevel)
    {
        if(base != null)
        {
            if(this == power)
                return (float) (currentModification*(Math.pow(1.1F, modLevel)));


            if(this == atropodsBane && base.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)
                return (float) (currentModification*(Math.pow(1.2F, modLevel)));

            if(this == eldritchBane && (base instanceof IEldritchMob || base instanceof EntityEnderman))
                return (float) (currentModification*(Math.pow(1.2F, modLevel)));

            if(this == dueling && base instanceof EntityPlayer)
                return (float) (currentModification*(Math.pow(1.2F, modLevel)));



            if(this == piercig)
                return currentModification * 1.1F;

            if(this == tainted)
            {
                if(base instanceof ITaintedMob)
                    return currentModification / 1.6F;
                return (float) (currentModification*(Math.pow(1.2F, modLevel)));
            }
        }
        return currentModification;
    }

    public float modifyDamage_end(EntityLivingBase base, ItemStack revolver, float currentModification, int modLevel)
    {
        if(this == primal)
            return currentModification * 2;

        return currentModification;
    }
    public boolean afterhit(EntityLivingBase base, EntityPlayer user,ItemStack revolver,final float damageDealt, int modLevel)
    {
        if(this == tainted)
            if(base.world.rand.nextDouble() <= 0.1D)
                base.addPotionEffect(new PotionEffect(PotionFluxTaint.instance,200,1,true,false));

        if(this == poisoned)
            base.addPotionEffect(new PotionEffect(MobEffects.POISON,200,1,true,false));

        if(this == knowledge && base.getHealth() <= 0 && user != null)
        {
            if(user.world.rand.nextInt(Math.max(1, 7-modLevel)) == 0)
            {
                AspectList aspectsCompound = AspectHelper.getEntityAspects(base);
                if(aspectsCompound != null && aspectsCompound.size() > 0)
                {
                    aspectsCompound = AspectHelper.reduceToPrimals(aspectsCompound);
                    Aspect[] al = aspectsCompound.getAspects();
                    for(int i = 0; i < al.length; ++i)
                    {
                        EntityXPOrb xp = new EntityXPOrb(user.world,user.posX,user.posY,user.posZ,aspectsCompound.getAmount(al[i]));
                        if(!user.world.isRemote)
                            user.world.spawnEntity(xp);
                        if(user.world.rand.nextBoolean())
                            break;
                    }
                }
            }
        }


        if(this == piercig)
            return false;

        return true;
    }

    public boolean bulletNoclip(EntityPlayer user,ItemStack revolver, int modLevel)
    {
        if(this == primal)
            return true;

        return false;
    }

    public int modifyShots(EntityPlayer user, ItemStack revolver, int modLevel, int originalLevel, boolean hasPrimal)
    {
        if(this == primal)
            return -1;

        if(this == efficiency)
            return hasPrimal ? modLevel == 5 ? 2 : -1 : originalLevel+modLevel;

        return originalLevel;
    }
    public double modifySpeed(EntityPlayer user, ItemStack revolver, float origSpeed, int modLevel)
    {
        if(this == speed)
            return origSpeed*Math.pow(1.09F, modLevel);
        return origSpeed;
    }
    public ResourceLocation getOverridePartTexture(ItemStack revolver, int part, int level)
    {
        if(this == eldritch && part == 2)
            return eldritchTextures;

        if(this == primal && part == 3)
            return primalTextures;

        if(this == tainted && part == 3)
            return taintedTextures;

        if(this == uvoid && part == 0)
            return voidTextures;

        return null;
    }

    public static void initConflictingMappings()
    {
        power.addConflict(atropodsBane,eldritchBane,dueling,eldritch);
        atropodsBane.addConflict(eldritchBane,dueling,eldritch);
        eldritchBane.addConflict(dueling,eldritch);
        dueling.addConflict(eldritch);

        accuracy.addConflict(speed,efficiency);
        speed.addConflict(efficiency);

        poisoned.addConflict(primal,tainted,knowledge);
        primal.addConflict(tainted,knowledge);
        tainted.addConflict(knowledge);
    }

    public static final RevolverUpgrade power = new RevolverUpgrade(0,"POWER").setMaxLevel(5).setInstabilityPerLevel(2);
    public static final RevolverUpgrade accuracy = new RevolverUpgrade(1,"ACCURACY").setMaxLevel(3).setInstabilityPerLevel(1);
    public static final RevolverUpgrade poisoned = new RevolverUpgrade(2,"POISONED").setMaxLevel(3).setInstabilityPerLevel(1);
    public static final RevolverUpgrade atropodsBane = new RevolverUpgrade(3,"BANE_OF_ATROPODS").setMaxLevel(5).setInstabilityPerLevel(3);
    public static final RevolverUpgrade eldritchBane = new RevolverUpgrade(4,"BANE_OF_ELDRITCH").setMaxLevel(5).setInstabilityPerLevel(3);
    public static final RevolverUpgrade piercig = new RevolverUpgrade(5,"PIERCING").setMaxLevel(1).setInstabilityPerLevel(12);
    public static final RevolverUpgrade dueling = new RevolverUpgrade(6,"DUELING").setMaxLevel(5).setInstabilityPerLevel(3);
    public static final RevolverUpgrade tainted = new RevolverUpgrade(7,"TAINTED").setMaxLevel(3).setInstabilityPerLevel(5);
    public static final RevolverUpgrade primal = new RevolverUpgrade(8,"PRIMAL").setMaxLevel(1).setInstabilityPerLevel(12);
    public static final RevolverUpgrade speed = new RevolverUpgrade(9,"SPEED").setMaxLevel(5).setInstabilityPerLevel(2);
    public static final RevolverUpgrade efficiency = new RevolverUpgrade(10,"EFFICIENCY").setMaxLevel(5).setInstabilityPerLevel(4);
    public static final RevolverUpgrade eldritch = new RevolverUpgrade(11,"ELDRITCH").setMaxLevel(2).setInstabilityPerLevel(7);
    public static final RevolverUpgrade uvoid = new RevolverUpgrade(12,"VOID").setMaxLevel(3).setInstabilityPerLevel(4);
    public static final RevolverUpgrade knowledge = new RevolverUpgrade(13,"KNOWLEDGE").setMaxLevel(5).setInstabilityPerLevel(1);
    public static final RevolverUpgrade heavy = new RevolverUpgrade(14,"HEAVY").setMaxLevel(5).setInstabilityPerLevel(1);
}
