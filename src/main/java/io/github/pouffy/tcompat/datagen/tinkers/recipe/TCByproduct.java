package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;

import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum TCByproduct implements IByproduct {
    ZANITE("zanite",false, AetherInit.moltenZanite, FluidValues.GEM, IMeltingContainer.OreRateType.GEM),
    SKYJADE("skyjade", false, DeepAetherInit.moltenSkyjade, FluidValues.GEM, IMeltingContainer.OreRateType.GEM),
    GRAVITITE("gravitite", false, AetherInit.moltenGravitite, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL),
    VERIDIUM("veridium", false, AetherReduxInit.moltenVeridium, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL),
    REFINED_SENTRITE("refined_sentrite", false, AetherReduxInit.moltenRefinedSentrite, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL),
    QUARTZ("quartz", true, TinkerFluids.moltenQuartz, FluidValues.GEM, IMeltingContainer.OreRateType.GEM),
    ;

    private final String name;
    private final boolean alwaysPresent;
    private final FluidObject<?> fluid;
    private final int amount;
    private final IMeltingContainer.OreRateType oreRate;

    TCByproduct(boolean alwaysPresent, FluidObject<?> fluid, int amount, IMeltingContainer.OreRateType oreRate) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.alwaysPresent = alwaysPresent;
        this.fluid = fluid;
        this.amount = amount;
        this.oreRate = oreRate;
    }

    TCByproduct(boolean alwaysPresent, FluidObject<?> fluid) {
        this(alwaysPresent, fluid, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL);
    }

    @Override
    public FluidOutput getFluid(float scale) {
        return fluid.result((int)(amount * scale));
    }
}
