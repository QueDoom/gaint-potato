package net.quedoon.giant_potato.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.*;
import net.quedoon.giant_potato.block.entity.custom.MashPipeBlockEntity;
import net.quedoon.giant_potato.block.entity.util.block.AbstractPipeBlock;
import org.jetbrains.annotations.Nullable;

public class MashPipeBlock extends AbstractPipeBlock {
    public static final MapCodec<MashPipeBlock> CODEC = createCodec(MashPipeBlock::new);

    public MashPipeBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MashPipeBlockEntity(pos, state);
    }


}
