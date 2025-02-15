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
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public TComBaseRecipeProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator);
        this.lookupProvider = lookupProvider;
        TCompat.sealTComClass(this, "TComBaseRecipeProvider", "TComBaseRecipeProvider is trivial to recreate and directly extending can lead to addon recipes polluting our namespace.");
    }

    @Override
    protected abstract void buildRecipes(Consumer<FinishedRecipe> consumer);

    @Override
    public abstract String getName();

    public HolderLookup.Provider getLookupProvider() {
        try {
            return lookupProvider.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getModId() {
        return TCompat.MOD_ID;
    }
}
