package io.github.pouffy.tcompat.compat.betternether;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;

import java.util.function.Consumer;

public class BetterNetherSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        
        metal(cConsumer, TCFluids.moltenCincinnasite, compatModId()).ore().metal(4, true).optional();
        metal(cConsumer, TCFluids.moltenNetherRuby, compatModId()).ore().gem(9, true).optional();

        salvageAll(cConsumer, this::compatId, TCFluids.moltenNetherRuby, FluidValues.GEM, "nether_ruby", new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET}, gemFolder("melting"));
        salvageAll(cConsumer, this::compatId, TCFluids.moltenNetherRuby, FluidValues.GEM, "flaming_ruby", new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET}, gemFolder("melting"));
        salvageAll(cConsumer, this::compatId, TCFluids.moltenCincinnasite, FluidValues.INGOT, "cincinnasite", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));

        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT, "cincinnasite", listedInput(
                "cincinnasite_button",
                "cincinnasite_chain",
                "nether_brewing_stand"
        ), metalFolder("melting"), "ingot_1");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 2, "cincinnasite", listedInput(
                "cincinnasite_bricks",
                "cincinnasite_bricks_pillar",
                "cincinnasite_shears",
                "cincinnasite_slab",
                "roof_tile_cincinnasite_slab",
                "bone_cincinnasite_door"
        ), metalFolder("melting"), "ingot_2");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 4, "cincinnasite", listedInput(
                "cincinnasite_pillar",
                "taburet_cincinnasite",
                "chair_cincinnasite",
                "bar_stool_cincinnasite",
                "cincinnasite_lantern",
                "cincinnasite_tile_large",
                "cincinnasite_tile_small",
                "cincinnasite_carved",
                "cincinnasite_wall",
                "roof_tile_cincinnasite"
        ), metalFolder("melting"), "ingot_4");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, 112, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_small_lantern")), metalFolder("melting"), "small_lantern");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, 144, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_brick_plate")), metalFolder("melting"), "brick_plate");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 16, "cincinnasite", listedInput("cincinnasite_forge", "cincinnasite_anvil", "chest_of_drawers"), metalFolder("melting"), "ingot_16");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 6, "cincinnasite", listedInput("cincinnasite_stairs", "roof_tile_cincinnasite_stairs"), metalFolder("melting"), "stairs");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 8, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_plate")), metalFolder("melting"), "pressure_plate");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, 22, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_frame")), metalFolder("melting"), "frame");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 12, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_pedestal")), metalFolder("melting"), "pedestal");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.NUGGET * 3, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_bars")), metalFolder("melting"), "bars");
        simpleMelting(cConsumer, TCFluids.moltenCincinnasite, FluidValues.INGOT * 14, "cincinnasite", ItemNameIngredient.from(compatId("cincinnasite_fire_bowl")), metalFolder("melting"), "fire_bowl");

        simpleMelting(cConsumer, TCFluids.moltenNetherRuby, 1350, "nether_ruby", ItemNameIngredient.from(compatId("nether_ruby_stairs")), gemFolder("melting"), "stairs");
        simpleMelting(cConsumer, TCFluids.moltenNetherRuby, FluidValues.LARGE_GEM_BLOCK / 2, "nether_ruby", ItemNameIngredient.from(compatId("nether_ruby_slab")), gemFolder("melting"), "slab");

        simpleMelting(TCFluids.moltenCincinnasite, FluidValues.INGOT * 3, ItemNameIngredient.from(compatId("cincinnasite_pot")))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/cincinnasite/pot"));
        simpleMelting(TCFluids.moltenCincinnasite, FluidValues.INGOT * 14, ItemNameIngredient.from(compatId("cincinnasite_fire_bowl_soul")))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/cincinnasite/soul_fire_bowl"));
        simpleMelting(TCFluids.moltenCincinnasite, FluidValues.INGOT / 2, Ingredient.of(TCTags.Items.FRAMED_QUARTZ_GLASS))
                .addByproduct(TinkerFluids.moltenQuartz.result(FluidValues.GEM / 2))
                .save(cConsumer, location(metalFolder("melting") + "/cincinnasite/framed_quartz_glass"));
        simpleMelting(TCFluids.moltenCincinnasite, 20, Ingredient.of(TCTags.Items.FRAMED_QUARTZ_GLASS_PANES))
                .addByproduct(TinkerFluids.moltenQuartz.result(15))
                .save(cConsumer, location(metalFolder("melting") + "/cincinnasite/framed_quartz_glass_pane"));

        simpleMelting(cConsumer, TinkerFluids.moltenQuartz, FluidValues.GEM, "quartz", Ingredient.of(TCTags.Items.QUARTZ_GLASS), gemFolder("melting"), "glass");
        simpleMelting(cConsumer, TinkerFluids.moltenQuartz, 25, "quartz", Ingredient.of(TCTags.Items.QUARTZ_GLASS_PANES), gemFolder("melting"), "glass_pane");

        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, ItemNameIngredient.from(compatId("netherite_fire_bowl")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 14))
                .save(cConsumer, location(metalFolder("melting") + "/netherite/fire_bowl"));
        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, ItemNameIngredient.from(compatId("netherite_fire_bowl_soul")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 14))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/netherite/soul_fire_bowl"));

        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM * 7, ItemNameIngredient.from(compatId("flaming_ruby_upgrade_smithing_template")))
                .addByproduct(TCFluids.moltenNetherRuby.result(FluidValues.GEM))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/flaming_ruby_upgrade_smithing_template"));

        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(compatId("cincinnite_helmet_diamond")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 5))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_helmet"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(compatId("cincinnite_chestplate_diamond")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 8))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_chestplate"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(compatId("cincinnite_leggings_diamond")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 7))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_leggings"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(compatId("cincinnite_boots_diamond")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 4))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_boots"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, listedInput("cincinnite_pickaxe_diamond", "cincinnite_axe_diamond"))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 3))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_axes"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, listedInput("cincinnite_sword_diamond", "cincinnite_hoe_diamond"))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT * 2))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_weapons"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(compatId("cincinnite_shovel_diamond")))
                .addByproduct(TCFluids.moltenCincinnasite.result(FluidValues.INGOT))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/cincinnite_diamond_shovel"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("cincinnasite_bars")))
                .setFluidAndTime(TCFluids.moltenCincinnasite, FluidValues.NUGGET * 3)
                .save(cConsumer, location(metalFolder("casting") + "/cincinnasite/bars"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("quartz_glass_framed")))
                .setFluidAndTime(TCFluids.moltenCincinnasite, FluidValues.INGOT / 2)
                .setCast(ItemNameIngredient.from(compatId("quartz_glass")), true)
                .save(cConsumer, location(metalFolder("casting") + "/cincinnasite/framed_glass/colorless"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("quartz_glass_framed_pane")))
                .setFluidAndTime(TCFluids.moltenCincinnasite, FluidValues.NUGGET * 2)
                .setCast(ItemNameIngredient.from(compatId("quartz_glass_pane")), true)
                .save(cConsumer, location(metalFolder("casting") + "/cincinnasite/framed_glass/pane_colorless"));
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getSerializedName();
            ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("quartz_glass_framed_" + colorName)))
                    .setFluidAndTime(TCFluids.moltenCincinnasite, FluidValues.INGOT / 2)
                    .setCast(ItemNameIngredient.from(compatId("quartz_glass_" + colorName)), true)
                    .save(cConsumer, location(metalFolder("casting") + "/cincinnasite/framed_glass/" + colorName));
            ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("quartz_glass_framed_pane_" + colorName)))
                    .setFluidAndTime(TCFluids.moltenCincinnasite, FluidValues.NUGGET * 2)
                    .setCast(ItemNameIngredient.from(compatId("quartz_glass_pane_" + colorName)), true)
                    .save(cConsumer, location(metalFolder("casting") + "/cincinnasite/framed_glass/pane_" + colorName));
        }
    }

    @Override
    public String compatModId() {
        return "betternether";
    }
}
