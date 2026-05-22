package io.github.pouffy.tcompat.compat.malum;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.function.Consumer;

public class MalumSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        // Core melting
        metal(cConsumer, TCFluids.moltenSoulStainedSteel, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenHallowedGold, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenMalignantPewter, compatModId()).metal(9, true).optional();
        gem(cConsumer, TCFluids.moltenBlazingQuartz, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .baseUnit(80)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .meltingCasting(1 / 8f, TinkerSmeltery.nuggetCast, 1 / 4f, true);
        gem(cConsumer, TCFluids.moltenCthonicGold, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .baseUnit(80)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .meltingCasting(1 / 8f, TinkerSmeltery.nuggetCast, 1 / 4f, true)
                .sparseOre(0.5f).singularOre(1).denseOre(3);
        gem(cConsumer, TCFluids.moltenMalignantLead, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .baseUnit(80)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true);

        // Salvaging
        salvageTools(cConsumer, this::compatId, TCFluids.moltenSoulStainedSteel, FluidValues.INGOT, "soul_stained_steel", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, FluidValues.INGOT * 4, "soul_stained_steel", listedInput("soul_stained_steel_scythe", "mnemonic_hex_staff"), metalFolder("melting"), "magic_weapon", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, 390, "soul_stained_steel", listedInput("soul_stained_steel_helmet", "soul_stained_steel_chestplate", "soul_stained_steel_leggings", "soul_stained_steel_boots"), metalFolder("melting"), "armor", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenMalignantPewter, FluidValues.INGOT * 2, "malignant_pewter", listedInput("edge_of_deliverance", "erosion_scepter", "weight_of_worlds"), metalFolder("melting"), "magic_weapon", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenMalignantPewter, 195, "malignant_pewter", listedInput("malignant_stronghold_helmet", "malignant_stronghold_chestplate", "malignant_stronghold_leggings", "malignant_stronghold_boots"), metalFolder("melting"), "armor", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", listedInput("catalyst_lobber"), metalFolder("melting"), "catalyst_lobber", new int[]{FluidValues.NUGGET});

        // Specific Salvaging
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, FluidValues.INGOT, "soul_stained_steel", listedInput("ornate_ring", "ornate_necklace"), metalFolder("melting"), "ornate_curio");
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, FluidValues.INGOT + FluidValues.METAL_BLOCK, "soul_stained_steel", listedInput("elaborate_brooch"), metalFolder("melting"), "elaborate_brooch");
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, 65, "soul_stained_steel", listedInput("soul_stained_steel_plating"), metalFolder("melting"), "plating");
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, 390, "soul_stained_steel", listedInput("ring_of_manaweaving", "belt_of_the_magebane"), metalFolder("melting"), "infused_curio");
        simpleMelting(cConsumer, TCFluids.moltenSoulStainedSteel, 130, "soul_stained_steel", listedInput("shielding_apparatus"), metalFolder("melting"), "shielding_apparatus");
        simpleMelting(cConsumer, TCFluids.moltenHallowedGold, FluidValues.INGOT, "hallowed_gold", listedInput("gilded_ring", "weavers_workbench"), metalFolder("melting"), "single");
        simpleMelting(cConsumer, TCFluids.moltenHallowedGold, FluidValues.INGOT * 2, "hallowed_gold", listedInput("runic_workbench", "runewood_obelisk"), metalFolder("melting"), "ritual_parts");
        simpleMelting(cConsumer, TCFluids.moltenHallowedGold, FluidValues.INGOT * 3, "hallowed_gold", listedInput("gilded_belt"), metalFolder("melting"), "gilded_belt");
        simpleMelting(cConsumer, TCFluids.moltenHallowedGold, FluidValues.INGOT + FluidValues.METAL_BLOCK, "hallowed_gold", listedInput("runic_brooch"), metalFolder("melting"), "runic_brooch");
        simpleMelting(cConsumer, TCFluids.moltenMalignantPewter, 65, "malignant_pewter", listedInput("malignant_pewter_plating"), metalFolder("melting"), "plating");
        simpleMelting(cConsumer, TCFluids.moltenBlazingQuartz, 160, "blazing_quartz", listedInput("blazing_diode"), gemFolder("melting"), "blazing_diode");
        simpleMelting(cConsumer, TCFluids.moltenBlazingQuartz, 40, "blazing_quartz", listedInput("ether", "iridescent_ether"), gemFolder("melting"), "ether");

        simpleMelting(cConsumer, TinkerFluids.moltenEmerald, FluidValues.GEM * 2, "emerald", listedInput("intricate_assembly"), gemFolder("melting"), "intricate_assembly");
        simpleMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 8, "diamond", listedInput("glass_brooch"), gemFolder("melting"), "glass_brooch");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 6, "iron", listedInput("ring_of_the_demolitionist"), metalFolder("melting"), "ring_of_the_demolitionist");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", listedInput("necklace_of_tidal_affinity"), metalFolder("melting"), "necklace_of_tidal_affinity");
        simpleMelting(cConsumer, TinkerFluids.moltenIron,  FluidValues.METAL_BLOCK + (FluidValues.INGOT * 2), "iron", listedInput("necklace_of_the_narrow_edge"), metalFolder("melting"), "necklace_of_the_narrow_edge");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", listedInput("tuning_fork", "lamplighters_tongs"), metalFolder("melting"), "magic_tools");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.METAL_BLOCK, "iron", listedInput("anomalous_design", "complete_design"), metalFolder("melting"), "design");


        simpleMelting(cConsumer, TCFluids.runicSap, 500, "sap/runic", listedInput("runic_sapball"), miscFolder("melting"), "ball");
        simpleMelting(cConsumer, TCFluids.runicSap, 125, "sap/runic", listedInput("runic_sap_block"), miscFolder("melting"), "block");
        simpleMelting(cConsumer, TCFluids.cursedSap, 500, "sap/cursed", listedInput("cursed_sapball"), miscFolder("melting"), "ball");
        simpleMelting(cConsumer, TCFluids.cursedSap, 125, "sap/cursed", listedInput("cursed_sap_block"), miscFolder("melting"), "block");

        // Byproducts
        simpleMelting(TCFluids.moltenHallowedGold, FluidValues.INGOT, listedInput("spirit_jar"))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 7))
                .save(cConsumer, location(metalFolder("melting") + "/hallowed_gold/spirit_jar"));
        simpleMelting(TCFluids.moltenHallowedGold, FluidValues.INGOT, listedInput("spectral_optic"))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE / 2))
                .save(cConsumer, location(metalFolder("melting") + "/hallowed_gold/spectral_optic"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT * 6, listedInput("ring_of_the_hoarder"))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL * 2))
                .save(cConsumer, location(metalFolder("melting") + "/iron/ring_of_the_hoarder"));
        simpleMelting(TCFluids.moltenHallowedGold, FluidValues.INGOT, listedInput("necklace_of_the_mystic_mirror"))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE / 2))
                .save(cConsumer, location(metalFolder("melting") + "/hallowed_gold/necklace_of_the_mystic_mirror"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM * 2, listedInput("necklace_of_blissful_harmony"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT * 6))
                .save(cConsumer, location(gemFolder("melting") + "/diamond/necklace_of_blissful_harmony"));
        simpleMelting(TinkerFluids.moltenGold, FluidValues.INGOT * 4, listedInput("belt_of_the_prospector"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT * 4))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 4))
                .save(cConsumer, location(metalFolder("melting") + "/gold/belt_of_the_prospector"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("runic_sap")))
                .setFluidAndTime(TCFluids.runicSap, FluidValues.BOTTLE)
                .setCast(Items.GLASS_BOTTLE, true).setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/sap/runic/bottle"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("runic_sapball")))
                .setFluidAndTime(TCFluids.runicSap, 500).setCoolingTime(2)
                .save(cConsumer, location(miscFolder("casting") + "/sap/runic/ball"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("runic_sap_block")))
                .setFluidAndTime(TCFluids.runicSap, 125).setCoolingTime(4)
                .save(cConsumer, location(miscFolder("casting") + "/sap/runic/block"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("cursed_sap")))
                .setFluidAndTime(TCFluids.cursedSap, FluidValues.BOTTLE)
                .setCast(Items.GLASS_BOTTLE, true).setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/sap/cursed/bottle"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("cursed_sapball")))
                .setFluidAndTime(TCFluids.cursedSap, 500).setCoolingTime(2)
                .save(cConsumer, location(miscFolder("casting") + "/sap/cursed/ball"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("cursed_sap_block")))
                .setFluidAndTime(TCFluids.cursedSap, 125).setCoolingTime(4)
                .save(cConsumer, location(miscFolder("casting") + "/sap/cursed/block"));
    }

    @Override
    public String compatModId() {
        return "malum";
    }
}
