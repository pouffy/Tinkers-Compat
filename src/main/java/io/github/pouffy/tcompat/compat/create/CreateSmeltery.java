package io.github.pouffy.tcompat.compat.create;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.function.Consumer;

public class CreateSmeltery implements CompatSmeltery {

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", Ingredient.of(TCTags.Items.named("create", "toolboxes")), metalFolder("melting"), "toolbox");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 3, "copper", Ingredient.of(TCTags.Items.named("create", "valve_handles")), metalFolder("melting"), "valve_handle");

        equipment(cConsumer);
        kinetics(cConsumer);
        fluids(cConsumer);
        decorations(cConsumer);
    }

    private void equipment(Consumer<FinishedRecipe> cConsumer) {
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 12, "copper", listedInput("copper_backtank"), metalFolder("melting"), "backtank", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 4, "copper", listedInput("copper_diving_boots"), metalFolder("melting"), "diving_boots", new int[]{FluidValues.NUGGET});
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT * 5, listedInput("copper_diving_helmet"))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK))
                .setDamagable(FluidValues.NUGGET, FluidValues.GLASS_PANE)
                .save(cConsumer, location(metalFolder("melting") + "/copper/diving_helmet"));

        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, listedInput("netherite_backtank"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 12))
                .setDamagable(FluidValues.NUGGET, FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/netherite/backtank"));
        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, listedInput("netherite_diving_boots"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 4))
                .setDamagable(FluidValues.NUGGET, FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/netherite/diving_boots"));
        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, listedInput("netherite_diving_helmet"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 5))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK))
                .setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GLASS_PANE)
                .save(cConsumer, location(metalFolder("melting") + "/netherite/diving_helmet"));

        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 3, "gold", listedInput("wrench"), metalFolder("melting"), "wrench");

        simpleMelting(TinkerFluids.moltenCopper, 381, listedInput("potato_cannon"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/copper/potato_cannon"));

        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT * 5, listedInput("extendo_grip"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/brass/extendo_grip"));

        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("wand_of_symmetry"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .addByproduct(TinkerFluids.moltenObsidian.result(FluidValues.GLASS_BLOCK))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK * 3))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL))
                .setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GLASS_PANE, FluidValues.GLASS_PANE, FluidValues.SLIME_DROP)
                .save(cConsumer, location(metalFolder("melting") + "/brass/wand_of_symmetry"));

        simpleMelting(TinkerFluids.moltenGold, FluidValues.INGOT, listedInput("goggles"))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK * 2))
                .save(cConsumer, location(metalFolder("melting") + "/gold/goggles"));

        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 3, "copper", listedInput("linked_controller"), metalFolder("melting"), "linked_controller");
    }

    private void kinetics(Consumer<FinishedRecipe> cConsumer) {

    }

    private void fluids(Consumer<FinishedRecipe> cConsumer) {
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", listedInput("copper_casing"), metalFolder("melting"), "casing");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 67, "copper", listedInput("fluid_pipe", "mechanical_pump"), metalFolder("melting"), "fluid_pipes");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("smart_fluid_pipe"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenCopper.result(67))
                .save(cConsumer, location(metalFolder("melting") + "/brass/smart_fluid_pipe"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT, listedInput("fluid_valve"))
                .addByproduct(TinkerFluids.moltenCopper.result(67))
                .save(cConsumer, location(metalFolder("melting") + "/iron/fluid_valve"));
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 2, "copper", listedInput("fluid_tank", "hose_pulley"), metalFolder("melting"), "fluid_tank");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT, listedInput("item_drain"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 3))
                .save(cConsumer, location(metalFolder("melting") + "/copper/item_drain"));
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", listedInput("spout"), metalFolder("melting"), "spout");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT, listedInput("steam_engine", "steam_whistle"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/copper/steam_appliances"));
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT, listedInput("portable_fluid_interface"))
                .addByproduct(TinkerFluids.moltenIron.result(67))
                .save(cConsumer, location(metalFolder("melting") + "/copper/portable_fluid_interface"));
    }

    private void decorations(Consumer<FinishedRecipe> cConsumer) {

    }

    @Override
    public String compatModId() {
        return "create";
    }
}
