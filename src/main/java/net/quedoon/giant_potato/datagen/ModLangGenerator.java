package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.fluid.ModFluids;
import net.quedoon.giant_potato.item.ModItems;
import net.quedoon.giant_potato.util.ModTags;

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
        translationBuilder.add(ModItems.FOUNDRY, "Foundry");
        translationBuilder.add(ModItems.CRUSHER_WHEEL, "Crushing Wheel");
        translationBuilder.add(ModBlocks.CRUSHER, "Crusher");
        translationBuilder.add(ModFluids.MASH_BUCKET, "Mash Bucket");

        translationBuilder.add(ModItems.CHARRED_POTATO, "Charred Potato");
        translationBuilder.add(ModItems.BIOSTEEL_ALLOY, "Biosteel");
        translationBuilder.add(ModItems.POTATO_ALLOY, "Potato Alloy");
        translationBuilder.add(ModItems.POISONOUS_POTATO_ALLOY, "Poisonous Potato Alloy");

        translationBuilder.add(ModTags.Items.MASH_BOWL_POTATO, "Mash Bowl: Potato");
        translationBuilder.add(ModTags.Items.MASH_BOWL_POISONOUS_POTATO, "Mash Bowl: Poisonous Potato");

        translationBuilder.add(ModFluids.MASH_BLOCK, "Mash");


        translationBuilder.add("gui.giant_potato.foundry", "Alloying");
        translationBuilder.add("gui.giant_potato.crusher", "Crushing");
        translationBuilder.add("giant_potato.crusher_workstation", "Crushing");

    }
}
