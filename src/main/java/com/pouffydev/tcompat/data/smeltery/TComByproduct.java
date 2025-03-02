package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.fluids.TComFluids;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum TComByproduct implements IByproduct {
    ZANITE("zanite",false, TComFluids.moltenZanite, FluidValues.GEM),
    SKYJADE("skyjade", false, TComFluids.moltenSkyjade, FluidValues.GEM),
    GRAVITITE("gravitite", false, TComFluids.moltenGravitite, FluidValues.INGOT),
    VERIDIUM("veridium", false, TComFluids.moltenVeridium, FluidValues.INGOT),
    REFINED_SENTRITE("refined_sentrite", false, TComFluids.moltenRefinedSentrite, FluidValues.INGOT),
    ;

    private final String name;
    private final boolean alwaysPresent;
    private final FluidObject<?> fluid;
    private final int amount;

    TComByproduct(boolean alwaysPresent, FluidObject<?> fluid) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.alwaysPresent = alwaysPresent;
        this.fluid = fluid;
        this.amount = FluidValues.INGOT;
    }

    @Override
    public FluidOutput getFluid(float scale) {
        return fluid.result((int)(amount * scale));
    }
}
