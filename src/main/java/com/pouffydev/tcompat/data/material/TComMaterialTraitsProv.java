package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import com.pouffydev.tcompat.modifier.TComModifierIds;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

public class TComMaterialTraitsProv extends AbstractMaterialTraitDataProvider {
    public TComMaterialTraitsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialTraits() {
        //addTraits(GTCMaterialIds.sterlingSilver, MELEE_HARVEST, ModifierIds.smite, new ModifierId(TConstruct.MOD_ID, "dwarven"));

        addDefaultTraits(TComMaterialIds.aetherWood, TComModifierIds.aetherForged, ModifierIds.cultivated);
        addDefaultTraits(TComMaterialIds.aetherRock, new ModifierEntry(TComModifierIds.aetherForged, 1), new ModifierEntry(TinkerModifiers.stonebound.getId(), 1));

        //noTraits(TComMaterialIds.astralWeave);
        //noTraits(TComMaterialIds.alchemicalCalx);
        //noTraits(TComMaterialIds.spiritFabric);
        //noTraits(TComMaterialIds.soulstone);

    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Traits";
    }
}
