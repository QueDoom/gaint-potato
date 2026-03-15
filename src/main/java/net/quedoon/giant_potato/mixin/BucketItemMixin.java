package net.quedoon.giant_potato.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Inject(
            method = "playEmptyingSound",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"
            ))
    private void playEmptyingSound(PlayerEntity player, WorldAccess world, BlockPos pos, CallbackInfo ci, @Local SoundEvent soundEvent) {
        soundEvent = SoundEvents.BLOCK_HONEY_BLOCK_PLACE;
    }
}
