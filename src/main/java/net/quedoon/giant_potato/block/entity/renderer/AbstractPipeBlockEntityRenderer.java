package net.quedoon.giant_potato.block.entity.renderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.quedoon.giant_potato.block.entity.util.block_entity.pipe.AbstractPipeBlockEntity;
import net.quedoon.giant_potato.mixin.access.DebugRendererAccess;
import net.quedoon.giant_potato.util.ModTags;
import org.joml.Vector3f;

public class AbstractPipeBlockEntityRenderer implements BlockEntityRenderer<AbstractPipeBlockEntity> {
    public AbstractPipeBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }
    @Override
    public void render(AbstractPipeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) return;
        if (((DebugRendererAccess) client.debugRenderer).showChunkBorder()) {
            renderInteractionBoxes(entity, matrices, vertexConsumers);
        }
    }

    private void renderInteractionBoxes(AbstractPipeBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        BlockState state = entity.getCachedState();
        if (!state.isIn(ModTags.Blocks.PIPES)) return;
        entity.getHitBoxes().forEach((identifier, hitBox) -> {
            Vector3f color = hitBox.getDebugColor();
            WorldRenderer.drawBox(matrices, vertexConsumers.getBuffer(RenderLayer.LINES), hitBox.getRotatedBox(Direction.NORTH),
                    color.x, color.y, color.z, 1f);
        });
    }
}
