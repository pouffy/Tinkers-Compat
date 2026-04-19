package io.github.pouffy.tcompat.compat.deep_aether;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.function.Consumer;

public class DeepAetherSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        
        gem(cConsumer, TCFluids.moltenSkyjade, compatModId())
                .baseUnit(FluidValues.INGOT).damageUnit(FluidValues.NUGGET).oreRate(IMeltingContainer.OreRateType.METAL)
                .sparseOre(0.5f).singularOre(1).denseOre(3).ore(TCByproduct.QUARTZ)
                .melting(9, "block", "storage_blocks", (float)Math.sqrt(9), false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .meltingCasting(1 / 9f, TinkerSmeltery.nuggetCast, 1 / 3f, true);

        metal(cConsumer, TCFluids.moltenStratus, compatModId()).metal(true).optional();
        metal(cConsumer, TCFluids.moltenStormforgedSteel, compatModId()).metal(true).optional();

        simpleMelting(cConsumer, TCFluids.moltenSkyjade, FluidValues.NUGGET * 8, "skyjade", ItemNameIngredient.from(compatId("skyjade_lantern")), gemFolder("melting"), "skyjade_lantern");
        simpleMelting(cConsumer, TCFluids.moltenSkyjade, FluidValues.NUGGET * 2, "skyjade", ItemNameIngredient.from(compatId("skyjade_chain")), gemFolder("melting"), "skyjade_chain");
        simpleMelting(cConsumer, TCFluids.moltenSkyjade, FluidValues.INGOT * 3, "skyjade", ItemNameIngredient.from(compatId("combiner")), gemFolder("melting"), "combiner");

        simpleMelting(cConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 4, "obsidian", ItemNameIngredient.from(compatId("afterburner")), miscFolder("melting"), "afterburner");
        simpleMelting(cConsumer, TCFluids.moltenStratus, FluidValues.INGOT * 2, "stratus", ItemNameIngredient.from(compatId("aerwhale_saddle")), metalFolder("melting"), "aerwhale_saddle");
        simpleMelting(cConsumer, TCFluids.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(compatId("gravitite_ring")), metalFolder("melting"), "aether_ring");
        //Gloves
        glovesMelting(cConsumer, TCFluids.moltenSkyjade, FluidValues.INGOT * 2, "skyjade", ItemNameIngredient.from(compatId("skyjade_gloves")), gemFolder("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(cConsumer, TCFluids.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(compatId("stratus_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, TCFluids.moltenGravitite.result(FluidValues.INGOT * 2));
        //Rings
        ringMelting(cConsumer, TCFluids.moltenSkyjade, FluidValues.INGOT * 4, "skyjade", ItemNameIngredient.from(compatId("skyjade_ring")), gemFolder("melting"), true);
        ringMelting(cConsumer, TCFluids.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(compatId("stratus_ring")), metalFolder("melting"), true, TCFluids.moltenGravitite.result(FluidValues.INGOT * 4));
        //Salvaging
        salvageAll(cConsumer, this::compatId, TCFluids.moltenSkyjade, FluidValues.INGOT, "skyjade", new int[]{FluidValues.GEM_SHARD}, gemFolder("melting"));
        salvageAll(cConsumer, this::compatId, TCFluids.moltenStratus, TCFluids.moltenGravitite, FluidValues.INGOT, "stratus", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));

        simpleMelting(cConsumer, TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 2, "stormforged_steel", ItemNameIngredient.from(compatId("storm_sword")), metalFolder("melting"), "sword");
        simpleMelting(cConsumer, TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 3, "stormforged_steel", ItemNameIngredient.from(compatId("storm_bow")), metalFolder("melting"), "bow");
        simpleMelting(TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 5, ItemNameIngredient.from(compatId("stormforged_helmet")))
                .setDamagable(FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/stormforged_steel/helmet"));
        simpleMelting(TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 8, ItemNameIngredient.from(compatId("stormforged_chestplate")))
                .setDamagable(FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/stormforged_steel/chestplate"));
        simpleMelting(TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 7, ItemNameIngredient.from(compatId("stormforged_leggings")))
                .setDamagable(FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/stormforged_steel/leggings"));
        simpleMelting(TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 4, ItemNameIngredient.from(compatId("stormforged_boots")))
                .setDamagable(FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/stormforged_steel/boots"));

        glovesMelting(cConsumer, TCFluids.moltenStormforgedSteel, FluidValues.INGOT * 2, "stormforged_steel", ItemNameIngredient.from(compatId("stormforged_gloves")), gemFolder("melting"), true, new int[]{FluidValues.NUGGET});
    }

    @Override
    public String compatModId() {
        return "deep_aether";
    }
}
