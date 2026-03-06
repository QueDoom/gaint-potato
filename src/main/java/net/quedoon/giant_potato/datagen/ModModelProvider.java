package net.quedoon.giant_potato.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SMOOTH_POTATOES);
//        BlockStateModelGenerator.createSlabBlockState(ModBlocks.SMOOTH_POTATOES_SLAB, Identifier.of(GiantPotato.MOD_ID, "smooth_potatoes_slab_bottom"),
//                Identifier.of(GiantPotato.MOD_ID, "smooth_potatoes_slab_top"), Identifier.of(GiantPotato.MOD_ID, "smooth_potatoes_slab_full"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.TILLER, Models.HANDHELD);
    }
}
