package net.quedoon.giant_potato.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CrusherWheelBlockEntity extends BlockEntity implements GeoBlockEntity {

    protected RawAnimation ACTIVE = RawAnimation.begin().thenLoop("active");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CrusherWheelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHING_WHEEL_BE, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "stop", 0, animationState -> PlayState.STOP));
        controllerRegistrar.add(new AnimationController<>(this, "active", 0, animationState -> PlayState.STOP).triggerableAnim("active", ACTIVE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
