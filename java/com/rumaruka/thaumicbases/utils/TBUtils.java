package com.rumaruka.thaumicbases.utils;


import com.rumaruka.thaumicbases.api.dummycore_remove.utils.Coord3D;
import javafx.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.List;

public class TBUtils {


    public static ArrayList<Pair<Integer, Coord3D>> loadedClientSpawners = new ArrayList<Pair<Integer,Coord3D>>();

    public static Coord3D getClosestLoadedClientSpawner(EntityPlayer player)
    {
        if(player == null)
            return null;

        if(player instanceof FakePlayer)
            return null;

        if(player.world == null)
            return null;

        if(!player.world.isRemote)
            return null;

        double closestDistance = Short.MAX_VALUE;
        int index = -1;

        for(Pair<Integer,Coord3D> p : loadedClientSpawners)
        {
            if(p.getKey() != player.dimension)
                continue;

            double distance = Math.sqrt(player.getDistance(p.getValue().x, p.getValue().y, p.getValue().z));
            if(distance < closestDistance)
            {
                closestDistance = distance;
                index = loadedClientSpawners.indexOf(p);
            }
        }

        if(index != -1)
            return loadedClientSpawners.get(index).getValue();

        return null;
    }






    public static List<Entity> castLst(List<? extends Entity> l)
    {
        List<Entity> retLst = new ArrayList<Entity>();

        for(Object e : l)
            retLst.add((Entity) e);

        return retLst;
    }

}
