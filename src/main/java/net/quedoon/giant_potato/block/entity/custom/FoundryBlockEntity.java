package net.quedoon.giant_potato.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
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
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.quedoon.giant_potato.GiantPotato;
import net.quedoon.giant_potato.block.entity.ImplementedInventory;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import net.quedoon.giant_potato.recipe.FoundryRecipe;
import net.quedoon.giant_potato.recipe.FoundryRecipeInput;
import net.quedoon.giant_potato.recipe.ModRecipes;
import net.quedoon.giant_potato.screen.custom.FoundryScreenHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FoundryBlockEntity extends BlockEntity implements GeoBlockEntity, ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    protected boolean active = Boolean.FALSE;
    private boolean open = Boolean.FALSE;
    private int openTimer = -1;

    private static final int INPUT_SLOT_0 = 0;
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2 = 2;
    private static final int INPUT_SLOTS = 3;
    private static final int OUTPUT_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;
    private int mash = 0;
    private final int MAX_MASH = 200;

    public int getMaxMash() {
        return MAX_MASH;
    }

    protected static final RawAnimation IDLE_CLOSED = RawAnimation.begin().thenLoop("idle_closed");
    protected static final RawAnimation IDLE_OPEN = RawAnimation.begin().thenLoop("idle_open");
    protected static final RawAnimation OPEN = RawAnimation.begin().thenPlay("open").thenLoop("idle_open");
    protected static final RawAnimation ACTIVE = RawAnimation.begin().thenLoop("active");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FoundryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FOUNDRY_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FoundryBlockEntity.this.progress;
                    case 1 -> FoundryBlockEntity.this.maxProgress;
                    case 2 -> FoundryBlockEntity.this.mash;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FoundryBlockEntity.this.progress = value;
                    case 1 -> FoundryBlockEntity.this.maxProgress = value;
                    case 2 -> FoundryBlockEntity.this.mash = value;
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
        nbt.putInt("giant_potato.foundry.progress", progress);
        nbt.putInt("giant_potato.foundry.maxProgress", maxProgress);
        nbt.putInt("giant_potato.foundry.mash", mash);
        nbt.putBoolean("giant_potato.foundry.open", open);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("giant_potato.foundry.progress");
        maxProgress = nbt.getInt("giant_potato.foundry.maxProgress");
        mash = nbt.getInt("giant_potato.foundry.mash");
        open = nbt.getBoolean("giant_potato.foundry.open");
        if (this.open) {
            this.triggerAnim("idle_open", "idle_open");
        }
        super.readNbt(nbt, registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui." + GiantPotato.MOD_ID + ".foundry");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FoundryScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    public void setOpen() {
        this.open = true;
        markDirty();
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setOpenTimer() {
        this.openTimer = 30;
    }

    /*
        GECKOLIB STUFF

         */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add((new AnimationController<>(this, "idle_closed", 0, state -> state.setAndContinue(IDLE_CLOSED))));
        controllerRegistrar.add((new AnimationController<>(this, "idle_open", 0, state -> PlayState.STOP).triggerableAnim("idle_open", IDLE_OPEN)));
        controllerRegistrar.add((new AnimationController<>(this, "open", 0, state -> PlayState.STOP)).triggerableAnim("open", OPEN).triggerableAnim("active", ACTIVE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // // // // // // // // //

    public void tick(World world, BlockPos pos, BlockState state) {
        if (this.openTimer >= 0) {
            this.open = this.openTimer == 0;
            this.openTimer--;
        }

        if (!this.open) return;
        if (hasRecipe() && canInsertIntoOutputSlot()) {
            increaseCraftingProgress();
            markDirty(world, pos, state);

            this.active = true;

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
            this.active = false;
        }
//        if (this.active) {
//            this.triggerAnim("active", "active");
//        } else {
//            this.triggerAnim("idle_open", "idle_open");
//        }


    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeEntry<FoundryRecipe>> recipe = getCurrentRecipe();

        for (int i = 0; i < INPUT_SLOTS; i++) {
            this.removeStack(i, 1);
        }
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().value().output().getCount()));
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
        Optional<RecipeEntry<FoundryRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeEntry<FoundryRecipe>> getCurrentRecipe() {
        List<ItemStack> input = new ArrayList<>();
        for (int i = 0; i < INPUT_SLOTS; i++) {
            input.add(inventory.get(i));
        }

        return this.getWorld().getRecipeManager()
                .getFirstMatch(ModRecipes.FOUNDRY_TYPE, new FoundryRecipeInput(input), this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
