package net.quedoon.giant_potato.client.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.GiantPotato;

public record ClientboundSetCrusherWheelStatePayload(BlockPos pos) implements CustomPayload {
    public static final Identifier SET_CRUSHER_WHEEL_STATE_PAYLOAD_ID = Identifier.of(GiantPotato.MOD_ID, "set_crusher_wheel_state");
    public static final CustomPayload.Id<ClientboundSetCrusherWheelStatePayload> ID = new CustomPayload.Id<>(SET_CRUSHER_WHEEL_STATE_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, ClientboundSetCrusherWheelStatePayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, ClientboundSetCrusherWheelStatePayload::pos, ClientboundSetCrusherWheelStatePayload::new);


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
