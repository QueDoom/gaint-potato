package net.quedoon.giant_potato.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;

public class ModTags {
    public static class Blocks {




        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(GiantPotato.MOD_ID, name));
        }
    }
    public static class Items {




        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(GiantPotato.MOD_ID, name));
        }
    }
}
