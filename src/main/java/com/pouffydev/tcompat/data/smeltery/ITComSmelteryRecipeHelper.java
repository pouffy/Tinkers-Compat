package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.data.TComTags;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import static slimeknights.mantle.Mantle.COMMON;
import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public interface ITComSmelteryRecipeHelper extends ISmelteryRecipeHelper {

    default void glovesMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int fluidValue, String name, Ingredient input, String folder, boolean isOptional, int[] damageSizes, FluidOutput... byproducts) {
        String directory = folder + "/" + name + "/aether_glove";

        MeltingRecipeBuilder builder = MeltingRecipeBuilder.melting(input, fluid, fluidValue)
                .setDamagable(damageSizes);
        for (FluidOutput output : byproducts) {
            builder.addByproduct(output);
        }
        builder.save(consumer, location(directory));
    }

    default void ringMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int fluidValue, String name, Ingredient input, String folder, boolean isOptional, FluidOutput... byproducts) {
        String directory = folder + "/" + name + "/aether_ring";

        MeltingRecipeBuilder builder = MeltingRecipeBuilder.melting(input, fluid, fluidValue);
        for (FluidOutput output : byproducts) {
            builder.addByproduct(output);
        }
        builder.save(consumer, location(directory));
    }

    default void pendantMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int fluidValue, String name, Ingredient input, String folder, boolean isOptional, FluidOutput... byproducts) {
        String directory = folder + "/" + name + "/aether_pendant";

        MeltingRecipeBuilder builder = MeltingRecipeBuilder.melting(input, fluid, fluidValue);
        for (FluidOutput output : byproducts) {
            builder.addByproduct(output);
        }
        builder.save(consumer, location(directory));
    }

    default void simpleMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, int outAmount, String name, Ingredient input, String folder, String type) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        MeltingRecipeBuilder.melting(input, fluidOut.apply(outAmount), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(outAmount)).save(consumer, location(prefix + type));
    }
    default void simpleSalvaging(Consumer<FinishedRecipe> consumer, Function<String, ResourceLocation> idFunc, FluidObject<?> fluid, int singularUnit, String name, String techName, SalvageType salvage, int[] damageSizes, String folder) {
        String prefix = folder + "/" + name + "/" + salvage.recipeSuffix();
        IntFunction<FluidOutput> fluidOut = fluid::result;
        MeltingRecipeBuilder.melting(salvage.input(idFunc, techName), fluidOut.apply(salvage.getSalvageAmount(singularUnit)), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(salvage.getSalvageAmount(singularUnit))).setDamagable(damageSizes).save(consumer, location(prefix));
    }
    default void simpleSalvaging(Consumer<FinishedRecipe> consumer, Function<String, ResourceLocation> idFunc, FluidObject<?> fluid, int singularUnit, String name, SalvageType salvage, int[] damageSizes, String folder) {
        String prefix = folder + "/" + name + "/" + salvage.recipeSuffix();
        IntFunction<FluidOutput> fluidOut = fluid::result;
        MeltingRecipeBuilder.melting(salvage.input(idFunc, name), fluidOut.apply(salvage.getSalvageAmount(singularUnit)), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(salvage.getSalvageAmount(singularUnit))).setDamagable(damageSizes).save(consumer, location(prefix));
    }
    default void simpleSalvaging(Consumer<FinishedRecipe> consumer, Function<String, ResourceLocation> idFunc, FluidObject<?> fluid, FluidObject<?> byproductFluid, int singularUnit, String name, SalvageType salvage, int[] damageSizes, String folder) {
        String prefix = folder + "/" + name + "/" + salvage.recipeSuffix();
        IntFunction<FluidOutput> fluidOut = fluid::result;
        IntFunction<FluidOutput> fluidByOut = byproductFluid::result;
        MeltingRecipeBuilder.melting(salvage.input(idFunc, name), fluidOut.apply(singularUnit), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(salvage.getSalvageAmount(singularUnit))).setDamagable(damageSizes).addByproduct(fluidByOut.apply(salvage.getSalvageAmount(singularUnit))).save(consumer, location(prefix));
    }

    default void salvageAll(Consumer<FinishedRecipe> consumer, Function<String, ResourceLocation> idFunc, FluidObject<?> fluid, int singularUnit, String name, int[] damageSizes, String folder) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        for (SalvageType salvage : SalvageType.values()) {
            if (!salvage.includeInSalvageAll) continue;
            MeltingRecipeBuilder.melting(salvage.input(idFunc, name), fluidOut.apply(salvage.getSalvageAmount(singularUnit)), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(salvage.getSalvageAmount(singularUnit))).setDamagable(damageSizes).save(consumer, location(prefix + salvage.recipeSuffix()));
        }
    }
    default void salvageAll(Consumer<FinishedRecipe> consumer, Function<String, ResourceLocation> idFunc, FluidObject<?> fluid, FluidObject<?> byproductFluid, int singularUnit, String name, int[] damageSizes, String folder) {
        String prefix = folder + "/" + name + "/";
        IntFunction<FluidOutput> fluidOut = fluid::result;
        IntFunction<FluidOutput> fluidByOut = byproductFluid::result;
        for (SalvageType salvage : SalvageType.values()) {
            if (!salvage.includeInSalvageAll) continue;
            MeltingRecipeBuilder.melting(salvage.input(idFunc, name), fluidOut.apply(singularUnit), getTemperature(fluid), IMeltingRecipe.calcTimeFactor(salvage.getSalvageAmount(singularUnit))).setDamagable(damageSizes).addByproduct(fluidByOut.apply(salvage.getSalvageAmount(singularUnit))).save(consumer, location(prefix + salvage.recipeSuffix()));
        }
    }

    default void salvageArmor(Consumer<FinishedRecipe> consumer, Function<String, ResourceLocation> idFunc, FluidObject<?> fluid, int singularUnit, String name, int[] damageSizes, String folder) {
        simpleSalvaging(consumer, idFunc, fluid, singularUnit, name, SalvageType.HELMET, damageSizes, folder);
        simpleSalvaging(consumer, idFunc, fluid, singularUnit, name, SalvageType.CHESTPLATE, damageSizes, folder);
        simpleSalvaging(consumer, idFunc, fluid, singularUnit, name, SalvageType.LEGGINGS, damageSizes, folder);
        simpleSalvaging(consumer, idFunc, fluid, singularUnit, name, SalvageType.BOOTS, damageSizes, folder);
    }

    default void oreberryMelting(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String material, String folder, boolean isOptional, IByproduct... byproducts) {
        String prefix = folder + "/" + material + "/";
        Function<String, ResourceLocation> oreberriesId = name -> new ResourceLocation("oreberriesreplanted", name);
        IntFunction<FluidOutput> fluidOut = fluid::result;
        Consumer<FinishedRecipe> wrapped = isOptional ? withCondition(consumer, tagCondition("nuggets/" + material)) : consumer;
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(oreberriesId.apply(material + "_oreberry")), fluidOut.apply((int) (FluidValues.NUGGET * 2.5)), getTemperature(fluid), 1 / 4f)
                .save(wrapped, location(prefix + "oreberry"));
    }

    enum SalvageType {
        HELMET(5),
        CHESTPLATE(8),
        LEGGINGS(7),
        BOOTS(4),
        AXES(3, "axe", "pickaxe"),
        WEAPON(2, "hoe", "sword"),
        SHOVEL(1),
        AXE(3, false),
        PICKAXE(3, false),
        SWORD(2, false),
        HOE(2, false),
        ;

        final int salvageAmount;
        final List<String> types;
        boolean includeInSalvageAll = true;

        SalvageType(int salvageAmount) {
            this.salvageAmount = salvageAmount;
            this.types = List.of(this.name().toLowerCase());
        }

        SalvageType(int salvageAmount, boolean includeInSalvageAll) {
            this.salvageAmount = salvageAmount;
            this.includeInSalvageAll = includeInSalvageAll;
            this.types = List.of(this.name().toLowerCase());
        }

        SalvageType(int salvageAmount, String... types) {
            this.salvageAmount = salvageAmount;
            this.types = Arrays.asList(types);
        }

        public String recipeSuffix() {
            return this.name().toLowerCase();
        }

        public int getSalvageAmount(int singularUnit) {
            return  this.salvageAmount * singularUnit;
        }

        public ItemNameIngredient input(Function<String, ResourceLocation> idFunc, String material) {
            List<ResourceLocation> inputs = new ArrayList<>();
            for (String type : this.types) {
                ResourceLocation id = idFunc.apply(material + "_" + type);
                inputs.add(id);
            }
            return ItemNameIngredient.from(inputs);
        }

    }
}
