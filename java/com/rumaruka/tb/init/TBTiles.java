package com.rumaruka.tb.init;

//import com.rumaruka.tb.common.tiles.TileOverchanter;
import com.rumaruka.tb.common.tiles.TileCampfire;
import com.rumaruka.tb.common.tiles.TileOverchanter;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TBTiles {

    public static void setup(){
       GameRegistry.registerTileEntity(TileOverchanter.class,"tb.overchanter");
       GameRegistry.registerTileEntity(TileCampfire.class,"tb.campfire");

    }
}
