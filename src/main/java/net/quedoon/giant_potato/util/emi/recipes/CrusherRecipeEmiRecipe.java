package net.quedoon.giant_potato.util.emi.recipes;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.recipe.CrusherRecipe;
import net.quedoon.giant_potato.recipe.ModRecipes;
import net.quedoon.giant_potato.util.emi.GiantPotatoEmiPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrusherRecipeEmiRecipe extends BasicEmiRecipe {

    public CrusherRecipeEmiRecipe(RecipeEntry<CrusherRecipe> recipe) {
        super(GiantPotatoEmiPlugin.CRUSHER_CATEGORY, recipe.id(), 70, 18);
        this.inputs.add(EmiIngredient.of(recipe.value().getIngredients().get(0)));
        this.outputs.add(EmiStack.of(recipe.value().getResult(null)));
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);
        widgetHolder.addSlot(inputs.get(0), 0, 0);
        widgetHolder.addSlot(outputs.get(0), 58, 0).recipeContext(this);
    }

    @Override
    public boolean supportsRecipeTree() {
        return true;
    }
}
