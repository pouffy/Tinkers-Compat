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
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
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
        oreberries(consumer, folder);
        twilightForest(consumer, folder);
    }

    private void aether(Consumer<FinishedRecipe> consumer, String folder) {
        String aether = "aether";
        String aetherFolder = folder + aether + "/";
        String metalFolder = aetherFolder + "metal/";
        Function<String, ResourceLocation> aetherId = name -> new ResourceLocation(aether, name);
        Function<String,TagKey<Item>> aetherTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(aether, name));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, new ModLoadedCondition(aether));
        //Generic Melting
        gemMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", true, 9, aetherFolder, true, Byproduct.QUARTZ);
        metalMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", true, false, metalFolder, true, TComByproduct.ZANITE);
        //Gloves
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(aetherId.apply("iron_gloves")), metalFolder, true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(aetherId.apply("golden_gloves")), metalFolder, true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, "diamond", ItemNameIngredient.from(aetherId.apply("diamond_gloves")), aetherFolder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(aetherId.apply("netherite_gloves")), metalFolder, true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, "obsidian", ItemNameIngredient.from(aetherId.apply("obsidian_gloves")), aetherFolder, true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 2, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_gloves")), aetherFolder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 2, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_gloves")), metalFolder, true, new int[]{FluidValues.NUGGET});
        //Rings
        ringMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(aetherId.apply("iron_ring")), metalFolder, true);
        ringMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(aetherId.apply("golden_ring")), metalFolder, true);
        ringMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 4, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_ring")), aetherFolder, true);
        ringMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 4, "gravitite", ItemNameIngredient.from(aetherId.apply("gravitite_ring")), metalFolder, true);
        //Pendant
        pendantMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(aetherId.apply("iron_pendant")), metalFolder, true);
        pendantMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", ItemNameIngredient.from(aetherId.apply("golden_pendant")), metalFolder, true);
        pendantMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM, "zanite", ItemNameIngredient.from(aetherId.apply("zanite_pendant")), aetherFolder, true);
        //Salvaging
        salvageAll(aetherConsumer, aetherId, TComFluids.moltenZanite, FluidValues.GEM, "zanite", new int[]{FluidValues.GEM_SHARD}, aetherFolder);
        salvageAll(aetherConsumer, aetherId, TComFluids.moltenGravitite, FluidValues.INGOT, "gravitite", new int[]{FluidValues.NUGGET}, metalFolder);
        salvageArmor(aetherConsumer, aetherId, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK, "obsidian", new int[]{FluidValues.GLASS_PANE}, aetherFolder);

        //Casting
        metalTagCasting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", metalFolder, false);
        this.castingWithCast(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM, TinkerSmeltery.gemCast, ItemNameOutput.fromName(aetherId.apply("zanite_gemstone")), aetherFolder + "zanite/gem");
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(aetherId.apply("zanite_block")))
                .setFluidAndTime(TComFluids.moltenZanite, FluidValues.LARGE_GEM_BLOCK)
                .save(aetherConsumer, location(aetherFolder + "zanite/block"));
    }

    private void deepAether(Consumer<FinishedRecipe> consumer, String folder) {
        String deepAether = "deep_aether";
        String deepAetherFolder = folder + deepAether + "/";
        String metalFolder = deepAetherFolder + "metal/";
        Function<String, ResourceLocation> deepAetherId = name -> new ResourceLocation(deepAether, name);
        Function<String, TagKey<Item>> deepAetherTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(deepAether, name));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, new ModLoadedCondition(deepAether));
        //Generic Melting
        gemMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", true, 9, deepAetherFolder, true, TComByproduct.ZANITE);
        metalMelting(deepAetherConsumer, TComFluids.moltenStratus, "stratus", false, false, metalFolder, true);
        //Gloves
        glovesMelting(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 2, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_gloves")), deepAetherFolder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(deepAetherConsumer, TComFluids.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_gloves")), metalFolder, true, new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, TComFluids.moltenGravitite.result(FluidValues.INGOT * 2));
        //Rings
        ringMelting(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 4, "skyjade", ItemNameIngredient.from(deepAetherId.apply("skyjade_ring")), deepAetherFolder, true);
        ringMelting(deepAetherConsumer, TComFluids.moltenStratus, FluidValues.INGOT, "stratus", ItemNameIngredient.from(deepAetherId.apply("stratus_ring")), metalFolder, true, TComFluids.moltenGravitite.result(FluidValues.INGOT * 4));
        //Salvaging
        salvageAll(deepAetherConsumer, deepAetherId, TComFluids.moltenSkyjade, FluidValues.GEM, "skyjade", new int[]{FluidValues.GEM_SHARD}, deepAetherFolder);
        salvageAll(deepAetherConsumer, deepAetherId, TComFluids.moltenStratus, TComFluids.moltenGravitite, FluidValues.INGOT, "stratus", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder);

        //Casting
        metalTagCasting(deepAetherConsumer, TComFluids.moltenStratus, "stratus", metalFolder, false);
        this.castingWithCast(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM, TinkerSmeltery.gemCast, ItemNameOutput.fromName(deepAetherId.apply("skyjade")), deepAetherFolder + "skyjade/gem");
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(deepAetherId.apply("skyjade_block")))
                .setFluidAndTime(TComFluids.moltenSkyjade, FluidValues.LARGE_GEM_BLOCK)
                .save(deepAetherConsumer, location(deepAetherFolder + "skyjade/block"));
    }

    private void aetherRedux(Consumer<FinishedRecipe> consumer, String folder) {
        String aetherRedux = "aether_redux";
        String aetherReduxFolder = folder + aetherRedux + "/";
        String metalFolder = aetherReduxFolder + "metal/";
        Function<String, ResourceLocation> aetherReduxId = name -> new ResourceLocation(aetherRedux, name);
        Function<String, TagKey<Item>> aetherReduxTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(aetherRedux, name));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, new ModLoadedCondition(aetherRedux));
        //Generic Melting
        metalMelting(aetherReduxConsumer, TComFluids.moltenVeridium, "veridium", true, false, metalFolder, true, TComByproduct.GRAVITITE);
        metalMelting(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, "refined_sentrite", true, false, metalFolder, true, TComByproduct.VERIDIUM);
        //Rings
        simpleMelting(aetherReduxConsumer, TComFluids.moltenSkyjade, FluidValues.GEM, "skyjade", ItemNameIngredient.from(aetherReduxId.apply("ring_of_wisdom")), aetherReduxFolder, "ring_of_wisdom");
        simpleMelting(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, FluidValues.INGOT * 7, "refined_sentrite", ItemNameIngredient.from(aetherReduxId.apply("sentry_ring")), aetherReduxFolder, "sentry_ring");
        //Salvaging
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder);
        // infused veridium is still veridium
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.WEAPON, new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(aetherReduxConsumer, aetherReduxId, TComFluids.moltenVeridium, FluidValues.INGOT, "veridium/infused", "infused_veridium", SalvageType.SHOVEL, new int[]{FluidValues.NUGGET}, metalFolder);

        //Casting
        metalTagCasting(aetherReduxConsumer, TComFluids.moltenVeridium, "veridium", metalFolder, false);
        metalTagCasting(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, "refined_sentrite", metalFolder, false);
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
        String twilightForestFolder = folder + twilightForest + "/";
        String metalFolder = twilightForestFolder + "metal/";
        Function<String, ResourceLocation> twilightForestId = name -> new ResourceLocation(twilightForest, name);
        Function<String,TagKey<Item>> twilightForestTag = name -> TagKey.create(Registries.ITEM, new ResourceLocation(twilightForest, name));
        Consumer<FinishedRecipe> twilightForestConsumer = withCondition(consumer, new ModLoadedCondition(twilightForest));
        //Generic Melting
        metalMelting(twilightForestConsumer, TComFluids.moltenFiery, "fiery", false, false, metalFolder, true);
        metalMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, "knightmetal", false, false, metalFolder, true);
        //Can't use empty-filling because it is two different bottles of the same fluid.
        simpleMelting(twilightForestConsumer, TComFluids.fieryEssence, FluidValues.BOTTLE, "fiery_essence", ItemNameIngredient.from(List.of(twilightForestId.apply("fiery_tears"), twilightForestId.apply("fiery_blood"))), twilightForestFolder, "vial");
        //Extra Melting
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT * 4, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("knightmetal_ring")), metalFolder, "loop");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.NUGGET, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("armor_shard")), metalFolder, "armor_shard");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("armor_shard_cluster")), metalFolder, "armor_shard_cluster");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT * 7, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("knightmetal_shield")), metalFolder, "shield");
        simpleMelting(twilightForestConsumer, TComFluids.moltenKnightmetal, FluidValues.INGOT * 16, "knightmetal", ItemNameIngredient.from(twilightForestId.apply("block_and_chain")), metalFolder, "block_and_chain");
        //Salvaging
        salvageArmor(twilightForestConsumer, twilightForestId, TComFluids.moltenFiery, FluidValues.INGOT, "fiery", new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenFiery, FluidValues.INGOT, "fiery", SalvageType.SWORD, new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenFiery, FluidValues.INGOT, "fiery", SalvageType.PICKAXE, new int[]{FluidValues.NUGGET}, metalFolder);
        salvageArmor(twilightForestConsumer, twilightForestId, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", SalvageType.SWORD, new int[]{FluidValues.NUGGET}, metalFolder);
        simpleSalvaging(twilightForestConsumer, twilightForestId, TComFluids.moltenKnightmetal, FluidValues.INGOT, "knightmetal", SalvageType.AXES, new int[]{FluidValues.NUGGET}, metalFolder);

        //Casting
        metalTagCasting(twilightForestConsumer, TComFluids.moltenFiery, "fiery", metalFolder, false);
        metalTagCasting(twilightForestConsumer, TComFluids.moltenKnightmetal, "knightmetal", metalFolder, false);
    }
}
