package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.quedoon.giant_potato.fluid.ModFluids;
import net.quedoon.giant_potato.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {


    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.MASH_BOWL_POTATO).add(Items.POTATO);
        getOrCreateTagBuilder(ModTags.Items.MASH_BOWL_POISONOUS_POTATO).add(Items.POISONOUS_POTATO);
        getOrCreateTagBuilder(ModTags.Items.MASH_BOWL_POTATOES).add(Items.POTATO).add(Items.POISONOUS_POTATO);

        getOrCreateTagBuilder(ModTags.Items.MASH_BUCKETS).add(ModFluids.MASH_BUCKET);
        getOrCreateTagBuilder(ModTags.Items.POISONOUS_MASH_BUCKETS).add(ModFluids.POISONOUS_MASH_BUCKET);
    }
}
