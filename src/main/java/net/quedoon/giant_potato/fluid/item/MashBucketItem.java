package net.quedoon.giant_potato.fluid.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.quedoon.giant_potato.fluid.util.EmptyableItemContainer;
import net.quedoon.giant_potato.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class MashBucketItem extends BucketItem implements EmptyableItemContainer {
    private final Fluid fluid;

    public MashBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    @Override
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.isIn(ModTags.Fluid.MASH_FLUIDS) ? SoundEvents.BLOCK_HONEY_BLOCK_BREAK : SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        return super.placeFluid(player, world, pos, hitResult);
    }


}
