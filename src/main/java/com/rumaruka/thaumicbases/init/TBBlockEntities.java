package com.rumaruka.thaumicbases.init;

import com.rumaruka.thaumicbases.ThaumicBases;
import com.rumaruka.thaumicbases.common.blockentity.TBBrazierBlockEntity;
import com.rumaruka.thaumicbases.common.blockentity.TBCampfireBlockEntity;
import com.rumaruka.thaumicbases.common.blockentity.TBOverchanterBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TBBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumicBases.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TBCampfireBlockEntity>> CAMPFIRE =
            BLOCK_ENTITIES.register("campfire",
                    () -> BlockEntityType.Builder.of(TBCampfireBlockEntity::new, TBBlocks.CAMPFIRE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TBBrazierBlockEntity>> BRAZIER =
            BLOCK_ENTITIES.register("braizer",
                    () -> BlockEntityType.Builder.of(TBBrazierBlockEntity::new, TBBlocks.BRAZIER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TBOverchanterBlockEntity>> OVERCHANTER =
            BLOCK_ENTITIES.register("overchanter",
                    () -> BlockEntityType.Builder.of(TBOverchanterBlockEntity::new, TBBlocks.OVERCHANTER.get()).build(null));

    private TBBlockEntities() {
    }
}
