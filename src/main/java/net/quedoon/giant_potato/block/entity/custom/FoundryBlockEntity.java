package net.quedoon.giant_potato.block.entity.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.quedoon.giant_potato.block.entity.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FoundryBlockEntity extends BlockEntity implements Inventory, GeoBlockEntity {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    protected static final RawAnimation IDLE_CLOSED = RawAnimation.begin().thenLoop("idle_closed");
    protected static final RawAnimation OPEN = RawAnimation.begin().thenPlay("open").thenLoop("idle_open");
    protected static final RawAnimation ACTIVE = RawAnimation.begin().thenLoop("active");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FoundryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FOUNDRY_BE, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if(!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        markDirty();
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        markDirty();
        ItemStack stack = inventory.get(slot);
        stack.decrement(amount);
        return inventory.set(slot, stack);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, this::idleClosedAnimController));
        controllerRegistrar.add(new AnimationController<>(this, this::openAnimController));
        controllerRegistrar.add(new AnimationController<>(this, this::activeAnimController));
    }


    protected <E extends FoundryBlockEntity>PlayState idleClosedAnimController(final AnimationState<E> state) {
        return state.setAndContinue(IDLE_CLOSED);
    }

    protected <E extends FoundryBlockEntity>PlayState openAnimController(final AnimationState<E> state) {
        return state.setAndContinue(OPEN);
    }

    protected <E extends FoundryBlockEntity>PlayState activeAnimController(final AnimationState<E> state) {
        return state.setAndContinue(ACTIVE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


}
