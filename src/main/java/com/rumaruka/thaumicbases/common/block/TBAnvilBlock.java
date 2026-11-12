package com.rumaruka.thaumicbases.common.block;

import com.mojang.serialization.MapCodec;
import com.rumaruka.thaumicbases.common.menu.ThaumicAnvilMenu;
import com.rumaruka.thaumicbases.common.menu.VoidAnvilMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Thaumium and void metal anvil base block. The void variant slowly repairs its own damage.
 */
public class TBAnvilBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 2);
    private static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);
    private static final VoxelShape Z_AXIS_AABB = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 16.0D);
    private final boolean selfRepairing;

    public TBAnvilBlock(Properties properties, boolean selfRepairing) {
        super(properties);
        this.selfRepairing = selfRepairing;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(DAMAGE, Integer.valueOf(0)));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, DAMAGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return selfRepairing && state.getValue(DAMAGE) > 0;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (selfRepairing && state.getValue(DAMAGE) > 0 && random.nextInt(3) == 0) {
            level.setBlockAndUpdate(pos, state.setValue(DAMAGE, Integer.valueOf(state.getValue(DAMAGE) - 1)));
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        player.openMenu(getMenuProvider(state, level, pos));
        return InteractionResult.CONSUME;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        ContainerLevelAccess access = ContainerLevelAccess.create(level, pos);
        if (selfRepairing) {
            return new SimpleMenuProvider(
                    (id, inv, player) -> new VoidAnvilMenu(id, inv, access),
                    Component.translatable("tile.void_anvil.intact.name"));
        }
        return new SimpleMenuProvider(
                (id, inv, player) -> new ThaumicAnvilMenu(id, inv, access),
                Component.translatable("tile.thaumic_anvil.intact.name"));
    }
}
