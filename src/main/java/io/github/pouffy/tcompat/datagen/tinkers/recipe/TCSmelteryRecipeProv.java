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
        gem(aetherConsumer, AetherInit.moltenZanite, aether).largeGem(true).ore(TCByproduct.QUARTZ).optional();
        metal(aetherConsumer, AetherInit.moltenGravitite, aether).metal(true).ore(TCByproduct.ZANITE).rawOre().optional();
        //Gloves
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, (FluidValues.NUGGET * 6) * 2, "chainmail", ItemNameIngredient.from(aetherId.apply("chainmail_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET}, TinkerFluids.moltenSteel.result((FluidValues.NUGGET * 3) * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(aetherId.apply("iron_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(aetherId.apply("golden_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, "diamond", ItemNameIngredient.from(aetherId.apply("diamond_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(aetherId.apply("netherite_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, "obsidian", ItemNameIngredient.from(aetherId.apply("obsidian_gloves")), miscFolder.apply("melting"), true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.GEM * 2, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, AetherInit.moltenGravitite, FluidValues.INGOT * 2, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        //Rings
        ringMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(aetherId.apply("iron_ring")), metalFolder.apply("melting"), true);
        ringMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(aetherId.apply("golden_ring")), metalFolder.apply("melting"), true);
        ringMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.GEM * 4, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_ring")), gemFolder.apply("melting"), true);
        ringMelting(aetherConsumer, AetherInit.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_ring")), metalFolder.apply("melting"), true);
        //Pendant
        pendantMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(aetherId.apply("iron_pendant")), metalFolder.apply("melting"), true);
        pendantMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", ItemNameIngredient.from(aetherId.apply("golden_pendant")), metalFolder.apply("melting"), true);
        pendantMelting(aetherConsumer, AetherInit.moltenZanite, FluidValues.GEM, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_pendant")), gemFolder.apply("melting"), true);
        //Salvaging
        salvageAll(aetherConsumer, aetherId, AetherInit.moltenZanite, FluidValues.GEM, "zanite", new int[]{FluidValues.GEM_SHARD}, gemFolder.apply("melting"));
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
        gem(deepAetherConsumer, DeepAetherInit.moltenSkyjade, deepAether).largeGem(true).ore(TCByproduct.QUARTZ).optional();
        metal(deepAetherConsumer, DeepAetherInit.moltenStratus, deepAether).metal(true).optional();
        //Gloves
        glovesMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.GEM * 2, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(deepAetherConsumer, DeepAetherInit.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        //Rings
        ringMelting(deepAetherConsumer, DeepAetherInit.moltenSkyjade, FluidValues.GEM * 4, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_ring")), gemFolder.apply("melting"), true);
        ringMelting(deepAetherConsumer, DeepAetherInit.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_ring")), metalFolder.apply("melting"), true, AetherInit.moltenGravitite.result(FluidValues.INGOT * 4));
        //Salvaging
        salvageAll(deepAetherConsumer, deepAetherId, DeepAetherInit.moltenSkyjade, FluidValues.GEM, "skyjade", new int[]{FluidValues.GEM_SHARD}, gemFolder.apply("melting"));
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
        simpleMelting(aetherReduxConsumer, DeepAetherInit.moltenSkyjade, FluidValues.GEM, "skyjade", ItemNameIngredient.from(aetherReduxId.apply("ring_of_wisdom")), gemFolder.apply("melting"), "ring_of_wisdom");
        simpleMelting(aetherReduxConsumer, AetherReduxInit.moltenRefinedSentrite, FluidValues.INGOT * 7, "refined_sentrite", ItemNameIngredient.from(aetherReduxId.apply("sentry_ring")), metalFolder.apply("melting"), "sentry_ring");
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
        simpleMelting(aetherTreasureConsumer, AetherTRInit.moltenPyral, FluidValues.INGOT * 3, "pyral", ItemNameIngredient.from(aetherId.apply("phoenix_bow")), metalFolder.apply("melting"), "phoenix_bow");
        //Gloves
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenValkyrum, FluidValues.INGOT, "valkyrie", ItemNameIngredient.from(aetherId.apply("valkyrie_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenPyral, FluidValues.INGOT, "pyral", ItemNameIngredient.from(aetherId.apply("phoenix_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenGravitite.result(FluidValues.INGOT * 2));
        glovesMelting(aetherTreasureConsumer, AetherTRInit.moltenNeptune, FluidValues.INGOT, "neptune", ItemNameIngredient.from(aetherId.apply("neptune_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, AetherInit.moltenZanite.result(FluidValues.INGOT * 2));
        //Salvaging
        salvageAll(aetherTreasureConsumer, aetherId, AetherTRInit.moltenValkyrum, AetherInit.moltenGravitite, FluidValues.INGOT, "valkyrie", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherTreasureConsumer, aetherId, AetherTRInit.moltenPyral, AetherInit.moltenGravitite, FluidValues.INGOT, "phoenix", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherTreasureConsumer, aetherId, AetherTRInit.moltenNeptune, AetherInit.moltenZanite, FluidValues.INGOT, "neptune", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));
    }

    public TCSmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal/" + modId).meltingFolder("smeltery/melting/metal/" + modId);
    }

    public TCSmelteryRecipeBuilder gem(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/gem/" + modId).meltingFolder("smeltery/melting/gem/" + modId);
    }
}
