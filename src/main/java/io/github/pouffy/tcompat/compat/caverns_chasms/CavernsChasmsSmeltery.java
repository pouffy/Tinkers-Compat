package io.github.pouffy.tcompat.compat.caverns_chasms;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CavernsChasmsSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        metal(cConsumer, TCFluids.moltenNecromium, compatModId()).metal(9, true).optional();

        gem(cConsumer, TCFluids.moltenSpinel, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .ore(TCByproduct.ENDER)
                .baseUnit(100)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .sparseOre(0.5f).singularOre(1).denseOre(3);
        gem(cConsumer, TCFluids.moltenTurquoise, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .ore(Byproduct.SMALL_DIAMOND)
                .baseUnit(100)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .sparseOre(0.5f).singularOre(1).denseOre(3);
        gem(cConsumer, TCFluids.moltenZirconia, compatModId())
                .baseUnit(100)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true);

        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", oxidised("copper_ingot", true), metalFolder("melting"), "oxidised_ingot");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 11, "copper", oxidised("chiseled_copper", false), metalFolder("melting"), "chiseled");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.METAL_BLOCK, "copper", oxidised("copper_grate", false), metalFolder("melting"), "grate");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", CompoundIngredient.of(oxidised("copper_bricks", false), oxidised("copper_brick_wall", false), oxidised("chiseled_copper_bricks", false)), metalFolder("melting"), "bricks");
        simpleMelting(consumer, TinkerFluids.moltenCopper, 65, "copper", oxidised("copper_brick_stairs", false), metalFolder("melting"), "brick_stairs");
        simpleMelting(consumer, TinkerFluids.moltenCopper, 20, "copper", oxidised("copper_brick_slab", false), metalFolder("melting"), "brick_slab");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", oxidised("copper_button", false), metalFolder("melting"), "button");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 2, "copper", oxidised("copper_door", false), metalFolder("melting"), "door");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 4, "copper", oxidised("copper_trapdoor", false), metalFolder("melting"), "trapdoor");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 3, "copper", CompoundIngredient.of(oxidised("copper_bars", false), oxidised("copper_chain", false)), metalFolder("melting"), "bars");
        simpleMelting(consumer, TinkerFluids.moltenCopper, 605, "copper", oxidised("copper_bulb", false), metalFolder("melting"), "bulb");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 8, "copper", oxidised("copper_lantern", false), metalFolder("melting"), "lantern");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 30, "copper", oxidised("toolbox", false), metalFolder("melting"), "toolbox");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 2, "copper", oxidised("copper_rail", false), metalFolder("melting"), "rail");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT * 4, oxidised("floodlight", false))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM))
                .save(cConsumer, location(metalFolder("melting") + "/copper/floodlight"));
        simpleMelting(consumer, TinkerFluids.moltenCopper, 20, "copper", listedInput("cupric_torch"), metalFolder("melting"), "torch");
        simpleMelting(TinkerFluids.moltenCopper, 20, listedInput("cupric_lantern"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 8))
                .save(cConsumer, location(metalFolder("melting") + "/copper/cupric_lantern"));
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", listedInput("cupric_campfire"), metalFolder("melting"), "campfire");

        platedBricks(cConsumer, TinkerFluids.moltenIron, "iron");

        simpleMelting(consumer, TCFluids.moltenTurquoise, 10, "turquoise", listedInput("turquoise_tiles", "turquoise_pillar", "turquoise_tile_wall"), gemFolder("melting"), "tiles");
        simpleMelting(consumer, TCFluids.moltenTurquoise, 15, "turquoise", listedInput("turquoise_tile_stairs"), gemFolder("melting"), "tile_stairs");
        simpleMelting(consumer, TCFluids.moltenTurquoise, 5, "turquoise", listedInput("turquoise_tile_slab"), gemFolder("melting"), "tile_slab");
        simpleMelting(consumer, TCFluids.moltenTurquoise, FluidValues.GEM * 8, "turquoise", listedInput("trim_modifier_smithing_template"), gemFolder("melting"), "trim_modifier_smithing_template");

        simpleMelting(consumer, TinkerFluids.moltenAmethyst, FluidValues.LARGE_GEM_BLOCK, "amethyst", listedInput("amethyst_block"), gemFolder("melting"), "large_block");
        simpleMelting(consumer, TinkerFluids.moltenAmethyst, FluidValues.SMALL_GEM_BLOCK, "amethyst", listedInput("cut_amethyst", "cut_amethyst_bricks", "cut_amethyst_brick_wall"), gemFolder("melting"), "cut");
        simpleMelting(consumer, TinkerFluids.moltenAmethyst, FluidValues.GEM * 2, "amethyst", listedInput("cut_amethyst_brick_slab"), gemFolder("melting"), "cut_slab");
        simpleMelting(consumer, TinkerFluids.moltenAmethyst, FluidValues.GEM * 6, "amethyst", listedInput("cut_amethyst_brick_stairs"), gemFolder("melting"), "cut_stairs");

        simpleMelting(consumer, TCFluids.moltenSpinel, FluidValues.SMALL_GEM_BLOCK, "spinel", listedInput("spinel_bricks", "spinel_pillar", "spinel_brick_wall"), gemFolder("melting"), "bricks");
        simpleMelting(consumer, TCFluids.moltenSpinel, FluidValues.GEM * 2, "spinel", listedInput("spinel_brick_slab"), gemFolder("melting"), "brick_slab");
        simpleMelting(consumer, TCFluids.moltenSpinel, FluidValues.GEM * 6, "spinel", listedInput("spinel_brick_stairs"), gemFolder("melting"), "brick_stairs");
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 8, listedInput("dismantling_table"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT * 2))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/dismantling_table"));
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 4, listedInput("bejeweled_anvil"))
                .addByproduct(TinkerFluids.moltenIron.result(1395))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/bejeweled_anvil"));
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 8, listedInput("atoning_table"))
                .addByproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2))
                .addByproduct(TinkerFluids.moltenObsidian.result(FluidValues.GLASS_BLOCK * 4))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/atoning_table"));
        simpleMelting(consumer, TCFluids.moltenSpinel, FluidValues.GEM, "spinel", listedInput("tmt"), gemFolder("melting"), "tmt");
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 4, listedInput("bejeweled_apple"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT * 4))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/bejeweled_apple"));
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 2, listedInput("bejeweled_pearl"))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL / 2))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/bejeweled_pearl"));
        simpleMelting(consumer, TCFluids.moltenSpinel, FluidValues.GEM / 4, "spinel", listedInput("blunt_arrow"), gemFolder("melting"), "blunt_arrow");


        simpleMelting(consumer, TCFluids.moltenZirconia, FluidValues.GEM, "zirconia", listedInput("music_disc_copy"), gemFolder("melting"), "copied_disc");

        simpleMelting(consumer, TCFluids.moltenTurquoise, FluidValues.SMALL_GEM_BLOCK, "turquoise", listedInput("turquoise_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(consumer, TCFluids.moltenSpinel, FluidValues.SMALL_GEM_BLOCK, "spinel", listedInput("spinel_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(consumer, TCFluids.moltenZirconia, FluidValues.SMALL_GEM_BLOCK, "zirconia", listedInput("zirconia_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(consumer, TinkerFluids.moltenAmethyst, FluidValues.SMALL_GEM_BLOCK, "amethyst", listedInput("amethyst_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(consumer, TinkerFluids.moltenEmerald, FluidValues.SMALL_GEM_BLOCK, "emerald", listedInput("emerald_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(consumer, TinkerFluids.moltenDiamond, FluidValues.SMALL_GEM_BLOCK, "diamond", listedInput("diamond_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(consumer, TinkerFluids.moltenQuartz, FluidValues.SMALL_GEM_BLOCK, "quartz", listedInput("quartz_lamp"), gemFolder("melting"), "lamp");

        simpleMelting(consumer, TinkerFluids.moltenTin, FluidValues.INGOT, "tin", listedInput("saddled_egg", "hold_button"), metalFolder("melting"), "egg");
        simpleMelting(consumer, TinkerFluids.moltenTin, FluidValues.INGOT * 2, "tin", listedInput("roller_door", "roller_window", "hold_plate"), metalFolder("melting"), "roller");
        simpleMelting(consumer, TinkerFluids.moltenTin, 5, "tin", listedInput("tinplate"), metalFolder("melting"), "tinplate");
        simpleMelting(consumer, TinkerFluids.moltenTin, 45, "tin", listedInput("tinplate_block"), metalFolder("melting"), "tinplate_block");
        simpleMelting(consumer, TinkerFluids.moltenTin, FluidValues.NUGGET * 3, "tin", listedInput("tin_bars", "tin_chain"), metalFolder("melting"), "bars");
        platedBricks(cConsumer, TinkerFluids.moltenTin, "tin");
        simpleMelting(consumer, TinkerFluids.moltenTin, 65, "tin", listedInput("tin_bulb"), metalFolder("melting"), "bulb");

        simpleMelting(consumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 2, "silver", listedInput("medium_weighted_pressure_plate"), metalFolder("melting"), "medium_weighted_pressure_plate");
        simpleMelting(consumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 3, "silver", listedInput("silver_bars"), metalFolder("melting"), "bars");
        platedBricks(cConsumer, TinkerFluids.moltenSilver, "silver");
        simpleMelting(consumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 3, "silver", listedInput("brazier"), metalFolder("melting"), "brazier");
        simpleMelting(TinkerFluids.moltenSilver, FluidValues.INGOT * 3, listedInput("soul_brazier"))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/silver/soul_brazier"));
        simpleMelting(TinkerFluids.moltenSilver, FluidValues.INGOT * 3, listedInput("cupric_brazier"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/silver/cupric_brazier"));

        simpleMelting(consumer, TinkerFluids.moltenGold, FluidValues.NUGGET * 3, "gold", listedInput("golden_bars"), metalFolder("melting"), "bars");
        platedBricks(cConsumer, TinkerFluids.moltenGold, "gold");

        casting(cConsumer);
    }

    private void casting(Consumer<FinishedRecipe> consumer) {
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("sugilite")))
                .setCast(Items.GRANITE, true)
                .setFluidAndTime(TCFluids.moltenSpinel, FluidValues.GEM)
                .save(consumer, location(gemFolder("casting") + "/spinel/sugilite"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("cassiterite")))
                .setCast(Items.GRANITE, true)
                .setFluidAndTime(TinkerFluids.moltenTin, FluidValues.INGOT)
                .save(consumer, location(metalFolder("casting") + "/tin/cassiterite"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("cylindrite")))
                .setCast(Items.DEEPSLATE, true)
                .setFluidAndTime(TinkerFluids.moltenTin, FluidValues.INGOT)
                .save(consumer, location(metalFolder("casting") + "/tin/cylindrite"));

        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("magmatic_rhyolite")))
                .setCast(ItemNameIngredient.from(compatId("rhyolite")), true)
                .setFluidAndTime(TinkerFluids.magma, FluidValues.SLIMEBALL)
                .save(consumer, location(miscFolder("casting") + "/magma/magmatic_rhyolite"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("turquoise_tiles")))
                .setCast(Items.STONE_BRICKS, true)
                .setFluidAndTime(TCFluids.moltenTurquoise, 10)
                .save(consumer, location(gemFolder("casting") + "/turquoise/tiles"));

        //Lamps
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("turquoise_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TCFluids.moltenTurquoise, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/turquoise/lamp"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("spinel_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TCFluids.moltenSpinel, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/spinel/lamp"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("zirconia_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TCFluids.moltenZirconia, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/zirconia/lamp"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("emerald_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TinkerFluids.moltenEmerald, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/emerald/lamp"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("diamond_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TinkerFluids.moltenDiamond, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/diamond/lamp"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("quartz_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TinkerFluids.moltenQuartz, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/quartz/lamp"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("amethyst_lamp")))
                .setCast(Items.GLOWSTONE, true)
                .setFluidAndTime(TinkerFluids.moltenAmethyst, FluidValues.SMALL_GEM_BLOCK)
                .save(consumer, location(gemFolder("casting") + "/amethyst/lamp"));

        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("ornate_glass")))
                .setCast(Tags.Items.GLASS_SILICA, true)
                .setFluidAndTime(TCFluids.moltenZirconia, 10)
                .save(consumer, location(gemFolder("casting") + "/zirconia/ornate_glass"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("ornate_glass_pane")))
                .setCast(TCTags.Items.common("glass_panes/silica"), true)
                .setFluidAndTime(TCFluids.moltenZirconia, 5)
                .save(consumer, location(gemFolder("casting") + "/zirconia/ornate_glass_pane"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("tin_bars")))
                .setFluidAndTime(TinkerFluids.moltenTin, FluidValues.NUGGET * 3)
                .save(consumer, location(metalFolder("casting") + "/tin/bars"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("silver_bars")))
                .setFluidAndTime(TinkerFluids.moltenSilver, FluidValues.NUGGET * 3)
                .save(consumer, location(metalFolder("casting") + "/silver/bars"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("copper_bars")))
                .setFluidAndTime(TinkerFluids.moltenCopper, FluidValues.NUGGET * 3)
                .save(consumer, location(metalFolder("casting") + "/copper/bars"));

        ItemCastingRecipeBuilder.tableDuplication()
                .setCast(listedInput("trim_modifier_smithing_template"), false)
                .setCoolingTime(186)
                .setFluid(TinkerFluids.moltenDiamond.ingredient(500))
                .save(consumer, location(gemFolder("casting") + "/diamond/trim_modifier_smithing_template"));
    }

    private void platedBricks(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String name) {
        simpleMelting(consumer, fluid, FluidValues.INGOT / 2, name, listedInput(name + "_bricks", name + "_brick_wall", "chiseled_" + name + "_bricks"), metalFolder("melting"), "bricks");
        simpleMelting(consumer, fluid, 65, name, listedInput(name + "_brick_stairs"), metalFolder("melting"), "brick_stairs");
        simpleMelting(consumer, fluid, 20, name, listedInput(name + "_brick_slab"), metalFolder("melting"), "brick_slab");
    }

    private Ingredient oxidised(String id, boolean ignoreRegular) {
        List<ResourceLocation> list = new ArrayList<>();
        ResourceLocation primaryId = compatId(id);
        if (!ignoreRegular) list.add(primaryId);

        list.add(primaryId.withPrefix("exposed_"));
        list.add(primaryId.withPrefix("weathered_"));
        list.add(primaryId.withPrefix("oxidized_"));
        list.add(primaryId.withPrefix("waxed_"));
        list.add(primaryId.withPrefix("waxed_exposed_"));
        list.add(primaryId.withPrefix("waxed_weathered_"));
        list.add(primaryId.withPrefix("waxed_oxidized_"));
        return ItemNameIngredient.from(list);
    }

    @Override
    public String compatModId() {
        return "caverns_and_chasms";
    }
}
