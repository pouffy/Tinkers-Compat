package io.github.pouffy.tcompat.compat.ad_astra;

import earth.terrarium.adastra.common.recipes.machines.CryoFreezingRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AdAstraHandler {

    public static boolean isCryoFreezerFuel(Level level, ItemStack stack) {
        if (!CompatHelper.isLoaded("ad_astra")) return false;
        return LoadedOnly.isCryoFreezerFuel(level, stack);
    }

    public static int getCryoTime(Level level, ItemStack stack) {
        if (!CompatHelper.isLoaded("ad_astra")) return 0;
        return LoadedOnly.getCryoTime(level, stack);
    }

    public static class LoadedOnly {

        public static boolean isCryoFreezerFuel(Level level, ItemStack stack) {
            var allRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get()).stream().filter((recipe) -> recipe.input().test(stack));
            return allRecipes.findAny().isPresent();
        }

        public static int getCryoTime(Level level, ItemStack stack) {
            var allRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get()).stream().filter((recipe) -> recipe.input().test(stack));
            return allRecipes.findFirst().map(CryoFreezingRecipe::cookingTime).orElse(0);
        }
    }
}
