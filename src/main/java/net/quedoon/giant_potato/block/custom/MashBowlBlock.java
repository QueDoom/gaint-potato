package net.quedoon.giant_potato.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.quedoon.giant_potato.block.entity.ImplementedInventory;
import net.quedoon.giant_potato.block.entity.custom.MashBowlBlockEntity;
import net.quedoon.giant_potato.block.util.ModBlockHitboxes;
import net.quedoon.giant_potato.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class MashBowlBlock extends Block {
    public static final IntProperty STAGE = IntProperty.of("stage", 0, 3);
    private static final VoxelShape SHAPE = ModBlockHitboxes.getMashBowlHitbox();


    public MashBowlBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof MashBowlBlockEntity)) return;
        ((MashBowlBlockEntity) blockEntity).jumpedOn(world, state, pos, entity, fallDistance);
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) return;
        System.out.println("I got stepped on, ouch!");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof MashBowlBlockEntity mashBowlBlockEntity)) return;
        if (!(entity instanceof ItemEntity item)) return;
        ItemStack itemEntityStack = item.getStack();
        DefaultedList<ItemStack> inventory = mashBowlBlockEntity.getItems();
        ItemStack itemStack = inventory.getFirst();
        if (canInsert(inventory, itemEntityStack)) {
            if (itemStack.isEmpty()) {
                inventory.set(0, itemEntityStack);
                item.setStack(ItemStack.EMPTY);
            } else if (canMergeItems(itemEntityStack, itemStack)) {
                int i = itemEntityStack.getMaxCount() - itemEntityStack.getCount();
                int j = Math.min(itemEntityStack.getCount(), i);
                itemEntityStack.decrement(j);
                itemStack.increment(j);
            }
            mashBowlBlockEntity.markDirty();
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    private static boolean canInsert(DefaultedList<ItemStack> inventory, ItemStack stack) {
        boolean var10000;
        if (inventory instanceof ImplementedInventory implementedInventory) {
            if (!implementedInventory.canInsert(0, stack, null)) {
                var10000 = false;
                return var10000;
            }
        }
        var10000 = true;
        return var10000;

    }
    private static boolean canMergeItems(ItemStack first, ItemStack second) {
        return first.getCount() <= first.getMaxCount() && ItemStack.areItemsAndComponentsEqual(first, second);
    }
}
