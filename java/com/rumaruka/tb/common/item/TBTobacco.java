package com.rumaruka.tb.common.item;

import com.rumaruka.tb.api.ITobacco;
import com.rumaruka.tb.init.TBItems;
import com.rumaruka.tb.utils.TBUtils;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.lib.potions.PotionDeathGaze;

import java.util.ArrayList;
import java.util.Collection;

import static thaumcraft.api.capabilities.IPlayerWarp.EnumWarpType.*;

public class TBTobacco extends Item implements ITobacco {



    public TBTobacco()
    {

    }


    @Override
    public void performTobaccoEffect(EntityPlayer smoker, ItemStack tobbaco, boolean isSilverwood) {
        if(tobbaco.getItem()==TBItems.tobacco_pile){
            if(isSilverwood&&smoker.world.rand.nextFloat()<=0.3f){
                TBUtils.addWarpToPlayer(smoker,-1,TEMPORARY);
            }
            if(!isSilverwood&&smoker.world.rand.nextFloat()<=0.1f){
                TBUtils.addWarpToPlayer(smoker,-1,TEMPORARY);
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_eldritch){
            if(!smoker.world.isRemote){
                smoker.addPotionEffect(new PotionEffect(PotionDeathGaze.instance,2_000,0,true,false));
                if(!isSilverwood){
                    TBUtils.addWarpToPlayer(smoker,3,TEMPORARY);
                }
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_fighting){
            if(!smoker.world.isRemote){
                smoker.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,8_000,1,true,false));
                if(isSilverwood&&smoker.world.rand.nextFloat()<=0.1f){
                    smoker.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,8_000,0,true,false));
                }
            }
        }

        if(tobbaco.getItem()==TBItems.tobacco_hunger){
            smoker.getFoodStats().addStats(3,3);

            if(!isSilverwood&&smoker.world.rand.nextFloat()<=0.4f){
                smoker.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,200,0,true,false));
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_knowledge){
            if(!smoker.world.isRemote)
            {
                ArrayList<Aspect> aspects = new ArrayList<Aspect>();
                Collection<Aspect> pa = Aspect.aspects.values();
                for (Aspect aspect:pa)
                {
                    aspects.add(aspect);
                }

                for(int i = 0; i < (isSilverwood ? 20 : 10); ++i)
                {
                    Aspect a = aspects.get(smoker.world.rand.nextInt(aspects.size()));
                    EntityXPOrb xp = new EntityXPOrb(smoker.world,smoker.posX,smoker.posY,smoker.posZ,a.isPrimal() ? 4 : a == Aspect.FLUX ? 8 : 2);
                    smoker.world.spawnEntity(xp);
                    if(a == Aspect.FLUX && !isSilverwood)
                        TBUtils.addWarpToPlayer(smoker, 1, TEMPORARY);
                }
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_mining){
            if(!smoker.world.isRemote){
                smoker.addPotionEffect(new PotionEffect(MobEffects.HASTE,8_000,1,true,false));
                if(isSilverwood&&smoker.world.rand.nextFloat()<=0.3f){
                    smoker.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,8_000,0,true,false));
                }
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_sanity){
            if(!smoker.world.isRemote){
                if(!isSilverwood){
                    TBUtils.addWarpToPlayer(smoker,1+smoker.world.rand.nextInt(3),TEMPORARY);
                }else {
                    ItemStack is = smoker.getHeldItemMainhand();
                    if(is!=ItemStack.EMPTY){
                        smoker.renderBrokenItemStack(is);
                        smoker.inventory.setInventorySlotContents(smoker.inventory.currentItem,ItemStack.EMPTY);
                    }
                    return;
                }
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_tainted){
            ArrayList<Aspect> aspects = new ArrayList<Aspect>();
            Collection<Aspect> pa = Aspect.aspects.values();

            for(Aspect aspect : pa){
                aspects.add(aspect);
            }
            if(isSilverwood){
                aspects.remove(Aspect.FLUX);
            }
            EntityWisp wisp = new EntityWisp(smoker.world);
            wisp.setPositionAndRotation(smoker.posX,smoker.posY,smoker.posZ,0,0);
            if(!smoker.world.isRemote){
                wisp.setType(aspects.get(smoker.world.rand.nextInt(aspects.size())).getTag());
                smoker.world.spawnEntity(wisp);
            }
        }
        if(tobbaco.getItem()==TBItems.tobacco_wispy){

        }


    }


}
