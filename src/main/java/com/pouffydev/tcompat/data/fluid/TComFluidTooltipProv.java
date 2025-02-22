package com.pouffydev.tcompat.data.fluid;

import com.pouffydev.tcompat.TCompat;
import net.minecraft.data.PackOutput;
import slimeknights.mantle.fluid.tooltip.AbstractFluidTooltipProvider;
import slimeknights.tconstruct.TConstruct;

public class TComFluidTooltipProv extends AbstractFluidTooltipProvider {
    public TComFluidTooltipProv(PackOutput packOutput) {
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
