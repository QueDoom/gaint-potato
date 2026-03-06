package net.quedoon.giant_potato.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.custom.FoundryBlockEntity;


public class ModBlockEntities {
    public static final BlockEntityType<FoundryBlockEntity> FOUNDRY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(GiantPotato.MOD_ID, "foundry_be"),
                    BlockEntityType.Builder.create(FoundryBlockEntity::new, ModBlocks.FOUNDRY).build(null));


    public static void registerBlockEntities() {
        GiantPotato.LOGGER.info("Registering Mod Block Entities for " + GiantPotato.MOD_ID);
    }
}
