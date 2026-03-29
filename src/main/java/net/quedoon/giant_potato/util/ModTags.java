package net.quedoon.giant_potato.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;

public class ModTags {
    public static class Fluid {

        public static final TagKey<net.minecraft.fluid.Fluid> MASH = createTag("mash");
        public static final TagKey<net.minecraft.fluid.Fluid> POISONOUS_MASH = createTag("poisonous_mash");
        public static final TagKey<net.minecraft.fluid.Fluid> MASH_FLUIDS = createTag("mash_fluids");

        private static TagKey<net.minecraft.fluid.Fluid> createTag(String name) {
            return TagKey.of(RegistryKeys.FLUID, Identifier.of(GiantPotato.MOD_ID, name));
        }
    }
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
        public static final TagKey<Item> MASH_BOWL_POTATOES = createTag("mash_bowl_potatoes");

        public static final TagKey<Item> MASH_BUCKETS = createTag("mash_buckets");
        public static final TagKey<Item> POISONOUS_MASH_BUCKETS = createTag("poisonous_mash_buckets");


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(GiantPotato.MOD_ID, name));
        }
    }
}
