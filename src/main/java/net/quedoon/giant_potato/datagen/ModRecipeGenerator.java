package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.item.ModItems;
import net.quedoon.giant_potato.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.TILLER)
                .input(Items.DIAMOND_HOE)
                .input(Items.POISONOUS_POTATO)
                .criterion(hasItem(Items.POTATO), conditionsFromItem(Items.POTATO))
                .group("tiller")
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MASH_BOWL)
                .pattern("   ")
                .pattern("S S")
                .pattern("SSS")
                .input('S', ItemTags.WOODEN_SLABS)
                .criterion(hasItem(Items.OAK_SLAB), conditionsFromTag(ItemTags.WOODEN_SLABS))
                .group("mash_bowl")
                .offerTo(recipeExporter);
    }
}
