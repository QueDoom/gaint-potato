package net.quedoon.giant_potato.block.entity.util.block_entity.mashines;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractMashMachineBlockEntity<T extends Recipe<?>> extends AbstractMachineBlockEntity<T> {

    private int mash = 0;
    private final int MAX_MASH;

    protected final PropertyDelegate propertyDelegate;

    public AbstractMashMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int maxMash) {
        super(type, pos, state, inventorySize);
        this.MAX_MASH = maxMash;
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return (int) switch (index) {
                    case 0 -> AbstractMashMachineBlockEntity.this.progress;
                    case 1 -> AbstractMashMachineBlockEntity.this.maxProgress;
                    case 2 -> AbstractMashMachineBlockEntity.this.fluidStorage.getAmount();
                    case 3 -> AbstractMashMachineBlockEntity.this.fluidStorage.getCapacity();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AbstractMashMachineBlockEntity.this.progress = value;
                    case 1 -> AbstractMashMachineBlockEntity.this.maxProgress = value;
                    default -> {
                        return;
                    }
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81) * MAX_MASH; // 1 Bucket = 81000 Droplets = 1000mB || * 64 ==> 64,000mB = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };
    public final SingleVariantStorage<FluidVariant> fluidStorageHalf = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81) * MAX_MASH; // 1 Bucket = 81000 Droplets = 1000mB || * 64 ==> 64,000mB = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    public FluidVariant getFluid() {
        return fluidStorage.variant;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("giant_potato.crusher.progress", progress);
        nbt.putInt("giant_potato.crusher.max_progress", maxProgress);
        nbt.putBoolean("giant_potato.crusher.active", active);
        SingleVariantStorage.writeNbt(this.fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("giant_potato.crusher.progress");
        maxProgress = nbt.getInt("giant_potato.crusher.max_progress");
        active = nbt.getBoolean("giant_potato.crusher.active");
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
    }
}
