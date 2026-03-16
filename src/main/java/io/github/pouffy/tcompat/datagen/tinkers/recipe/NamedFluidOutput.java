package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.recipe.helper.FluidOutput;

@RequiredArgsConstructor
public class NamedFluidOutput extends FluidOutput {

    private final ResourceLocation id;
    @Getter
    private final int amount;

    @Override
    public FluidStack get() {
        Fluid fluid = BuiltInRegistries.FLUID.get(id);
        return new FluidStack(fluid, amount);
    }

    @Override
    public void serialize(JsonObject json) {
        if (amount > 0) {
            json.addProperty("fluid", this.id.toString());
        }
        json.addProperty("amount", amount);
    }
}
