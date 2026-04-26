package net.quedoon.giant_potato.block.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.joml.Vector3d;

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
            PipeShape.PipeShapes north = state.get(ModProperties.NORTH_PIPE_SHAPE);
            PipeShape.PipeShapes south = state.get(ModProperties.SOUTH_PIPE_SHAPE);
            PipeShape.PipeShapes east = state.get(ModProperties.EAST_PIPE_SHAPE);
            PipeShape.PipeShapes west = state.get(ModProperties.WEST_PIPE_SHAPE);
            PipeShape.PipeShapes up = state.get(ModProperties.UP_PIPE_SHAPE);
            PipeShape.PipeShapes down = state.get(ModProperties.DOWN_PIPE_SHAPE);
            VoxelShape empty = VoxelShapes.empty();
            VoxelShape center = center();
            VoxelShape northShape = north != PipeShape.PipeShapes.NONE ? north() : empty;
            VoxelShape southShape = south != PipeShape.PipeShapes.NONE ? south() : empty;
            VoxelShape eastShape = east != PipeShape.PipeShapes.NONE ? east() : empty;
            VoxelShape westShape = west != PipeShape.PipeShapes.NONE ? west() : empty;
            VoxelShape upShape = up != PipeShape.PipeShapes.NONE ? up() : empty;
            VoxelShape downShape = down != PipeShape.PipeShapes.NONE ? down() : empty;
            return VoxelShapes.union(center, northShape, southShape, eastShape, westShape, upShape, downShape);
        }
    }

    public static class BoxPipe {
        public static Vec3d centerFrom() {
            //return Block.createCuboidShape(5.5, 5.5, 5.5, 10.5, 10.5, 10.5);
            return new Vec3d(5.5, 5.5, 5.5);
        }
        public static Vec3d centerTo() {
            //return Block.createCuboidShape(5.5, 5.5, 5.5, 10.5, 10.5, 10.5);
            return new Vec3d(10.5, 10.5, 10.5);
        }
        public static Vec3d northFrom() {
            //return Block.createCuboidShape(5.5, 5.5, 0, 10.5, 10.5, 5.5);
            return new Vec3d(5.5, 5.5, 0);
        }
        public static Vec3d northTo() {
            //return Block.createCuboidShape(5.5, 5.5, 0, 10.5, 10.5, 5.5);
            return new Vec3d(10.5, 10.5, 5.5);
        }
        public static Vec3d southFrom() {
            //return Block.createCuboidShape(5.5, 5.5, 10.5, 10.5, 10.5, 16);
            return new Vec3d(5.5, 5.5, 10.5);
        }
        public static Vec3d southTo() {
            //return Block.createCuboidShape(5.5, 5.5, 10.5, 10.5, 10.5, 16);
            return new Vec3d(10.5, 10.5, 16);
        }
        public static Vec3d eastFrom() {
            //return Block.createCuboidShape(10.5, 5.5, 5.5, 16, 10.5, 10.5);
            return new Vec3d(0.5, 5.5, 5.5);
        }
        public static Vec3d eastTo() {
            //return Block.createCuboidShape(10.5, 5.5, 5.5, 16, 10.5, 10.5);
            return new Vec3d(16, 10.5, 10.5);
        }
        public static Vec3d westFrom() {
            //return Block.createCuboidShape(0, 5.5, 5.5, 5.5, 10.5, 10.5);
            return new Vec3d(0, 5.5, 5.5);
        }
        public static Vec3d westTo() {
            //return Block.createCuboidShape(0, 5.5, 5.5, 5.5, 10.5, 10.5);
            return new Vec3d(5.5, 10.5, 10.5);
        }
        public static Vec3d upFrom() {
            //return Block.createCuboidShape(5.5, 10.5, 5.5, 10.5, 16, 10.5);
            return new Vec3d(5.5, 10.5, 5.5);
        }
        public static Vec3d upTo() {
            //return Block.createCuboidShape(5.5, 10.5, 5.5, 10.5, 16, 10.5);
            return new Vec3d(10.5, 16, 10.5);
        }
        public static Vec3d downFrom() {
            //return Block.createCuboidShape(5.5, 0, 5.5, 10.5, 5.5, 10.5);
            return new Vec3d(5.5, 0, 5.5);
        }
        public static Vec3d downTo() {
            //return Block.createCuboidShape(5.5, 0, 5.5, 10.5, 5.5, 10.5);
            return new Vec3d(10.5, 5.5, 10.5);
        }
    }
}
