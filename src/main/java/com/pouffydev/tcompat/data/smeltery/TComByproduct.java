package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.fluids.TComFluids;
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
public enum TComByproduct implements IByproduct {
    ZANITE("zanite",false, TComFluids.moltenZanite, FluidValues.GEM, IMeltingContainer.OreRateType.GEM),
    SKYJADE("skyjade", false, TComFluids.moltenSkyjade, FluidValues.GEM, IMeltingContainer.OreRateType.GEM),
    GRAVITITE("gravitite", false, TComFluids.moltenGravitite, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL),
    VERIDIUM("veridium", false, TComFluids.moltenVeridium, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL),
    REFINED_SENTRITE("refined_sentrite", false, TComFluids.moltenRefinedSentrite, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL),
    QUARTZ("quartz", true, TinkerFluids.moltenQuartz, FluidValues.GEM, IMeltingContainer.OreRateType.GEM),
    ;

    private final String name;
    private final boolean alwaysPresent;
    private final FluidObject<?> fluid;
    private final int amount;
    private final IMeltingContainer.OreRateType oreRate;

    TComByproduct(boolean alwaysPresent, FluidObject<?> fluid, int amount, IMeltingContainer.OreRateType oreRate) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.alwaysPresent = alwaysPresent;
        this.fluid = fluid;
        this.amount = amount;
        this.oreRate = oreRate;
    }

    TComByproduct(boolean alwaysPresent, FluidObject<?> fluid) {
        this(alwaysPresent, fluid, FluidValues.INGOT, IMeltingContainer.OreRateType.METAL);
    }

    @Override
    public FluidOutput getFluid(float scale) {
        return fluid.result((int)(amount * scale));
    }
}
