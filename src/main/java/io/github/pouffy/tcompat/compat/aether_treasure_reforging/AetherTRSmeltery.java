package io.github.pouffy.tcompat.compat.aether_treasure_reforging;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class AetherTRSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        Function<String, ResourceLocation> aetherId = name -> getResource("aether", name);
        Function<String, ResourceLocation> aetherReduxId = name -> getResource("aether_redux", name);

        metal(cConsumer, TCFluids.moltenValkyrum, compatModId()).metal(true).optional();
        metal(cConsumer, TCFluids.moltenPyral, compatModId()).metal(true).optional();
        //Misc Items
        simpleMelting(withCondition(cConsumer, new ModLoadedCondition("aether_redux")), TCFluids.moltenValkyrum, FluidValues.INGOT, "valkyrie", ItemNameIngredient.from(aetherReduxId.apply("grand_victory_medal")), metalFolder("melting"), "grand_victory_medal");
        simpleMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT * 3, "valkyrie", ItemNameIngredient.from(aetherId.apply("valkyrie_cape")), metalFolder("melting"), "valkyrie_cape");
        simpleMelting(cConsumer, TCFluids.moltenNeptune, FluidValues.INGOT, "neptune", ItemNameIngredient.from(compatId("neptune_mesh")), metalFolder("melting"), "neptune_mesh");
        simpleMelting(cConsumer, TCFluids.moltenPyral, FluidValues.INGOT * 3, "phoenix", ItemNameIngredient.from(aetherId.apply("phoenix_bow")), metalFolder("melting"), "phoenix_bow");
        simpleMelting(cConsumer, TCFluids.moltenPyral, FluidValues.INGOT * 2, "phoenix", ItemNameIngredient.from(aetherId.apply("flaming_sword")), metalFolder("melting"), "flaming_sword");
        //Gloves
        glovesMelting(cConsumer, TCFluids.moltenValkyrum, FluidValues.INGOT, "valkyrie", ItemNameIngredient.from(aetherId.apply("valkyrie_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, TCFluids.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(cConsumer, TCFluids.moltenPyral, FluidValues.INGOT, "phoenix", ItemNameIngredient.from(aetherId.apply("phoenix_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, TCFluids.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(cConsumer, TCFluids.moltenNeptune, FluidValues.INGOT, "neptune", ItemNameIngredient.from(aetherId.apply("neptune_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, TCFluids.moltenZanite.result(FluidValues.INGOT * 2));
        //Salvaging
        salvageAll(cConsumer, aetherId, TCFluids.moltenValkyrum, TCFluids.moltenGravitite, FluidValues.INGOT, "valkyrie", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        salvageArmor(cConsumer, aetherId, TCFluids.moltenPyral, TCFluids.moltenGravitite, FluidValues.INGOT, "phoenix", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        salvageArmor(cConsumer, aetherId, TCFluids.moltenNeptune, TCFluids.moltenZanite, FluidValues.INGOT, "neptune", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("neptune_mesh")))
                .setFluidAndTime(TCFluids.moltenNeptune, FluidValues.INGOT)
                .save(consumer, location(metalFolder("casting") + "neptune/mesh"));

        for (String type : new String[]{"valkyrie", "neptune", "phoenix"}) {
            String template = type + "_upgrade_smithing_template";
            simpleMelting(cConsumer, TCFluids.moltenGravitite, FluidValues.INGOT * 7, type, ItemNameIngredient.from(compatId(template)), metalFolder("melting"), template);
        }
    }

    @Override
    public String compatModId() {
        return "aether_treasure_reforging";
    }
}
