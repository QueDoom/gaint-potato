package net.quedoon.giant_potato.item;

import net.minecraft.block.BlockKeys;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.item.custom.TillerItem;

public class ModItems {
    public static Item TILLER = registerItem("tiller", new TillerItem(new Item.Settings().maxCount(1).maxDamage(500)));
    public static Item FERTILIZER_DIRT = registerItem("fertilizer_dirt", new Item(new Item.Settings()));
    public static Item FOUNDRY = registerItem("foundry", new BlockItem(ModBlocks.FOUNDRY, new Item.Settings()));
    public static Item CRUSHER_WHEEL = registerItem("crusher_wheel", new BlockItem(ModBlocks.CRUSHER_WHEEL, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(GiantPotato.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GiantPotato.LOGGER.info("Registering Mod Items for " + GiantPotato.MOD_ID);
    }
}
