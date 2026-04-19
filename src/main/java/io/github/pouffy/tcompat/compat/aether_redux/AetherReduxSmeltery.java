package io.github.pouffy.tcompat.compat.aether_redux;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class AetherReduxSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        
        //Generic Melting
        metal(cConsumer, TCFluids.moltenVeridium, compatModId()).metal(true).ore(TCByproduct.GRAVITITE).rawOre().optional();
        metal(cConsumer, TCFluids.moltenRefinedSentrite, compatModId()).metal(true).ore(TCByproduct.VERIDIUM).optional();
        //Rings
        simpleMelting(cConsumer, TCFluids.moltenSkyjade, FluidValues.INGOT, "skyjade", ItemNameIngredient.from(compatId("ring_of_wisdom")), gemFolder("melting"), "ring_of_wisdom");
        simpleMelting(cConsumer, TCFluids.moltenRefinedSentrite, FluidValues.INGOT * 7, "refined_sentrite", ItemNameIngredient.from(compatId("sentry_ring")), metalFolder("melting"), "aether_ring");
        simpleMelting(cConsumer, TCFluids.moltenRefinedSentrite, FluidValues.NUGGET * 8, "refined_sentrite", ItemNameIngredient.from(compatId("sentrite_lantern")), metalFolder("melting"), "lantern");
        simpleMelting(cConsumer, TCFluids.moltenRefinedSentrite, FluidValues.INGOT, "refined_sentrite", Ingredient.of(TCTags.Items.SENTRITE_STONE_BLOCKS), metalFolder("melting"), "stone_blocks");
        simpleMelting(cConsumer, TCFluids.moltenRefinedSentrite, FluidValues.INGOT / 2, "refined_sentrite", Ingredient.of(TCTags.Items.SENTRITE_STONE_SLABS), metalFolder("melting"), "stone_slabs");
        simpleMelting(cConsumer, TCFluids.moltenVeridium, (FluidValues.INGOT * 2) + (FluidValues.NUGGET * 6), "veridium", ItemNameIngredient.from(compatId("veridium_lantern")), metalFolder("melting"), "lantern");
        simpleMelting(cConsumer, TCFluids.moltenRefinedSentrite, FluidValues.INGOT * 4, "refined_sentrite", ItemNameIngredient.from(getResource("aether", "sentry_boots")), metalFolder("melting"), "boots");

        simpleMelting(cConsumer, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium", ItemNameIngredient.from(compatId("veridium_dart_shooter")), metalFolder("melting"), "dart_shooter");
        simpleMelting(cConsumer, TCFluids.moltenVeridium, FluidValues.METAL_BLOCK * 4, "veridium", ItemNameIngredient.from(compatId("feather_of_warding")), metalFolder("melting"), "feather_of_warding");
        simpleMelting(cConsumer, TCFluids.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(compatId("snailshell_shield")), metalFolder("melting"), "snailshell_shield");

        //Salvaging
        simpleSalvaging(cConsumer, this::compatId, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        simpleSalvaging(cConsumer, this::compatId, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        simpleSalvaging(cConsumer, this::compatId, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        // infused veridium is still veridium
        simpleSalvaging(cConsumer, this::compatId, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        simpleSalvaging(cConsumer, this::compatId, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        simpleSalvaging(cConsumer, this::compatId, TCFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder("melting"));

        simpleMelting(cConsumer, TCFluids.moltenDraculite, FluidValues.INGOT, "draculite", ItemNameIngredient.from(compatId("vampire_amulet")), metalFolder("melting"), "vampire_amulet");
    }

    @Override
    public String compatModId() {
        return "aether_redux";
    }
}
