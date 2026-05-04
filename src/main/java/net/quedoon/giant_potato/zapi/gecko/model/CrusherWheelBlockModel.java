package net.quedoon.giant_potato.zapi.gecko.model;

import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.block.entity.custom.CrusherWheelBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class CrusherWheelBlockModel extends GeoModel<CrusherWheelBlockEntity> {
    @Override
    public Identifier getModelResource(CrusherWheelBlockEntity crusherWheelBlockEntity) {
        return GeoModelIdentifier.blockModel("crusher_wheel");
    }

    @Override
    public Identifier getTextureResource(CrusherWheelBlockEntity crusherWheelBlockEntity) {
        return GeoModelIdentifier.blockTexture("crusher_wheel");
    }

    @Override
    public Identifier getAnimationResource(CrusherWheelBlockEntity crusherWheelBlockEntity) {
        return GeoModelIdentifier.blockAnimation("crusher_wheel");
    }
}
