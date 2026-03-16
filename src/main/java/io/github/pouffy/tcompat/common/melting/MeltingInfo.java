package io.github.pouffy.tcompat.common.melting;

import com.google.common.base.Preconditions;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.NamedFluidOutput;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public class MeltingInfo {
    @Getter
    private final ResourceLocation itemKey;
    @Getter
    private ResourceLocation result;
    @Getter
    private final Map<ResourceLocation, Integer> outputs = new HashMap<>();

    private int temperature;

    private MeltingInfo(ResourceLocation itemKey) {
        this.itemKey = itemKey;
    }

    public static MeltingInfo create(ResourceLocation itemKey) {
        return new MeltingInfo(itemKey);
    }

    public MeltingInfo setResult(ResourceLocation fluidKey, int temperature) {
        this.result = fluidKey;
        this.temperature = temperature;
        return this;
    }

    public MeltingInfo setResult(FluidObject<?> fluid, int amount) {
        this.outputs.put(fluid.getId(), amount);
        return setResult(fluid.getId(), getTemperature(fluid));
    }

    public MeltingInfo byproduct(FluidOutput... output) {
        for (FluidOutput fluid : output) {
            outputs.put(fluid.get().getFluid().builtInRegistryHolder().key().location(), fluid.getAmount());
        }
        return this;
    }

    public MeltingInfo byproduct(FluidOutput output) {
        outputs.put(output.get().getFluid().builtInRegistryHolder().key().location(), output.getAmount());
        return this;
    }

    public MeltingInfo byproduct(ResourceLocation fluid, int amount) {
        outputs.put(fluid, amount);
        return this;
    }

    public MeltingInfo component(MeltingInfo meltingInfo) {
        for (var entry : meltingInfo.getOutputs().entrySet()) {
            this.outputs.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
        return this;
    }

    public MeltingInfo components(MeltingInfo... components) {
        for (MeltingInfo meltingInfo : components) {
            for (var entry : meltingInfo.getOutputs().entrySet()) {
                this.outputs.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return this;
    }

    public MeltingInfo copyWithCount(int count) {
        Preconditions.checkArgument(count >= 1, "Amount must be greater than 1");
        MeltingInfo meltingInfo = new MeltingInfo(itemKey);
        meltingInfo.setResult(result, temperature);
        var newOutputs = meltingInfo.outputs;
        newOutputs.replaceAll((fluid, fluidAmount) -> fluidAmount * count);
        meltingInfo.outputs.clear();
        meltingInfo.outputs.putAll(newOutputs);
        return meltingInfo;
    }

    public MeltingRecipeBuilder toRecipe() {
        ResourceLocation result = this.getResult();
        int resultAmount = this.getOutputs().get(result);
        var initialBuilder = MeltingRecipeBuilder.melting(ItemNameIngredient.from(getItemKey()), new NamedFluidOutput(result, resultAmount), temperature, IMeltingRecipe.calcTimeFactor(resultAmount));
        for (var byproduct : getOutputs().entrySet()) {
            if (byproduct.getKey().equals(result)) continue;
            initialBuilder.addByproduct(new NamedFluidOutput(byproduct.getKey(), byproduct.getValue()));
        }
        return initialBuilder;
    }

    public MeltingInfo save(Consumer<FinishedRecipe> consumer) {
        return save(consumer, "metal");
    }

    public MeltingInfo save(Consumer<FinishedRecipe> consumer, String type) {
        Consumer<FinishedRecipe> finalConsumer = withCondition(consumer, new ModLoadedCondition(getItemKey().getNamespace()));
        String name = withoutMolten(getResult());
        String prefix = "smeltery/melting/" + type + "/" + getItemKey().getNamespace() + "/" + name + "/";
        toRecipe().save(finalConsumer, location(prefix.formatted(getItemKey().getNamespace()) + getItemKey().getPath()));
        return this;
    }

    private static final int MOLTEN_LENGTH = "molten_".length();
    public static String withoutMolten(ResourceLocation fluid) {
        return fluid.getPath().substring(MOLTEN_LENGTH);
    }

    ResourceLocation location(String name) {
        return TCompat.getResource(name);
    }

    Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();
        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }
        return builder.build(consumer);
    }
}
