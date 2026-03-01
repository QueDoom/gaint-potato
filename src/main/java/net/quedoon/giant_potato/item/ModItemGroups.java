package net.quedoon.giant_potato.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;

public class ModItemGroups {


    public static final ItemGroup GIANT_POTATO_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(net.quedoon.giant_potato.GiantPotato.MOD_ID, "giant_potato"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.giant_potaot"))
                    .icon(() -> new ItemStack(ModItems.TILLER)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.MASH_BOWL);
                        entries.add(ModItems.TILLER);
                            }
                    ).build());

    public static void registerModItemGroups() {
        GiantPotato.LOGGER.info("Registering Mod Item Groups for " + GiantPotato.MOD_ID);
    }
}
