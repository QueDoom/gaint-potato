package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // getOrCreateTagBuilder()

        getOrCreateTagBuilder(ModTags.Blocks.ELEMENTAL_SMOOTH_POTATOES).add(ModBlocks.SMOOTH_POTATOES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.SMOOTH_POTATOES);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.FOUNDRY);
    }
}
