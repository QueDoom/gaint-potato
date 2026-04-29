package net.quedoon.giant_potato.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.util.block_entity.pipe.AbstractMashPipeBlockEntity;
import net.quedoon.giant_potato.block.entity.util.block.AbstractPipeBlock;
import net.quedoon.giant_potato.util.ModTags;

import java.util.Collection;
import java.util.HashSet;

public class MashPipeOutputBlockEntity extends AbstractMashPipeBlockEntity {

    private int cooldown = 0;

    public MashPipeOutputBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MASH_PIPE_OUTPUT_BE, pos, state, (AbstractPipeBlock) ModBlocks.MASH_PIPE, (AbstractPipeBlock) ModBlocks.MASH_PIPE_OUTPUT);
    }

//    public void tick(World world, BlockPos pos, BlockState state) {
//        if (this.cooldown < 5) {
//            this.cooldown += 1;
//        } else {
//            this.cooldown = 0;
//            attemptToFillInputs(world, pos, state);
//        }
//    }

//    public boolean attemptToFillInputs(World world, BlockPos pos, BlockState state) {
//        for (Direction connectedDirection : getConnectedDirections(this)) {
//            BlockState pullFrom = world.getBlockState(pos.offset(connectedDirection));
//            if(pullFrom.isIn(ModTags.Blocks.MASH_MACHINES)) {
//                for (BlockPos input : findInputs(world, pos)) {
//
//                }
//            }
//        }
//    }

    public static Collection<BlockPos> findInputs(World world, BlockPos pos) {
        //positions that are already processed
        Collection<BlockPos> visitedPositions = new HashSet<>();
        //positions we are currently looking at
        Collection<BlockPos> currentPositions = new HashSet<>();
        //new positions are added here
        Collection<BlockPos> newPositions = new HashSet<>();
        //resulting machines
        Collection<BlockPos> machines = new HashSet<>();

        currentPositions.add(pos);

        while(!currentPositions.isEmpty()) {
            for(BlockPos currentPosition : currentPositions) {
                for(Direction dir : Direction.values()) {
                    //check each neighbor of the current positions
                    BlockPos newPos = currentPosition.offset(dir);
                    //Do not check blocks that were already checked
                    if(!visitedPositions.contains(newPos)) {
                        if (world.getBlockState(newPos).isIn(ModTags.Blocks.MASH_PIPES)) {
                            //Add pipes to positions for the next iteration
                            newPositions.add(newPos);
                        } else if (world.getBlockState(newPos).isIn(ModTags.Blocks.MASH_MACHINES)) {
                            //Add machines to output set
                            machines.add(newPos);
                        }
                        //Add checked blocks here so that they are not checked again
                        visitedPositions.add(newPos);
                    }
                }
            }
            //Check the new positions in the next iteration
            currentPositions = newPositions;
            newPositions = new HashSet<>();
        }

        return machines;
    }

}
