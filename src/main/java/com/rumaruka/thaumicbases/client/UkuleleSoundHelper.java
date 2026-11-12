package com.rumaruka.thaumicbases.client;

import com.rumaruka.thaumicbases.ThaumicBases;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

public class UkuleleSoundHelper {
    private static final String[] GUITAR_SOUNDS = {
            "guitar.simple", "guitar.knowledge", "guitar.calming", "guitar.electric",
            "guitar.resistance", "guitar.buffing", "guitar.confusion", "guitar.growth", "guitar.love"
    };

    public static void stopUkuleleSounds() {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.getSoundManager() == null) return;
        for (String id : GUITAR_SOUNDS) {
            mc.getSoundManager().stop(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, id), SoundSource.PLAYERS);
            mc.getSoundManager().stop(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, id), SoundSource.RECORDS);
        }
    }
}
