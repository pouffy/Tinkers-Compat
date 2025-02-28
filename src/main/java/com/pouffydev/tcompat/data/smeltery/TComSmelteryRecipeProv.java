package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import com.pouffydev.tcompat.fluids.TComFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import java.util.function.Consumer;
import java.util.function.IntFunction;

import static slimeknights.mantle.Mantle.COMMON;
import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public class TComSmelteryRecipeProv extends TComBaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {
    public TComSmelteryRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Smeltery Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";
        String metalFolder = folder + "metal/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, modLoaded("aether_redux"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));
        Consumer<FinishedRecipe> twilightConsumer = withCondition(consumer, modLoaded("twilightforest"));
        Consumer<FinishedRecipe> oreberriesConsumer = withCondition(consumer, modLoaded("oreberriesreplanted"));
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



        simpleMelting(twilightConsumer, TComFluids.moltenFiery, "fiery", folder);
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", folder);

        simpleMelting(twilightConsumer, TComFluids.fieryEssence, "fiery_essence", TComTags.Items.FIERY_VIAL, 250, folder, "vial");

        gemMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", true, 9, folder, true, Byproduct.QUARTZ);
        gemMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", true, 9, folder, true, TComByproduct.ZANITE);
        metalMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", true, false, metalFolder, true, TComByproduct.ZANITE);
        metalMelting(aetherReduxConsumer, TComFluids.moltenVeridium, "veridium", true, false, metalFolder, true, TComByproduct.GRAVITITE);
        metalMelting(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, "refined_sentrite", true, false, metalFolder, true, TComByproduct.VERIDIUM);

        //Salvaging
        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, TComTags.Items.Gloves.IRON, folder, true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, TComTags.Items.Gloves.GOLD, folder, true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, TComTags.Items.Gloves.DIAMOND, folder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, TComTags.Items.Gloves.NETHERITE, folder, true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, TComTags.Items.Gloves.OBSIDIAN, folder, true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 2, TComTags.Items.Gloves.ZANITE, folder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 2, TComTags.Items.Gloves.SKYJADE, folder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 2, TComTags.Items.Gloves.GRAVITITE, folder, true, new int[]{FluidValues.NUGGET});

        ringMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, TComTags.Items.Rings.IRON, folder, true);
        ringMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, TComTags.Items.Rings.GOLD, folder, true);
        ringMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 4, TComTags.Items.Rings.ZANITE, folder, true);
        ringMelting(deepAetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 4, TComTags.Items.Rings.SKYJADE, folder, true);
        ringMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 4, TComTags.Items.Rings.GRAVITITE, folder, true);
        simpleMelting(aetherReduxConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.WISDOM_RING, FluidValues.GEM, folder, "ring_of_wisdom");
        simpleMelting(aetherReduxConsumer, TComFluids.moltenRefinedSentrite, "refined_sentrite", TComTags.Items.SENTRY_RING, FluidValues.INGOT * 7, folder, "sentry_ring");

        pendantMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, TComTags.Items.Pendants.IRON, folder, true);
        pendantMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, TComTags.Items.Pendants.GOLD, folder, true);
        pendantMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM, TComTags.Items.Pendants.ZANITE, folder, true);

        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", TComTags.Items.KNIGHTMETAL_LOOP, 360, folder, "loop");
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", TComTags.Items.ARMOR_SHARD, 10, folder, "armor_shard");
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", TComTags.Items.ARMOR_SHARD_CLUSTER, 90, folder, "armor_shard_cluster");

        //Zanite
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_1.tag(), FluidValues.GEM, folder, "salvaging_1");
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_2.tag(), FluidValues.GEM * 2, folder, "salvaging_2");
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_3.tag(), FluidValues.GEM * 3, folder, "salvaging_3");
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_4.tag(), FluidValues.GEM * 4, folder, "salvaging_4");
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_5.tag(), FluidValues.GEM * 5, folder, "salvaging_5");
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_7.tag(), FluidValues.GEM * 7, folder, "salvaging_7");
        simpleMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", TComTags.Items.Salvaging.ZANITE_8.tag(), FluidValues.GEM * 8, folder, "salvaging_8");
        //Gravitite
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_1.tag(), FluidValues.INGOT, folder, "salvaging_1");
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_2.tag(), FluidValues.INGOT * 2, folder, "salvaging_2");
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_3.tag(), FluidValues.INGOT * 3, folder, "salvaging_3");
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_4.tag(), FluidValues.INGOT * 4, folder, "salvaging_4");
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_5.tag(), FluidValues.INGOT * 5, folder, "salvaging_5");
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_7.tag(), FluidValues.INGOT * 7, folder, "salvaging_7");
        simpleMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", TComTags.Items.Salvaging.GRAVITITE_8.tag(), FluidValues.INGOT * 8, folder, "salvaging_8");
        //Skyjade
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_1.tag(), FluidValues.GEM, folder, "salvaging_1");
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_2.tag(), FluidValues.GEM * 2, folder, "salvaging_2");
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_3.tag(), FluidValues.GEM * 3, folder, "salvaging_3");
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_4.tag(), FluidValues.GEM * 4, folder, "salvaging_4");
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_5.tag(), FluidValues.GEM * 5, folder, "salvaging_5");
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_7.tag(), FluidValues.GEM * 7, folder, "salvaging_7");
        simpleMelting(deepAetherConsumer, TComFluids.moltenSkyjade, "skyjade", TComTags.Items.Salvaging.SKYJADE_8.tag(), FluidValues.GEM * 8, folder, "salvaging_8");
        //Veridium
        simpleMelting(aetherConsumer, TComFluids.moltenVeridium, "veridium", TComTags.Items.Salvaging.VERIDIUM_1.tag(), FluidValues.INGOT, folder, "salvaging_1");
        simpleMelting(aetherConsumer, TComFluids.moltenVeridium, "veridium", TComTags.Items.Salvaging.VERIDIUM_2.tag(), FluidValues.INGOT * 2, folder, "salvaging_2");
        simpleMelting(aetherConsumer, TComFluids.moltenVeridium, "veridium", TComTags.Items.Salvaging.VERIDIUM_3.tag(), FluidValues.INGOT * 3, folder, "salvaging_3");
        //Obsidian
        simpleMelting(aetherConsumer, TinkerFluids.moltenObsidian, "obsidian", TComTags.Items.Salvaging.OBSIDIAN_4.tag(), FluidValues.GLASS_BLOCK * 4, folder, "salvaging_4");
        simpleMelting(aetherConsumer, TinkerFluids.moltenObsidian, "obsidian", TComTags.Items.Salvaging.OBSIDIAN_5.tag(), FluidValues.GLASS_BLOCK * 5, folder, "salvaging_5");
        simpleMelting(aetherConsumer, TinkerFluids.moltenObsidian, "obsidian", TComTags.Items.Salvaging.OBSIDIAN_7.tag(), FluidValues.GLASS_BLOCK * 7, folder, "salvaging_7");
        simpleMelting(aetherConsumer, TinkerFluids.moltenObsidian, "obsidian", TComTags.Items.Salvaging.OBSIDIAN_8.tag(), FluidValues.GLASS_BLOCK * 8, folder, "salvaging_8");
    }

    private void oreberryMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String name, String folder, boolean isOptional, IByproduct... byproducts) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        Consumer<FinishedRecipe> wrapped = isOptional ? withCondition(consumer, tagCondition("nuggets/" + name)) : consumer;
        MeltingRecipeBuilder.melting(Ingredient.of(getItemTag(COMMON, "oreberries/" + name)), fluidOut.apply((int) (FluidValues.NUGGET * 2.5)), getTemperature(fluid), 1 / 4f)
                .save(wrapped, location(prefix + "oreberry"));
    }

    private void glovesMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int fluidValue, TComTags.Items.Gloves gloveType, String folder, boolean isOptional, int[] damageSizes, FluidOutput... byproducts) {
        Consumer<FinishedRecipe> wrapped = isOptional ? withCondition(consumer, tagCondition("aether_gloves/" + gloveType.name().toLowerCase())) : consumer;
        String directory = folder + "/" + gloveType.name().toLowerCase() + "/aether_glove";

        MeltingRecipeBuilder builder = MeltingRecipeBuilder.melting(Ingredient.of(gloveType.tag()), fluid, fluidValue)
                .setDamagable(damageSizes);
        for (FluidOutput output : byproducts) {
            builder.addByproduct(output);
        }
        builder.save(wrapped, location(directory));
    }

    private void ringMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int fluidValue, TComTags.Items.Rings ringType, String folder, boolean isOptional, FluidOutput... byproducts) {
        Consumer<FinishedRecipe> wrapped = isOptional ? withCondition(consumer, tagCondition("aether_rings/" + ringType.name().toLowerCase())) : consumer;
        String directory = folder + "/" + ringType.name().toLowerCase() + "/aether_ring";

        MeltingRecipeBuilder builder = MeltingRecipeBuilder.melting(Ingredient.of(ringType.tag()), fluid, fluidValue);
        for (FluidOutput output : byproducts) {
            builder.addByproduct(output);
        }
        builder.save(wrapped, location(directory));
    }

    private void pendantMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int fluidValue, TComTags.Items.Pendants pendantType, String folder, boolean isOptional, FluidOutput... byproducts) {
        Consumer<FinishedRecipe> wrapped = isOptional ? withCondition(consumer, tagCondition("aether_pendants/" + pendantType.name().toLowerCase())) : consumer;
        String directory = folder + "/" + pendantType.name().toLowerCase() + "/aether_pendant";

        MeltingRecipeBuilder builder = MeltingRecipeBuilder.melting(Ingredient.of(pendantType.tag()), fluid, fluidValue);
        for (FluidOutput output : byproducts) {
            builder.addByproduct(output);
        }
        builder.save(wrapped, location(directory));
    }

    private void simpleMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String name, String folder) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        MeltingRecipeBuilder.melting(Ingredient.of(getItemTag(COMMON, "ingots/" + name)), fluidOut.apply(FluidValues.INGOT), getTemperature(fluid), 1.0f).save(consumer, location(prefix + "ingot"));
        MeltingRecipeBuilder.melting(Ingredient.of(getItemTag(COMMON, "storage_blocks/" + name)), fluidOut.apply(FluidValues.METAL_BLOCK), getTemperature(fluid), 3.0f).save(consumer, location(prefix + "block"));
    }

    private void simpleMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String name, TagKey<Item> input, int outAmount, String folder, String type) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        MeltingRecipeBuilder.melting(Ingredient.of(input), fluidOut.apply(outAmount), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(outAmount)).save(consumer, location(prefix + type));
    }
}
