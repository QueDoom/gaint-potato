package net.quedoon.giant_potato;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.custom.FoundryBlockEntity;
import net.quedoon.giant_potato.screen.ModScreenHandlers;
import net.quedoon.giant_potato.screen.custom.FoundryScreen;
import net.quedoon.giant_potato.util.gecko.model.CrusherWheelBlockModel;
import net.quedoon.giant_potato.util.gecko.render.CrusherWheelBlockRenderer;
import net.quedoon.giant_potato.util.gecko.render.FoundryBlockRenderer;

public class GiantPotatoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.FOUNDRY_BE, FoundryBlockRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.CRUSHER_WHEEL_BE, CrusherWheelBlockRenderer::new);

        HandledScreens.register(ModScreenHandlers.FOUNDRY_SCREEN_HANDLER, FoundryScreen::new);
    }
}
