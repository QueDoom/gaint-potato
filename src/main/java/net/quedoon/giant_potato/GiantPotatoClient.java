package net.quedoon.giant_potato;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.renderer.AbstractPipeBlockEntityRenderer;
import net.quedoon.giant_potato.block.entity.renderer.MashTankBlockEntityRenderer;
import net.quedoon.giant_potato.client.registrar.ClientboundPayloadRegistrar;
import net.quedoon.giant_potato.fluid.ModFluids;
import net.quedoon.giant_potato.fluid.render.MashFluidRenderHandler;
import net.quedoon.giant_potato.fluid.render.PoisonousMashFluidRenderHandler;
import net.quedoon.giant_potato.screen.ModScreenHandlers;
import net.quedoon.giant_potato.screen.custom.CrusherScreen;
import net.quedoon.giant_potato.screen.custom.FoundryScreen;
import net.quedoon.giant_potato.screen.custom.MashTankScreen;
import net.quedoon.giant_potato.zapi.gecko.render.CrusherWheelBlockRenderer;
import net.quedoon.giant_potato.zapi.gecko.render.FoundryBlockRenderer;

public class GiantPotatoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.FOUNDRY_BE, FoundryBlockRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.CRUSHER_WHEEL_BE, CrusherWheelBlockRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MASH_TANK, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.MASH_TANK_BE, MashTankBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.MASH_PIPE_BE, AbstractPipeBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.MASH_PIPE_OUTPUT_BE, AbstractPipeBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.FOUNDRY_SCREEN_HANDLER, FoundryScreen::new);
        HandledScreens.register(ModScreenHandlers.CRUSHER_SCREEN_HANDLER, CrusherScreen::new);
        HandledScreens.register(ModScreenHandlers.MASH_TANK_SCREEN_HANDLER, MashTankScreen::new);

//        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.MASH, ModFluids.MASH_FLOWING_UNUSED,
//                SimpleFluidRenderHandler.coloredWater(0xf3f300ff));
//        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
//                ModFluids.MASH,ModFluids.MASH_FLOWING_UNUSED);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.MASH, ModFluids.MASH_FLOWING_UNUSED, new MashFluidRenderHandler());
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.POISONOUS_MASH, ModFluids.POISONOUS_MASH_FLOWING_UNUSED, new PoisonousMashFluidRenderHandler());

        //ClientboundPayloadRegistrar.register();
    }
}

