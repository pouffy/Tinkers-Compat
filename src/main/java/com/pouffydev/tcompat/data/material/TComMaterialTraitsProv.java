package com.pouffydev.tcompat.data.material;

import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.library.materials.MaterialRegistry.*;

public class TComMaterialTraitsProv extends AbstractMaterialTraitDataProvider {
    public TComMaterialTraitsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialTraits() {
        //addTraits(GTCMaterialIds.sterlingSilver, MELEE_HARVEST, ModifierIds.smite, new ModifierId(TConstruct.MOD_ID, "dwarven"));

        //addDefaultTraits(GTCMaterialIds.bismuthBronze, ModifierIds.maintained);

        //noTraits(GTCMaterialIds.siliconeRubber);

    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Traits";
    }
}
