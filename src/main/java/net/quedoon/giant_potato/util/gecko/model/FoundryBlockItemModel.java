package net.quedoon.giant_potato.util.gecko.model;

import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.block.item.FoundryBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class FoundryBlockItemModel extends GeoModel<FoundryBlockItem> {
    @Override
    public Identifier getModelResource(FoundryBlockItem foundryBlockItem) {
        return GeoModelIdentifier.itemModel("foundry");
    }

    @Override
    public Identifier getTextureResource(FoundryBlockItem foundryBlockItem) {
        return GeoModelIdentifier.blockTexture("foundry");
    }

    @Override
    public Identifier getAnimationResource(FoundryBlockItem foundryBlockItem) {
        return GeoModelIdentifier.itemAnimation("foundry");
    }
}
