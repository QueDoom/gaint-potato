package net.quedoon.giant_potato.screen;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.screen.custom.FoundryScreenHandler;

public class ModScreenHandlers {

    public static final ScreenHandlerType<FoundryScreenHandler> FOUNDRY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(GiantPotato.MOD_ID, "foundry_screen_handler"),
                    new ExtendedScreenHandlerType<>(FoundryScreenHandler::new, BlockPos.PACKET_CODEC));


    public static void registerScreenHandlers() {
        GiantPotato.LOGGER.info("Registering Mod Screen Handlers for " + GiantPotato.MOD_ID);
    }
}
