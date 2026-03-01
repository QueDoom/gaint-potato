package net.quedoon.giant_potato.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.quedoon.giant_potato.GiantPotato;

import java.awt.*;

public class MashBowlBlock extends Block {
    public static final IntProperty STAGE = IntProperty.of("stage", 0, 3);
    private static final VoxelShape SHAPE = ModBlockHitboxes.getMashBowlHitbox();


    public MashBowlBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
        System.out.println("landed, fallDistance: " + fallDistance + ", entity: " + entity.getClass().getSimpleName());
        double random = Math.random();
        if (world.isClient()) return;

        if (fallDistance > 0.75F && entity instanceof PlayerEntity && random > .9) {
            world.setBlockState(pos, state.cycle(STAGE), 4);
        }
    }

}
