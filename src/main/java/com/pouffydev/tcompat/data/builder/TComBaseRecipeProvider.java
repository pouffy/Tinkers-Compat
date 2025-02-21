package com.pouffydev.tcompat.data.builder;

import com.pouffydev.tcompat.TCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.tconstruct.TConstruct;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public abstract class TComBaseRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public TComBaseRecipeProvider(PackOutput generator) {
        super(generator);
        TCompat.sealTComClass(this, "TComBaseRecipeProvider", "TComBaseRecipeProvider is trivial to recreate and directly extending can lead to addon recipes polluting our namespace.");
    }

    @Override
    protected abstract void buildRecipes(Consumer<FinishedRecipe> consumer);

    @Override
    public abstract String getName();

    @Override
    public String getModId() {
        return TCompat.MOD_ID;
    }
}
