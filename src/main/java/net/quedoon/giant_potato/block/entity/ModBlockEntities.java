package net.quedoon.giant_potato.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.custom.*;


public class ModBlockEntities {
    public static final BlockEntityType<FoundryBlockEntity> FOUNDRY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(GiantPotato.MOD_ID, "foundry_be"),
                    BlockEntityType.Builder.create(FoundryBlockEntity::new, ModBlocks.FOUNDRY).build(null));

    public static final BlockEntityType<CrusherBlockEntity> CRUSHER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(GiantPotato.MOD_ID, "crusher_be"),
                    BlockEntityType.Builder.create(CrusherBlockEntity::new, ModBlocks.CRUSHER).build(null));

    public static final BlockEntityType<CrusherWheelBlockEntity> CRUSHER_WHEEL_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(GiantPotato.MOD_ID, "crusher_wheel_be"),
                    BlockEntityType.Builder.create(CrusherWheelBlockEntity::new, ModBlocks.CRUSHER_WHEEL).build(null));

    public static final BlockEntityType<MashTankBlockEntity> MASH_TANK_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(GiantPotato.MOD_ID, "mash_tank_be"),
                    BlockEntityType.Builder.create(MashTankBlockEntity::new, ModBlocks.MASH_TANK).build(null));

    public static final BlockEntityType<MashPipeBlockEntity> MASH_PIPE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(GiantPotato.MOD_ID, "mash_pipe_be"),
                    BlockEntityType.Builder.create(MashPipeBlockEntity::new, ModBlocks.MASH_PIPE).build(null));



    public static void registerBlockEntities() {
        GiantPotato.LOGGER.info("Registering Mod Block Entities for " + GiantPotato.MOD_ID);
    }
}
