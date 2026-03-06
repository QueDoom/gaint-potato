package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FERTILIZER_DIRT)
                .input(ModItems.TILLER)
                .input(Items.DIRT)
                .input(Items.POISONOUS_POTATO)
                .criterion(hasItem(ModItems.TILLER), conditionsFromItem(ModItems.TILLER))
                .group("fertilizer_dirt")
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_POTATOES, 8)
                .pattern("SSS")
                .pattern("SFS")
                .pattern("SSS")
                .input('S', Blocks.SMOOTH_STONE)
                .input('F', ModItems.FERTILIZER_DIRT)
                .criterion(hasItem(Blocks.SMOOTH_STONE), conditionsFromItem(Blocks.SMOOTH_STONE))
                .group("smooth_potatoes")
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.IRON_GOLEM_SPAWN_EGG)
                .pattern("III")
                .pattern("IFI")
                .pattern("SSS")
                .input('S', ModBlocks.SMOOTH_POTATOES)
                .input('F', ModItems.FERTILIZER_DIRT)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(ModItems.FERTILIZER_DIRT), conditionsFromItem(ModItems.FERTILIZER_DIRT))
                .group("foundry")
                .offerTo(recipeExporter);
    }
}
