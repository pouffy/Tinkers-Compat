package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.common.fluid.TCFluids;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;

import java.util.Locale;

@Getter
public enum TCByproduct implements IByproduct {
    ZANITE(false, TCFluids.moltenZanite, FluidValues.GEM, FluidValues.GEM_SHARD, IMeltingContainer.OreRateType.GEM),
    SKYJADE(false, TCFluids.moltenSkyjade, FluidValues.GEM, FluidValues.GEM_SHARD, IMeltingContainer.OreRateType.GEM),
    GRAVITITE(false, TCFluids.moltenGravitite),
    VERIDIUM(false, TCFluids.moltenVeridium),
    REFINED_SENTRITE(false, TCFluids.moltenRefinedSentrite),
    QUARTZ(true, TinkerFluids.moltenQuartz, FluidValues.GEM, FluidValues.GEM_SHARD, IMeltingContainer.OreRateType.GEM),
    ;

    private final String name;
    private final boolean alwaysPresent;
    private final FluidObject<?> fluid;
    private final int amount;
    private final int damageUnit;
    private final IMeltingContainer.OreRateType oreRate;

    TCByproduct(boolean alwaysPresent, FluidObject<?> fluid, int amount, int damageUnit, IMeltingContainer.OreRateType oreRate) {
        this.name = this.name().toLowerCase(Locale.ROOT);
        this.alwaysPresent = alwaysPresent;
        this.fluid = fluid;
        this.amount = amount;
        this.damageUnit = damageUnit;
        this.oreRate = oreRate;
    }

    TCByproduct(boolean alwaysPresent, FluidObject<?> fluid) {
        this(alwaysPresent, fluid, FluidValues.INGOT, FluidValues.NUGGET, IMeltingContainer.OreRateType.METAL);
    }

    TCByproduct(String name, boolean alwaysPresent, FluidObject<?> fluid, int amount, int damageUnit, IMeltingContainer.OreRateType oreRate) {
        this.name = name;
        this.alwaysPresent = alwaysPresent;
        this.fluid = fluid;
        this.amount = amount;
        this.damageUnit = damageUnit;
        this.oreRate = oreRate;
    }

    @Override
    public @NotNull FluidOutput getFluid(float scale) {
        return fluid.result((int)(amount * scale));
    }
}
