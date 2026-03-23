package net.quedoon.giant_potato.block.util;

import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final EnumProperty<PipeShape> NORTH_PIPE_SHAPE = EnumProperty.of("north_pipe_shape", PipeShape.class);
    public static final EnumProperty<PipeShape> SOUTH_PIPE_SHAPE = EnumProperty.of("south_pipe_shape", PipeShape.class);
    public static final EnumProperty<PipeShape> EAST_PIPE_SHAPE = EnumProperty.of("east_pipe_shape", PipeShape.class);
    public static final EnumProperty<PipeShape> WEST_PIPE_SHAPE = EnumProperty.of("west_pipe_shape", PipeShape.class);
    public static final EnumProperty<PipeShape> UP_PIPE_SHAPE = EnumProperty.of("up_pipe_shape", PipeShape.class);
    public static final EnumProperty<PipeShape> DOWN_PIPE_SHAPE = EnumProperty.of("down_pipe_shape", PipeShape.class);
}
