package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.common.entity.RevolverBulletEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TBEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ThaumicBases.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<RevolverBulletEntity>> REVOLVER_BULLET =
            ENTITY_TYPES.register("revolver_bullet",
                    () -> EntityType.Builder.<RevolverBulletEntity>of(RevolverBulletEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build(ThaumicBases.MODID + ":revolver_bullet"));

    private TBEntities() {
    }
}
