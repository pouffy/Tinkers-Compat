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

        glovesMelting(aetherConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, TComTags.Items.Gloves.IRON, folder, true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, TComTags.Items.Gloves.GOLD, folder, true, new int[]{FluidValues.NUGGET});
        glovesMelting(aetherConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, TComTags.Items.Gloves.DIAMOND, folder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, TComTags.Items.Gloves.NETHERITE, folder, true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(aetherConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, TComTags.Items.Gloves.OBSIDIAN, folder, true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(aetherConsumer, TComFluids.moltenZanite, FluidValues.GEM * 2, TComTags.Items.Gloves.ZANITE, folder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TComFluids.moltenSkyjade, FluidValues.GEM * 2, TComTags.Items.Gloves.SKYJADE, folder, true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(aetherConsumer, TComFluids.moltenGravitite, FluidValues.INGOT * 2, TComTags.Items.Gloves.GRAVITITE, folder, true, new int[]{FluidValues.NUGGET});


        simpleMelting(twilightConsumer, TComFluids.moltenFiery, "fiery", folder);
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", folder);
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", TComTags.Items.KNIGHTMETAL_LOOP, 360, folder, "loop");
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", TComTags.Items.ARMOR_SHARD, 10, folder, "armor_shard");
        simpleMelting(twilightConsumer, TComFluids.moltenKnightmetal, "knightmetal", TComTags.Items.ARMOR_SHARD_CLUSTER, 90, folder, "armor_shard_cluster");
        simpleMelting(twilightConsumer, TComFluids.fieryEssence, "fiery_essence", TComTags.Items.FIERY_VIAL, 250, folder, "vial");

        gemMelting(aetherConsumer, TComFluids.moltenZanite, "zanite", true, 9, folder, true, Byproduct.QUARTZ);
        gemMelting(aetherConsumer, TComFluids.moltenSkyjade, "skyjade", true, 9, folder, true, TComByproduct.ZANITE);
        metalMelting(aetherConsumer, TComFluids.moltenGravitite, "gravitite", true, false, metalFolder, true, TComByproduct.ZANITE);
        metalMelting(aetherConsumer, TComFluids.moltenVeridium, "veridium", true, false, metalFolder, true, TComByproduct.GRAVITITE);
        metalMelting(aetherConsumer, TComFluids.moltenRefinedSentrite, "refined_sentrite", true, false, metalFolder, true, TComByproduct.VERIDIUM);
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
