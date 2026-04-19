package io.github.pouffy.tcompat.compat.betterend;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;

import java.util.function.Consumer;

public class BetterEndSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        
        metal(cConsumer, TCFluids.moltenThallasium, compatModId()).ore().metal(true).plate().optional();
        metal(cConsumer, TCFluids.moltenTerminite, compatModId()).metal(true).plate().optional();
        metal(cConsumer, TCFluids.moltenAeternium, compatModId()).metal(true).plate().optional();

        AlloyRecipeBuilder.alloy(TCFluids.moltenTerminite, FluidValues.INGOT)
                .addInput(TinkerFluids.moltenIron.ingredient(FluidValues.INGOT))
                .addInput(TinkerFluids.moltenEnder.ingredient(FluidValues.SLIMEBALL))
                .save(cConsumer, location(alloyFolder("molten_terminite")));

        AlloyRecipeBuilder.alloy(TCFluids.moltenAeternium, FluidValues.INGOT)
                .addInput(TCFluids.moltenTerminite.ingredient(FluidValues.INGOT))
                .addInput(TinkerFluids.moltenNetherite.ingredient(FluidValues.INGOT))
                .save(cConsumer, location(alloyFolder("molten_aeternium")));

        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.METAL_BLOCK, "thallasium", ItemNameIngredient.from(compatId("thallasium_tile")), metalFolder("melting"), "tile");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.METAL_BLOCK, "thallasium", ItemNameIngredient.from(compatId("thallasium_hammer")), metalFolder("melting"), "hammer");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, 1215, "thallasium", ItemNameIngredient.from(compatId("thallasium_stairs")), metalFolder("melting"), "stairs");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.METAL_BLOCK / 2, "thallasium", ItemNameIngredient.from(compatId("thallasium_slab")), metalFolder("melting"), "slab");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.INGOT * 4, "thallasium", ItemNameIngredient.from(compatId("thallasium_trapdoor")), metalFolder("melting"), "trapdoor");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.NUGGET * 3, "thallasium", ItemNameIngredient.from(compatId("thallasium_bars")), metalFolder("melting"), "bars");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, (FluidValues.NUGGET * 2) + FluidValues.INGOT, "thallasium", ItemNameIngredient.from(compatId("thallasium_chain")), metalFolder("melting"), "chain");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, (FluidValues.NUGGET * 2) + (FluidValues.INGOT * 2), "thallasium", Ingredient.of(TCTags.Items.THALLASIUM_BULB_LANTERNS), metalFolder("melting"), "bulb_lantern");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.INGOT * 2, "thallasium", listedInput(
                "thallasium_shovel_head",
                "thallasium_shovel",
                "thallasium_sword_blade",
                "thallasium_sword_handle",
                "thallasium_upgrade_smithing_template"
        ), metalFolder("melting"), "ingot_1");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.INGOT * 2, "thallasium", listedInput(
                "thallasium_plate",
                "thallasium_door",
                "thallasium_chandelier",
                "thallasium_hoe_head",
                "thallasium_sword",
                "thallasium_hoe"
        ), metalFolder("melting"), "ingot_2");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.INGOT * 3, "thallasium", listedInput(
                "thallasium_pickaxe_head",
                "thallasium_axe_head",
                "thallasium_pickaxe",
                "thallasium_axe"
        ), metalFolder("melting"), "ingot_3");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, (FluidValues.METAL_BLOCK * 3) + (FluidValues.INGOT * 4), "thallasium", ItemNameIngredient.from(compatId("thallasium_anvil")), metalFolder("melting"), "anvil");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.INGOT * 4, "thallasium", ItemNameIngredient.from(compatId("end_stone_smelter")), metalFolder("melting"), "end_stone_smelter");

        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.METAL_BLOCK, "terminite", ItemNameIngredient.from(compatId("terminite_tile")), metalFolder("melting"), "tile");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.METAL_BLOCK, "terminite", ItemNameIngredient.from(compatId("terminite_hammer")), metalFolder("melting"), "hammer");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, 1215, "terminite", ItemNameIngredient.from(compatId("terminite_stairs")), metalFolder("melting"), "stairs");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.METAL_BLOCK / 2, "terminite", ItemNameIngredient.from(compatId("terminite_slab")), metalFolder("melting"), "slab");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.INGOT * 4, "terminite", ItemNameIngredient.from(compatId("terminite_trapdoor")), metalFolder("melting"), "trapdoor");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.NUGGET * 3, "terminite", ItemNameIngredient.from(compatId("terminite_bars")), metalFolder("melting"), "bars");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, (FluidValues.NUGGET * 2) + FluidValues.INGOT, "terminite", ItemNameIngredient.from(compatId("terminite_chain")), metalFolder("melting"), "chain");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, (FluidValues.NUGGET * 2) + (FluidValues.INGOT * 2), "terminite", Ingredient.of(TCTags.Items.TERMINITE_BULB_LANTERNS), metalFolder("melting"), "bulb_lantern");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.INGOT * 2, "terminite", listedInput(
                "terminite_shovel_head",
                "terminite_shovel",
                "terminite_sword_blade",
                "terminite_sword_handle",
                "aeternium_sword_handle",
                "terminite_upgrade_smithing_template"
        ), metalFolder("melting"), "ingot_1");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.INGOT * 2, "terminite", listedInput(
                "terminite_plate",
                "terminite_door",
                "terminite_chandelier",
                "terminite_hoe_head",
                "terminite_sword",
                "terminite_hoe"
        ), metalFolder("melting"), "ingot_2");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.INGOT * 3, "terminite", listedInput(
                "terminite_pickaxe_head",
                "terminite_axe_head",
                "terminite_pickaxe",
                "terminite_axe"
        ), metalFolder("melting"), "ingot_3");
        simpleMelting(cConsumer, TCFluids.moltenTerminite, (FluidValues.METAL_BLOCK * 3) + (FluidValues.INGOT * 4), "terminite", ItemNameIngredient.from(compatId("terminite_anvil")), metalFolder("melting"), "anvil");

        simpleMelting(cConsumer, TinkerFluids.moltenEnder, FluidValues.SLIME_CONGEALED, "ender", ItemNameIngredient.from(compatId("ender_block")), metalFolder("melting"), "block");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, (FluidValues.NUGGET * 2) + (FluidValues.INGOT * 2), "iron", Ingredient.of(TCTags.Items.IRON_BULB_LANTERNS), metalFolder("melting"), "bulb_lantern");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(compatId("iron_chandelier")), metalFolder("melting"), "chandelier");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(compatId("gold_chandelier")), metalFolder("melting"), "chandelier");

        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, ItemNameIngredient.from(compatId("infusion_pedestal")))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL * 3))
                .save(cConsumer, location(miscFolder("melting") + "/obsidian/infusion_pedestal"));

        simpleMelting(TCFluids.moltenAeternium, FluidValues.INGOT, ItemNameIngredient.from(compatId("aeternium_anvil")))
                .addByproduct(TCFluids.moltenTerminite.result((FluidValues.METAL_BLOCK * 3) + (FluidValues.INGOT * 4)))
                .save(cConsumer, location(metalFolder("melting") + "/aeternium/anvil"));

        simpleMelting(cConsumer, TinkerFluids.moltenEnder, FluidValues.SLIMEBALL, "ender", listedInput("ender_ore", "ender_shard", "ender_dust"), metalFolder("melting"), "slimeball_1");

        simpleMelting(cConsumer, TCFluids.moltenAeternium, FluidValues.INGOT, "aeternium", listedInput(
                "elytra_armored",
                "aeternium_shovel_head",
                "aeternium_pickaxe_head",
                "aeternium_axe_head",
                "aeternium_hoe_head",
                "aeternium_hammer_head",
                "aeternium_sword_blade",
                "aeternium_hammer"
        ), metalFolder("melting"), "ingot_1");

        hammerMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(compatId("iron_hammer")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET});
        hammerMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(compatId("golden_hammer")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET});
        hammerMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 4, "diamond", ItemNameIngredient.from(compatId("diamond_hammer")), gemFolder("melting"), true, new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET});
        hammerMelting(cConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(compatId("netherite_hammer")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));

        salvageArmor(cConsumer, this::compatId, TCFluids.moltenTerminite, FluidValues.INGOT, "terminite", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        salvageArmor(cConsumer, this::compatId, TCFluids.moltenThallasium, FluidValues.INGOT, "thallasium", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        salvageArmor(cConsumer, this::compatId, TCFluids.moltenAeternium, TCFluids.moltenTerminite, FluidValues.INGOT, "aeternium", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));

        simpleMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM, "diamond", ItemNameIngredient.from(compatId("handle_attachment_smithing_template")), gemFolder("melting"), "handle_attachment_smithing_template");
        simpleMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 7, "diamond", listedInput(
                "leather_handle_attachment_smithing_template",
                "aeternium_upgrade_smithing_template",
                "netherite_upgrade_smithing_template"
        ), gemFolder("melting"), "diamond_rich_templates");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.METAL_BLOCK, "iron", ItemNameIngredient.from(compatId("tool_assembly_smithing_template")), metalFolder("melting"), "tool_assembly_smithing_template");
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM * 7, ItemNameIngredient.from(compatId("plate_upgrade_smithing_template")))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/plate_upgrade_smithing_template"));
        simpleMelting(cConsumer, TCFluids.moltenTerminite, FluidValues.INGOT, "terminite", ItemNameIngredient.from(compatId("terminite_upgrade_smithing_template")), metalFolder("melting"), "terminite_upgrade_smithing_template");
        simpleMelting(cConsumer, TCFluids.moltenThallasium, FluidValues.INGOT, "thallasium", ItemNameIngredient.from(compatId("thallasium_upgrade_smithing_template")), metalFolder("melting"), "thallasium_upgrade_smithing_template");

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("thallasium_bars")))
                .setFluidAndTime(TCFluids.moltenThallasium, FluidValues.NUGGET * 3)
                .save(cConsumer, location(metalFolder("casting") + "/thallasium/bars"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("terminite_bars")))
                .setFluidAndTime(TCFluids.moltenTerminite, FluidValues.NUGGET * 3)
                .save(cConsumer, location(metalFolder("casting") + "/terminite/bars"));
    }

    @Override
    public String compatModId() {
        return "betterend";
    }
}
