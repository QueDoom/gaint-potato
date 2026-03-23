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

            public static final TagKey<Block> ELEMENTAL_SMOOTH_POTATOES = createTag("elemental_smooth_potatoes");

            public static final TagKey<Block> MASH_PIPE_CONNECT_TO = createTag("mash_pipe_connect_to");
            public static final TagKey<Block> MASH_BOWLS = createTag("mash_bowls");

            public static final TagKey<Block> MIDAS_HAND_WORKS_ON = createTag("midas_hand_works_on");


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(GiantPotato.MOD_ID, name));
        }
    }
    public static class Items {

        public static final TagKey<Item> MASH_BOWL_POTATO = createTag("mash_bowl_potato");
        public static final TagKey<Item> MASH_BOWL_POISONOUS_POTATO = createTag("mash_bowl_poisonous_potato");


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(GiantPotato.MOD_ID, name));
        }
    }
}
