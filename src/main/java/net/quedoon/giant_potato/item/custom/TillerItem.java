package net.quedoon.giant_potato.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TillerItem extends Item {
    public TillerItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            ItemStack moreDamaged = stack.copy();
            moreDamaged.setDamage(stack.getDamage() + 1);
            return moreDamaged;
        }

        return ItemStack.EMPTY;
    }
}
