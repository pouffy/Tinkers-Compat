package io.github.pouffy.tcompat.compat.caverns_chasms;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.caverns_and_chasms.common.item.copper.CopperHornItem;
import com.teamabnormals.caverns_and_chasms.core.registry.CCInstruments;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.data.predicate.PartialNBTItemNameIngredient;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.registries.RegistryObject;
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

        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", oxidised("copper_ingot", true), metalFolder("melting"), "oxidised_ingot");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 11, "copper", oxidised("chiseled_copper", false), metalFolder("melting"), "chiseled");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.METAL_BLOCK, "copper", oxidised("copper_grate", false), metalFolder("melting"), "grate");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", CompoundIngredient.of(oxidised("copper_bricks", false), oxidised("copper_brick_wall", false), oxidised("chiseled_copper_bricks", false)), metalFolder("melting"), "bricks");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 65, "copper", oxidised("copper_brick_stairs", false), metalFolder("melting"), "brick_stairs");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 20, "copper", oxidised("copper_brick_slab", false), metalFolder("melting"), "brick_slab");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", oxidised("copper_button", false), metalFolder("melting"), "button");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 2, "copper", oxidised("copper_door", false), metalFolder("melting"), "door");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 4, "copper", oxidised("copper_trapdoor", false), metalFolder("melting"), "trapdoor");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 3, "copper", CompoundIngredient.of(oxidised("copper_bars", false), oxidised("copper_chain", false)), metalFolder("melting"), "bars");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 605, "copper", oxidised("copper_bulb", false), metalFolder("melting"), "bulb");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 8, "copper", oxidised("copper_lantern", false), metalFolder("melting"), "lantern");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 30, "copper", oxidised("toolbox", false), metalFolder("melting"), "toolbox");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 2, "copper", oxidised("copper_rail", false), metalFolder("melting"), "rail");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT * 4, oxidised("floodlight", false))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM))
                .save(cConsumer, location(metalFolder("melting") + "/copper/floodlight"));
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 20, "copper", listedInput("cupric_torch"), metalFolder("melting"), "torch");
        simpleMelting(TinkerFluids.moltenCopper, 20, listedInput("cupric_lantern"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 8))
                .save(cConsumer, location(metalFolder("melting") + "/copper/cupric_lantern"));
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", listedInput("cupric_campfire"), metalFolder("melting"), "campfire");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 4, "copper", listedInput("barometer", "tuning_fork"), metalFolder("melting"), "barometer");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 7, "copper", oxidised("copper_horse_armor", false), metalFolder("melting"), "horse_armor");

        platedBricks(cConsumer, TinkerFluids.moltenIron, "iron");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.NUGGET * 15, "iron", listedInput("halt_rail"), metalFolder("melting"), "halt_rail");

        simpleMelting(cConsumer, TCFluids.moltenTurquoise, 10, "turquoise", listedInput("turquoise_tiles", "turquoise_pillar", "turquoise_tile_wall"), gemFolder("melting"), "tiles");
        simpleMelting(cConsumer, TCFluids.moltenTurquoise, 15, "turquoise", listedInput("turquoise_tile_stairs"), gemFolder("melting"), "tile_stairs");
        simpleMelting(cConsumer, TCFluids.moltenTurquoise, 5, "turquoise", listedInput("turquoise_tile_slab"), gemFolder("melting"), "tile_slab");
        simpleMelting(cConsumer, TCFluids.moltenTurquoise, FluidValues.GEM * 8, "turquoise", listedInput("trim_modifier_smithing_template", "unicorn_horn"), gemFolder("melting"), "trim_modifier_smithing_template");
        simpleMelting(TCFluids.moltenTurquoise, FluidValues.GEM * 8, listedInput("monocle"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 2))
                .save(cConsumer, location(gemFolder("melting") + "/turquoise/monocle"));

        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.LARGE_GEM_BLOCK, "amethyst", listedInput("amethyst_block"), gemFolder("melting"), "large_block");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.SMALL_GEM_BLOCK, "amethyst", listedInput("cut_amethyst", "cut_amethyst_bricks", "cut_amethyst_brick_wall"), gemFolder("melting"), "cut");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM * 2, "amethyst", listedInput("cut_amethyst_brick_slab"), gemFolder("melting"), "cut_slab");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM * 6, "amethyst", listedInput("cut_amethyst_brick_stairs"), gemFolder("melting"), "cut_stairs");

        simpleMelting(cConsumer, TCFluids.moltenSpinel, FluidValues.SMALL_GEM_BLOCK, "spinel", listedInput("spinel_bricks", "spinel_pillar", "spinel_brick_wall"), gemFolder("melting"), "bricks");
        simpleMelting(cConsumer, TCFluids.moltenSpinel, FluidValues.GEM * 2, "spinel", listedInput("spinel_brick_slab"), gemFolder("melting"), "brick_slab");
        simpleMelting(cConsumer, TCFluids.moltenSpinel, FluidValues.GEM * 6, "spinel", listedInput("spinel_brick_stairs"), gemFolder("melting"), "brick_stairs");
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
        simpleMelting(cConsumer, TCFluids.moltenSpinel, FluidValues.GEM, "spinel", listedInput("tmt"), gemFolder("melting"), "tmt");
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 4, listedInput("bejeweled_apple"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT * 4))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/bejeweled_apple"));
        simpleMelting(TCFluids.moltenSpinel, FluidValues.GEM * 2, listedInput("bejeweled_pearl"))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL / 2))
                .save(cConsumer, location(gemFolder("melting") + "/spinel/bejeweled_pearl"));
        simpleMelting(cConsumer, TCFluids.moltenSpinel, FluidValues.GEM / 4, "spinel", listedInput("blunt_arrow"), gemFolder("melting"), "blunt_arrow");


        simpleMelting(cConsumer, TCFluids.moltenZirconia, FluidValues.GEM, "zirconia", listedInput("music_disc_copy"), gemFolder("melting"), "copied_disc");

        simpleMelting(cConsumer, TCFluids.moltenTurquoise, FluidValues.SMALL_GEM_BLOCK, "turquoise", listedInput("turquoise_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(cConsumer, TCFluids.moltenSpinel, FluidValues.SMALL_GEM_BLOCK, "spinel", listedInput("spinel_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(cConsumer, TCFluids.moltenZirconia, FluidValues.SMALL_GEM_BLOCK, "zirconia", listedInput("zirconia_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.SMALL_GEM_BLOCK, "amethyst", listedInput("amethyst_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(cConsumer, TinkerFluids.moltenEmerald, FluidValues.SMALL_GEM_BLOCK, "emerald", listedInput("emerald_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.SMALL_GEM_BLOCK, "diamond", listedInput("diamond_lamp"), gemFolder("melting"), "lamp");
        simpleMelting(cConsumer, TinkerFluids.moltenQuartz, FluidValues.SMALL_GEM_BLOCK, "quartz", listedInput("quartz_lamp"), gemFolder("melting"), "lamp");

        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT, "tin", listedInput("saddled_egg", "hold_button"), metalFolder("melting"), "egg");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 2, "tin", listedInput("roller_door", "roller_window", "hold_plate"), metalFolder("melting"), "roller");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, 5, "tin", listedInput("tinplate"), metalFolder("melting"), "tinplate");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, 45, "tin", listedInput("tinplate_block"), metalFolder("melting"), "tinplate_block");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.NUGGET * 3, "tin", listedInput("tin_bars", "tin_chain"), metalFolder("melting"), "bars");
        platedBricks(cConsumer, TinkerFluids.moltenTin, "tin");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, 65, "tin", listedInput("tin_bulb"), metalFolder("melting"), "bulb");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 7, "tin", listedInput("packing_container"), metalFolder("melting"), "packing_container");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.NUGGET * 14, "tin", listedInput("dimmer"), metalFolder("melting"), "dimmer");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 22, "tin", listedInput("storage_duct"), metalFolder("melting"), "storage_duct");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 6, "tin", listedInput("storage_duct_hatch"), metalFolder("melting"), "storage_duct_hatch");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 3, "tin", listedInput("resistor"), metalFolder("melting"), "resistor");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 4, "tin", listedInput("winch"), metalFolder("melting"), "winch");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.METAL_BLOCK, "tin", listedInput("bouncer"), metalFolder("melting"), "bouncer");
        simpleMelting(TinkerFluids.moltenTin, FluidValues.INGOT * 3, listedInput("refractor"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.SMALL_GEM_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/tin/refractor"));
        simpleMelting(TinkerFluids.moltenTin, FluidValues.INGOT * 8, listedInput("hoop"))
                .addByproduct(TinkerFluids.moltenQuartz.result(FluidValues.GEM))
                .save(cConsumer, location(metalFolder("melting") + "/tin/hoop"));
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 8, "tin", listedInput("scatterer", "splurter"), metalFolder("melting"), "scatterer");
        simpleMelting(cConsumer, TinkerFluids.moltenTin, FluidValues.INGOT * 14, "tin", listedInput("aegis"), metalFolder("melting"), "aegis", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TinkerFluids.moltenTin, 20, "tin", listedInput("ricochet_arrow"), metalFolder("melting"), "ricochet_arrow");

        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 2, "silver", listedInput("medium_weighted_pressure_plate"), metalFolder("melting"), "medium_weighted_pressure_plate");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 3, "silver", listedInput("silver_bars"), metalFolder("melting"), "bars");
        platedBricks(cConsumer, TinkerFluids.moltenSilver, "silver");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 3, "silver", listedInput("brazier"), metalFolder("melting"), "brazier");
        simpleMelting(TinkerFluids.moltenSilver, FluidValues.INGOT * 3, listedInput("soul_brazier"))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/silver/soul_brazier"));
        simpleMelting(TinkerFluids.moltenSilver, FluidValues.INGOT * 3, listedInput("cupric_brazier"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/silver/cupric_brazier"));
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 4, "silver", listedInput("depth_gauge"), metalFolder("melting"), "depth_gauge");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 6, "silver", listedInput("spiked_rail"), metalFolder("melting"), "spiked_rail");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 15, "silver", listedInput("slaughter_rail"), metalFolder("melting"), "slaughter_rail");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, 20, "silver", listedInput("large_arrow"), metalFolder("melting"), "large_arrow");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 3, "silver", listedInput("kunai"), metalFolder("melting"), "kunai");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT, "silver", listedInput("living_flesh"), metalFolder("melting"), "living_flesh");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.METAL_BLOCK, "silver", listedInput("sanguine_block"), metalFolder("melting"), "sanguine_block");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 7, "silver", listedInput("silver_horse_armor"), metalFolder("melting"), "horse_armor");

        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.NUGGET * 3, "gold", listedInput("golden_bars"), metalFolder("melting"), "bars");
        platedBricks(cConsumer, TinkerFluids.moltenGold, "gold");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.METAL_BLOCK * 3, "gold", listedInput("golden_bucket"), metalFolder("melting"), "bucket");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 6, "gold", listedInput("lava_lamp"), metalFolder("melting"), "lava_lamp");


        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 3, "copper", CompoundIngredient.of(
                makeCopperHorn(CavernsChasmsInit.GREAT_SKY_FALLING_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.OLD_HYMN_RESTING_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.PURE_WATER_DESIRE_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.HUMBLE_FIRE_MEMORY_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.DRY_URGE_ANGER_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.CLEAR_TEMPER_JOURNEY_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.FRESH_NEST_THOUGHT_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.SECRET_LAKE_TEAR_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.FEARLESS_RIVER_GIFT_COPPER_HORN),
                makeCopperHorn(CavernsChasmsInit.SWEET_MOON_LOVE_COPPER_HORN)
        ), metalFolder("melting"), "horn");


        simpleMelting(TinkerFluids.moltenGlass, FluidValues.GLASS_PANE * 2, listedInput("frosted_glass"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM))
                .addByproduct(TinkerFluids.moltenQuartz.result(FluidValues.GEM))
                .save(cConsumer, location(miscFolder("melting") + "/glass/frosted"));
        simpleMelting(TinkerFluids.moltenGlass, FluidValues.GLASS_PANE * 2, listedInput("float_glass"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM))
                .addByproduct(TinkerFluids.moltenTin.result(FluidValues.INGOT))
                .save(cConsumer, location(miscFolder("melting") + "/glass/float"));

        simpleMelting(TinkerFluids.moltenGlass, FluidValues.GLASS_PANE / 2, listedInput("frosted_glass_pane"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM / 2))
                .addByproduct(TinkerFluids.moltenQuartz.result(FluidValues.GEM / 2))
                .save(cConsumer, location(miscFolder("melting") + "/glass/frosted_pane"));
        simpleMelting(TinkerFluids.moltenGlass, FluidValues.GLASS_PANE / 2, listedInput("float_glass_pane"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM / 2))
                .addByproduct(TinkerFluids.moltenTin.result(FluidValues.INGOT / 2))
                .save(cConsumer, location(miscFolder("melting") + "/glass/float_pane"));

        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, listedInput("netherite_horse_armor"))
                .addByproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 7))
                .save(cConsumer, location(metalFolder("melting") + "/netherite/horse_armor"));
        simpleMelting(TCFluids.moltenNecromium, FluidValues.INGOT, listedInput("necromium_horse_armor"))
                .addByproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 7))
                .save(cConsumer, location(metalFolder("melting") + "/necromium/horse_armor"));

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

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("barometer")))
                .setCast(Tags.Items.DUSTS_REDSTONE, true)
                .setFluidAndTime(TinkerFluids.moltenCopper, FluidValues.INGOT * 4)
                .save(consumer, location(metalFolder("casting") + "/copper/barometer"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("depth_gauge")))
                .setCast(Tags.Items.DUSTS_REDSTONE, true)
                .setFluidAndTime(TinkerFluids.moltenSilver, FluidValues.INGOT * 4)
                .save(consumer, location(metalFolder("casting") + "/silver/depth_gauge"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("monocle")))
                .setCast(Items.SPYGLASS, true)
                .setFluidAndTime(TCFluids.moltenTurquoise, FluidValues.GEM * 8)
                .save(consumer, location(gemFolder("casting") + "/turquoise/monocle"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("unicorn_horn")))
                .setCast(Items.END_ROD, true)
                .setFluidAndTime(TCFluids.moltenTurquoise, FluidValues.GEM * 8)
                .save(consumer, location(gemFolder("casting") + "/turquoise/unicorn_horn"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("caviar")))
                .setCast(Items.SALMON_BUCKET, true)
                .setFluidAndTime(TCFluids.moltenTurquoise, FluidValues.GEM * 8)
                .save(consumer, location(gemFolder("casting") + "/turquoise/caviar"));

        copperHorn(consumer, Instruments.PONDER_GOAT_HORN, CavernsChasmsInit.GREAT_SKY_FALLING_COPPER_HORN);
        copperHorn(consumer, Instruments.SING_GOAT_HORN, CavernsChasmsInit.OLD_HYMN_RESTING_COPPER_HORN);
        copperHorn(consumer, Instruments.SEEK_GOAT_HORN, CavernsChasmsInit.PURE_WATER_DESIRE_COPPER_HORN);
        copperHorn(consumer, Instruments.FEEL_GOAT_HORN, CavernsChasmsInit.HUMBLE_FIRE_MEMORY_COPPER_HORN);
        copperHorn(consumer, Instruments.ADMIRE_GOAT_HORN, CavernsChasmsInit.DRY_URGE_ANGER_COPPER_HORN);
        copperHorn(consumer, Instruments.CALL_GOAT_HORN, CavernsChasmsInit.CLEAR_TEMPER_JOURNEY_COPPER_HORN);
        copperHorn(consumer, Instruments.YEARN_GOAT_HORN, CavernsChasmsInit.FRESH_NEST_THOUGHT_COPPER_HORN);
        copperHorn(consumer, Instruments.DREAM_GOAT_HORN, CavernsChasmsInit.SECRET_LAKE_TEAR_COPPER_HORN);
        copperHorn(consumer, CavernsChasmsInit.FLY_GOAT_HORN, CavernsChasmsInit.FEARLESS_RIVER_GIFT_COPPER_HORN);
        copperHorn(consumer, CavernsChasmsInit.RESIST_GOAT_HORN, CavernsChasmsInit.SWEET_MOON_LOVE_COPPER_HORN);
    }

    private void copperHorn(Consumer<FinishedRecipe> consumer, ResourceKey<Instrument> input, ImmutableList<ResourceKey<Instrument>> output) {
        CompoundTag inputTag = new CompoundTag();
        inputTag.putString("instrument", input.location().toString());

        CompoundTag outputTag = new CompoundTag();
        String harmonyID = output.get(0).location().toString();
        String melodyID = output.get(1).location().toString();
        String bassID = output.get(2).location().toString();
        outputTag.putString("harmony_instrument", harmonyID);
        outputTag.putString("melody_instrument", melodyID);
        outputTag.putString("bass_instrument", bassID);

        String recipeName = (harmonyID + melodyID + bassID).replace("caverns_and_chasms:", "").replace("copper_horn", "");

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("copper_horn"), 1, outputTag))
                .setCast(PartialNBTIngredient.of(Items.GOAT_HORN, inputTag), true)
                .setFluidAndTime(TinkerFluids.moltenCopper, FluidValues.INGOT * 3)
                .save(consumer, location(metalFolder("casting") + "/copper/horn/" + recipeName.substring(0, recipeName.length() - 1)));

    }

    private PartialNBTItemNameIngredient makeCopperHorn(ImmutableList<ResourceKey<Instrument>> instruments) {
        CompoundTag tag = new CompoundTag();
        String harmonyID = instruments.get(0).location().toString();
        String melodyID = instruments.get(1).location().toString();
        String bassID = instruments.get(2).location().toString();
        tag.putString("harmony_instrument", harmonyID);
        tag.putString("melody_instrument", melodyID);
        tag.putString("bass_instrument", bassID);
        return PartialNBTItemNameIngredient.from(tag, compatId("copper_horn"));
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
