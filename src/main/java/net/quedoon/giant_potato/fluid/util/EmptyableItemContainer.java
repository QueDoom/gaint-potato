package net.quedoon.giant_potato.fluid.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public interface EmptyableItemContainer {
    public default Item getEmptyItem() {
        return Items.BUCKET;
    }
}
