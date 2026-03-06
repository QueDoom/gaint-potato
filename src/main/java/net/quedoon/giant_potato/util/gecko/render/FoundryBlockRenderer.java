package net.quedoon.giant_potato.util.gecko.render;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.quedoon.giant_potato.block.entity.custom.FoundryBlockEntity;
import net.quedoon.giant_potato.util.gecko.model.FoundryBlockModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FoundryBlockRenderer extends GeoBlockRenderer<FoundryBlockEntity> {
    public FoundryBlockRenderer(EntityRendererFactory.Context context) {
        super(new FoundryBlockModel());
    }
}
