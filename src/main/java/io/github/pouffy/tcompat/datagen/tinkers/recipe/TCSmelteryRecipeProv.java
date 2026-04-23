package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.compat.ad_astra.AdAstraSmeltery;
import io.github.pouffy.tcompat.compat.aether.AetherSmeltery;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxSmeltery;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRSmeltery;
import io.github.pouffy.tcompat.compat.betterend.BetterEndSmeltery;
import io.github.pouffy.tcompat.compat.betternether.BetterNetherSmeltery;
import io.github.pouffy.tcompat.compat.bwg.BWGSmeltery;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherSmeltery;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFSmeltery;
import io.github.pouffy.tcompat.compat.species.SpeciesSmeltery;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;

import java.util.function.Consumer;

public class TCSmelteryRecipeProv extends TCBaseRecipeProvider implements ITCSmelteryRecipeHelper, ICommonRecipeHelper {
    public TCSmelteryRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public @NotNull String getName() {
        return "Tinkers' Compatibility Smeltery Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";
        new AetherSmeltery().addRecipes(consumer, folder);
        new DeepAetherSmeltery().addRecipes(consumer, folder);
        new AetherReduxSmeltery().addRecipes(consumer, folder);
        new AetherTRSmeltery().addRecipes(consumer, folder);
        new SpeciesSmeltery().addRecipes(consumer, folder);
        new AdAstraSmeltery().addRecipes(consumer, folder);
        new BetterEndSmeltery().addRecipes(consumer, folder);
        new BetterNetherSmeltery().addRecipes(consumer, folder);
        new IFSmeltery().addRecipes(consumer, folder);
        new BWGSmeltery().addRecipes(consumer, folder);
    }
}
