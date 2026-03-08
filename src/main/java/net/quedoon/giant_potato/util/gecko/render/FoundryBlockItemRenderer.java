package net.quedoon.giant_potato.util.gecko.render;

import net.quedoon.giant_potato.block.item.FoundryBlockItem;
import net.quedoon.giant_potato.util.gecko.model.FoundryBlockItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FoundryBlockItemRenderer extends GeoItemRenderer<FoundryBlockItem> {
    public FoundryBlockItemRenderer() {
        super(new FoundryBlockItemModel());
    }
}
