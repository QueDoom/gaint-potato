package net.quedoon.giant_potato.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.quedoon.giant_potato.block.entity.custom.MashPipeBlockEntity;
import net.quedoon.giant_potato.block.util.ModBlockHitboxes;
import net.quedoon.giant_potato.block.util.ModProperties;
import net.quedoon.giant_potato.block.util.PipeShape;
import net.quedoon.giant_potato.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class MashPipeBlock extends BlockWithEntity {
    public static final MapCodec<MashPipeBlock> CODEC = createCodec(MashPipeBlock::new);

    public static final EnumProperty<PipeShape> NORTH = ModProperties.NORTH_PIPE_SHAPE;
    public static final EnumProperty<PipeShape> SOUTH = ModProperties.SOUTH_PIPE_SHAPE;
    public static final EnumProperty<PipeShape> EAST = ModProperties.EAST_PIPE_SHAPE;
    public static final EnumProperty<PipeShape> WEST = ModProperties.WEST_PIPE_SHAPE;
    public static final EnumProperty<PipeShape> UP = ModProperties.UP_PIPE_SHAPE;
    public static final EnumProperty<PipeShape> DOWN = ModProperties.DOWN_PIPE_SHAPE;

    public MashPipeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(NORTH, PipeShape.NONE).with(SOUTH, PipeShape.NONE).with(EAST, PipeShape.NONE).with(WEST, PipeShape.NONE).with(UP, PipeShape.NONE).with(DOWN, PipeShape.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH,SOUTH,EAST,WEST,UP,DOWN);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ModBlockHitboxes.Pipe.getShape(state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ModBlockHitboxes.Pipe.getShape(state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return getPipeStates(world, pos);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getPipeStates((World) world, pos);
    }

    private @Nullable BlockState getPipeStates(World world, BlockPos pos) {
        PipeShape north = world.getBlockState(pos.north()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.TRUE : PipeShape.NONE;
        PipeShape south = world.getBlockState(pos.south()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.TRUE : PipeShape.NONE;
        PipeShape east = world.getBlockState(pos.east()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.TRUE : PipeShape.NONE;
        PipeShape west = world.getBlockState(pos.west()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.TRUE : PipeShape.NONE;
        PipeShape up = world.getBlockState(pos.up()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.TRUE : PipeShape.NONE;
        PipeShape down = world.getBlockState(pos.down()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.TRUE : PipeShape.NONE;

        return this.getDefaultState().with(NORTH, north).with(SOUTH, south).with(EAST, east).with(WEST, west).with(UP, up).with(DOWN, down);
    }


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MashPipeBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
