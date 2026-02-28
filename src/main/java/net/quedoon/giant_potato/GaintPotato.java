package net.quedoon.giant_potato;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GaintPotato implements ModInitializer {
	public static final String MOD_ID = "giant_potato";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Giant Potato Initialized");
	}
}