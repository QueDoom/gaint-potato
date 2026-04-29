package net.quedoon.giant_potato.block.entity.util.block_entity.pipe;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.util.block.AbstractPipeBlock;

public abstract class AbstractFluidPipeBlockEntity extends AbstractPipeBlockEntity {
    public AbstractFluidPipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, AbstractPipeBlock notOutput, AbstractPipeBlock output) {
        super(type, pos, state, notOutput, output);
    }



}
