package net.quedoon.giant_potato.fluid;

import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.fluid.fluid.MashFluid;
import net.quedoon.giant_potato.fluid.fluid.PoisonousMashFluid;
import net.quedoon.giant_potato.fluid.item.MashBucketItem;

public class ModFluids {

    public static final FlowableFluid MASH = Registry.register(Registries.FLUID,
            Identifier.of(GiantPotato.MOD_ID, "mash"), new MashFluid.Still());
    public static final FlowableFluid MASH_FLOWING_UNUSED = Registry.register(Registries.FLUID,
            Identifier.of(GiantPotato.MOD_ID, "flowing_mash"), new MashFluid.Flowing());

    public static final FlowableFluid POISONOUS_MASH = Registry.register(Registries.FLUID,
            Identifier.of(GiantPotato.MOD_ID, "poisonous_mash"), new PoisonousMashFluid.Still());
    public static final FlowableFluid POISONOUS_MASH_FLOWING_UNUSED = Registry.register(Registries.FLUID,
            Identifier.of(GiantPotato.MOD_ID, "flowing_poisonous_mash"), new PoisonousMashFluid.Flowing());

    public static final Block MASH_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(GiantPotato.MOD_ID,
            "mash_block"), new FluidBlock(ModFluids.MASH, AbstractBlock.Settings.copy(Blocks.WATER)));
    public static final Item MASH_BUCKET = Registry.register(Registries.ITEM, Identifier.of(GiantPotato.MOD_ID,
            "mash_bucket"), new MashBucketItem(ModFluids.MASH,
            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static final Block POISONOUS_MASH_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(GiantPotato.MOD_ID,
            "poisonous_mash_block"), new FluidBlock(ModFluids.POISONOUS_MASH, AbstractBlock.Settings.copy(Blocks.WATER)));
    public static final Item POISONOUS_MASH_BUCKET = Registry.register(Registries.ITEM, Identifier.of(GiantPotato.MOD_ID,
            "poisonous_mash_bucket"), new MashBucketItem(ModFluids.POISONOUS_MASH,
            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));


    public static void registerFluids() {
        GiantPotato.LOGGER.info("Registering Mod Fluids for " + GiantPotato.MOD_ID);
    }
}
