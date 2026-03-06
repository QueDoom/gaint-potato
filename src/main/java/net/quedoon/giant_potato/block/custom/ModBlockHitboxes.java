package net.quedoon.giant_potato.block.custom;

import net.minecraft.block.Block;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class ModBlockHitboxes {
    public static VoxelShape getMashBowlHitbox() {
        return VoxelShapes.union(
                  Block.createCuboidShape(0, 0, 0, 16, 8, 16)//,
//                Block.createCuboidShape(0, 2, 0, 2, 8, 16),
//                Block.createCuboidShape(14, 2, 0, 16, 8, 2),
//                Block.createCuboidShape(2, 2, 0, 14, 8, 2),
//                Block.createCuboidShape(2, 2, 14, 14, 8, 16)
        );
    }
}
