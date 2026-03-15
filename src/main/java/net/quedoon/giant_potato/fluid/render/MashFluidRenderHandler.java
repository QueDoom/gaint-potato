package net.quedoon.giant_potato.fluid.render;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.quedoon.giant_potato.GiantPotato;
import org.jetbrains.annotations.Nullable;

public class MashFluidRenderHandler implements FluidRenderHandler {
    private Sprite[] sprites = new Sprite[2];

    @Override
    public Sprite[] getFluidSprites(@Nullable BlockRenderView view, @Nullable BlockPos pos, FluidState state) {
        return sprites;
    }

    public void updateSprites(Sprite[] customSprites) {
        sprites = customSprites;
    }

    @Override
    public void reloadTextures(SpriteAtlasTexture textureAtlas) {
        sprites[0] = textureAtlas.getSprite(Identifier.of(GiantPotato.MOD_ID, "block/mash_liquid"));
        sprites[1] = textureAtlas.getSprite(Identifier.of(GiantPotato.MOD_ID, "block/mash_liquid_falling"));
    }
}
