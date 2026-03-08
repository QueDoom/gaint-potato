package net.quedoon.giant_potato.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.custom.CrusherBlockEntity;
import net.quedoon.giant_potato.block.entity.custom.CrusherWheelBlockEntity;
import org.jetbrains.annotations.Nullable;

public class CrusherWheelBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<FoundryBlock> CODEC = FoundryBlock.createCodec(FoundryBlock::new);
    protected CrusherWheelBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrusherWheelBlockEntity(pos, state);
    }
}
