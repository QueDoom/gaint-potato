package net.quedoon.giant_potato;

import net.fabricmc.api.ModInitializer;

import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.item.ModItemGroups;
import net.quedoon.giant_potato.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class GiantPotato implements ModInitializer {
	public static final String MOD_ID = "giant_potato";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Giant Potato Initialized");

		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();


	}
}