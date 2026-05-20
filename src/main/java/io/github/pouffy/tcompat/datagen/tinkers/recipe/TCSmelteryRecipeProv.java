package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.compat.ad_astra.AdAstraSmeltery;
import io.github.pouffy.tcompat.compat.aether.AetherSmeltery;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxSmeltery;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRSmeltery;
import io.github.pouffy.tcompat.compat.betterend.BetterEndSmeltery;
import io.github.pouffy.tcompat.compat.betternether.BetterNetherSmeltery;
import io.github.pouffy.tcompat.compat.bwg.BWGSmeltery;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmSmeltery;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherSmeltery;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFSmeltery;
import io.github.pouffy.tcompat.compat.malum.MalumSmeltery;
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
        new AetherSmeltery().toData(consumer);
        new DeepAetherSmeltery().toData(consumer);
        new AetherReduxSmeltery().toData(consumer);
        new AetherTRSmeltery().toData(consumer);
        new SpeciesSmeltery().toData(consumer);
        new AdAstraSmeltery().toData(consumer);
        new BetterEndSmeltery().toData(consumer);
        new BetterNetherSmeltery().toData(consumer);
        new IFSmeltery().toData(consumer);
        new BWGSmeltery().toData(consumer);
        new CataclysmSmeltery().toData(consumer);
        new MalumSmeltery().toData(consumer);
    }
}
