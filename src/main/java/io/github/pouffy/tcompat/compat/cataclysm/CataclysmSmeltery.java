package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class CataclysmSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        metal(cConsumer, TCFluids.moltenWitherite, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenAncientMetal, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenBlackSteel, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenCursium, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenIgnitium, compatModId()).metal(9, true).optional();
        gem(cConsumer, TCFluids.moltenLacrima, compatModId()).gem(9, true).optional();

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(compatId("belt_of_beginner")), metalFolder("melting"), "belt_of_beginner");
        simpleMelting(cConsumer, TinkerFluids.earthSlime, FluidValues.SLIMEBALL, "earth_slime", ItemNameIngredient.from(compatId("sticky_gloves")), metalFolder("melting"), "sticky_gloves");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM_SHARD, "amethyst", ItemNameIngredient.from(compatId("amethyst_crab_shell")), metalFolder("melting"), "amethyst_crab_shell");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM_SHARD * 2, "amethyst", listedInput("amethyst_crab_meat", "blessed_amethyst_crab_meat"), metalFolder("melting"), "amethyst_crab_meat");
    }

    @Override
    public String compatModId() {
        return "cataclysm";
    }
}
