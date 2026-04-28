package net.quedoon.giant_potato.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.custom.MashPipeBlockEntity;
import net.quedoon.giant_potato.block.util.ModBlockHitboxes;
import net.quedoon.giant_potato.block.util.ModProperties;
import net.quedoon.giant_potato.block.util.PipeShape;
import net.quedoon.giant_potato.item.ModItems;
import net.quedoon.giant_potato.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MashPipeBlock extends BlockWithEntity {
    public static final MapCodec<MashPipeBlock> CODEC = createCodec(MashPipeBlock::new);

    public static final EnumProperty<PipeShape.PipeShapes> NORTH = ModProperties.NORTH_PIPE_SHAPE;
    public static final EnumProperty<PipeShape.PipeShapes> SOUTH = ModProperties.SOUTH_PIPE_SHAPE;
    public static final EnumProperty<PipeShape.PipeShapes> EAST = ModProperties.EAST_PIPE_SHAPE;
    public static final EnumProperty<PipeShape.PipeShapes> WEST = ModProperties.WEST_PIPE_SHAPE;
    public static final EnumProperty<PipeShape.PipeShapes> UP = ModProperties.UP_PIPE_SHAPE;
    public static final EnumProperty<PipeShape.PipeShapes> DOWN = ModProperties.DOWN_PIPE_SHAPE;

    public MashPipeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(NORTH, PipeShape.PipeShapes.NONE).with(SOUTH, PipeShape.PipeShapes.NONE).with(EAST, PipeShape.PipeShapes.NONE).with(WEST, PipeShape.PipeShapes.NONE).with(UP, PipeShape.PipeShapes.NONE).with(DOWN, PipeShape.PipeShapes.NONE));
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
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof MashPipeBlockEntity blockEntity) {
            blockEntity.setPipeStates(((World) world), pos);
        }
        return getPipeStates((World) world, pos);
    }

    private @Nullable BlockState getPipeStates(World world, BlockPos pos) {
        PipeShape.PipeShapes north = world.getBlockState(pos.north()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.PipeShapes.TRUE : PipeShape.PipeShapes.NONE;
        PipeShape.PipeShapes south = world.getBlockState(pos.south()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.PipeShapes.TRUE : PipeShape.PipeShapes.NONE;
        PipeShape.PipeShapes east = world.getBlockState(pos.east()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.PipeShapes.TRUE : PipeShape.PipeShapes.NONE;
        PipeShape.PipeShapes west = world.getBlockState(pos.west()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.PipeShapes.TRUE : PipeShape.PipeShapes.NONE;
        PipeShape.PipeShapes up = world.getBlockState(pos.up()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.PipeShapes.TRUE : PipeShape.PipeShapes.NONE;

        PipeShape.PipeShapes down = world.getBlockState(pos.down()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? PipeShape.PipeShapes.TRUE : PipeShape.PipeShapes.NONE;
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof MashPipeBlockEntity blockEntity)) return this.getDefaultState();
        blockEntity.initializeSides(north, south, east, west, up, down);

        return this.getDefaultState().with(NORTH, north).with(SOUTH, south).with(EAST, east).with(WEST, west).with(UP, up).with(DOWN, down);
    }

//    @Override
//    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if(!(stack.isOf(ModItems.WRENCH))) return ItemActionResult.FAIL;
//        if(!(state.getBlock() instanceof MashPipeBlock)) return ItemActionResult.FAIL;
//
//        if(world.isClient()) {
//            Vec3d posInBlock = hit.getPos();
//            world.addParticle(ParticleTypes.FLAME, posInBlock.x, posInBlock.y, posInBlock.z, 0, 0, 0);
//        } else {
//            Optional<Direction> shape = getPipeShapeFromHitPos(hit, pos);
//            if (shape.isPresent()) {
//                Direction shapeDir = shape.get();
//            }
//        }
//        stack.damage(1, player, EquipmentSlot.MAINHAND);
//        return ItemActionResult.SUCCESS;
//    }

    private static Optional<Direction> getPipeShapeFromHitPos(BlockHitResult hit, BlockPos pos) {
        return Optional.of(Direction.NORTH);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (hit == null) return ActionResult.PASS;
        MashPipeBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity == null) return ActionResult.PASS;
        return blockEntity.attemptInteraction(player, Hand.MAIN_HAND);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.MASH_PIPE_BE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    
    public static @Nullable MashPipeBlockEntity getBlockEntity(BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof MashPipeBlockEntity blockEntity) return blockEntity;
        if (!(world.getBlockEntity(pos) instanceof MashPipeBlockEntity mashPipeBlockEntity)) return null;
        return mashPipeBlockEntity;
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
