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
    ZANITE(false, TComFluids.moltenZanite),
    SKYJADE(false, TComFluids.moltenSkyjade),
    GRAVITITE(false, TComFluids.moltenGravitite),
    VERIDIUM(false, TComFluids.moltenVeridium),
    STRATUS(false, TComFluids.moltenStratus),
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
