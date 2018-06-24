package com.rumaruka.tb.utils;

import DummyCore.Utils.Coord3D;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.research.ResearchStage;

import java.util.ArrayList;
import java.util.List;

public class TBUtils {

    public static void addWarpToPlayer(EntityPlayer addTo, int amount, IPlayerWarp.EnumWarpType type)
    {
        switch(type)
        {
            case PERMANENT:
            {
                addWarpToPlayer(addTo, amount, IPlayerWarp.EnumWarpType.PERMANENT);
                return;
            }
            case NORMAL:
            {
                addWarpToPlayer(addTo, amount, IPlayerWarp.EnumWarpType.NORMAL);
                return;
            }
            case TEMPORARY:
            {
                addWarpToPlayer(addTo, amount, IPlayerWarp.EnumWarpType.TEMPORARY);
                return;
            }
            default:
            {
                addWarpToPlayer(addTo, amount, IPlayerWarp.EnumWarpType.NORMAL);
                return;
            }
        }
    }

    public static List<Entity> castLst(List<? extends Entity> l)
    {
        List<Entity> retLst = new ArrayList<Entity>();

        for(Object e : l)
            retLst.add((Entity) e);

        return retLst;
    }

}
