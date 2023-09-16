package com.rumaruka.thaumicbases.init;

//import com.rumaruka.thaumicbases.common.tiles.TileOverchanter;
import com.rumaruka.thaumicbases.common.tiles.TileBraizer;
import com.rumaruka.thaumicbases.common.tiles.TileCampfire;
import com.rumaruka.thaumicbases.common.tiles.TileOverchanter;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TBTiles {

    public static void setup(){
       GameRegistry.registerTileEntity(TileOverchanter.class,"thaumicbases.overchanter");
       GameRegistry.registerTileEntity(TileCampfire.class,"thaumicbases.campfire");
       GameRegistry.registerTileEntity(TileBraizer.class, "thaumicbases.brazier");
    }
}
