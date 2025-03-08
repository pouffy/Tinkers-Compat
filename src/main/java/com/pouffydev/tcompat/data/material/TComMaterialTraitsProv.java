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

import static slimeknights.tconstruct.library.materials.MaterialRegistry.*;

public class TComMaterialTraitsProv extends AbstractMaterialTraitDataProvider {
    public TComMaterialTraitsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialTraits() {
        //addTraits(GTCMaterialIds.sterlingSilver, MELEE_HARVEST, ModifierIds.smite, new ModifierId(TConstruct.MOD_ID, "dwarven"));

        addDefaultTraits(TComMaterialIds.aetherWood, TComModifierIds.aetherForged, ModifierIds.cultivated);
        addDefaultTraits(TComMaterialIds.aetherRock, new ModifierEntry(TComModifierIds.aetherForged, 1), new ModifierEntry(TComModifiers.ambrogen, 1), new ModifierEntry(TinkerModifiers.stonebound.getId(), 1));
        addDefaultTraits(TComMaterialIds.zanite, new ModifierEntry(TComModifierIds.aetherForged, 1), new ModifierEntry(TComModifiers.acclimatization, 1));
        addDefaultTraits(TComMaterialIds.gravitite, TComModifierIds.aetherForged);
        addDefaultTraits(TComMaterialIds.skyjade, new ModifierEntry(TComModifierIds.aetherForged, 1), new ModifierEntry(TComModifiers.dulling, 1));
        addDefaultTraits(TComMaterialIds.veridium, TComModifierIds.aetherForged, TComModifiers.ambrofusion.getId());
        addDefaultTraits(TComMaterialIds.refinedSentrite, new ModifierEntry(TComModifierIds.aetherForged, 1));

        addTraits(TComMaterialIds.gravitite, MELEE_HARVEST, TComModifiers.ascension);
        addTraits(TComMaterialIds.gravitite, RANGED, TComModifiers.ascension);
        addTraits(TComMaterialIds.gravitite, ARMOR, TComModifiers.superJump);

        addTraits(TComMaterialIds.refinedSentrite, MELEE_HARVEST, ModifierIds.blasting);
        addTraits(TComMaterialIds.refinedSentrite, RANGED, TinkerModifiers.punch);
        addTraits(TComMaterialIds.refinedSentrite, ARMOR, TComModifiers.blastDeterrance);

        addDefaultTraits(TComMaterialIds.twilightWood, TComModifiers.twilit.getId(), TComModifiers.uprooting.getId());
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
