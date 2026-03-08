package net.quedoon.giant_potato.util.gecko.model;

import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.block.entity.custom.CrusherWheelBlockEntity;
import net.quedoon.giant_potato.util.QueT.GeoModelIdentifier;
import software.bernie.geckolib.model.GeoModel;

public class CrusherWheelBlockModel extends GeoModel<CrusherWheelBlockEntity> {
    @Override
    public Identifier getModelResource(CrusherWheelBlockEntity crusherWheelBlockEntity) {
        return GeoModelIdentifier.blockModel("crushing_wheel");
    }

    @Override
    public Identifier getTextureResource(CrusherWheelBlockEntity crusherWheelBlockEntity) {
        return GeoModelIdentifier.blockTexture("crushing_wheel");
    }

    @Override
    public Identifier getAnimationResource(CrusherWheelBlockEntity crusherWheelBlockEntity) {
        return GeoModelIdentifier.blockAnimation("crushing_wheel");
    }
}
