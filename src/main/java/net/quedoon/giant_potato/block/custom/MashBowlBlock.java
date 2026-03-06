package net.quedoon.giant_potato.block.custom;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        System.out.println("landed");
        if (!world.isClient()) {
            if(entity instanceof PlayerEntity player) {
                spawnBreakParticles(world, player, BlockPos.ofFloored(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), Blocks.SPONGE.getDefaultState());
                player.playSoundToPlayer(SoundEvents.ENTITY_SLIME_SQUISH_SMALL, SoundCategory.BLOCKS, 1, 1);
                double random = Math.random();
                System.out.println("landed_server, " + random);
                if (fallDistance > 0.5F && random > .9) {
                    spawnBreakParticles(world, player, BlockPos.ofFloored(pos.getX() + 0.4, pos.getY() + 0.4, pos.getZ() + 0.4), Blocks.SPONGE.getDefaultState());
                    spawnBreakParticles(world, player, BlockPos.ofFloored(pos.getX() + 0.6, pos.getY() + 0.6, pos.getZ() + 0.6), Blocks.SPONGE.getDefaultState());
                    player.playSoundToPlayer(SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1, 1);
                    System.out.println("landed_server_height, " + fallDistance);
                    world.setBlockState(pos, state.cycle(STAGE), 4);
                }
            }
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

}
