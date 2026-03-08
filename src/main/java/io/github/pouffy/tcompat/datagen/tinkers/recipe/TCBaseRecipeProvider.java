package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.mantle.recipe.data.IRecipeHelper;

import java.util.function.Consumer;

public abstract class TCBaseRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public TCBaseRecipeProvider(PackOutput generator) {
        super(generator);
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
