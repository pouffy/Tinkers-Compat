package io.github.pouffy.tcompat.datagen.fluid;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.data.PackOutput;
import slimeknights.mantle.fluid.tooltip.AbstractFluidTooltipProvider;

public class TCFluidTooltipProv extends AbstractFluidTooltipProvider {
    public TCFluidTooltipProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }

    @Override
    protected void addFluids() {

    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid Tooltip Provider";
    }
}
