package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.SmelteryRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCSmelteryRecipeProv extends TCBaseRecipeProvider implements ITCSmelteryRecipeHelper, ICommonRecipeHelper {
    public TCSmelteryRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Smeltery Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";
        aether(consumer, folder);
        deepAether(consumer, folder);
        aetherRedux(consumer, folder);
        aetherTreasure(consumer, folder);
        species(consumer, folder);
    }

    private void aether(Consumer<FinishedRecipe> consumer, String folder) {
        String aether = "aether";
        Function<String, ResourceLocation> aetherId = name -> getResource(aether, name);
        Function<String,TagKey<Item>> aetherTag = name -> TagKey.create(Registries.ITEM, getResource(aether, name));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, new ModLoadedCondition(aether));

        Function<String, String> aetherFolderFunction = name -> name.formatted(aether);
        Function<String, String> metalFolder = type -> aetherFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> aetherFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> aetherFolderFunction.apply("smeltery/" + type + "/misc/%s/");
        //Generic Melting
        gem(aetherConsumer, AetherInit.moltenZanite, aether)
                .baseUnit(FluidValues.INGOT).damageUnit(FluidValues.NUGGET).oreRate(IMeltingContainer.OreRateType.METAL)
                .sparseOre(0.5f).singularOre(1).denseOre(3).ore(TCByproduct.QUARTZ)
                .melting(9, "block", "storage_blocks", (float)Math.sqrt(9), false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true);

        metal(aetherConsumer, AetherInit.moltenGravitite, aether).ore(TCByproduct.ZANITE).metal(true).optional();

        simpleMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.INGOT, "zanite", ItemNameIngredient.from(aetherId.apply("altar")), gemFolder.apply("melting"), "altar");

        //Gloves
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, (FluidValues.NUGGET * 6) * 2, "chainmail", ItemNameIngredient.from(aetherId.apply("chainmail_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET}, TinkerFluids.moltenSteel.result((FluidValues.NUGGET * 3) * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(aetherId.apply("iron_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(aetherId.apply("golden_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, "diamond", ItemNameIngredient.from(aetherId.apply("diamond_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(aetherId.apply("netherite_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, "obsidian", ItemNameIngredient.from(aetherId.apply("obsidian_gloves")), miscFolder.apply("melting"), true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.INGOT * 2, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, AetherInit.moltenGravitite, FluidValues.INGOT * 2, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        //Rings
        ringMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(aetherId.apply("iron_ring")), metalFolder.apply("melting"), true);
        ringMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(aetherId.apply("golden_ring")), metalFolder.apply("melting"), true);
        ringMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.INGOT * 4, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_ring")), gemFolder.apply("melting"), true);
        //Pendant
        pendantMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(aetherId.apply("iron_pendant")), metalFolder.apply("melting"), true);
        pendantMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", ItemNameIngredient.from(aetherId.apply("golden_pendant")), metalFolder.apply("melting"), true);
        pendantMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.INGOT, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_pendant")), gemFolder.apply("melting"), true);
        //Salvaging
        salvageAll(aetherConsumer, aetherId, AetherInit.moltenZanite, FluidValues.INGOT, "zanite", new int[]{FluidValues.NUGGET}, gemFolder.apply("melting"));
        salvageAll(aetherConsumer, aetherId, AetherInit.moltenGravitite, FluidValues.INGOT, "gravitite", new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherConsumer, aetherId, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK, "obsidian", new int[]{FluidValues.GLASS_PANE}, miscFolder.apply("melting"));
    }

    private void deepAether(Consumer<FinishedRecipe> consumer, String folder) {
        String deepAether = "deep_aether";
        Function<String, ResourceLocation> deepAetherId = name -> getResource(deepAether, name);
        Function<String, TagKey<Item>> deepAetherTag = name -> TagKey.create(Registries.ITEM, getResource(deepAether, name));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, new ModLoadedCondition(deepAether));

        Function<String, String> deepAetherFolderFunction = name -> name.formatted(deepAether);
        Function<String, String> metalFolder = type -> deepAetherFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> deepAetherFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> deepAetherFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        //Generic Melting
        gem(deepAetherConsumer, DeepAetherInit.moltenSkyjade, deepAether)
                .baseUnit(FluidValues.INGOT).damageUnit(FluidValues.NUGGET).oreRate(IMeltingContainer.OreRateType.METAL)
                .sparseOre(0.5f).singularOre(1).denseOre(3).ore(TCByproduct.QUARTZ)
                .melting(9, "block", "storage_blocks", (float)Math.sqrt(9), false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .meltingCasting(1 / 9f, TinkerSmeltery.nuggetCast, 1 / 3f, true);

        metal(deepAetherConsumer, DeepAetherInit.moltenStratus, deepAether).metal(true).optional();

        simpleMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.NUGGET * 8, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_lantern")), gemFolder.apply("melting"), "skyjade_lantern");
        simpleMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.NUGGET * 2, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_chain")), gemFolder.apply("melting"), "skyjade_chain");
        simpleMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.INGOT * 3, "skyjade", ItemNameIngredient.from(deepAetherId.apply("combiner")), gemFolder.apply("melting"), "combiner");

        simpleMelting(deepAetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 4, "obsidian", ItemNameIngredient.from(deepAetherId.apply("afterburner")), miscFolder.apply("melting"), "afterburner");
        simpleMelting(deepAetherConsumer, DeepAetherInit.moltenStratus, FluidValues.INGOT * 2, "stratus", ItemNameIngredient.from(deepAetherId.apply("aerwhale_saddle")), metalFolder.apply("melting"), "aerwhale_saddle");
        simpleMelting(deepAetherConsumer, AetherInit.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(deepAetherId.apply("gravitite_ring")), metalFolder.apply("melting"), "aether_ring");
        //Gloves
        glovesMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.INGOT * 2, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(deepAetherConsumer, DeepAetherInit.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        //Rings
        ringMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.INGOT * 4, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_ring")), gemFolder.apply("melting"), true);
        ringMelting(deepAetherConsumer, DeepAetherInit.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_ring")), metalFolder.apply("melting"), true, AetherInit.moltenGravitite.result(FluidValues.INGOT * 4));
        //Salvaging
        salvageAll(deepAetherConsumer, deepAetherId, DeepAetherInit.moltenSkyjade, FluidValues.INGOT, "skyjade", new int[]{FluidValues.GEM_SHARD}, gemFolder.apply("melting"));
        salvageAll(deepAetherConsumer, deepAetherId, DeepAetherInit.moltenStratus, AetherInit.moltenGravitite, FluidValues.INGOT, "stratus", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
    }

    private void aetherRedux(Consumer<FinishedRecipe> consumer, String folder) {
        String aetherRedux = "aether_redux";
        Function<String, ResourceLocation> aetherReduxId = name -> getResource(aetherRedux, name);
        Function<String, TagKey<Item>> aetherReduxTag = name -> TagKey.create(Registries.ITEM, getResource(aetherRedux, name));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, new ModLoadedCondition(aetherRedux));

        Function<String, String> aetherReduxFolderFunction = name -> name.formatted(aetherRedux);
        Function<String, String> metalFolder = type -> aetherReduxFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> aetherReduxFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> aetherReduxFolderFunction.apply("smeltery/" + type + "/misc/%s/");
        //Generic Melting
        metal(aetherReduxConsumer, AetherReduxInit.moltenVeridium, aetherRedux).metal(true).ore(TCByproduct.GRAVITITE).rawOre().optional();
        metal(aetherReduxConsumer, AetherReduxInit.moltenRefinedSentrite, aetherRedux).metal(true).ore(TCByproduct.VERIDIUM).optional();
        //Rings
        simpleMelting(aetherReduxConsumer, DeepAetherInit.moltenSkyjade, FluidValues.INGOT, "skyjade", ItemNameIngredient.from(aetherReduxId.apply("ring_of_wisdom")), gemFolder.apply("melting"), "ring_of_wisdom");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenRefinedSentrite, FluidValues.INGOT * 7, "refined_sentrite", ItemNameIngredient.from(aetherReduxId.apply("sentry_ring")), metalFolder.apply("melting"), "aether_ring");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenRefinedSentrite, FluidValues.NUGGET * 8, "refined_sentrite", ItemNameIngredient.from(aetherReduxId.apply("sentrite_lantern")), metalFolder.apply("melting"), "lantern");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenRefinedSentrite, FluidValues.INGOT, "refined_sentrite", Ingredient.of(TCTags.Items.SENTRITE_STONE_BLOCKS), metalFolder.apply("melting"), "stone_blocks");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenRefinedSentrite, FluidValues.INGOT / 2, "refined_sentrite", Ingredient.of(TCTags.Items.SENTRITE_STONE_SLABS), metalFolder.apply("melting"), "stone_slabs");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenVeridium, (FluidValues.INGOT * 2) + (FluidValues.NUGGET * 6), "veridium", ItemNameIngredient.from(aetherReduxId.apply("veridium_lantern")), metalFolder.apply("melting"), "lantern");

        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium", ItemNameIngredient.from(aetherReduxId.apply("veridium_dart_shooter")), metalFolder.apply("melting"), "dart_shooter");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenVeridium, FluidValues.METAL_BLOCK * 4, "veridium", ItemNameIngredient.from(aetherReduxId.apply("feather_of_warding")), metalFolder.apply("melting"), "feather_of_warding");
        simpleMelting(aetherReduxConsumer, AetherInit.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(aetherReduxId.apply("snailshell_shield")), metalFolder.apply("melting"), "snailshell_shield");

        //Salvaging
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        // infused veridium is still veridium
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, AetherReduxInit.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
    }

    private void aetherTreasure(Consumer<FinishedRecipe> consumer, String folder) {
        String aetherTreasure = "aether_treasure_reforging";
        Function<String, ResourceLocation> aetherId = name -> getResource("aether", name);
        Function<String, ResourceLocation> aetherReduxId = name -> getResource("aether_redux", name);
        Function<String, ResourceLocation> aetherTreasureId = name -> getResource(aetherTreasure, name);
        Function<String, TagKey<Item>> aetherTreasureTag = name -> TagKey.create(Registries.ITEM, getResource(aetherTreasure, name));
        Consumer<FinishedRecipe> aetherTreasureConsumer = withCondition(consumer, new ModLoadedCondition(aetherTreasure));

        Function<String, String> aetherTreasureFolderFunction = name -> name.formatted(aetherTreasure);
        Function<String, String> metalFolder = type -> aetherTreasureFolderFunction.apply("smeltery/" + type + "/metal/%s/");

        metal(aetherTreasureConsumer, AetherTRInit.moltenValkyrum, aetherTreasure).metal(true).optional();
        metal(aetherTreasureConsumer, AetherTRInit.moltenPyral, aetherTreasure).metal(true).optional();
        //Misc Items
        simpleMelting(withCondition(aetherTreasureConsumer, new ModLoadedCondition("aether_redux")), AetherTRInit.moltenValkyrum, FluidValues.INGOT, "valkyrie", ItemNameIngredient.from(aetherReduxId.apply("grand_victory_medal")), metalFolder.apply("melting"), "grand_victory_medal");
        simpleMelting(aetherTreasureConsumer, AetherTRInit.moltenValkyrum, FluidValues.INGOT * 3, "valkyrie", ItemNameIngredient.from(aetherId.apply("valkyrie_cape")), metalFolder.apply("melting"), "valkyrie_cape");
        simpleMelting(aetherTreasureConsumer, AetherTRInit.moltenNeptune, FluidValues.INGOT, "neptune", ItemNameIngredient.from(aetherTreasureId.apply("neptune_mesh")), metalFolder.apply("melting"), "neptune_mesh");
        simpleMelting(aetherTreasureConsumer, AetherTRInit.moltenPyral, FluidValues.INGOT * 3, "phoenix", ItemNameIngredient.from(aetherId.apply("phoenix_bow")), metalFolder.apply("melting"), "phoenix_bow");
        //Gloves
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenValkyrum, FluidValues.INGOT, "valkyrie", ItemNameIngredient.from(aetherId.apply("valkyrie_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenPyral, FluidValues.INGOT, "phoenix", ItemNameIngredient.from(aetherId.apply("phoenix_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenNeptune, FluidValues.INGOT, "neptune", ItemNameIngredient.from(aetherId.apply("neptune_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenZanite.result(FluidValues.INGOT * 2));
        //Salvaging
        salvageAll(aetherTreasureConsumer, aetherId, AetherTRInit.moltenValkyrum, AetherInit.moltenGravitite, FluidValues.INGOT, "valkyrie", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherTreasureConsumer, aetherId, AetherTRInit.moltenPyral, AetherInit.moltenGravitite, FluidValues.INGOT, "phoenix", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherTreasureConsumer, aetherId, AetherTRInit.moltenNeptune, AetherInit.moltenZanite, FluidValues.INGOT, "neptune", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));

        for (String type : new String[]{"valkyrie", "neptune", "phoenix"}) {
            String template = type + "_upgrade_smithing_template";
            simpleMelting(aetherTreasureConsumer, AetherInit.moltenGravitite, FluidValues.INGOT * 7, type, ItemNameIngredient.from(aetherTreasureId.apply(template)), metalFolder.apply("melting"), template);
        }
    }

    private void species(Consumer<FinishedRecipe> consumer, String folder) {
        String species = "species";
        Function<String, ResourceLocation> speciesId = name -> getResource(species, name);
        Function<String, TagKey<Item>> speciesTag = name -> TagKey.create(Registries.ITEM, getResource(species, name));
        Consumer<FinishedRecipe> speciesConsumer = withCondition(consumer, new ModLoadedCondition(species));

        Function<String, String> speciesFolderFunction = name -> name.formatted(species);
        Function<String, String> metalFolder = type -> speciesFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> speciesFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> speciesFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        int brokenLinks = 55;

        simpleMelting(speciesConsumer, TinkerFluids.moltenIron, brokenLinks, "iron", ItemNameIngredient.from(speciesId.apply("broken_links")), metalFolder.apply("melting"), "broken_links");
        simpleMelting(speciesConsumer, TinkerFluids.moltenIron, 110 + (brokenLinks * 3), "iron", ItemNameIngredient.from(speciesId.apply("chaindelier")), metalFolder.apply("melting"), "chaindelier");
        simpleMelting(speciesConsumer, TinkerFluids.moltenIron, 135, "iron", ItemNameIngredient.from(speciesId.apply("cranktrap")), metalFolder.apply("melting"), "cranktrap");
        simpleMelting(speciesConsumer, TinkerFluids.moltenIron, (FluidValues.INGOT * 5) + FluidValues.NUGGET * 4, "iron", ItemNameIngredient.from(speciesId.apply("crankbow")), metalFolder.apply("melting"), "crankbow");
        simpleMelting(speciesConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(speciesId.apply("birtday_cake")), metalFolder.apply("melting"), "birtday_cake");
        simpleMelting(TinkerFluids.moltenIron, brokenLinks * 2, ItemNameIngredient.from(speciesId.apply("hopelight")))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 2))
                .save(speciesConsumer, location(metalFolder.apply("melting") + "/iron/hopelight"));
        simpleMelting(TinkerFluids.moltenIron, FluidValues.INGOT + (brokenLinks * 5), ItemNameIngredient.from(speciesId.apply("ricoshield")))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT))
                .save(speciesConsumer, location(metalFolder.apply("melting") + "/iron/ricoshield"));
        simpleMelting(speciesConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", ItemNameIngredient.from(speciesId.apply("kinetic_core")), metalFolder.apply("melting"), "kinetic_core");
        simpleMelting(speciesConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", ItemNameIngredient.from(speciesId.apply("quake_head")), metalFolder.apply("melting"), "quake_head");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT * 2, ItemNameIngredient.from(speciesId.apply("harpoon")))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 4))
                .save(speciesConsumer, location(metalFolder.apply("melting") + "/copper/harpoon"));
        simpleMelting(speciesConsumer, TinkerFluids.moltenEnder, FluidValues.SLIMEBALL, "ender", ItemNameIngredient.from(speciesId.apply("wicked_swapper")), miscFolder.apply("melting"), "wicked_swapper");
    }

    public TCSmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal/" + modId).meltingFolder("smeltery/melting/metal/" + modId);
    }

    public TCSmelteryRecipeBuilder gem(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/gem/" + modId).meltingFolder("smeltery/melting/gem/" + modId);
    }
}
