package io.github.pouffy.tcompat.compat.ancient_aether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;

import java.util.function.Consumer;

import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public class AncientAetherSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT * 2, "valkyrum", listedInput("valkyrum_sword", "valkyrum_lance", "valkyrum_hoe"), metalFolder("melting"), "weapon", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT * 3, "valkyrum", listedInput("valkyrum_pickaxe", "valkyrum_axe"), metalFolder("melting"), "axe", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT, "valkyrum", listedInput("valkyrum_shovel"), metalFolder("melting"), "shovel", new int[]{FluidValues.NUGGET});
        salvageArmor(cConsumer, this::compatId, TCFluids.moltenValkyrum, FluidValues.INGOT, "valkyrum", new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        glovesMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT * 2, "valkyrum", listedInput("valkyrum_gloves"), metalFolder("melting"), true, new int[]{FluidValues.NUGGET});

        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT * 4, "valkyrum", ItemNameIngredient.from(compatId("valkyrum_ring")), metalFolder("melting"), "valkyrum_ring");
        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT, "valkyrum", ItemNameIngredient.from(compatId("valkyrum_pendant")), metalFolder("melting"), "valkyrum_pendant");
        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT * 5, "valkyrum", Ingredient.of(TCTags.Items.VALKYRUM_SMITHING_TEMPLATES), metalFolder("melting"), "smithing_template");

        ItemCastingRecipeBuilder.tableDuplication()
                .setCast(Ingredient.of(TCTags.Items.VALKYRUM_SMITHING_TEMPLATES), false)
                .setCoolingTime(IMeltingRecipe.calcTime(getTemperature(TCFluids.moltenValkyrum), IMeltingRecipe.calcTimeFactor(FluidValues.INGOT * 5)))
                .setFluid(TCFluids.moltenValkyrum.ingredient(FluidValues.INGOT * 5))
                .save(consumer, location(metalFolder("casting") + "/valkyrum/smithing_template"));
    }

    @Override
    public String compatModId() {
        return "ancient_aether";
    }
}
