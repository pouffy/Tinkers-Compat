package com.pouffydev.tcompat.modifier.aether.recipe;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import slimeknights.mantle.recipe.data.AbstractRecipeBuilder;

import java.util.function.Consumer;

@RequiredArgsConstructor(staticName = "modifier")
public class AmbrofusionModifierRecipeBuilder extends AbstractRecipeBuilder<AmbrofusionModifierRecipeBuilder> {
    private final Ingredient ingredient;
    private final int restoreAmount;

    /** Creates a new builder for the given item */
    public static AmbrofusionModifierRecipeBuilder modifier(ItemLike item, int restoreAmount) {
        return modifier(Ingredient.of(item), restoreAmount);
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        ItemStack[] stacks = ingredient.getItems();
        if (stacks.length == 0) {
            throw new IllegalStateException("Empty ingredient not allowed");
        }
        save(consumer, BuiltInRegistries.ITEM.getKey(stacks[0].getItem()));
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        if (ingredient == Ingredient.EMPTY) {
            throw new IllegalStateException("Empty ingredient not allowed");
        }
        ResourceLocation advancementId = buildOptionalAdvancement(id, "modifiers");
        consumer.accept(new LoadableFinishedRecipe<>(new AmbrofusionModifierRecipe(id, ingredient, restoreAmount), AmbrofusionModifierRecipe.LOADER, advancementId));
    }
}
