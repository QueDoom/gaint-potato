package net.quedoon.giant_potato.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;

public class MashPipeBlockEntity extends BlockEntity {
    private final boolean isNORTHLocked = false;
    private final boolean isSOUTHLocked = false;
    private final boolean isEASTLocked = false;
    private final boolean isWESTLocked = false;
    private final boolean isUPLocked = false;
    private final boolean isDOWNLocked = false;

    public MashPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MASH_PIPE_BE, pos, state);
    }


}
