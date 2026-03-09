package net.quedoon.giant_potato.util.gecko.model;

import net.minecraft.util.Identifier;
import net.quedoon.giant_potato.GiantPotato;

public class GeoModelIdentifier {
    public static Identifier itemModel(String name) {
        return Identifier.of(GiantPotato.MOD_ID, "geo/item/" + name + ".geo.json");
    }
    public static Identifier blockModel(String name) {
        return Identifier.of(GiantPotato.MOD_ID, "geo/block/" + name + ".geo.json");
    }

    public static Identifier itemTexture(String name) {
        return Identifier.of(GiantPotato.MOD_ID, "textures/item/" + name + ".png");
    }
    public static Identifier blockTexture(String name) {
        return Identifier.of(GiantPotato.MOD_ID, "textures/block/" + name + ".png");
    }

    public static Identifier itemAnimation(String name) {
        return Identifier.of(GiantPotato.MOD_ID, "animations/item/" + name + ".animation.json");
    }
    public static Identifier blockAnimation(String name) {
        return Identifier.of(GiantPotato.MOD_ID, "animations/block/" + name + ".animation.json");
    }
}
