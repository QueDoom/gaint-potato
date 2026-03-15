package net.quedoon.giant_potato.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.sound.Sound;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class MashFluid extends FlowableFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.MASH_FLOWING_UNUSED;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.MASH;
    }

    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 15;
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.MASH_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 99999;
    }

    @Override
    protected float getBlastResistance() {
        return 6f;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.ofNullable(SoundEvents.BLOCK_HONEY_BLOCK_BREAK);
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluids.MASH_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }


    @Override
    public boolean isStill(FluidState state) {
        return true;
    }

    public static class Flowing extends MashFluid {

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        protected int getMaxFlowDistance(WorldView world) {
            return 0;
        }

        @Override
        public int getLevel(FluidState state) {
            return 0;
        }
    }

    public static class Still extends MashFluid {

        @Override
        protected int getMaxFlowDistance(WorldView world) {
            return 0;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
}
