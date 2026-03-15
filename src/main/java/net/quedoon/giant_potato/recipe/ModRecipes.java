package net.quedoon.giant_potato.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.GiantPotatoClient;

public class ModRecipes {

    public static final RecipeSerializer<FoundryRecipe> FOUNDRY_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(GiantPotato.MOD_ID, "alloying"), new FoundryRecipe.Serializer());
    public static final RecipeType<FoundryRecipe> FOUNDRY_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(GiantPotato.MOD_ID, "alloying"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "alloying";
                }
            });
    public static final RecipeSerializer<CrusherRecipe> CRUSHER_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(GiantPotato.MOD_ID, "crushing"), new CrusherRecipe.Serializer());
    public static final RecipeType<CrusherRecipe> CRUSHER_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(GiantPotato.MOD_ID, "crushing"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "crushing";
                }
            });



    public static final Identifier FOUNDRY_ID = Identifier.of(GiantPotato.MOD_ID, "alloying");
    public static final Identifier CRUSHER_ID = Identifier.of(GiantPotato.MOD_ID, "crushing");
    public static final Identifier ASSEMBLER_ID = Identifier.of(GiantPotato.MOD_ID, "assembling");
    public static final Identifier PRESS_ID = Identifier.of(GiantPotato.MOD_ID, "pressing");


    public static void registerRecipes() {
        GiantPotato.LOGGER.info("Registering Mod Recipes for " + GiantPotato.MOD_ID);
    }
}
