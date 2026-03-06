package net.quedoon.giant_potato.util.gecko.model;

import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.entity.custom.FoundryBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class FoundryBlockModel extends GeoModel<FoundryBlockEntity> {
    @Override
    public Identifier getModelResource(FoundryBlockEntity foundryBlockEntity) {
        return Identifier.of(GiantPotato.MOD_ID, "geo/models/block/foundry.geo.json");
    }

    @Override
    public Identifier getTextureResource(FoundryBlockEntity foundryBlockEntity) {
        return Identifier.of(GiantPotato.MOD_ID, "geo/textures/block/foundry.png");
    }

    @Override
    public Identifier getAnimationResource(FoundryBlockEntity foundryBlockEntity) {
        return Identifier.of(GiantPotato.MOD_ID, "geo/animations/block/foundry.animation.json");
    }
}
