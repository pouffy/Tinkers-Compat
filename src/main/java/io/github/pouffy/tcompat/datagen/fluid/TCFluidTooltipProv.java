package io.github.pouffy.tcompat.datagen.fluid;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import net.minecraft.data.PackOutput;
import slimeknights.mantle.fluid.tooltip.AbstractFluidTooltipProvider;

public class TCFluidTooltipProv extends AbstractFluidTooltipProvider {
    public TCFluidTooltipProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }

    @Override
    protected void addFluids() {
        this.add("smaller_gem", TCTags.Fluids.SMALLER_GEM_TOOLTIPS).addUnit("block", 720).addUnit("gem", 80).addUnit("shard", 10);
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid Tooltip Provider";
    }
}
