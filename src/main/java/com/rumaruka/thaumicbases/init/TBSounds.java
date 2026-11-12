package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TBSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, ThaumicBases.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> FIRE_LOOP = register("fire_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_SHOT = register("revolver_shot");
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_NO_AMMO = register("revolver_no_ammo");
    public static final DeferredHolder<SoundEvent, SoundEvent> REVOLVER_RELOAD = register("revolver_reload");

    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_SIMPLE = register("guitar.simple");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_KNOWLEDGE = register("guitar.knowledge");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_CALMING = register("guitar.calming");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_ELECTRIC = register("guitar.electric");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_RESISTANCE = register("guitar.resistance");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_BUFFING = register("guitar.buffing");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_CONFUSION = register("guitar.confusion");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_GROWTH = register("guitar.growth");
    public static final DeferredHolder<SoundEvent, SoundEvent> GUITAR_LOVE = register("guitar.love");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ThaumicBases.MODID, name)));
    }

    private TBSounds() {
    }
}
