package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

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
        ICondition aetherLoadedCondition = new ModLoadedCondition("aether");
        ICondition malumLoadedCondition = new ModLoadedCondition("malum");

        addMaterial(TComMaterialIds.aetherWood, 1, ORDER_HARVEST, true, false, aetherLoadedCondition);
        addMaterial(TComMaterialIds.aetherRock, 1, ORDER_HARVEST, true, false, aetherLoadedCondition);

        addMaterial(TComMaterialIds.astralWeave, 1, ORDER_BINDING, true, false, malumLoadedCondition);
        addMaterial(TComMaterialIds.alchemicalCalx, 1, ORDER_BINDING, true, false, malumLoadedCondition);
        addMaterial(TComMaterialIds.spiritFabric, 1, ORDER_BINDING, true, false, malumLoadedCondition);
        addMaterial(TComMaterialIds.soulstone, 2, ORDER_HARVEST, true, false, malumLoadedCondition);
        //addMaterial(GTCMaterialIds.bismuth,                 2, ORDER_COMPAT + ORDER_GENERAL, false);

    }
}
