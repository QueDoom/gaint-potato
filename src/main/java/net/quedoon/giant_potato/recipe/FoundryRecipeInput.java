package net.quedoon.giant_potato.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record FoundryRecipeInput(List<ItemStack> input) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return input.get(slot);
    }

    @Override
    public int getSize() {
        return 3;
    }
}
