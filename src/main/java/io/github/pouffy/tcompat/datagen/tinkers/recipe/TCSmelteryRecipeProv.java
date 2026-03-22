package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCMeltingInfo;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
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
        adAstra(consumer, folder);
        betterEnd(consumer, folder);
        betterNether(consumer, folder);
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

        Function<List<String>, Ingredient> listedInput = inputs -> {
            List<ResourceLocation> list = new ArrayList<>();
            for (String s : inputs) {
                list.add(aetherId.apply(s));
            }
            return ItemNameIngredient.from(list);
        };

        //Generic Melting
        gem(aetherConsumer, AetherInit.moltenZanite, aether)
                .baseUnit(FluidValues.INGOT).damageUnit(FluidValues.NUGGET).oreRate(IMeltingContainer.OreRateType.METAL)
                .sparseOre(0.5f).singularOre(1).denseOre(3).ore(TCByproduct.QUARTZ)
                .melting(9, "block", "storage_blocks", (float)Math.sqrt(9), false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true);

        metal(aetherConsumer, AetherInit.moltenGravitite, aether).ore(TCByproduct.ZANITE).metal(true).optional();
        metal(aetherConsumer, AetherInit.moltenLightnum, aether).metal(true).optional();
        metal(aetherConsumer, AetherInit.moltenDraculite, aether).metal(true).optional();

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

        simpleMelting(aetherConsumer, AetherInit.moltenLightnum, FluidValues.INGOT * 2, "lightnum", ItemNameIngredient.from(aetherId.apply("lightning_sword")), metalFolder.apply("melting"), "sword");
        simpleMelting(aetherConsumer, AetherInit.moltenLightnum, FluidValues.INGOT, "lightnum", ItemNameIngredient.from(aetherId.apply("lightning_knife")), metalFolder.apply("melting"), "knife");
        simpleMelting(aetherConsumer, AetherInit.moltenDraculite, FluidValues.INGOT * 2, "draculite", ItemNameIngredient.from(aetherId.apply("vampire_blade")), metalFolder.apply("melting"), "sword");
        simpleMelting(aetherConsumer, AetherInit.moltenDraculite, FluidValues.INGOT, "draculite", listedInput.apply(List.of("life_shard", "regeneration_stone")), metalFolder.apply("melting"), "ingot_1");
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
        metal(deepAetherConsumer, DeepAetherInit.moltenStormforgedSteel, deepAether).metal(true).optional();

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

        simpleMelting(deepAetherConsumer, DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 2, "stormforged_steel", ItemNameIngredient.from(deepAetherId.apply("storm_sword")), metalFolder.apply("melting"), "sword");
        simpleMelting(deepAetherConsumer, DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 3, "stormforged_steel", ItemNameIngredient.from(deepAetherId.apply("storm_bow")), metalFolder.apply("melting"), "bow");
        simpleMelting(DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 5, ItemNameIngredient.from(deepAetherId.apply("stormforged_helmet")))
                .setDamagable(FluidValues.NUGGET)
                .save(deepAetherConsumer, location(metalFolder.apply("melting") + "/stormforged_steel/helmet"));
        simpleMelting(DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 8, ItemNameIngredient.from(deepAetherId.apply("stormforged_chestplate")))
                .setDamagable(FluidValues.NUGGET)
                .save(deepAetherConsumer, location(metalFolder.apply("melting") + "/stormforged_steel/chestplate"));
        simpleMelting(DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 7, ItemNameIngredient.from(deepAetherId.apply("stormforged_leggings")))
                .setDamagable(FluidValues.NUGGET)
                .save(deepAetherConsumer, location(metalFolder.apply("melting") + "/stormforged_steel/leggings"));
        simpleMelting(DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 4, ItemNameIngredient.from(deepAetherId.apply("stormforged_boots")))
                .setDamagable(FluidValues.NUGGET)
                .save(deepAetherConsumer, location(metalFolder.apply("melting") + "/stormforged_steel/boots"));

        glovesMelting(deepAetherConsumer, DeepAetherInit.moltenStormforgedSteel, FluidValues.INGOT * 2, "stormforged_steel", ItemNameIngredient.from(deepAetherId.apply("stormforged_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
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

        simpleMelting(aetherReduxConsumer, AetherInit.moltenDraculite, FluidValues.INGOT, "draculite", ItemNameIngredient.from(aetherReduxId.apply("vampire_amulet")), metalFolder.apply("melting"), "vampire_amulet");
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
        simpleMelting(aetherTreasureConsumer, AetherTRInit.moltenPyral, FluidValues.INGOT * 2, "phoenix", ItemNameIngredient.from(aetherId.apply("flaming_sword")), metalFolder.apply("melting"), "flaming_sword");
        //Gloves
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenValkyrum, FluidValues.INGOT, "valkyrie", ItemNameIngredient.from(aetherId.apply("valkyrie_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenPyral, FluidValues.INGOT, "phoenix", ItemNameIngredient.from(aetherId.apply("phoenix_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenNeptune, FluidValues.INGOT, "neptune", ItemNameIngredient.from(aetherId.apply("neptune_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenZanite.result(FluidValues.INGOT * 2));
        //Salvaging
        salvageAll(aetherTreasureConsumer, aetherId, AetherTRInit.moltenValkyrum, AetherInit.moltenGravitite, FluidValues.INGOT, "valkyrie", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherTreasureConsumer, aetherId, AetherTRInit.moltenPyral, AetherInit.moltenGravitite, FluidValues.INGOT, "phoenix", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherTreasureConsumer, aetherId, AetherTRInit.moltenNeptune, AetherInit.moltenZanite, FluidValues.INGOT, "neptune", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(aetherTreasureId.apply("neptune_mesh")))
                .setFluidAndTime(AetherTRInit.moltenNeptune, FluidValues.INGOT)
                .save(consumer, location(metalFolder.apply("casting") + "neptune/mesh"));

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

        int brokenLinks = 110;

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
                .setDamagable(FluidValues.NUGGET)
                .save(speciesConsumer, location(metalFolder.apply("melting") + "/iron/ricoshield"));
        simpleMelting(speciesConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", ItemNameIngredient.from(speciesId.apply("kinetic_core")), metalFolder.apply("melting"), "kinetic_core");
        simpleMelting(speciesConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", ItemNameIngredient.from(speciesId.apply("quake_head")), metalFolder.apply("melting"), "quake_head");
        simpleMelting(TinkerFluids.moltenCopper, FluidValues.INGOT * 2, ItemNameIngredient.from(speciesId.apply("harpoon")))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.NUGGET * 4))
                .save(speciesConsumer, location(metalFolder.apply("melting") + "/copper/harpoon"));
        simpleMelting(speciesConsumer, TinkerFluids.moltenEnder, FluidValues.SLIMEBALL, "ender", ItemNameIngredient.from(speciesId.apply("wicked_swapper")), miscFolder.apply("melting"), "wicked_swapper");
    }

    //Holy shit... so many metals are used to make stuff
    private void adAstra(Consumer<FinishedRecipe> consumer, String folder) {
        String adAstra = "ad_astra";
        Function<String, ResourceLocation> adAstraId = name -> getResource(adAstra, name);
        Function<String, TagKey<Item>> adAstraTag = name -> TagKey.create(Registries.ITEM, getResource(adAstra, name));
        Consumer<FinishedRecipe> adAstraConsumer = withCondition(consumer, new ModLoadedCondition(adAstra));

        Function<String, String> adAstraFolderFunction = name -> name.formatted(adAstra);
        Function<String, String> metalFolder = type -> adAstraFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> adAstraFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> adAstraFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        int plating = 7;
        int rod = 45;

        Map<String, FluidObject<?>> decorativeMetals = Map.of(
                "iron", TinkerFluids.moltenIron,
                "steel", TinkerFluids.moltenSteel,
                "desh", AdAstraInit.moltenDesh,
                "ostrum", AdAstraInit.moltenOstrum,
                "calorite", AdAstraInit.moltenCalorite
        );

        decorativeMetals.forEach((name, fluid) -> {
            simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply(name + "_factory_block")), metalFolder.apply("melting"), "factory_block");
            simpleMelting(fluid, 4, ItemNameIngredient.from(adAstraId.apply("encased_" + name + "_block")))
                    .addByproduct(TinkerFluids.moltenSteel.result(8))
                    .save(adAstraConsumer, location(metalFolder.apply("melting") + "/" + name + "/encased_block"));
            if (!List.of("iron", "steel").contains(name)) {
                simpleMelting(fluid, 11, ItemNameIngredient.from(adAstraId.apply(name + "_plateblock")))
                        .addByproduct(TinkerFluids.moltenSteel.result(1))
                        .save(adAstraConsumer, location(metalFolder.apply("melting") + "/" + name + "/plateblock"));
            } else simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply(name + "_plateblock")), metalFolder.apply("melting"), "plateblock");
            simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply(name + "_panel")), metalFolder.apply("melting"), "panel");
            simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply(name + "_plating")), metalFolder.apply("melting"), "plating");
            simpleMelting(adAstraConsumer, fluid, 18, name, ItemNameIngredient.from(adAstraId.apply(name + "_plating_stairs")), metalFolder.apply("melting"), "plating_stairs");
            simpleMelting(adAstraConsumer, fluid, 6, name, ItemNameIngredient.from(adAstraId.apply(name + "_plating_slab")), metalFolder.apply("melting"), "plating_slab");
            simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply(name + "_pillar")), metalFolder.apply("melting"), "pillar");
            simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply("glowing_" + name + "_pillar")), metalFolder.apply("melting"), "glowing_pillar");
            simpleMelting(adAstraConsumer, fluid, 12, name, ItemNameIngredient.from(adAstraId.apply(name + "_plating_button")), metalFolder.apply("melting"), "plating_button");
            simpleMelting(adAstraConsumer, fluid, 24, name, ItemNameIngredient.from(adAstraId.apply(name + "_plating_pressure_plate")), metalFolder.apply("melting"), "plating_pressure_plate");
            if (!Objects.equals(name, "iron")) {
                simpleMelting(fluid, FluidValues.INGOT * 15, ItemNameIngredient.from(adAstraId.apply(name + "_sliding_door")))
                        .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 2))
                        .save(adAstraConsumer, location(metalFolder.apply("melting") + "/" + name + "/sliding_door"));
            } else {
                simpleMelting(fluid, 12 * 6, ItemNameIngredient.from(adAstraId.apply(name + "_sliding_door")))
                        .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_PANE * 2))
                        .addByproduct(TinkerFluids.moltenSteel.result(FluidValues.METAL_BLOCK))
                        .save(adAstraConsumer, location(metalFolder.apply("melting") + "/" + name + "/sliding_door"));
            }
        });
        simpleMelting(adAstraConsumer, TinkerFluids.moltenSteel, FluidValues.INGOT * 25, "steel", ItemNameIngredient.from(adAstraId.apply("airlock")), metalFolder.apply("melting"), "airlock");
        simpleMelting(adAstraConsumer, TinkerFluids.moltenSteel, FluidValues.INGOT * 2, "steel", ItemNameIngredient.from(adAstraId.apply("steel_door")), metalFolder.apply("melting"), "door");
        simpleMelting(TinkerFluids.moltenSteel, FluidValues.INGOT * 11, ItemNameIngredient.from(adAstraId.apply("reinforced_door")))
                .addByproduct(TinkerFluids.moltenGlass.result(FluidValues.GLASS_BLOCK * 2))
                .addByproduct(TinkerFluids.moltenObsidian.result(FluidValues.GLASS_BLOCK * 4))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/steel/reinforced_door"));
        simpleMelting(adAstraConsumer, TinkerFluids.moltenSteel, FluidValues.INGOT * 3, "steel", ItemNameIngredient.from(adAstraId.apply("steel_trapdoor")), metalFolder.apply("melting"), "trapdoor");
        simpleMelting(adAstraConsumer, TinkerFluids.moltenIron, 12, "iron", ItemNameIngredient.from(adAstraId.apply("marked_iron_pillar")), metalFolder.apply("melting"), "marked_pillar");
        simpleMelting(adAstraConsumer, TinkerFluids.moltenSteel, 112, "steel", ItemNameIngredient.from(adAstraId.apply("vent")), metalFolder.apply("melting"), "vent");

        simpleMelting(AdAstraInit.moltenDesh, 32, ItemNameIngredient.from(adAstraId.apply("desh_fluid_pipe")))
                .addByproduct(TinkerFluids.moltenGlass.result(185))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/desh/fluid_pipe"));
        simpleMelting(AdAstraInit.moltenOstrum, 32, ItemNameIngredient.from(adAstraId.apply("ostrum_fluid_pipe")))
                .addByproduct(TinkerFluids.moltenGlass.result(185))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/ostrum/fluid_pipe"));
        simpleMelting(TinkerFluids.moltenSteel, 32, ItemNameIngredient.from(adAstraId.apply("steel_cable")))
                .addByproduct(TinkerFluids.moltenCopper.result(16))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/steel/cable"));
        simpleMelting(AdAstraInit.moltenDesh, 32, ItemNameIngredient.from(adAstraId.apply("desh_cable")))
                .addByproduct(TinkerFluids.moltenCopper.result(16))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/desh/cable"));

        simpleMelting(TinkerFluids.moltenSteel, 196, ItemNameIngredient.from(adAstraId.apply("cable_duct")))
                .addByproduct(TinkerFluids.moltenCopper.result(188))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/steel/cable_duct"));
        simpleMelting(AdAstraInit.moltenDesh, 196, ItemNameIngredient.from(adAstraId.apply("fluid_pipe_duct")))
                .addByproduct(TinkerFluids.moltenCopper.result(FluidValues.INGOT * 2))
                .addByproduct(TinkerFluids.moltenGlass.result(92))
                .save(adAstraConsumer, location(metalFolder.apply("melting") + "/desh/fluid_pipe_duct"));

        metal(adAstraConsumer, AdAstraInit.moltenDesh, adAstra).metal(true).optional();
        metal(adAstraConsumer, AdAstraInit.moltenOstrum, adAstra).metal(true).optional();
        metal(adAstraConsumer, AdAstraInit.moltenCalorite, adAstra).metal(true).optional();

        TCMeltingInfo.AdAstra.adAstraGroup.saveAll(consumer);
        //Deco
        simpleMelting(adAstraConsumer, TinkerFluids.moltenSteel, rod * 3, "steel", Ingredient.of(adAstraTag.apply("flags")), metalFolder.apply("melting"), "space_flags");
    }

    private void betterEnd(Consumer<FinishedRecipe> consumer, String folder) {
        String betterEnd = "betterend";
        Function<String, ResourceLocation> betterEndId = name -> getResource(betterEnd, name);
        Function<String, TagKey<Item>> betterEndTag = name -> TagKey.create(Registries.ITEM, getResource(betterEnd, name));
        Consumer<FinishedRecipe> betterEndConsumer = withCondition(consumer, new ModLoadedCondition(betterEnd));

        Function<String, String> betterEndFolderFunction = name -> name.formatted(betterEnd);
        Function<String, String> alloyFolder = type -> betterEndFolderFunction.apply("smeltery/alloys/%s/" + type);
        Function<String, String> metalFolder = type -> betterEndFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> betterEndFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> betterEndFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        metal(betterEndConsumer, BetterendInit.moltenThallasium, betterEnd).ore().metal(true).plate().optional();
        metal(betterEndConsumer, BetterendInit.moltenTerminite, betterEnd).metal(true).plate().optional();
        metal(betterEndConsumer, BetterendInit.moltenAeternium, betterEnd).metal(true).plate().optional();

        AlloyRecipeBuilder.alloy(BetterendInit.moltenTerminite, FluidValues.INGOT)
                .addInput(TinkerFluids.moltenIron.ingredient(FluidValues.INGOT))
                .addInput(TinkerFluids.moltenEnder.ingredient(FluidValues.SLIMEBALL))
                .save(betterEndConsumer, location(alloyFolder.apply("molten_terminite")));

        AlloyRecipeBuilder.alloy(BetterendInit.moltenAeternium, FluidValues.INGOT)
                .addInput(BetterendInit.moltenTerminite.ingredient(FluidValues.INGOT))
                .addInput(TinkerFluids.moltenNetherite.ingredient(FluidValues.INGOT))
                .save(betterEndConsumer, location(alloyFolder.apply("molten_aeternium")));

        Function<List<String>, Ingredient> listedInput = inputs -> {
            List<ResourceLocation> list = new ArrayList<>();
            for (String s : inputs) {
                list.add(betterEndId.apply(s));
            }
            return ItemNameIngredient.from(list);
        };

        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.METAL_BLOCK, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_tile")), metalFolder.apply("melting"), "tile");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.METAL_BLOCK, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_hammer")), metalFolder.apply("melting"), "hammer");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, 1215, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_stairs")), metalFolder.apply("melting"), "stairs");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.METAL_BLOCK / 2, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_slab")), metalFolder.apply("melting"), "slab");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.INGOT * 4, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_trapdoor")), metalFolder.apply("melting"), "trapdoor");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.NUGGET * 3, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_bars")), metalFolder.apply("melting"), "bars");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, (FluidValues.NUGGET * 2) + FluidValues.INGOT, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_chain")), metalFolder.apply("melting"), "chain");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, (FluidValues.NUGGET * 2) + (FluidValues.INGOT * 2), "thallasium", Ingredient.of(TCTags.Items.THALLASIUM_BULB_LANTERNS), metalFolder.apply("melting"), "bulb_lantern");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.INGOT * 2, "thallasium", listedInput.apply(List.of(
                "thallasium_shovel_head",
                "thallasium_shovel",
                "thallasium_sword_blade",
                "thallasium_sword_handle",
                "thallasium_upgrade_smithing_template"
        )), metalFolder.apply("melting"), "ingot_1");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.INGOT * 2, "thallasium", listedInput.apply(List.of(
                "thallasium_plate",
                "thallasium_door",
                "thallasium_chandelier",
                "thallasium_hoe_head",
                "thallasium_sword",
                "thallasium_hoe"
        )), metalFolder.apply("melting"), "ingot_2");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.INGOT * 3, "thallasium", listedInput.apply(List.of(
                "thallasium_pickaxe_head",
                "thallasium_axe_head",
                "thallasium_pickaxe",
                "thallasium_axe"
        )), metalFolder.apply("melting"), "ingot_3");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, (FluidValues.METAL_BLOCK * 3) + (FluidValues.INGOT * 4), "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_anvil")), metalFolder.apply("melting"), "anvil");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.INGOT * 4, "thallasium", ItemNameIngredient.from(betterEndId.apply("end_stone_smelter")), metalFolder.apply("melting"), "end_stone_smelter");

        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.METAL_BLOCK, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_tile")), metalFolder.apply("melting"), "tile");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.METAL_BLOCK, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_hammer")), metalFolder.apply("melting"), "hammer");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, 1215, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_stairs")), metalFolder.apply("melting"), "stairs");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.METAL_BLOCK / 2, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_slab")), metalFolder.apply("melting"), "slab");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.INGOT * 4, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_trapdoor")), metalFolder.apply("melting"), "trapdoor");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.NUGGET * 3, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_bars")), metalFolder.apply("melting"), "bars");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, (FluidValues.NUGGET * 2) + FluidValues.INGOT, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_chain")), metalFolder.apply("melting"), "chain");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, (FluidValues.NUGGET * 2) + (FluidValues.INGOT * 2), "terminite", Ingredient.of(TCTags.Items.TERMINITE_BULB_LANTERNS), metalFolder.apply("melting"), "bulb_lantern");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.INGOT * 2, "terminite", listedInput.apply(List.of(
                "terminite_shovel_head",
                "terminite_shovel",
                "terminite_sword_blade",
                "terminite_sword_handle",
                "aeternium_sword_handle",
                "terminite_upgrade_smithing_template"
        )), metalFolder.apply("melting"), "ingot_1");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.INGOT * 2, "terminite", listedInput.apply(List.of(
                "terminite_plate",
                "terminite_door",
                "terminite_chandelier",
                "terminite_hoe_head",
                "terminite_sword",
                "terminite_hoe"
        )), metalFolder.apply("melting"), "ingot_2");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.INGOT * 3, "terminite", listedInput.apply(List.of(
                "terminite_pickaxe_head",
                "terminite_axe_head",
                "terminite_pickaxe",
                "terminite_axe"
        )), metalFolder.apply("melting"), "ingot_3");
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, (FluidValues.METAL_BLOCK * 3) + (FluidValues.INGOT * 4), "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_anvil")), metalFolder.apply("melting"), "anvil");

        simpleMelting(betterEndConsumer, TinkerFluids.moltenEnder, FluidValues.SLIME_CONGEALED, "ender", ItemNameIngredient.from(betterEndId.apply("ender_block")), metalFolder.apply("melting"), "block");
        simpleMelting(betterEndConsumer, TinkerFluids.moltenIron, (FluidValues.NUGGET * 2) + (FluidValues.INGOT * 2), "iron", Ingredient.of(TCTags.Items.IRON_BULB_LANTERNS), metalFolder.apply("melting"), "bulb_lantern");
        simpleMelting(betterEndConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(betterEndId.apply("iron_chandelier")), metalFolder.apply("melting"), "chandelier");
        simpleMelting(betterEndConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(betterEndId.apply("gold_chandelier")), metalFolder.apply("melting"), "chandelier");

        simpleMelting(TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, ItemNameIngredient.from(betterEndId.apply("infusion_pedestal")))
                .addByproduct(TinkerFluids.moltenEnder.result(FluidValues.SLIMEBALL * 3))
                .save(betterEndConsumer, location(miscFolder.apply("melting") + "/obsidian/infusion_pedestal"));

        simpleMelting(BetterendInit.moltenAeternium, FluidValues.INGOT, ItemNameIngredient.from(betterEndId.apply("aeternium_anvil")))
                .addByproduct(BetterendInit.moltenTerminite.result((FluidValues.METAL_BLOCK * 3) + (FluidValues.INGOT * 4)))
                .save(betterEndConsumer, location(metalFolder.apply("melting") + "/aeternium/anvil"));

        simpleMelting(betterEndConsumer, TinkerFluids.moltenEnder, FluidValues.SLIMEBALL, "ender", listedInput.apply(List.of("ender_ore", "ender_shard", "ender_dust")), metalFolder.apply("melting"), "slimeball_1");

        simpleMelting(betterEndConsumer, BetterendInit.moltenAeternium, FluidValues.INGOT, "aeternium", listedInput.apply(List.of(
                "elytra_armored",
                "aeternium_shovel_head",
                "aeternium_pickaxe_head",
                "aeternium_axe_head",
                "aeternium_hoe_head",
                "aeternium_hammer_head",
                "aeternium_sword_blade",
                "aeternium_hammer"
        )), metalFolder.apply("melting"), "ingot_1");

        hammerMelting(betterEndConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(betterEndId.apply("iron_hammer")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        hammerMelting(betterEndConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(betterEndId.apply("golden_hammer")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        hammerMelting(betterEndConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 4, "diamond", ItemNameIngredient.from(betterEndId.apply("diamond_hammer")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET});
        hammerMelting(betterEndConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(betterEndId.apply("netherite_hammer")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));

        salvageArmor(betterEndConsumer, betterEndId, BetterendInit.moltenTerminite, FluidValues.INGOT, "terminite", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(betterEndConsumer, betterEndId, BetterendInit.moltenThallasium, FluidValues.INGOT, "thallasium", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(betterEndConsumer, betterEndId, BetterendInit.moltenAeternium, BetterendInit.moltenTerminite, FluidValues.INGOT, "aeternium", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));

        simpleMelting(betterEndConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM, "diamond", ItemNameIngredient.from(betterEndId.apply("handle_attachment_smithing_template")), gemFolder.apply("melting"), "handle_attachment_smithing_template");
        simpleMelting(betterEndConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 7, "diamond", listedInput.apply(List.of(
                "leather_handle_attachment_smithing_template",
                "aeternium_upgrade_smithing_template",
                "netherite_upgrade_smithing_template"
        )), gemFolder.apply("melting"), "diamond_rich_templates");
        simpleMelting(betterEndConsumer, TinkerFluids.moltenIron, FluidValues.METAL_BLOCK, "iron", ItemNameIngredient.from(betterEndId.apply("tool_assembly_smithing_template")), metalFolder.apply("melting"), "tool_assembly_smithing_template");
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM * 7, ItemNameIngredient.from(betterEndId.apply("plate_upgrade_smithing_template")))
                .addByproduct(TinkerFluids.moltenIron.result(FluidValues.INGOT))
                .save(betterEndConsumer, location(gemFolder.apply("melting") + "/diamond/plate_upgrade_smithing_template"));
        simpleMelting(betterEndConsumer, BetterendInit.moltenTerminite, FluidValues.INGOT, "terminite", ItemNameIngredient.from(betterEndId.apply("terminite_upgrade_smithing_template")), metalFolder.apply("melting"), "terminite_upgrade_smithing_template");
        simpleMelting(betterEndConsumer, BetterendInit.moltenThallasium, FluidValues.INGOT, "thallasium", ItemNameIngredient.from(betterEndId.apply("thallasium_upgrade_smithing_template")), metalFolder.apply("melting"), "thallasium_upgrade_smithing_template");

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betterEndId.apply("thallasium_bars")))
                .setFluidAndTime(BetterendInit.moltenThallasium, FluidValues.NUGGET * 3)
                .save(betterEndConsumer, location(metalFolder.apply("casting") + "/thallasium/bars"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betterEndId.apply("terminite_bars")))
                .setFluidAndTime(BetterendInit.moltenTerminite, FluidValues.NUGGET * 3)
                .save(betterEndConsumer, location(metalFolder.apply("casting") + "/terminite/bars"));
    }

    private void betterNether(Consumer<FinishedRecipe> consumer, String folder) {
        String betternether = "betternether";
        Function<String, ResourceLocation> betternetherId = name -> getResource(betternether, name);
        Function<String, TagKey<Item>> betternetherTag = name -> TagKey.create(Registries.ITEM, getResource(betternether, name));
        Consumer<FinishedRecipe> betternetherConsumer = withCondition(consumer, new ModLoadedCondition(betternether));

        Function<String, String> betternetherFolderFunction = name -> name.formatted(betternether);
        Function<String, String> alloyFolder = type -> betternetherFolderFunction.apply("smeltery/alloys/%s/" + type);
        Function<String, String> metalFolder = type -> betternetherFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> betternetherFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> betternetherFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        metal(betternetherConsumer, BetternetherInit.moltenCincinnasite, betternether).ore().metal(4, true).optional();
        metal(betternetherConsumer, BetternetherInit.moltenNetherRuby, betternether).ore().gem(9, true).optional();

        salvageAll(betternetherConsumer, betternetherId, BetternetherInit.moltenNetherRuby, FluidValues.GEM, "nether_ruby", new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET}, gemFolder.apply("melting"));
        salvageAll(betternetherConsumer, betternetherId, BetternetherInit.moltenNetherRuby, FluidValues.GEM, "flaming_ruby", new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET}, gemFolder.apply("melting"));
        salvageAll(betternetherConsumer, betternetherId, BetternetherInit.moltenCincinnasite, FluidValues.INGOT, "cincinnasite", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));

        Function<List<String>, Ingredient> listedInput = inputs -> {
            List<ResourceLocation> list = new ArrayList<>();
            for (String s : inputs) {
                list.add(betternetherId.apply(s));
            }
            return ItemNameIngredient.from(list);
        };
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT, "cincinnasite", listedInput.apply(List.of(
                "cincinnasite_button",
                "cincinnasite_chain",
                "nether_brewing_stand"
        )), metalFolder.apply("melting"), "ingot_1");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 2, "cincinnasite", listedInput.apply(List.of(
                "cincinnasite_bricks",
                "cincinnasite_bricks_pillar",
                "cincinnasite_shears",
                "cincinnasite_slab",
                "roof_tile_cincinnasite_slab",
                "bone_cincinnasite_door"
        )), metalFolder.apply("melting"), "ingot_2");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 4, "cincinnasite", listedInput.apply(List.of(
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
        )), metalFolder.apply("melting"), "ingot_4");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, 112, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_small_lantern")), metalFolder.apply("melting"), "small_lantern");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, 144, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_brick_plate")), metalFolder.apply("melting"), "brick_plate");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 16, "cincinnasite", listedInput.apply(List.of("cincinnasite_forge", "cincinnasite_anvil", "chest_of_drawers")), metalFolder.apply("melting"), "ingot_16");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 6, "cincinnasite", listedInput.apply(List.of("cincinnasite_stairs", "roof_tile_cincinnasite_stairs")), metalFolder.apply("melting"), "stairs");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 8, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_plate")), metalFolder.apply("melting"), "pressure_plate");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, 22, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_frame")), metalFolder.apply("melting"), "frame");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 12, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_pedestal")), metalFolder.apply("melting"), "pedestal");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.NUGGET * 3, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_bars")), metalFolder.apply("melting"), "bars");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 14, "cincinnasite", ItemNameIngredient.from(betternetherId.apply("cincinnasite_fire_bowl")), metalFolder.apply("melting"), "fire_bowl");

        simpleMelting(betternetherConsumer, BetternetherInit.moltenNetherRuby, 1350, "nether_ruby", ItemNameIngredient.from(betternetherId.apply("nether_ruby_stairs")), gemFolder.apply("melting"), "stairs");
        simpleMelting(betternetherConsumer, BetternetherInit.moltenNetherRuby, FluidValues.LARGE_GEM_BLOCK / 2, "nether_ruby", ItemNameIngredient.from(betternetherId.apply("nether_ruby_slab")), gemFolder.apply("melting"), "slab");

        simpleMelting(BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 3, ItemNameIngredient.from(betternetherId.apply("cincinnasite_pot")))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(betternetherConsumer, location(metalFolder.apply("melting") + "/cincinnasite/pot"));
        simpleMelting(BetternetherInit.moltenCincinnasite, FluidValues.INGOT * 14, ItemNameIngredient.from(betternetherId.apply("cincinnasite_fire_bowl_soul")))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(betternetherConsumer, location(metalFolder.apply("melting") + "/cincinnasite/soul_fire_bowl"));
        simpleMelting(BetternetherInit.moltenCincinnasite, FluidValues.INGOT / 2, Ingredient.of(TCTags.Items.FRAMED_QUARTZ_GLASS))
                .addByproduct(TinkerFluids.moltenQuartz.result(FluidValues.GEM / 2))
                .save(betternetherConsumer, location(metalFolder.apply("melting") + "/cincinnasite/framed_quartz_glass"));
        simpleMelting(BetternetherInit.moltenCincinnasite, 20, Ingredient.of(TCTags.Items.FRAMED_QUARTZ_GLASS_PANES))
                .addByproduct(TinkerFluids.moltenQuartz.result(15))
                .save(betternetherConsumer, location(metalFolder.apply("melting") + "/cincinnasite/framed_quartz_glass_pane"));

        simpleMelting(betternetherConsumer, TinkerFluids.moltenQuartz, FluidValues.GEM, "quartz", Ingredient.of(TCTags.Items.QUARTZ_GLASS), gemFolder.apply("melting"), "glass");
        simpleMelting(betternetherConsumer, TinkerFluids.moltenQuartz, 25, "quartz", Ingredient.of(TCTags.Items.QUARTZ_GLASS_PANES), gemFolder.apply("melting"), "glass_pane");

        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, ItemNameIngredient.from(betternetherId.apply("netherite_fire_bowl")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 14))
                .save(betternetherConsumer, location(metalFolder.apply("melting") + "/netherite/fire_bowl"));
        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, ItemNameIngredient.from(betternetherId.apply("netherite_fire_bowl_soul")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 14))
                .addByproduct(TinkerFluids.liquidSoul.result(FluidValues.GLASS_BLOCK))
                .save(betternetherConsumer, location(metalFolder.apply("melting") + "/netherite/soul_fire_bowl"));

        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM * 7, ItemNameIngredient.from(betternetherId.apply("flaming_ruby_upgrade_smithing_template")))
                .addByproduct(BetternetherInit.moltenNetherRuby.result(FluidValues.GEM))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/flaming_ruby_upgrade_smithing_template"));

        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(betternetherId.apply("cincinnite_helmet_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 5))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_helmet"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(betternetherId.apply("cincinnite_chestplate_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 8))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_chestplate"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(betternetherId.apply("cincinnite_leggings_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 7))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_leggings"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(betternetherId.apply("cincinnite_boots_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 4))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_boots"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, listedInput.apply(List.of("cincinnite_pickaxe_diamond", "cincinnite_axe_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 3))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_axes"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, listedInput.apply(List.of("cincinnite_sword_diamond", "cincinnite_hoe_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT * 2))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_weapons"));
        simpleMelting(TinkerFluids.moltenDiamond, FluidValues.GEM, ItemNameIngredient.from(betternetherId.apply("cincinnite_shovel_diamond")))
                .addByproduct(BetternetherInit.moltenCincinnasite.result(FluidValues.INGOT))
                .save(betternetherConsumer, location(gemFolder.apply("melting") + "/diamond/cincinnite_diamond_shovel"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betternetherId.apply("cincinnasite_bars")))
                .setFluidAndTime(BetternetherInit.moltenCincinnasite, FluidValues.NUGGET * 3)
                .save(betternetherConsumer, location(metalFolder.apply("casting") + "/cincinnasite/bars"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betternetherId.apply("quartz_glass_framed")))
                .setFluidAndTime(BetternetherInit.moltenCincinnasite, FluidValues.INGOT / 2)
                .setCast(ItemNameIngredient.from(betternetherId.apply("quartz_glass")), true)
                .save(betternetherConsumer, location(metalFolder.apply("casting") + "/cincinnasite/framed_glass/colorless"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betternetherId.apply("quartz_glass_framed_pane")))
                .setFluidAndTime(BetternetherInit.moltenCincinnasite, FluidValues.NUGGET * 2)
                .setCast(ItemNameIngredient.from(betternetherId.apply("quartz_glass_pane")), true)
                .save(betternetherConsumer, location(metalFolder.apply("casting") + "/cincinnasite/framed_glass/pane_colorless"));
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getSerializedName();
            ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betternetherId.apply("quartz_glass_framed_" + colorName)))
                    .setFluidAndTime(BetternetherInit.moltenCincinnasite, FluidValues.INGOT / 2)
                    .setCast(ItemNameIngredient.from(betternetherId.apply("quartz_glass_" + colorName)), true)
                    .save(betternetherConsumer, location(metalFolder.apply("casting") + "/cincinnasite/framed_glass/" + colorName));
            ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(betternetherId.apply("quartz_glass_framed_pane_" + colorName)))
                    .setFluidAndTime(BetternetherInit.moltenCincinnasite, FluidValues.NUGGET * 2)
                    .setCast(ItemNameIngredient.from(betternetherId.apply("quartz_glass_pane_" + colorName)), true)
                    .save(betternetherConsumer, location(metalFolder.apply("casting") + "/cincinnasite/framed_glass/pane_" + colorName));
        }
    }

    public TCSmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal/" + modId).meltingFolder("smeltery/melting/metal/" + modId);
    }

    public TCSmelteryRecipeBuilder gem(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/gem/" + modId).meltingFolder("smeltery/melting/gem/" + modId);
    }
}
