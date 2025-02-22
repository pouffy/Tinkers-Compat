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

        addMaterial(TComMaterialIds.aetherWood, 1, ORDER_HARVEST, true, false, aetherLoadedCondition);
        addMaterial(TComMaterialIds.aetherRock, 1, ORDER_HARVEST, true, false, aetherLoadedCondition);

        addMaterial(TComMaterialIds.fiery, 3, ORDER_WEAPON, false);
        addMaterial(TComMaterialIds.knightmetal, 3, ORDER_WEAPON, false);
        addMaterial(TComMaterialIds.nagascale, 1, ORDER_WEAPON, true);
        addMaterial(TComMaterialIds.ravenFeather, 1, ORDER_WEAPON, true);
        addMaterial(TComMaterialIds.steeleaf, 1, ORDER_WEAPON, true);
    }
}
