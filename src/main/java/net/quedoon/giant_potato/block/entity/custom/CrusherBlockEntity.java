package net.quedoon.giant_potato.block.entity.custom;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ImplementedInventory;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.block.entity.util.block_entity.mashines.AbstractMashMachineBlockEntity;
import net.quedoon.giant_potato.recipe.CrusherRecipe;
import net.quedoon.giant_potato.recipe.CrusherRecipeInput;
import net.quedoon.giant_potato.recipe.ModRecipes;
import net.quedoon.giant_potato.screen.custom.CrusherScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrusherBlockEntity extends AbstractMashMachineBlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private int hasCrusherWheels = 0;

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHER_BE, pos, state, 2, 128);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui."+ GiantPotato.MOD_ID +".crusher");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrusherScreenHandler(syncId, playerInventory, pos);
    }

    // // // // // // // // //

    public void tick(World world, BlockPos pos, BlockState state) {
        this.hasCrusherWheels = getCrusherWheels(world, pos, state);
        if (hasCrusherWheels < 2) return;
        if (hasRecipe() && canInsertIntoOutputSlot()) {
            setCrusherWheelState(world, pos, state, true);
            increaseCraftingProgress();
            this.active = true;
            markDirty(world, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
            this.active = false;
            setCrusherWheelState(world, pos, state, false);
            markDirty(world, pos, state);
        }
    }

    @Override
    protected Optional<RecipeEntry<CrusherRecipe>> getCurrentRecipe() {
        return Optional.empty();
    }

    @Override
    protected ItemStack getRecipeOutput(RecipeEntry<CrusherRecipe> recipe) {
        return getCurrentRecipe().get().value().output();
    }


    private void setCrusherWheelState(World world, BlockPos pos, BlockState state, boolean value) {
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        BlockEntity north = world.getBlockEntity(pos.north(1));
        BlockEntity south = world.getBlockEntity(pos.south(1));
        BlockEntity east = world.getBlockEntity(pos.east(1));
        BlockEntity west = world.getBlockEntity(pos.west(1));

        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            if (east instanceof CrusherWheelBlockEntity eastWheel && west instanceof CrusherWheelBlockEntity westWheel) {
                eastWheel.setActive(value);
                westWheel.setActive(value);
            }
        } else {
            if (north instanceof CrusherWheelBlockEntity northWheel && south instanceof CrusherWheelBlockEntity southWheel) {
                northWheel.setActive(value);
                southWheel.setActive(value);
            }
        }
    }

    private int getCrusherWheels(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(Properties.HORIZONTAL_FACING);
        int wheels = 0;
        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            if (world.getBlockState(pos.east(1)).isOf(ModBlocks.CRUSHER_WHEEL)
                    && world.getBlockState(pos.east(1)).get(Properties.HORIZONTAL_FACING) == Direction.WEST) wheels += 1;
            if (world.getBlockState(pos.west(1)).isOf(ModBlocks.CRUSHER_WHEEL)
                    && world.getBlockState(pos.west(1)).get(Properties.HORIZONTAL_FACING) == Direction.EAST) wheels += 1;
        } else {
            if (world.getBlockState(pos.north(1)).isOf(ModBlocks.CRUSHER_WHEEL)
                    && world.getBlockState(pos.north(1)).get(Properties.HORIZONTAL_FACING) == Direction.SOUTH) wheels += 1;
            if (world.getBlockState(pos.south(1)).isOf(ModBlocks.CRUSHER_WHEEL)
                    && world.getBlockState(pos.south(1)).get(Properties.HORIZONTAL_FACING) == Direction.NORTH) wheels += 1;
        }
        return wheels;
    }

}
