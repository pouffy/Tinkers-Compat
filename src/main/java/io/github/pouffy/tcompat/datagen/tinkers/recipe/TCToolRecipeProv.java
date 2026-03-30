package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;

import java.util.function.Consumer;

public class TCToolRecipeProv extends TCBaseRecipeProvider implements IMaterialRecipeHelper, IToolRecipeHelper {
    public TCToolRecipeProv(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/building/";

        toolBuilding(consumer, GlobalInit.glaive, folder);
    }

    @Override
    public String getName() {
        return "Tinkers' Compatability Tool Recipes";
    }
}
