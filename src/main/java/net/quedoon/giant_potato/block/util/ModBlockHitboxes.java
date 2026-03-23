package net.quedoon.giant_potato.block.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class ModBlockHitboxes {
    public static VoxelShape getMashBowlHitbox() {
        return VoxelShapes.union(
                  Block.createCuboidShape(0, 0, 0, 16, 8, 16)//,
        );
    }
    public static VoxelShape getCrusherWheelHitbox(Direction direction) {
        VoxelShape north = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 13, 3));
        VoxelShape south = VoxelShapes.union(Block.createCuboidShape( 0, 0, 13,16, 13, 16));
        VoxelShape east = VoxelShapes.union(Block.createCuboidShape(13, 0, 0, 16, 13, 16));
        VoxelShape west = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 4, 13, 16));
        return switch (direction) {
            case Direction.SOUTH -> south;
            case Direction.EAST -> east;
            case Direction.WEST -> west;
            default -> north;
        };
    }
    public static VoxelShape getCrusherHitbox() {
        return VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 13, 16)//,
        );
    }

    public static class Pipe {
        public static VoxelShape center() {
            return Block.createCuboidShape(5.5, 5.5, 5.5, 10.5, 10.5, 10.5);
        }
        public static VoxelShape north() {
            return Block.createCuboidShape(5.5, 5.5, 0, 10.5, 10.5, 5.5);
        }
        public static VoxelShape south() {
            return Block.createCuboidShape(5.5, 5.5, 10.5, 10.5, 10.5, 16);
        }
        public static VoxelShape east() {
            return Block.createCuboidShape(10.5, 5.5, 5.5, 16, 10.5, 10.5);
        }
        public static VoxelShape west() {
            return Block.createCuboidShape(0, 5.5, 5.5, 5.5, 10.5, 10.5);
        }
        public static VoxelShape up() {
            return Block.createCuboidShape(5.5, 10.5, 5.5, 10.5, 16, 10.5);
        }
        public static VoxelShape down() {
            return Block.createCuboidShape(5.5, 0, 5.5, 10.5, 5.5, 10.5);
        }

        public static VoxelShape getShape(BlockState state) {
            PipeShape north = state.get(ModProperties.NORTH_PIPE_SHAPE);
            PipeShape south = state.get(ModProperties.SOUTH_PIPE_SHAPE);
            PipeShape east = state.get(ModProperties.EAST_PIPE_SHAPE);
            PipeShape west = state.get(ModProperties.WEST_PIPE_SHAPE);
            PipeShape up = state.get(ModProperties.UP_PIPE_SHAPE);
            PipeShape down = state.get(ModProperties.DOWN_PIPE_SHAPE);
            VoxelShape empty = VoxelShapes.empty();
            VoxelShape center = center();
            VoxelShape northShape = north != PipeShape.NONE ? north() : empty;
            VoxelShape southShape = south != PipeShape.NONE ? south() : empty;
            VoxelShape eastShape = east != PipeShape.NONE ? east() : empty;
            VoxelShape westShape = west != PipeShape.NONE ? west() : empty;
            VoxelShape upShape = up != PipeShape.NONE ? up() : empty;
            VoxelShape downShape = down != PipeShape.NONE ? down() : empty;
            return VoxelShapes.union(center, northShape, southShape, eastShape, westShape, upShape, downShape);
        }
    }
}
