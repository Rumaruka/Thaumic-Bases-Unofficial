package com.rumaruka.tb.init;

import com.rumaruka.tb.common.item.foci.FocusExperience;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.casters.FocusEngine;

public class TBFocus {

    public static  void loadFocus(){
        FocusEngine.registerElement(FocusExperience.class,new ResourceLocation("thaumicbases","blocks/boneblock.png"),1000);
    }
}
