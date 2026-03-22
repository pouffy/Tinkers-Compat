package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import static slimeknights.tconstruct.library.materials.MaterialRegistry.*;

@MethodsReturnNonnullByDefault
public class TCMaterialTraitsProv extends AbstractMaterialTraitDataProvider {
    public TCMaterialTraitsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Traits";
    }

    @Override
    protected void addMaterialTraits() {
        addDefaultTraits(TCMaterials.aetherWood, TCModifiers.aetherForged, ModifierIds.cultivated);
        addTraits(TCMaterials.aetherWood, AMMO, ModifierIds.economical);
        addDefaultTraits(TCMaterials.aetherRock, new ModifierEntry(TCModifiers.aetherForged, 1), new ModifierEntry(AetherInit.ambrogen, 1), new ModifierEntry(ModifierIds.stonebound, 1));
        addDefaultTraits(TCMaterials.zanite, new ModifierEntry(TCModifiers.aetherForged, 1), new ModifierEntry(AetherInit.acclimatization, 1));
        addTraits(TCMaterials.zanite, AMMO, ModifierIds.crystalbound);
        addDefaultTraits(TCMaterials.gravitite, TCModifiers.aetherForged);
        addTraits(TCMaterials.gravitite, HeadMaterialStats.ID, AetherInit.ascension.getId(), TCModifiers.aetherForged);
        addDefaultTraits(TCMaterials.lightnum, TCModifiers.aetherForged);
        addTraits(TCMaterials.lightnum, HeadMaterialStats.ID, AetherInit.thunderstruck.getId());
        addDefaultTraits(TCMaterials.draculite, TCModifiers.aetherForged);
        addTraits(TCMaterials.draculite, HeadMaterialStats.ID, AetherInit.bloodsucking.getId());

        addDefaultTraits(TCMaterials.skyjade, new ModifierEntry(TCModifiers.aetherForged, 1), new ModifierEntry(DeepAetherInit.dulling, 1));
        addTraits(TCMaterials.skyjade, AMMO, ModifierIds.punch);
        addDefaultTraits(TCMaterials.stormforgedSteel, TCModifiers.aetherForged);
        addTraits(TCMaterials.stormforgedSteel, HeadMaterialStats.ID, DeepAetherInit.gale.getId());

        addDefaultTraits(TCMaterials.veridium, TCModifiers.aetherForged, AetherReduxInit.ambrofusion.getId());
        addDefaultTraits(TCMaterials.refinedSentrite, new ModifierEntry(TCModifiers.aetherForged, 1));

        addDefaultTraits(TCMaterials.hellbark, ModifierIds.fiery);
        addTraits(TCMaterials.hellbark, AMMO, ModifierIds.spectral);

        addDefaultTraits(TCMaterials.blightbunnyFang, AetherReduxInit.blighted);
        addDefaultTraits(TCMaterials.pyral, TCModifiers.aetherForged);
        addTraits(TCMaterials.pyral, ARMOR, ModifierIds.fireProtection);
        addTraits(TCMaterials.pyral, RANGED, AetherTRInit.phoenixTouched);
        addDefaultTraits(TCMaterials.valkyrum, TCModifiers.aetherForged);
        addTraits(TCMaterials.valkyrum, MELEE_HARVEST, ModifierIds.reach);
        addTraits(TCMaterials.valkyrum, ARMOR, ModifierIds.doubleJump);
        addTraits(TCMaterials.neptune, ARMOR, ModifierIds.depthStrider);
        addTraits(TCMaterials.mykapodShell, StatlessMaterialStats.SHIELD_CORE.getIdentifier(), TCModifiers.escarstay);

        addDefaultTraits(TCMaterials.wickedWax, SpeciesInit.wicked);

        addDefaultTraits(TCMaterials.desh, AdAstraInit.oxygenated);
        addDefaultTraits(TCMaterials.calorite, AdAstraInit.oxygenated);
        addDefaultTraits(TCMaterials.ostrum, AdAstraInit.oxygenated);

        addDefaultTraits(TCMaterials.thallasium, BetterendInit.voidTouched);
        addDefaultTraits(TCMaterials.terminite, BetterendInit.voidTouched);
        addDefaultTraits(TCMaterials.aeternium, BetterendInit.voidTouched);

        addDefaultTraits(TCMaterials.cincinnasite, ModifierIds.worldbound);
        addTraits(TCMaterials.cincinnasite, HeadMaterialStats.ID, BetternetherInit.obsidianBreaker);
        addDefaultTraits(TCMaterials.netherRuby, ModifierIds.worldbound);
        addTraits(TCMaterials.netherRuby, HeadMaterialStats.ID, BetternetherInit.obsidianBreaker);
    }
}
