package net.quedoon.giant_potato;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.fluid.ModFluids;
import net.quedoon.giant_potato.item.ModItemGroups;
import net.quedoon.giant_potato.item.ModItems;
import net.quedoon.giant_potato.recipe.ModRecipes;
import net.quedoon.giant_potato.screen.ModScreenHandlers;
import net.quedoon.giant_potato.client.packet.ClientboundSetCrusherWheelStatePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiantPotato implements ModInitializer {
	public static final String MOD_ID = "giant_potato";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Giant Potato Initialized");

		PayloadTypeRegistry.playC2S().register(ClientboundSetCrusherWheelStatePayload.ID, ClientboundSetCrusherWheelStatePayload.CODEC);

		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModFluids.registerFluids();

		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();






	}
}