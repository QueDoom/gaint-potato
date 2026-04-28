package net.quedoon.giant_potato.block.entity.custom;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.custom.MashPipeBlock;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.data.hitbox.MashPipeHitbox;
import net.quedoon.giant_potato.block.entity.util.AbstractInteractionHitbox;
import net.quedoon.giant_potato.block.util.ModBlockHitboxes;
import net.quedoon.giant_potato.block.util.ModProperties;
import net.quedoon.giant_potato.block.util.PipeShape;
import net.quedoon.giant_potato.util.ModTags;
import net.quedoon.giant_potato.util.ShapeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MashPipeBlockEntity extends BlockEntity {
    private int NORTH = 0;
    private int SOUTH = 0;
    private int EAST = 0;
    private int WEST = 0;
    private int UP = 0;
    private int DOWN = 0;

    private final HashMap<Identifier, AbstractInteractionHitbox> hitBoxes = new HashMap<>();

    private final Map<String, Integer> sidesIntLookup = Map.of(
            "none", 0,
            "none_locked", 1,
            "true", 2,
            "locked", 3,
            "output", 4,
            "output_locked", 5
    );

    private int cooldown = 0;

    public MashPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MASH_PIPE_BE, pos, state);
        this.initializeInnerHitBoxes();
    }

    private void initializeInnerHitBoxes() {
        this.hitBoxes.put(
                MashPipeHitbox.getIdentifier(Direction.NORTH),
                new MashPipeHitbox(
                    this,
                    ShapeUtil.getBoxFromVoxelCoordinates(
                            ModBlockHitboxes.BoxPipe.northFrom(),
                            ModBlockHitboxes.BoxPipe.northTo()
                    ),
                    AbstractInteractionHitbox.RED,
                    Direction.NORTH
            )
        );
        this.hitBoxes.put(
                MashPipeHitbox.getIdentifier(Direction.SOUTH),
                new MashPipeHitbox(
                    this,
                    ShapeUtil.getBoxFromVoxelCoordinates(
                            ModBlockHitboxes.BoxPipe.southFrom(),
                            ModBlockHitboxes.BoxPipe.southTo()
                    ),
                    AbstractInteractionHitbox.RED,
                    Direction.SOUTH
            )
        );
        this.hitBoxes.put(
                MashPipeHitbox.getIdentifier(Direction.EAST),
                new MashPipeHitbox(
                    this,
                    ShapeUtil.getBoxFromVoxelCoordinates(
                            ModBlockHitboxes.BoxPipe.eastFrom(),
                            ModBlockHitboxes.BoxPipe.eastTo()
                    ),
                    AbstractInteractionHitbox.RED,
                    Direction.EAST
            )
        );
        this.hitBoxes.put(
                MashPipeHitbox.getIdentifier(Direction.WEST),
                new MashPipeHitbox(
                    this,
                    ShapeUtil.getBoxFromVoxelCoordinates(
                            ModBlockHitboxes.BoxPipe.westFrom(),
                            ModBlockHitboxes.BoxPipe.westTo()
                    ),
                    AbstractInteractionHitbox.RED,
                    Direction.WEST
            )
        );
        this.hitBoxes.put(
                MashPipeHitbox.getIdentifier(Direction.UP),
                new MashPipeHitbox(
                    this,
                    ShapeUtil.getBoxFromVoxelCoordinates(
                            ModBlockHitboxes.BoxPipe.upFrom(),
                            ModBlockHitboxes.BoxPipe.upTo()
                    ),
                    AbstractInteractionHitbox.RED,
                    Direction.UP
            )
        );
        this.hitBoxes.put(
                MashPipeHitbox.getIdentifier(Direction.DOWN),
                new MashPipeHitbox(
                    this,
                    ShapeUtil.getBoxFromVoxelCoordinates(
                            ModBlockHitboxes.BoxPipe.downFrom(),
                            ModBlockHitboxes.BoxPipe.downTo()
                    ),
                    AbstractInteractionHitbox.RED,
                    Direction.DOWN
            )
        );

    }

    public ActionResult attemptInteraction(PlayerEntity player, Hand hand) {
        BlockState state = this.getCachedState();
        if (!state.isOf(ModBlocks.MASH_PIPE)) return ActionResult.FAIL;
        Pair<AbstractInteractionHitbox, Vec3d> targetedHitbox = getTargetedHitbox(player);
        if (targetedHitbox == null) return ActionResult.PASS;
        return targetedHitbox.getLeft().interact(this, targetedHitbox.getRight(), player, hand);
    }

    @Nullable
    public Pair<AbstractInteractionHitbox, Vec3d> getTargetedHitbox(PlayerEntity player) {
        double distance = -1;
        AbstractInteractionHitbox closestInteraction = null;
        Vec3d closestInteractionHitPos = null;

        double reachDistance = player.isCreative() ? 5.0 : 4.5;
        Vec3d eyePos = player.getEyePos();
        Vec3d fullRangeReach = player.getRotationVector().multiply(reachDistance);
        Vec3d endPos = eyePos.add(fullRangeReach);

        for (var hitBox : this.getHitBoxes().values()) {
            Optional<Vec3d> raycast = hitBox.getRotatedBox(Direction.NORTH).offset(this.pos).raycast(eyePos, endPos);
            if (raycast.isEmpty()) continue;
            Vec3d successfulRaycast = raycast.get();
            double currentDistance = eyePos.squaredDistanceTo(successfulRaycast);
            if (closestInteraction == null || distance > currentDistance) {
                closestInteraction = hitBox;
                distance = currentDistance;
                closestInteractionHitPos = successfulRaycast;
            }
        }
        return closestInteraction == null ? null : new Pair<>(closestInteraction, closestInteractionHitPos);
    }

    public void toggleSideFromHitbox(MashPipeBlockEntity blockEntity, Vec3d actualPos, PlayerEntity player, Hand hand, Direction side) {
        BlockPos pos = blockEntity.getPos();
        boolean isServer = player.getWorld() instanceof ServerWorld;
        if (isServer) {
            toggleOutput(blockEntity, side);
        }
    }

    private void toggleOutput(MashPipeBlockEntity blockEntity, Direction direction) {
        assert blockEntity.getWorld() != null;
        BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
        switch (direction) {
            case DOWN -> {
                int intState = blockEntity.DOWN;
                blockEntity.setSide(state, direction, toggleState(intState));
            }
            case UP -> {
                int intState = blockEntity.UP;
                blockEntity.setSide(state, direction, toggleState(intState));
            }
            case NORTH -> {
                int intState = blockEntity.NORTH;
                blockEntity.setSide(state, direction, toggleState(intState));
            }
            case SOUTH -> {
                int intState = blockEntity.SOUTH;
                blockEntity.setSide(state, direction, toggleState(intState));
            }
            case EAST -> {
                int intState = blockEntity.EAST;
                blockEntity.setSide(state, direction, toggleState(intState));
            }
            case WEST -> {
                int intState = blockEntity.WEST;
                blockEntity.setSide(state, direction, toggleState(intState));
            }
        }
    }

    private int toggleState(int input) {
        return switch (input) {
            case 1 -> 1;
            case 2, 3 -> 5;
            case 4, 5 -> 2;
            default -> 0;
        };
    }

    public HashMap<Identifier, AbstractInteractionHitbox> getHitBoxes() {
        return hitBoxes;
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81); // 1 Bucket = 81000 Droplets = 1000mB || * 64 ==> 64,000mB = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    public void initializeSides(PipeShape.PipeShapes n, PipeShape.PipeShapes s, PipeShape.PipeShapes e,
                                PipeShape.PipeShapes w, PipeShape.PipeShapes u, PipeShape.PipeShapes d) {
        NORTH = PipeShape.As.integer(n);
        SOUTH = PipeShape.As.integer(s);
        EAST = PipeShape.As.integer(e);
        WEST = PipeShape.As.integer(w);
        UP = PipeShape.As.integer(u);
        DOWN = PipeShape.As.integer(d);
    }

    public void setPipeStates(World world, BlockPos pos) {
        if (!isLocked(NORTH)) {
            NORTH = world.getBlockState(pos.north()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? 2 : 0;
        }
        if (!isLocked(SOUTH)) {
            SOUTH = world.getBlockState(pos.south()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? 2 : 0;
        }
        if (!isLocked(EAST)) {
            EAST = world.getBlockState(pos.east()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? 2 : 0;
        }
        if (!isLocked(WEST)) {
            WEST = world.getBlockState(pos.west()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? 2 : 0;
        }
        if (!isLocked(UP)) {
            UP = world.getBlockState(pos.up()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? 2 : 0;
        }
        if (!isLocked(DOWN)) {
            DOWN = world.getBlockState(pos.down()).isIn(ModTags.Blocks.MASH_PIPE_CONNECT_TO) ? 2 : 0;
        }
        BlockState state = world.getBlockState(pos);
        updateSides(state, world, pos);
    }

    private boolean isLocked(int value) {
        return value == 1 || value == 3 || value == 5;
    }

    public void setSide(BlockState state, Direction side, PipeShape.PipeShapes value) {
        World world = getWorld();
        if(world == null) return;
        BlockPos pos = getPos();

        int valueInt = PipeShape.As.integer(value);
        switch (side) {
            case Direction.NORTH -> this.NORTH = valueInt;
            case Direction.SOUTH -> this.SOUTH = valueInt;
            case Direction.EAST -> this.EAST = valueInt;
            case Direction.WEST -> this.WEST = valueInt;
            case Direction.UP -> this.UP = valueInt;
            case Direction.DOWN -> this.DOWN = valueInt;
        }
        markDirty();
        updateSides(state, world, pos);
    }

    public void setSide(BlockState state, Direction side, int valueInt) {
        World world = getWorld();
        if(world == null) return;
        BlockPos pos = getPos();

        switch (side) {
            case Direction.NORTH -> this.NORTH = valueInt;
            case Direction.SOUTH -> this.SOUTH = valueInt;
            case Direction.EAST -> this.EAST = valueInt;
            case Direction.WEST -> this.WEST = valueInt;
            case Direction.UP -> this.UP = valueInt;
            case Direction.DOWN -> this.DOWN = valueInt;
        }
        markDirty();
        updateSides(state, world, pos);
    }

    public void updateSides(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state
                .with(ModProperties.NORTH_PIPE_SHAPE, PipeShape.As.pipeShapes(NORTH))
                .with(ModProperties.SOUTH_PIPE_SHAPE, PipeShape.As.pipeShapes(SOUTH))
                .with(ModProperties.EAST_PIPE_SHAPE, PipeShape.As.pipeShapes(EAST))
                .with(ModProperties.WEST_PIPE_SHAPE, PipeShape.As.pipeShapes(WEST))
                .with(ModProperties.UP_PIPE_SHAPE, PipeShape.As.pipeShapes(UP))
                .with(ModProperties.DOWN_PIPE_SHAPE, PipeShape.As.pipeShapes(DOWN))
        );
    }

    public void tick(World world, BlockPos pos, BlockState state) {
    }

    
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        this.NORTH = nbt.getInt("north");
        this.SOUTH = nbt.getInt("south");
        this.EAST = nbt.getInt("east");
        this.WEST = nbt.getInt("west");
        this.UP = nbt.getInt("up");
        this.DOWN = nbt.getInt("down");
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("north", this.NORTH);
        nbt.putInt("south", this.SOUTH);
        nbt.putInt("east", this.EAST);
        nbt.putInt("west", this.WEST);
        nbt.putInt("up", this.UP);
        nbt.putInt("down", this.DOWN);
        SingleVariantStorage.writeNbt(fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }
}
