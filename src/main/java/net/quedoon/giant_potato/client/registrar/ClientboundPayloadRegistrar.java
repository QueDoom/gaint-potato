package net.quedoon.giant_potato.client.registrar;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.custom.CrusherWheelBlockEntity;
import net.quedoon.giant_potato.client.packet.ClientboundSetCrusherWheelStatePayload;

public class ClientboundPayloadRegistrar {
    public static void register() {
        registerSetCrusherWheelPayload();



    }

    private static void registerSetCrusherWheelPayload() {
        ClientPlayNetworking.registerGlobalReceiver(ClientboundSetCrusherWheelStatePayload.ID, (payload, context) -> {
            ClientWorld world = context.client().world;

            if (world == null) {
                return;
            }

            BlockPos pos = payload.pos();
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof CrusherWheelBlockEntity blockEntity) {
                blockEntity.setActive(true);
            }
        });
    }
}
