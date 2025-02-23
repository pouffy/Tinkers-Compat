package com.pouffydev.tcompat.data.smeltery;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.fluids.TComFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import slimeknights.mantle.fluid.transfer.AbstractFluidContainerTransferProvider;
import slimeknights.tconstruct.library.recipe.FluidValues;
import twilightforest.init.TFItems;

public class TComFluidTransferProv extends AbstractFluidContainerTransferProvider {
    public TComFluidTransferProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }

    @Override
    protected void addTransfers() {
        //addFillEmpty("fiery_blood_", TFItems.FIERY_BLOOD.get(),  Items.GLASS_BOTTLE, TComFluids.fieryEssence,        FluidValues.BOTTLE, false);
        //addFillEmpty("fiery_tears_", TFItems.FIERY_TEARS.get(),  Items.GLASS_BOTTLE, TComFluids.fieryEssence,        FluidValues.BOTTLE, false);
    }

    @Override
    public String getName() {
        return "";
    }
}
