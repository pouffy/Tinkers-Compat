package io.github.pouffy.tcompat.compat.create;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.TrueCondition;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

        MeltingRecipeBuilder.melting(listedInput("bar_of_chocolate"), FluidOutput.fromTag(TCTags.Fluids.common("chocolate"), 250), 150, IMeltingRecipe.calcTimeFactor(250))
                .save(cConsumer, location(miscFolder("melting") + "/chocolate/bar"));

        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT, listedInput("crushed_raw_iron"))
                .addByproduct(Byproduct.STEEL.getFluid(1))
                .setOre(IMeltingContainer.OreRateType.METAL, Byproduct.STEEL.getOreRate())
                .save(cConsumer, location(metalFolder("melting") + "/iron/crushed_raw_ore"));
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT, listedInput("crushed_raw_copper"))
                .addByproduct(Byproduct.SMALL_GOLD.getFluid(1))
                .setOre(IMeltingContainer.OreRateType.METAL, Byproduct.SMALL_GOLD.getOreRate())
                .save(cConsumer, location(metalFolder("melting") + "/copper/crushed_raw_ore"));
        simpleMelting(TinkerFluids.moltenGold, FluidValues.INGOT, listedInput("crushed_raw_gold"))
                .addByproduct(Byproduct.COBALT.getFluid(1))
                .setOre(IMeltingContainer.OreRateType.METAL, Byproduct.COBALT.getOreRate())
                .save(cConsumer, location(metalFolder("melting") + "/gold/crushed_raw_ore"));
        Supplier<MeltingRecipeBuilder> crushedZinc = () -> simpleMelting(TinkerFluids.moltenZinc, FluidValues.INGOT, listedInput("crushed_raw_zinc"));
        ConditionalRecipe.builder()
                .addCondition(TrueCondition.INSTANCE)
                .addRecipe(crushedZinc.get().addByproduct(Byproduct.COPPER.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.COPPER.getOreRate())::save)
                .addCondition(tagCondition("ingots/" + Byproduct.TIN.getName()))
                .addRecipe(crushedZinc.get().addByproduct(Byproduct.TIN.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.TIN.getOreRate())::save)
                .build(cConsumer, location(metalFolder("melting") + "/zinc/crushed_raw_ore"));
        simpleMelting(TinkerFluids.moltenOsmium, FluidValues.INGOT, listedInput("crushed_raw_osmium"))
                .addByproduct(Byproduct.IRON.getFluid(1))
                .setOre(IMeltingContainer.OreRateType.METAL, Byproduct.IRON.getOreRate())
                .save(withCondition(cConsumer, tagCondition("ingots/osmium")), location(metalFolder("melting") + "/osmium/crushed_raw_ore"));
        simpleMelting(TinkerFluids.moltenPlatinum, FluidValues.INGOT, listedInput("crushed_raw_platinum"))
                .addByproduct(Byproduct.GOLD.getFluid(1))
                .setOre(IMeltingContainer.OreRateType.METAL, Byproduct.GOLD.getOreRate())
                .save(withCondition(cConsumer, tagCondition("ingots/platinum")), location(metalFolder("melting") + "/platinum/crushed_raw_ore"));
        Supplier<MeltingRecipeBuilder> crushedSilver = () -> simpleMelting(TinkerFluids.moltenSilver, FluidValues.INGOT, listedInput("crushed_raw_silver"));
        ConditionalRecipe.builder()
                .addCondition(TrueCondition.INSTANCE)
                .addRecipe(crushedSilver.get().addByproduct(Byproduct.GOLD.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.GOLD.getOreRate())::save)
                .addCondition(tagCondition("ingots/" + Byproduct.LEAD.getName()))
                .addRecipe(crushedSilver.get().addByproduct(Byproduct.LEAD.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.LEAD.getOreRate())::save)
                .build(withCondition(cConsumer, tagCondition("ingots/silver")), location(metalFolder("melting") + "/silver/crushed_raw_ore"));
        Supplier<MeltingRecipeBuilder> crushedTin = () -> simpleMelting(TinkerFluids.moltenTin, FluidValues.INGOT, listedInput("crushed_raw_tin"));
        ConditionalRecipe.builder()
                .addCondition(TrueCondition.INSTANCE)
                .addRecipe(crushedTin.get().addByproduct(Byproduct.COPPER.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.COPPER.getOreRate())::save)
                .addCondition(tagCondition("ingots/" + Byproduct.NICKEL.getName()))
                .addRecipe(crushedTin.get().addByproduct(Byproduct.NICKEL.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.NICKEL.getOreRate())::save)
                .build(withCondition(cConsumer, tagCondition("ingots/tin")), location(metalFolder("melting") + "/tin/crushed_raw_ore"));
        Supplier<MeltingRecipeBuilder> crushedLead = () -> simpleMelting(TinkerFluids.moltenLead, FluidValues.INGOT, listedInput("crushed_raw_lead"));
        ConditionalRecipe.builder()
                .addCondition(TrueCondition.INSTANCE)
                .addRecipe(crushedLead.get().addByproduct(Byproduct.GOLD.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.GOLD.getOreRate())::save)
                .addCondition(tagCondition("ingots/" + Byproduct.SILVER.getName()))
                .addRecipe(crushedLead.get().addByproduct(Byproduct.SILVER.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.SILVER.getOreRate())::save)
                .build(withCondition(cConsumer, tagCondition("ingots/lead")), location(metalFolder("melting") + "/lead/crushed_raw_ore"));
        simpleMelting(TinkerFluids.moltenAluminum, FluidValues.INGOT, listedInput("crushed_raw_aluminum"))
                .addByproduct(Byproduct.IRON.getFluid(1))
                .setOre(IMeltingContainer.OreRateType.METAL, Byproduct.IRON.getOreRate())
                .save(withCondition(cConsumer, tagCondition("ingots/aluminum")), location(metalFolder("melting") + "/aluminum/crushed_raw_ore"));
        Supplier<MeltingRecipeBuilder> crushedUranium = () -> simpleMelting(TinkerFluids.moltenUranium, FluidValues.INGOT, listedInput("crushed_raw_uranium"));
        ConditionalRecipe.builder()
                .addCondition(TrueCondition.INSTANCE)
                .addRecipe(crushedUranium.get().addByproduct(Byproduct.COPPER.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.COPPER.getOreRate())::save)
                .addCondition(tagCondition("ingots/" + Byproduct.LEAD.getName()))
                .addRecipe(crushedUranium.get().addByproduct(Byproduct.LEAD.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.LEAD.getOreRate())::save)
                .build(withCondition(cConsumer, tagCondition("ingots/uranium")), location(metalFolder("melting") + "/uranium/crushed_raw_ore"));
        Supplier<MeltingRecipeBuilder> crushedNickel = () -> simpleMelting(TinkerFluids.moltenNickel, FluidValues.INGOT, listedInput("crushed_raw_nickel"));
        ConditionalRecipe.builder()
                .addCondition(TrueCondition.INSTANCE)
                .addRecipe(crushedNickel.get().addByproduct(Byproduct.IRON.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.IRON.getOreRate())::save)
                .addCondition(tagCondition("ingots/" + Byproduct.PLATINUM.getName()))
                .addRecipe(crushedNickel.get().addByproduct(Byproduct.PLATINUM.getFluid(1)).setOre(IMeltingContainer.OreRateType.METAL, Byproduct.PLATINUM.getOreRate())::save)
                .build(withCondition(cConsumer, tagCondition("ingots/nickel")), location(metalFolder("melting") + "/nickel/crushed_raw_ore"));
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

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.NUGGET * 2, "iron", listedInput("filter"), metalFolder("melting"), "filter");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.NUGGET * 2, "brass", listedInput("attribute_filter"), metalFolder("melting"), "attribute_filter");
        simpleMelting(cConsumer, TinkerFluids.moltenZinc, FluidValues.NUGGET * 2, "zinc", listedInput("package_filter"), metalFolder("melting"), "package_filter");

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", listedInput("minecart_coupling"), metalFolder("melting"), "minecart_coupling");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT + FluidValues.NUGGET, "iron", listedInput("super_glue"), metalFolder("melting"), "super_glue", new int[]{FluidValues.NUGGET});

        simpleMelting(TinkerFluids.moltenGold, FluidValues.INGOT, listedInput("precision_mechanism"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .save(cConsumer, location(metalFolder("melting") + "/gold/precision_mechanism"));
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 6, "copper", listedInput("transmitter"), metalFolder("melting"), "transmitter");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 3, "copper", listedInput("redstone_link"), metalFolder("melting"), "redstone_link");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.NUGGET * 3, "brass", listedInput("crafter_slot_cover"), metalFolder("melting"), "crafter_slot_cover");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT * 4, "brass", listedInput("brass_hand"), metalFolder("melting"), "brass_hand");

        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT, "brass", listedInput("placard", "pulse_repeater", "pulse_extender"), metalFolder("melting"), "redstone_appliances");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("pulse_timer"))
                .addByproduct(TinkerFluids.moltenAmethyst.result(FluidValues.GEM))
                .save(cConsumer, location(metalFolder("melting") + "/brass/pulse_timer"));

        simpleMelting(cConsumer, TinkerFluids.moltenZinc, FluidValues.INGOT, "zinc", listedInput("rose_quartz_lamp"), metalFolder("melting"), "rose_quartz_lamp");

        simpleMelting(cConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_PANE, "obsidian", listedInput("sturdy_sheet"), miscFolder("melting"), "sturdy_sheet");
    }

    private void kinetics(Consumer<FinishedRecipe> cConsumer) {
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT, "brass", listedInput("brass_casing", "brass_door"), metalFolder("melting"), "casing");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.METAL_BLOCK * 2, "iron", listedInput("schematicannon"), metalFolder("melting"), "schematicannon");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.NUGGET * 3, "iron", listedInput("encased_chain_drive"), metalFolder("melting"), "encased_chain_drive");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.NUGGET * 12, "iron", listedInput("adjustable_chain_gearshift"), metalFolder("melting"), "adjustable_chain_gearshift");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", listedInput("encased_fan", "propeller", "mechanical_saw"), metalFolder("melting"), "encased_fan");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", listedInput("cuckoo_clock", "mysterious_cuckoo_clock"), metalFolder("melting"), "cuckoo_clock");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.METAL_BLOCK, "iron", listedInput("mechanical_press"), metalFolder("melting"), "mechanical_press");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 5, "iron", listedInput("mechanical_mixer", "whisk"), metalFolder("melting"), "mechanical_mixer");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", listedInput("empty_blaze_burner"), metalFolder("melting"), "empty_blaze_burner");
        simpleMelting(TinkerFluids.blazingBlood, 100, listedInput("blaze_burner"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT * 4))
                .save(cConsumer, location(miscFolder("melting") + "/blazing_blood/blaze_burner"));
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", listedInput("weighted_ejector"), metalFolder("melting"), "weighted_ejector");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, 65, "iron", listedInput("chute", "portable_storage_interface"), metalFolder("melting"), "chute");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("smart_chute"))
                .addByproduct(TinkerFluids.moltenIron.result(155))
                .save(cConsumer, location(metalFolder("melting") + "/brass/smart_chute"));
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", listedInput("speedometer", "stressometer"), metalFolder("melting"), "ometer");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, 50, "iron", listedInput("metal_bracket"), metalFolder("melting"), "metal_bracket");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("clockwork_bearing", "elevator_pulley"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/brass/clockwork_bearing"));
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", listedInput("rope_pulley"), metalFolder("melting"), "rope_pulley");
        simpleMelting(TinkerFluids.moltenGold, FluidValues.INGOT, listedInput("controller_rail"))
                .addByproduct(TinkerFluids.moltenIron.result(15))
                .save(cConsumer, location(metalFolder("melting") + "/gold/controller_rail"));
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", listedInput("contraption_controls", "mechanical_roller", "electron_tube", "mechanical_drill"), metalFolder("melting"), "electron_tube");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT * 4, listedInput("deployer"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/brass/deployer"));
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT / 2, "iron", listedInput("redstone_contact", "display_board", "nixie_tube"), metalFolder("melting"), "redstone_contact");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", listedInput("mechanical_harvester"), metalFolder("melting"), "mechanical_harvester");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 3, "iron", listedInput("mechanical_plough"), metalFolder("melting"), "mechanical_plough");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT / 3, listedInput("mechanical_crafter"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT / 3))
                .save(cConsumer, location(metalFolder("melting") + "/brass/mechanical_crafter"));
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("sequenced_gearshift"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/brass/sequenced_gearshift"));
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT * 8, "brass", listedInput("flywheel"), metalFolder("melting"), "flywheel");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("rotation_speed_controller"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .save(cConsumer, location(metalFolder("melting") + "/brass/rotation_speed_controller"));
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT * 4, listedInput("mechanical_arm"))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .save(cConsumer, location(metalFolder("melting") + "/brass/mechanical_arm"));
        simpleMelting(cConsumer, TinkerFluids.moltenZinc, FluidValues.NUGGET * 2, "zinc", listedInput("track"), metalFolder("melting"), "track");
        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_PANE, listedInput("railway_casing", "train_door", "train_trapdoor"))
                .addByproduct(TinkerFluids.moltenBrass.result(FluidValues.INGOT))
                .save(cConsumer, location(miscFolder("melting") + "/obsidian/railway_casing"));
        simpleMelting(cConsumer, TinkerFluids.moltenObsidian, 60, "obsidian", listedInput("schedule"), miscFolder("melting"), "schedule");

        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_PANE / 2, listedInput("track_station"))
                .addByproduct(TinkerFluids.moltenBrass.result(FluidValues.INGOT / 2))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT * 2))
                .save(cConsumer, location(miscFolder("melting") + "/obsidian/track_station"));
        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_PANE / 2, listedInput("track_signal"))
                .addByproduct(TinkerFluids.moltenBrass.result(FluidValues.INGOT / 2))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT / 2))
                .save(cConsumer, location(miscFolder("melting") + "/obsidian/track_signal"));
        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_PANE / 2, listedInput("track_observer"))
                .addByproduct(TinkerFluids.moltenBrass.result(FluidValues.INGOT / 2))
                .save(cConsumer, location(miscFolder("melting") + "/obsidian/track_observer"));
        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_PANE, listedInput("controls"))
                .addByproduct(TinkerFluids.moltenBrass.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 5))
                .save(cConsumer, location(miscFolder("melting") + "/obsidian/controls"));

        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("content_observer", "stockpile_switch"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/brass/storage_observers"));

        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT / 2, "brass", listedInput("brass_funnel"), metalFolder("melting"), "funnel");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT, "brass", listedInput("brass_tunnel"), metalFolder("melting"), "tunnel");

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", listedInput("item_vault", "package_frogport"), metalFolder("melting"), "item_vault");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", listedInput("item_hatch", "packager", "repackager"), metalFolder("melting"), "item_hatch");

        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("display_link"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 6))
                .save(cConsumer, location(metalFolder("melting") + "/brass/display_link"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT * 2, listedInput("stock_link"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 6))
                .save(cConsumer, location(metalFolder("melting") + "/iron/stock_link"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT * 2, listedInput("stock_ticker"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 6))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK))
                .save(cConsumer, location(metalFolder("melting") + "/iron/stock_ticker"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT * 3, listedInput("redstone_requester"))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 6))
                .save(cConsumer, location(metalFolder("melting") + "/iron/redstone_requester"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT, listedInput("factory_gauge"))
                .addByproduct(TinkerFluids.moltenCopper.result(135))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT / 2))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 2))
                .save(cConsumer, location(metalFolder("melting") + "/iron/factory_gauge"));
    }

    private void fluids(Consumer<FinishedRecipe> cConsumer) {
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", listedInput("copper_casing", "copper_door"), metalFolder("melting"), "casing");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 65, "copper", listedInput("fluid_pipe", "mechanical_pump"), metalFolder("melting"), "fluid_pipes");
        simpleMelting(TinkerFluids.moltenBrass, FluidValues.INGOT, listedInput("smart_fluid_pipe"))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .addByproduct(TinkerFluids.moltenCopper.result(65))
                .save(cConsumer, location(metalFolder("melting") + "/brass/smart_fluid_pipe"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT, listedInput("fluid_valve"))
                .addByproduct(TinkerFluids.moltenCopper.result(65))
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
                .addByproduct(TinkerFluids.moltenIron.result(65))
                .save(cConsumer, location(metalFolder("melting") + "/copper/portable_fluid_interface"));
    }

    private void decorations(Consumer<FinishedRecipe> cConsumer) {
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", oxidised("copper_shingles"), metalFolder("melting"), "shingles");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 20, "copper", oxidised("copper_shingle_slab"), metalFolder("melting"), "shingle_slab");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", oxidised("copper_shingle_stairs"), metalFolder("melting"), "shingle_stairs");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", oxidised("copper_tiles"), metalFolder("melting"), "tiles");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 20, "copper", oxidised("copper_tile_slab"), metalFolder("melting"), "tile_slab");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", oxidised("copper_tile_stairs"), metalFolder("melting"), "tile_stairs");

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT / 2, "iron", listedInput("industrial_iron_block", "weathered_iron_block"), metalFolder("melting"), "industrial_block");
        simpleMelting(cConsumer, TinkerFluids.moltenGlass, FluidValues.GLASS_BLOCK, "glass", listedInput(
                "framed_glass_door", "framed_glass_trapdoor", "tiled_glass", "framed_glass", "horizontal_framed_glass", "vertical_framed_glass"
        ), miscFolder("melting"), "glass_variants");
        simpleMelting(cConsumer, TinkerFluids.moltenGlass, FluidValues.GLASS_PANE, "glass", listedInput(
                "tiled_glass_pane", "framed_glass_pane", "horizontal_framed_glass_pane", "vertical_framed_glass_pane"
        ), miscFolder("melting"), "glass_pane_variants");

        simpleMelting(TinkerFluids.moltenGlass, FluidValues.GLASS_BLOCK / 2, listedInput("ornate_iron_window"))
                .addByproduct(TinkerFluids.moltenIron.result(15))
                .save(cConsumer, location(metalFolder("melting") + "/iron/ornate_iron_window"));
        simpleMelting(TinkerFluids.moltenGlass, FluidValues.GLASS_BLOCK / 2, listedInput("industrial_iron_window", "weathered_iron_window"))
                .addByproduct(TinkerFluids.moltenIron.result(65))
                .save(cConsumer, location(metalFolder("melting") + "/iron/industrial_iron_window"));
        simpleMelting(TinkerFluids.moltenGlass, 50, listedInput("ornate_iron_window_pane"))
                .addByproduct(TinkerFluids.moltenIron.result(5))
                .save(cConsumer, location(metalFolder("melting") + "/iron/ornate_iron_window_pane"));
        simpleMelting(TinkerFluids.moltenGlass, 50, listedInput("industrial_iron_window_pane", "weathered_iron_window_pane"))
                .addByproduct(TinkerFluids.moltenIron.result(20))
                .save(cConsumer, location(metalFolder("melting") + "/iron/industrial_iron_window_pane"));

        simpleMelting(cConsumer, TinkerFluids.moltenZinc, 20, "zinc", listedInput("copycat_step", "copycat_panel"), metalFolder("melting"), "copycat");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, 30, "iron", listedInput("metal_girder"), metalFolder("melting"), "metal_girder");

        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", listedInput("copper_ladder", "copper_scaffolding", "copper_table_cloth"), metalFolder("melting"), "scaffold");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT / 2, "brass", listedInput("brass_ladder", "brass_scaffolding", "brass_table_cloth"), metalFolder("melting"), "scaffold");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, 20, "copper", listedInput("copper_bars"), metalFolder("melting"), "bars");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, 20, "brass", listedInput("brass_bars"), metalFolder("melting"), "bars");

        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", listedInput("desk_bell"), metalFolder("melting"), "desk_bell");
        simpleMelting(cConsumer, TinkerFluids.moltenBrass, FluidValues.INGOT * 10, "brass", listedInput("peculiar_bell", "haunted_bell"), metalFolder("melting"), "bell");
    }

    private Ingredient oxidised(String id) {
        List<ResourceLocation> list = new ArrayList<>();
        ResourceLocation primaryId = compatId(id);
        list.add(primaryId);
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
        return "create";
    }
}
