package net.quedoon.giant_potato.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public record FoundryRecipe(ItemStack output, DefaultedList<Ingredient> ingredients, int maxProgress) implements Recipe<FoundryRecipeInput> {
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(FoundryRecipeInput input, World world) {
        if (world.isClient) {
            return false;
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(input.getStackInSlot(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(FoundryRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FOUNDRY_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FOUNDRY_TYPE;
    }

    public static class Serializer implements RecipeSerializer<FoundryRecipe> {
        private static final MapCodec<FoundryRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Ingredient.DISALLOW_EMPTY_CODEC
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                ingredients -> {
                                                    Ingredient[] ingredients2 = (Ingredient[])ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                                    if (ingredients2.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for foundry recipe");
                                                    } else {
                                                        return ingredients2.length > 3
                                                                ? DataResult.error(() -> "Too many ingredients for foundry recipe")
                                                                : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(recipe -> recipe.ingredients),
                                Codec.INT.optionalFieldOf("time", 72).forGetter(recipe -> recipe.maxProgress)
                        )
                        .apply(instance, FoundryRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, FoundryRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                FoundryRecipe.Serializer::write, FoundryRecipe.Serializer::read
        );

        @Override
        public MapCodec<FoundryRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FoundryRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static FoundryRecipe read(RegistryByteBuf buf) {
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            int l = buf.readVarInt();
            return new FoundryRecipe(itemStack, defaultedList, l);
        }

        private static void write(RegistryByteBuf buf, FoundryRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.output);

            buf.writeVarInt(recipe.maxProgress);
        }
    }
}
