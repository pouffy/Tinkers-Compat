package io.github.pouffy.tcompat.compat.species;

import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.function.Consumer;

public class SpeciesSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

        int brokenLinks = 110;

        simpleMelting(cConsumer, TinkerFluids.moltenIron, brokenLinks, "iron", ItemNameIngredient.from(compatId("broken_links")), metalFolder("melting"), "broken_links");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, 110 + (brokenLinks * 3), "iron", ItemNameIngredient.from(compatId("chaindelier")), metalFolder("melting"), "chaindelier");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, 135, "iron", ItemNameIngredient.from(compatId("cranktrap")), metalFolder("melting"), "cranktrap");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, (FluidValues.INGOT * 5) + FluidValues.NUGGET * 4, "iron", ItemNameIngredient.from(compatId("crankbow")), metalFolder("melting"), "crankbow");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(compatId("birtday_cake")), metalFolder("melting"), "birtday_cake");
        simpleMelting(TinkerFluids.moltenIron, brokenLinks * 2, ItemNameIngredient.from(compatId("hopelight")))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 2))
                .save(cConsumer, location(metalFolder("melting") + "/iron/hopelight"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT + (brokenLinks * 5), ItemNameIngredient.from(compatId("ricoshield")))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT))
                .setDamagable(FluidValues.NUGGET)
                .save(cConsumer, location(metalFolder("melting") + "/iron/ricoshield"));
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", ItemNameIngredient.from(compatId("kinetic_core")), metalFolder("melting"), "kinetic_core");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", ItemNameIngredient.from(compatId("quake_head")), metalFolder("melting"), "quake_head");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT * 2, ItemNameIngredient.from(compatId("harpoon")))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 4))
                .save(cConsumer, location(metalFolder("melting") + "/copper/harpoon"));
        simpleMelting(cConsumer, TinkerFluids.moltenEnder, FluidValues.SLIMEBALL, "ender", ItemNameIngredient.from(compatId("wicked_swapper")), miscFolder("melting"), "wicked_swapper");
    }

    @Override
    public String compatModId() {
        return "species";
    }
}
