package net.quedoon.giant_potato.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.custom.CrusherWheelBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CrusherWheelBlock extends BlockWithEntity {
    VoxelShape SHAPE = ModBlockHitboxes.getCrusherWheelHitbox();

    public static final MapCodec<CrusherWheelBlock> CODEC = CrusherWheelBlock.createCodec(CrusherWheelBlock::new);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;


    public CrusherWheelBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Map<Integer, Direction> directionMap = Map.of(0, Direction.NORTH, 1, Direction.SOUTH, 2, Direction.EAST, 3, Direction.WEST);
        Map<Integer, Direction> directionMapCrusher = Map.of(0, Direction.EAST, 1, Direction.EAST, 2, Direction.NORTH, 3, Direction.NORTH);
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = null;
        Direction dirWheel;
        Direction dirCrusher;
        for(int i = 0; i <= 3; i++) {
            dirWheel = directionMap.get(i);
            dirCrusher = directionMapCrusher.get(i);
            switch (i) {
                case 0 -> state = world.getBlockState(pos.north(1));
                case 1 -> state = world.getBlockState(pos.south(1));
                case 2 -> state = world.getBlockState(pos.east(1));
                case 3 -> state = world.getBlockState(pos.west(1));
            }
            if (state != null && state.isOf(ModBlocks.CRUSHER) &&
                    (state.get(Properties.HORIZONTAL_FACING) == dirCrusher || state.get(Properties.HORIZONTAL_FACING) == dirCrusher.getOpposite() ))
                    return this.getDefaultState().with(FACING, dirWheel);
        }

        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrusherWheelBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
