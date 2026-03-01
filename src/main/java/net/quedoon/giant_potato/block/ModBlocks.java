package net.quedoon.giant_potato.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.custom.MashBowlBlock;

public class ModBlocks {

    public static final Block MASH_BOWL = registerBlock("mash_bowl",
            new MashBowlBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD)
                    .strength(1.0f).resistance(1.0f)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(GiantPotato.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(GiantPotato.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(GiantPotato.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        GiantPotato.LOGGER.info("Registering Mod Blocks for " + GiantPotato.MOD_ID);
    }
}
