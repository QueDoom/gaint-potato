package net.quedoon.giant_potato.block.util;

import net.minecraft.util.StringIdentifiable;

public enum PipeShape implements StringIdentifiable {
    NONE("none"),
    TRUE("true"),
    OUTPUT("output");

    private final String name;

    @Override
    public String asString() {
        return this.name;
    }

    PipeShape(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.asString();
    }
}
