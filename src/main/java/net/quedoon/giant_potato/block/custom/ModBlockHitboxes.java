package net.quedoon.giant_potato.block.custom;

import net.minecraft.block.Block;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class ModBlockHitboxes {
    public static VoxelShape getMashBowlHitbox() {
        return VoxelShapes.union(
                  Block.createCuboidShape(0, 0, 0, 16, 8, 16)//,
        );
    }
    public static VoxelShape getCrusherWheelHitbox() {
        return VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 4, 14, 16)//,
        );
    }
    public static VoxelShape getCrusherHitbox() {
        return VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 14, 16)//,
        );
    }
}
