package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
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
        ICondition aetherLoadedCondition = new ModLoadedCondition("aether");
        ICondition deepAetherLoadedCondition = new ModLoadedCondition("deep_aether");
        ICondition aetherReduxLoadedCondition = new ModLoadedCondition("aether_redux");
        ICondition twilightLoadedCondition = new ModLoadedCondition("twilightforest");

        addMaterial(TComMaterialIds.aetherWood, 1, ORDER_HARVEST, true, false, aetherLoadedCondition);
        addMaterial(TComMaterialIds.aetherRock, 1, ORDER_HARVEST, true, false, aetherLoadedCondition);

        addMaterial(TComMaterialIds.fiery, 3, ORDER_WEAPON, false, false, twilightLoadedCondition);
        addMaterial(TComMaterialIds.knightmetal, 3, ORDER_WEAPON, false, false, twilightLoadedCondition);
        addMaterial(TComMaterialIds.nagascale, 1, ORDER_WEAPON, true, false, twilightLoadedCondition);
        addMaterial(TComMaterialIds.ravenFeather, 1, ORDER_WEAPON, true, false, twilightLoadedCondition);
        addMaterial(TComMaterialIds.steeleaf, 1, ORDER_WEAPON, true, false, twilightLoadedCondition);

        addMaterial(TComMaterialIds.zanite, 2, ORDER_HARVEST, false, false, aetherLoadedCondition);
        addMaterial(TComMaterialIds.gravitite, 3, ORDER_HARVEST, false, false, aetherLoadedCondition);

        addMaterial(TComMaterialIds.skyjade, 2, ORDER_HARVEST, false, false, deepAetherLoadedCondition);

        addMaterial(TComMaterialIds.veridium, 3, ORDER_HARVEST, false, false, aetherReduxLoadedCondition);
        addMaterial(TComMaterialIds.refinedSentrite, 4, ORDER_HARVEST, false, false, aetherReduxLoadedCondition);

    }
}
