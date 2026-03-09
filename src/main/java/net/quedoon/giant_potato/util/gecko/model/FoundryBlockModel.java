package net.quedoon.giant_potato.util.gecko.model;

import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.block.entity.custom.FoundryBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class FoundryBlockModel extends GeoModel<FoundryBlockEntity> {
    @Override
    public Identifier getModelResource(FoundryBlockEntity foundryBlockEntity) {
        return GeoModelIdentifier.blockModel("foundry");
    }

    @Override
    public Identifier getTextureResource(FoundryBlockEntity foundryBlockEntity) {
        return GeoModelIdentifier.blockTexture("foundry");
    }

    @Override
    public Identifier getAnimationResource(FoundryBlockEntity foundryBlockEntity) {
        return GeoModelIdentifier.blockAnimation("foundry");
    }
}
