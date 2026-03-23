package net.quedoon.giant_potato.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.quedoon.giant_potato.block.entity.ImplementedInventory;

public class MashBowlBlockEntity extends BlockEntity implements ImplementedInventory {
    public MashBowlBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }

    public void jumpedOn(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {

    }
}
