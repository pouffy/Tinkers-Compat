package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
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
        oreberryMelting(consumer, TinkerFluids.moltenIron, "iron", metalFolder, false, Byproduct.NICKEL, Byproduct.COPPER);
        oreberryMelting(consumer, TinkerFluids.moltenGold,   "gold", metalFolder, false, Byproduct.COPPER);
        oreberryMelting(consumer, TinkerFluids.moltenCopper, "copper", metalFolder, false, Byproduct.SMALL_GOLD);
        oreberryMelting(consumer, TinkerFluids.moltenAluminum, "aluminum", metalFolder, true, Byproduct.IRON);
        oreberryMelting(consumer, TinkerFluids.moltenLead, "lead", metalFolder, true, Byproduct.GOLD);
        oreberryMelting(consumer, TinkerFluids.moltenNickel, "nickel", metalFolder, true, Byproduct.IRON);
        oreberryMelting(consumer, TinkerFluids.moltenOsmium, "osmium", metalFolder, true, Byproduct.IRON);
        oreberryMelting(consumer, TinkerFluids.moltenSilver, "silver", metalFolder, true, Byproduct.GOLD);
        oreberryMelting(consumer, TinkerFluids.moltenTin, "tin", metalFolder, true, Byproduct.COPPER);
        oreberryMelting(consumer, TinkerFluids.moltenUranium, "uranium", metalFolder, true, Byproduct.LEAD, Byproduct.COPPER);
        oreberryMelting(consumer, TinkerFluids.moltenZinc, "zinc", metalFolder, true, Byproduct.TIN, Byproduct.COPPER);
    }

    private void oreberryMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String name, String folder, boolean isOptional, IByproduct... byproducts) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        Consumer<FinishedRecipe> wrapped = isOptional ? withCondition(consumer, tagCondition("nuggets/" + name)) : consumer;
        MeltingRecipeBuilder.melting(Ingredient.of(getItemTag(COMMON, "oreberries/" + name)), fluidOut.apply((int) (FluidValues.NUGGET * 2.5)), getTemperature(fluid), 1 / 4f)
                .save(wrapped, location(prefix + "oreberry"));
    }
}
