package net.quedoon.giant_potato.util.gecko.render;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.quedoon.giant_potato.block.entity.custom.CrusherWheelBlockEntity;
import net.quedoon.giant_potato.util.gecko.model.CrusherWheelBlockModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CrusherWheelBlockRenderer extends GeoBlockRenderer<CrusherWheelBlockEntity> {
    public CrusherWheelBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new CrusherWheelBlockModel());
    }
}
