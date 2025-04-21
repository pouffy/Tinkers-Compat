package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import com.pouffydev.tcompat.fluids.TComFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.SmelteryRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TComSmelteryRecipeProv extends TComBaseRecipeProvider implements ITComSmelteryRecipeHelper, ICommonRecipeHelper {
    public TComSmelteryRecipeProv(PackOutput packOutput) {
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
        //oreberries(consumer, folder);
        twilightForest(consumer, folder);
    }

    private void aether(Consumer<FinishedRecipe> consumer, String folder) {
        String aether = "aether";
        Function<String, ResourceLocation> aetherId = name -> new ResourceLocation(aether, name);
        Function<String,TagKey<Item>> aetherTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(aether, name));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, new ModLoadedCondition(aether));

        Function<String, String> aetherFolderFunction = name -> name.formatted(aether);
        Function<String, String> metalFolder = type -> aetherFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> aetherFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> aetherFolderFunction.apply("smeltery/" + type + "/misc/%s/");
                //Generic Melting

        //gemMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", true, 9, aetherFolder, true, TinkerFluids.moltenQuartz.result(FluidValues.GEM * 2));
        gem(aetherConsumer, TComFluids.moltenZanite, aether).largeGem().ore(TComByproduct.QUARTZ);
        metal(aetherConsumer, TComFluids.moltenGravitite, aether).metal().ore(TComByproduct.ZANITE).rawOre();
        //Gloves
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(aetherId.apply("iron_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(aetherId.apply("golden_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, "diamond", ItemNameIngredient.from(aetherId.apply("diamond_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(aetherId.apply("netherite_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, "obsidian", ItemNameIngredient.from(aetherId.apply("obsidian_gloves")), miscFolder.apply("melting"), true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 2, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 2, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET});
        //Rings
        ringMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(aetherId.apply("iron_ring")), metalFolder.apply("melting"), true);
        ringMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(aetherId.apply("golden_ring")), metalFolder.apply("melting"), true);
        ringMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 4, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_ring")), gemFolder.apply("melting"), true);
        ringMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_ring")), metalFolder.apply("melting"), true);
        //Pendant
        pendantMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(aetherId.apply("iron_pendant")), metalFolder.apply("melting"), true);
        pendantMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", ItemNameIngredient.from(aetherId.apply("golden_pendant")), metalFolder.apply("melting"), true);
        pendantMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_pendant")), gemFolder.apply("melting"), true);
        //Salvaging
        salvageAll(aetherConsumer, aetherId, TComFluids.moltenZanite, FluidValues.GEM, "zanite", new int[]{FluidValues.GEM_SHARD}, gemFolder.apply("melting"));
        salvageAll(aetherConsumer, aetherId, TComFluids.moltenGravitite, FluidValues.INGOT, "gravitite", new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(aetherConsumer, aetherId, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK, "obsidian", new int[]{FluidValues.GLASS_PANE}, miscFolder.apply("melting"));

        //Casting
        //this.castingWithCast(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM, TinkerSmeltery.gemCast, ItemNameOutput.fromName(aetherId.apply("zanite_gemstone")), aetherFolder + "zanite/gem");
        //ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(aetherId.apply("zanite_block")))
        //        .setFluidAndTime(TComFluids.moltenZanite, FluidValues.LARGE_GEM_BLOCK)
        //        .save(aetherConsumer, location(aetherFolder + "zanite/block"));
    }

    private void deepAether(Consumer<FinishedRecipe> consumer, String folder) {
        String deepAether = "deep_aether";
        Function<String, ResourceLocation> deepAetherId = name -> new ResourceLocation(deepAether, name);
        Function<String, TagKey<Item>> deepAetherTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(deepAether, name));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, new ModLoadedCondition(deepAether));

        Function<String, String> deepAetherFolderFunction = name -> name.formatted(deepAether);
        Function<String, String> metalFolder = type -> deepAetherFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> deepAetherFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> deepAetherFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        //Generic Melting
        gem(deepAetherConsumer, TComFluids.moltenSkyjade, deepAether).largeGem().ore(TComByproduct.QUARTZ);
        //Gloves
        glovesMelting(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 2, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_gloves")), gemFolder.apply("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(deepAetherConsumer, TComFluids.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_gloves")), metalFolder.apply("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, TComFluids.moltenGravitite.result(FluidValues.INGOT * 2));
        //Rings
        ringMelting(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 4, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_ring")), gemFolder.apply("melting"), true);
        ringMelting(deepAetherConsumer, TComFluids.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_ring")), metalFolder.apply("melting"), true, TComFluids.moltenGravitite.result(FluidValues.INGOT * 4));
        //Salvaging
        salvageAll(deepAetherConsumer, deepAetherId, TComFluids.moltenSkyjade, FluidValues.GEM, "skyjade", new int[]{FluidValues.GEM_SHARD}, gemFolder.apply("melting"));
        salvageAll(deepAetherConsumer, deepAetherId, TComFluids.moltenStratus, TComFluids.moltenGravitite, FluidValues.INGOT, "stratus", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder.apply("melting"));

        //Casting
        //this.castingWithCast(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM, TinkerSmeltery.gemCast, ItemNameOutput.fromName(deepAetherId.apply("skyjade")), deepAetherFolder + "skyjade/gem");
        //ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(deepAetherId.apply("skyjade_block")))
        //        .setFluidAndTime(TComFluids.moltenSkyjade, FluidValues.LARGE_GEM_BLOCK)
        //        .save(deepAetherConsumer, location(deepAetherFolder + "skyjade/block"));
    }

    private void aetherRedux(Consumer<FinishedRecipe> consumer, String folder) {
        String aetherRedux = "aether_redux";
        Function<String, ResourceLocation> aetherReduxId = name -> new ResourceLocation(aetherRedux, name);
        Function<String, TagKey<Item>> aetherReduxTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(aetherRedux, name));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, new ModLoadedCondition(aetherRedux));

        Function<String, String> aetherReduxFolderFunction = name -> name.formatted(aetherRedux);
        Function<String, String> metalFolder = type -> aetherReduxFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> aetherReduxFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> aetherReduxFolderFunction.apply("smeltery/" + type + "/misc/%s/");
        //Generic Melting
        metal(aetherReduxConsumer, TComFluids.moltenVeridium, aetherRedux).metal().ore(TComByproduct.GRAVITITE).rawOre();
        metal(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, aetherRedux).metal().ore(TComByproduct.VERIDIUM);
        //Rings
        simpleMelting(aetherReduxConsumer, TComFluids.moltenSkyjade, FluidValues.GEM, "skyjade", ItemNameIngredient.from(aetherReduxId.apply("ring_of_wisdom")), gemFolder.apply("melting"), "ring_of_wisdom");
        simpleMelting(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, FluidValues.INGOT * 7, "refined_sentrite", ItemNameIngredient.from(aetherReduxId.apply("sentry_ring")), metalFolder.apply("melting"), "sentry_ring");
        //Salvaging
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        // infused veridium is still veridium
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));

        //Casting
    }

    private void oreberries(Consumer<FinishedRecipe> consumer, String folder) {
        String oreberries = "oreberriesreplanted";
        String oreberriesFolder = folder + oreberries + "/";
        String metalFolder = oreberriesFolder + "metal/";
        Function<String, ResourceLocation> oreberriesId = name -> new ResourceLocation(oreberries, name);
        Function<String,TagKey<Item>> oreberriesTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(oreberries, name));
        Consumer<FinishedRecipe> oreberriesConsumer = withCondition(consumer, new ModLoadedCondition(oreberries));
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenIron, "iron", metalFolder, false, Byproduct.NICKEL, Byproduct.COPPER);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenGold,   "gold", metalFolder, false, Byproduct.COPPER);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenCopper, "copper", metalFolder, false, Byproduct.SMALL_GOLD);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenAluminum, "aluminum", metalFolder, true, Byproduct.IRON);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenLead, "lead", metalFolder, true, Byproduct.GOLD);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenNickel, "nickel", metalFolder, true, Byproduct.IRON);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenOsmium, "osmium", metalFolder, true, Byproduct.IRON);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenSilver, "silver", metalFolder, true, Byproduct.GOLD);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenTin, "tin", metalFolder, true, Byproduct.COPPER);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenUranium, "uranium", metalFolder, true, Byproduct.LEAD, Byproduct.COPPER);
        oreberryMelting(oreberriesConsumer, TinkerFluids.moltenZinc, "zinc", metalFolder, true, Byproduct.TIN, Byproduct.COPPER);
    }

    private void twilightForest(Consumer<FinishedRecipe> consumer, String folder) {
        String twilightForest = "twilightforest";
        Function<String, ResourceLocation> twilightForestId = name -> new ResourceLocation(twilightForest, name);
        Function<String,TagKey<Item>> twilightForestTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(twilightForest, name));
        Consumer<FinishedRecipe> twilightForestConsumer = withCondition(consumer, new ModLoadedCondition(twilightForest));

        Function<String, String> twilightForestFolderFunction = name -> name.formatted(twilightForest);
        Function<String, String> metalFolder = type -> twilightForestFolderFunction.apply("smeltery/" + type + "/metal/%s/");
        Function<String, String> gemFolder = type -> twilightForestFolderFunction.apply("smeltery/" + type + "/gem/%s/");
        Function<String, String> miscFolder = type -> twilightForestFolderFunction.apply("smeltery/" + type + "/misc/%s/");

        //Generic Melting
        metal(twilightForestConsumer, TComFluids.moltenFiery, twilightForest).metal();
        metal(twilightForestConsumer, TComFluids.moltenKnightmetal, twilightForest).metal();
        //Can't use empty-filling because it is two different bottles of the same fluid.
        simpleMelting(twilightForestConsumer, TComFluids.fieryEssence, FluidValues.BOTTLE, "fiery_essence", ItemNameIngredient.from(List.of(twilightForestId.apply("fiery_tears"), twilightForestId.apply("fiery_blood"))), miscFolder.apply("melting"), "vial");
        //Extra Melting
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT * 4, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("knightmetal_ring")), metalFolder.apply("melting"), "loop");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.NUGGET, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("armor_shard")), metalFolder.apply("melting"), "armor_shard");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("armor_shard_cluster")), metalFolder.apply("melting"), "armor_shard_cluster");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT * 7, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("knightmetal_shield")), metalFolder.apply("melting"), "shield");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT * 16, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("block_and_chain")), metalFolder.apply("melting"), "block_and_chain");
        //Salvaging
        salvageArmor(twilightForestConsumer, twilightForestId, TComFluids.moltenFiery, FluidValues.INGOT, "fiery", new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenFiery, FluidValues.INGOT, "fiery", SalvageType.SWORD, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenFiery, FluidValues.INGOT, "fiery", SalvageType.PICKAXE, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        salvageArmor(twilightForestConsumer, twilightForestId, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", SalvageType.SWORD, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder.apply("melting"));
    }

    public SmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal/" + modId).meltingFolder("smeltery/melting/metal/" + modId);
    }

    public SmelteryRecipeBuilder gem(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/gem/" + modId).meltingFolder("smeltery/melting/gem/" + modId);
    }
}
