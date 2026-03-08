package net.quedoon.giant_potato.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;

public class FoundryScreen extends HandledScreen<FoundryScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(GiantPotato.MOD_ID, "textures/gui/foundry/foundry.png");
    private static final Identifier MASH_TEXTURE =
            Identifier.of(GiantPotato.MOD_ID, "textures/gui/foundry/mash_bar.png");
    private static final Identifier PROGRESS_TEXTURE =
            Identifier.of(GiantPotato.MOD_ID, "textures/gui/foundry/progress.png");

    public FoundryScreen(FoundryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        // Get rid of the Title and Inventory Title
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        renderValueMash(context, x, y);

    }

    private void renderValueMash(DrawContext context, int x, int y) {
        context.drawTexture(MASH_TEXTURE, x + 12, y + 12 + 58 - handler.getScaledMashDisplay(), 0,
                58 - handler.getScaledMashDisplay(), 16, handler.getScaledMashDisplay(), 16, 58);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(PROGRESS_TEXTURE, x + 104, y + 36, 0, 0,
                    handler.getScaledArrowProgress(), 12, 24, 12);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
