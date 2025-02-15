package com.pouffydev.tcompat.data.material;

import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class TComMaterialDataProv extends AbstractMaterialDataProvider {
    public TComMaterialDataProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Materials";
    }

    @Override
    protected void addMaterials() {
        //addMaterial(GTCMaterialIds.bismuth,                 2, ORDER_COMPAT + ORDER_GENERAL, false);

    }
}
