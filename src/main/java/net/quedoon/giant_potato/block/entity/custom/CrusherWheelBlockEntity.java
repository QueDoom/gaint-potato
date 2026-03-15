package net.quedoon.giant_potato.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CrusherWheelBlockEntity extends BlockEntity implements GeoBlockEntity {
    private boolean active = false;

    private static final RawAnimation ACTIVE = RawAnimation.begin().thenLoop("active");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CrusherWheelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHER_WHEEL_BE, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "active", 0, animationState -> {
            if (this.active) {
                return animationState.setAndContinue(ACTIVE);
            }
            return PlayState.STOP;
        }));
    }

    public void setActive(boolean value) {
        this.active = value;
        markDirty();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
    }
}
