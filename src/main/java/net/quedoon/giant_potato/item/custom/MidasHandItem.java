package net.quedoon.giant_potato.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.quedoon.giant_potato.util.ModTags;

import java.util.List;
import java.util.function.Consumer;

public class MidasHandItem extends Item {
    public MidasHandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        if (world.isClient()) return ActionResult.FAIL;
        if (player == null) return ActionResult.FAIL;
        if (state.isIn(ModTags.Blocks.MIDAS_HAND_WORKS_ON)) {
            world.setBlockState(pos, Blocks.GOLD_BLOCK.getDefaultState());
            stack.damage(1, ((ServerWorld) world), ((ServerPlayerEntity) player), item -> {
                player.sendEquipmentBreakStatus(stack.getItem(), EquipmentSlot.MAINHAND);
            });
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
