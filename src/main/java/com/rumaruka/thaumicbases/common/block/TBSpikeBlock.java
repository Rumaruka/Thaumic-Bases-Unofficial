package com.rumaruka.thaumicbases.common.block;

import com.leclowndu93150.thaumaturge.Thaumaturge;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Ported spike block: damages entities standing on it, supports the "bloody" visual state,
 * and can optionally be disabled with a redstone signal.
 */
public class TBSpikeBlock extends Block {
    public static final BooleanProperty BLOODY = BooleanProperty.create("bloody");
    private static final VoxelShape SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    private final float damage;
    private final boolean redstoneSensitive;

    public TBSpikeBlock(Properties properties, float damage, boolean redstoneSensitive) {
        super(properties);
        this.damage = damage;
        this.redstoneSensitive = redstoneSensitive;
        registerDefaultState(stateDefinition.any().setValue(BLOODY, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BLOODY);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide() || entity instanceof ItemEntity) {
            return;
        }
        if (redstoneSensitive && level.hasNeighborSignal(pos)) {
            return;
        }
        DamageSource source = level.damageSources().cactus();
        entity.hurt(source, damage);
        if (!state.getValue(BLOODY) && entity instanceof LivingEntity living && living.getHealth() <= 0.0F) {
            level.setBlockAndUpdate(pos, state.setValue(BLOODY, Boolean.TRUE));
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(BLOODY) || stack.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

                if (stack.getItem()==TTItems.FABRIC.get()) {
                    if (!level.isClientSide) {
                        ItemStack result = new ItemStack(TBItems.BLOODY_CLOTH.get());
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                        if (!player.getInventory().add(result)) {
                            player.drop(result, false);
                        }
                        level.setBlock(pos, state.setValue(BLOODY, Boolean.FALSE), 3);
                        level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.7F, 1.0F);
                    }
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                }

             return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }



    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}
