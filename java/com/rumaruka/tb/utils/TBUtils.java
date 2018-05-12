package com.rumaruka.tb.utils;

import DummyCore.Utils.Coord3D;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class TBUtils {



    public static List<Entity> castLst(List<? extends Entity> l)
    {
        List<Entity> retLst = new ArrayList<Entity>();

        for(Object e : l)
            retLst.add((Entity) e);

        return retLst;
    }
}
