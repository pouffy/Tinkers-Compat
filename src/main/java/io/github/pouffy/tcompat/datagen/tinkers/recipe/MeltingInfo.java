package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import com.google.common.base.Preconditions;
import io.github.pouffy.tcompat.TCompat;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import java.util.*;
import java.util.function.Consumer;

import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

//FIXME: PLEASE FIX THIS DISASTER, KRYSTAL
/**
 * A utility class for integration with tier-based mods. Useful for items made up of many different items.
 * <p>
 *     Blame Ad Astra for this
 * </p>
 */
public class MeltingInfo {

    @Getter
    private final ResourceLocation itemKey;
    @Getter
    private Fluid result;
    @Getter
    private int resultAmount;
    @Getter
    private final Map<Fluid, Integer> byproducts = new HashMap<>();

    private MeltingInfo(ResourceLocation itemKey) {
        this.itemKey = itemKey;
    }

    public MeltingInfo result(FluidObject<?> fluid, int amount) {
        this.result = fluid.get();
        this.resultAmount = amount;
        return this;
    }

    public MeltingInfo byproduct(FluidOutput... output) {
        for (FluidOutput fluid : output) {
            byproducts.put(fluid.get().getFluid(), fluid.getAmount());
        }
        return this;
    }

    public MeltingInfo component(MeltingInfo meltingInfo) {
        for (var entry : meltingInfo.allFluids().entrySet()) {
            if (entry.getKey().isSame(result)) {
                this.resultAmount += entry.getValue();
            } else {
                this.byproducts.put(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public MeltingInfo components(MeltingInfo... components) {
        for (MeltingInfo meltingInfo : components) {
            for (var entry : meltingInfo.allFluids().entrySet()) {
                if (entry.getKey().isSame(result)) {
                    this.resultAmount += entry.getValue();
                } else {
                    this.byproducts.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return this;
    }

    private MeltingInfo(ResourceLocation itemKey, Fluid result, int resultAmount, Map<Fluid, Integer> byproducts) {
        this.itemKey = itemKey;
        this.result = result;
        this.resultAmount = resultAmount;
        this.byproducts.putAll(byproducts);
    }

    public static MeltingInfo create(ResourceLocation itemKey) {
        return new MeltingInfo(itemKey);
    }

    public MeltingInfo withAmount(int amount) {
        Preconditions.checkArgument(amount >= 1, "Amount must be greater than 1");
        Map<Fluid, Integer> byproducts = new HashMap<>();
        for (var output : getByproducts().entrySet()) {
            byproducts.put(output.getKey(), output.getValue() * amount);
        }
        return new MeltingInfo(getItemKey(), getResult(), getResultAmount() * amount, byproducts);
    }

    public Map<Fluid, Integer> allFluids() {
        Map<Fluid, Integer> fluidsMap = new HashMap<>(getByproducts());
        fluidsMap.put(getResult(), getResultAmount());
        return fluidsMap;
    }

    public MeltingRecipeBuilder toRecipe() {
        Fluid result = this.getResult();
        int resultAmount = this.getResultAmount();
        Map<Fluid, Integer> byproducts = this.getByproducts();
        var initialBuilder = MeltingRecipeBuilder.melting(ItemNameIngredient.from(getItemKey()), FluidOutput.fromFluid(result, resultAmount), getTemperature(result), IMeltingRecipe.calcTimeFactor(resultAmount));
        for (var byproduct : byproducts.entrySet()) {
            initialBuilder.addByproduct(new NamedFluidOutput(byproduct.getKey().builtInRegistryHolder().key().location(), byproduct.getValue()));
        }
        return initialBuilder;
    }

    public MeltingInfo save(Consumer<FinishedRecipe> consumer, String name) {
        String prefix = "smeltery/melting/metal/" + getItemKey().getNamespace() + "/" + name + "/";
        toRecipe().save(consumer, location(prefix.formatted(getItemKey().getNamespace()) + getItemKey().getPath()));
        return this;
    }

    public MeltingInfo save(Consumer<FinishedRecipe> consumer, String name, String type) {
        String prefix = "smeltery/melting/" + type + "/" + getItemKey().getNamespace() + "/" + name + "/";
        toRecipe().save(consumer, location(prefix.formatted(getItemKey().getNamespace()) + getItemKey().getPath()));
        return this;
    }

    ResourceLocation location(String name) {
        return TCompat.getResource(name);
    }
}
