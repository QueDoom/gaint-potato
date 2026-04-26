package net.quedoon.giant_potato.block.entity.data.hitbox;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.entity.custom.MashPipeBlockEntity;
import net.quedoon.giant_potato.block.entity.util.AbstractInteractionHitbox;
import net.quedoon.giant_potato.util.GetStringFromDirection;
import org.joml.Vector3f;


@SuppressWarnings({"unused"})
public class MashPipeHitbox extends AbstractInteractionHitbox {
private final MashPipeBlockEntity blockEntity;
    private final Direction side;

    public MashPipeHitbox(MashPipeBlockEntity blockEntity, Box box, Vector3f debugColor, Direction side) {
        super(box, debugColor);
        this.blockEntity = blockEntity;
        this.side = side;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.of(GiantPotato.MOD_ID, "mash_pipe_" + GetStringFromDirection.minecraftDir(side));
    }

    public static Identifier getIdentifier(Direction dir) {
        return Identifier.of(GiantPotato.MOD_ID, "mash_pipe_" + GetStringFromDirection.minecraftDir(dir));
    }

    public MashPipeBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public ActionResult interact(MashPipeBlockEntity blockEntity, Vec3d actualPos, PlayerEntity player, Hand hand) {
        blockEntity.toggleSideFromHitbox(blockEntity, actualPos, player, hand, side);
        return ActionResult.PASS;
    }
}
