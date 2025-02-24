package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import com.pouffydev.tcompat.modifier.TComModifierIds;
import com.pouffydev.tcompat.modifier.TComModifiers;
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

        addDefaultTraits(TComMaterialIds.fiery, TComModifiers.twilit.getId(), TComModifiers.superheat.getId(), TinkerModifiers.autosmelt.getId(), TinkerModifiers.fiery.getId());
        addDefaultTraits(TComMaterialIds.knightmetal, TComModifiers.twilit.getId(), TComModifiers.stalwart.getId());
        addDefaultTraits(TComMaterialIds.nagascale, TComModifiers.twilit.getId(), TComModifiers.precipitate.getId());
        addDefaultTraits(TComMaterialIds.ravenFeather, TComModifiers.twilit.getId(), TComModifiers.veiled.getId());
        addDefaultTraits(TComMaterialIds.steeleaf, TComModifiers.twilit.getId(), TComModifiers.synergy.getId());

    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Traits";
    }
}
