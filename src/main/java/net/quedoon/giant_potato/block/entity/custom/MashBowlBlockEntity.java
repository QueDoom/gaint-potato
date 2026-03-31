package net.quedoon.giant_potato.block.entity.custom;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.quedoon.giant_potato.block.ModBlocks;
import net.quedoon.giant_potato.block.entity.ImplementedInventory;
import net.quedoon.giant_potato.block.entity.fluid.FluidUtils;
import net.quedoon.giant_potato.fluid.ModFluids;
import net.quedoon.giant_potato.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.random.RandomGenerator;

public class MashBowlBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private static final int MASH_PER_POTATO = 4;
    private static final int MASH_PER_POISONOUS_POTATO = 2;


    public MashBowlBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81); // 1 Bucket = 81000 Droplets = 1000mB || * 64 ==> 64,000mB = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public void jumpedOn(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient()) {
            if (this.inventory.getFirst().isIn(ModTags.Items.MASH_BOWL_POTATO) || this.inventory.getFirst().isIn(ModTags.Items.MASH_BOWL_POISONOUS_POTATO)) {
                ItemStack stack = this.inventory.getFirst();
                Fluid mashType = this.getFluidFromPotato(stack);
                int mashAmount = this.getFluidAmountFromPotato(stack);
                if (mashType != null && this.fluidStorage.amount + (stack.isIn(ModTags.Items.MASH_BUCKETS) ? MASH_PER_POTATO : MASH_PER_POISONOUS_POTATO) <= this.fluidStorage.getCapacity()) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        stack.decrement(1);
                        this.fluidStorage.insert(FluidVariant.of(mashType), mashAmount, transaction);
                        transaction.commit();
                    }

                }
                world.playSoundAtBlockCenter(pos, SoundEvents.ENTITY_SLIME_JUMP_SMALL, SoundCategory.BLOCKS, 1, 1, true);
            }
        } else {
            spawnSplashParticles(world,  pos, true);
        }
    }

    private int getFluidAmountFromPotato(ItemStack stack) {
        if (stack.isIn(ModTags.Items.MASH_BOWL_POTATO)) {
            return MASH_PER_POTATO;
        } else if (stack.isIn(ModTags.Items.MASH_BOWL_POISONOUS_POTATO)) {
            return MASH_PER_POISONOUS_POTATO;
        }
        return 0;
    }

    private void spawnSplashParticles(World world, BlockPos pos, boolean big) {
        double xPos = pos.getX() + 0.5f;
        double yPos = pos.getY() + 0.4f;
        double zPos = pos.getZ() + 0.5f;
        double offset;
        double xVel;
        double yVel;
        double zVel;
        for (int i = big ? 12 : 4; i > 0; i--) {
            offset = RandomGenerator.getDefault().nextDouble() * 0.6 - 0.3;
            xVel = RandomGenerator.getDefault().nextDouble() * 0.4 - 0.1;
            yVel = RandomGenerator.getDefault().nextDouble() * 0.4 - 0.1;
            zVel = RandomGenerator.getDefault().nextDouble() * 0.4 - 0.1;

            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, ModFluids.MASH_BLOCK.getDefaultState()), xPos + offset, yPos + offset, zPos + offset, xVel, yVel, zVel);
        }
        if (big) {
            for (int i = 4; i > 0; i--) {
                offset = RandomGenerator.getDefault().nextDouble() * 0.6 - 0.3;
                xVel = RandomGenerator.getDefault().nextDouble() * 0.4;
                yVel = RandomGenerator.getDefault().nextDouble() * 0.4;
                zVel = RandomGenerator.getDefault().nextDouble() * 0.4;

                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, ModFluids.MASH_BLOCK.getDefaultState()), xPos + offset, yPos + offset, zPos + offset, xVel, yVel, zVel);
            }
        }


    }


    @Nullable
    private Fluid getFluidFromPotato(ItemStack stack) {
        if (stack.isIn(ModTags.Items.MASH_BOWL_POTATO)) {
            return ModFluids.MASH;
        } else if (stack.isIn(ModTags.Items.MASH_BOWL_POISONOUS_POTATO)) {
            return ModFluids.POISONOUS_MASH;
        }
        return null;
    }


    private void transferFluidToTank(int amount, Fluid type) {
        if((fluidStorage.variant.isOf(type) || fluidStorage.isResourceBlank())) {
            try (Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(type), amount, transaction);
                transaction.commit();
            }
        }
    }
}
