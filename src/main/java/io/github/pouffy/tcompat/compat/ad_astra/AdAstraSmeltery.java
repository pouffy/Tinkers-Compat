package io.github.pouffy.tcompat.compat.ad_astra;

import io.github.pouffy.tcompat.common.material.TCMeltingInfo;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class AdAstraSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

        int plating = 7;
        int rod = 45;

        Map<String, FluidObject<?>> decorativeMetals = Map.of(
                "iron", TinkerFluids.moltenIron,
                "steel", TinkerFluids.moltenSteel,
                "desh", AdAstraInit.moltenDesh,
                "ostrum", AdAstraInit.moltenOstrum,
                "calorite", AdAstraInit.moltenCalorite
        );

        decorativeMetals.forEach((name, fluid) -> {
            simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId(name + "_factory_block")), metalFolder("melting"), "factory_block");
            simpleMelting(fluid, 4, ItemNameIngredient.from(compatId("encased_" + name + "_block")))
                    .addByproduct(TinkerFluids.moltenSteel.result(8))
                    .save(cConsumer, location(metalFolder("melting") + "/" + name + "/encased_block"));
            if (!List.of("iron", "steel").contains(name)) {
                simpleMelting(fluid, 11, ItemNameIngredient.from(compatId(name + "_plateblock")))
                        .addByproduct(TinkerFluids.moltenSteel.result(1))
                        .save(cConsumer, location(metalFolder("melting") + "/" + name + "/plateblock"));
            } else simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId(name + "_plateblock")), metalFolder("melting"), "plateblock");
            simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId(name + "_panel")), metalFolder("melting"), "panel");
            simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId(name + "_plating")), metalFolder("melting"), "plating");
            simpleMelting(cConsumer, fluid, 18, name, ItemNameIngredient.from(compatId(name + "_plating_stairs")), metalFolder("melting"), "plating_stairs");
            simpleMelting(cConsumer, fluid, 6, name, ItemNameIngredient.from(compatId(name + "_plating_slab")), metalFolder("melting"), "plating_slab");
            simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId(name + "_pillar")), metalFolder("melting"), "pillar");
            simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId("glowing_" + name + "_pillar")), metalFolder("melting"), "glowing_pillar");
            simpleMelting(cConsumer, fluid, 12, name, ItemNameIngredient.from(compatId(name + "_plating_button")), metalFolder("melting"), "plating_button");
            simpleMelting(cConsumer, fluid, 24, name, ItemNameIngredient.from(compatId(name + "_plating_pressure_plate")), metalFolder("melting"), "plating_pressure_plate");
            if (!Objects.equals(name, "iron")) {
                simpleMelting(fluid, FluidValues.INGOT * 15, ItemNameIngredient.from(compatId(name + "_sliding_door")))
                        .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 2))
                        .save(cConsumer, location(metalFolder("melting") + "/" + name + "/sliding_door"));
            } else {
                simpleMelting(fluid, 12 * 6, ItemNameIngredient.from(compatId(name + "_sliding_door")))
                        .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 2))
                        .addByproduct(TinkerFluids.moltenSteel.result(FluidValues.METAL_BLOCK))
                        .save(cConsumer, location(metalFolder("melting") + "/" + name + "/sliding_door"));
            }
        });
        simpleMelting(cConsumer, TinkerFluids.moltenSteel, FluidValues.INGOT * 25, "steel", ItemNameIngredient.from(compatId("airlock")), metalFolder("melting"), "airlock");
        simpleMelting(cConsumer, TinkerFluids.moltenSteel, FluidValues.INGOT * 2, "steel", ItemNameIngredient.from(compatId("steel_door")), metalFolder("melting"), "door");
        simpleMelting(TinkerFluids.moltenSteel, FluidValues.INGOT * 11, ItemNameIngredient.from(compatId("reinforced_door")))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK * 2))
                .addByproduct(TinkerFluids.moltenObsidian.result(FluidValues.GLASS_BLOCK * 4))
                .save(cConsumer, location(metalFolder("melting") + "/steel/reinforced_door"));
        simpleMelting(cConsumer, TinkerFluids.moltenSteel, FluidValues.INGOT * 3, "steel", ItemNameIngredient.from(compatId("steel_trapdoor")), metalFolder("melting"), "trapdoor");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, 12, "iron", ItemNameIngredient.from(compatId("marked_iron_pillar")), metalFolder("melting"), "marked_pillar");
        simpleMelting(cConsumer, TinkerFluids.moltenSteel, 112, "steel", ItemNameIngredient.from(compatId("vent")), metalFolder("melting"), "vent");

        simpleMelting(AdAstraInit.moltenDesh, 32, ItemNameIngredient.from(compatId("desh_fluid_pipe")))
                .addByproduct(TinkerFluids.moltenGlass.result(185))
                .save(cConsumer, location(metalFolder("melting") + "/desh/fluid_pipe"));
        simpleMelting(AdAstraInit.moltenOstrum, 32, ItemNameIngredient.from(compatId("ostrum_fluid_pipe")))
                .addByproduct(TinkerFluids.moltenGlass.result(185))
                .save(cConsumer, location(metalFolder("melting") + "/ostrum/fluid_pipe"));
        simpleMelting(TinkerFluids.moltenSteel, 32, ItemNameIngredient.from(compatId("steel_cable")))
                .addByproduct(TinkerFluids.moltenCopper.result(16))
                .save(cConsumer, location(metalFolder("melting") + "/steel/cable"));
        simpleMelting(AdAstraInit.moltenDesh, 32, ItemNameIngredient.from(compatId("desh_cable")))
                .addByproduct(TinkerFluids.moltenCopper.result(16))
                .save(cConsumer, location(metalFolder("melting") + "/desh/cable"));

        simpleMelting(TinkerFluids.moltenSteel, 196, ItemNameIngredient.from(compatId("cable_duct")))
                .addByproduct(TinkerFluids.moltenCopper.result(188))
                .save(cConsumer, location(metalFolder("melting") + "/steel/cable_duct"));
        simpleMelting(AdAstraInit.moltenDesh, 196, ItemNameIngredient.from(compatId("fluid_pipe_duct")))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 2))
                .addByproduct(TinkerFluids.moltenGlass.result(92))
                .save(cConsumer, location(metalFolder("melting") + "/desh/fluid_pipe_duct"));

        metal(cConsumer, AdAstraInit.moltenDesh, compatModId()).metal(true).optional();
        metal(cConsumer, AdAstraInit.moltenOstrum, compatModId()).metal(true).optional();
        metal(cConsumer, AdAstraInit.moltenCalorite, compatModId()).metal(true).optional();

        TCMeltingInfo.AdAstra.adAstraGroup.saveAll(consumer);
        //Deco
        simpleMelting(cConsumer, TinkerFluids.moltenSteel, rod * 3, "steel", Ingredient.of(compatTag("flags")), metalFolder("melting"), "space_flags");
    }

    @Override
    public String compatModId() {
        return "ad_astra";
    }
}
