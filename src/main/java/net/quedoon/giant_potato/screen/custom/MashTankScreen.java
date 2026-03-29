package net.quedoon.giant_potato.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.screen.render.FluidStackRenderer;
import net.quedoon.giant_potato.util.MouseUtil;

import java.util.Optional;

public class MashTankScreen extends HandledScreen<MashTankScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(GiantPotato.MOD_ID,"textures/gui/mash_tank/mash_tank.png");
    private static final Identifier MASH_TEXTURE =
            Identifier.of(GiantPotato.MOD_ID, "textures/gui/common/mash_bar.png");
    private FluidStackRenderer fluidStackRenderer;

    public MashTankScreen(MashTankScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        assignFluidStackRenderer();
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer((FluidConstants.BUCKET / 81) * 64, true, 16, 64);
    }

    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(Screens.getTextRenderer(this), renderer.getTooltip(handler.blockEntity.fluidStorage, Item.TooltipContext.DEFAULT),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderFluidTooltip(context, mouseX, mouseY, x, y, 80, 8, fluidStackRenderer);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);


        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorageHalf, x + 80, y + 40, 16, 32,
                (FluidConstants.BUCKET / 81) * 16);
        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorageHalfMinus, x + 80, y + 8, 16, 32,
                (FluidConstants.BUCKET / 81) * 16);
        //renderValueMash(context, x, y);
    }

    private void renderValueMash(DrawContext context, int x, int y) {
        context.drawTexture(MASH_TEXTURE, x + 80, y + 8 + 58 - handler.getScaledMashDisplay(), 0,
                58 - handler.getScaledMashDisplay(), 16, handler.getScaledMashDisplay(), 16, 58);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
