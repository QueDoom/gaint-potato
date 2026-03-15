package net.quedoon.giant_potato.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ImplementedInventory;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.item.ModItems;
import net.quedoon.giant_potato.recipe.CrusherRecipe;
import net.quedoon.giant_potato.recipe.CrusherRecipeInput;
import net.quedoon.giant_potato.recipe.ModRecipes;
import net.quedoon.giant_potato.screen.custom.CrusherScreenHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrusherBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    protected boolean active = Boolean.FALSE;
    private int hasCrusherWheels = 0;

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;
    private int mash = 0;
    private final int MAX_MASH = 200;

    public int getMaxMash() {
        return MAX_MASH;
    }

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrusherBlockEntity.this.progress;
                    case 1 -> CrusherBlockEntity.this.maxProgress;
                    case 2 -> CrusherBlockEntity.this.mash;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrusherBlockEntity.this.progress = value;
                    case 1 -> CrusherBlockEntity.this.maxProgress = value;
                    case 2 -> CrusherBlockEntity.this.mash = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("giant_potato.crusher.progress", progress);
        nbt.putInt("giant_potato.crusher.max_progress", maxProgress);
        nbt.putInt("giant_potato.crusher.mash", mash);
        nbt.putBoolean("giant_potato.crusher.active", active);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("giant_potato.crusher.progress");
        maxProgress = nbt.getInt("giant_potato.crusher.max_progress");
        mash = nbt.getInt("giant_potato.crusher.mash");
        active = nbt.getBoolean("giant_potato.crusher.active");
        super.readNbt(nbt, registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui."+ GiantPotato.MOD_ID +".crusher");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrusherScreenHandler(syncId, playerInventory, pos);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
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


    private void craftItem() {
        Optional<RecipeEntry<CrusherRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().value().output().getCount()));
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }


    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<CrusherRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResult(null);
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeEntry<CrusherRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager().getFirstMatch(ModRecipes.CRUSHER_TYPE, new CrusherRecipeInput(inventory.get(INPUT_SLOT)), this.getWorld());
    }


    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

}
