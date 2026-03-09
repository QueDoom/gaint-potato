package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLangGenerator extends FabricLanguageProvider {
    public ModLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("itemGroup.giant_potato", "Giant Potato");

        translationBuilder.add(ModItems.TILLER, "Tiller");
        translationBuilder.add(ModItems.FERTILIZER_DIRT, "Fertilizer Dirt");

        translationBuilder.add(ModBlocks.MASH_BOWL, "Mash Bowl");
        translationBuilder.add(ModBlocks.SMOOTH_POTATOES, "Smooth Potatoes");
        translationBuilder.add(ModBlocks.FOUNDRY, "Foundry");
        translationBuilder.add(ModItems.FOUNDRY, "Foundry");
        translationBuilder.add(ModBlocks.CRUSHER_WHEEL, "Crushing Wheel");
        translationBuilder.add(ModItems.CRUSHER_WHEEL, "Crushing Wheel");
        translationBuilder.add(ModBlocks.CRUSHER, "Crusher");


        translationBuilder.add("gui.giant_potato.foundry", "Alloying");

    }
}
