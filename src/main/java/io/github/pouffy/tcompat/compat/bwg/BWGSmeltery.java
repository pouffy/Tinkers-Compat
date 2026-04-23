package io.github.pouffy.tcompat.compat.bwg;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;

import java.util.function.Consumer;

public class BWGSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

        simpleMelting(cConsumer, TCFluids.aloeVeraJuice, FluidValues.BOTTLE * 4, "food", ItemNameIngredient.from(compatId("aloe_vera")), miscFolder("melting"), "aloe_vera_juice");
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("aloe_vera_juice")))
                .setFluidAndTime(TCFluids.aloeVeraJuice, FluidValues.BOTTLE)
                .setCast(Ingredient.of(Items.GLASS_BOTTLE), true)
                .setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/food/aloe_vera_juice"));
        simpleMelting(cConsumer, TCFluids.whitePuffballStew, 125, "food", ItemNameIngredient.from(compatId("white_puffball_cap")), miscFolder("melting"), "white_puffball_stew");
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("white_puffball_stew")))
                .setFluidAndTime(TCFluids.whitePuffballStew, FluidValues.BOWL)
                .setCast(Ingredient.of(Items.BOWL), true)
                .setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/food/white_puffball_stew"));
        simpleMelting(cConsumer, TCFluids.alliumOddionSoup, 125, "food", ItemNameIngredient.from(compatId("oddion_bulb")), miscFolder("melting"), "allium_oddion_soup");
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("allium_oddion_soup")))
                .setFluidAndTime(TCFluids.alliumOddionSoup, FluidValues.BOWL)
                .setCast(Ingredient.of(Items.BOWL), true)
                .setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/food/allium_oddion_soup"));
    }

    @Override
    public String compatModId() {
        return "biomeswevegone";
    }
}
