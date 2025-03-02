package com.pouffydev.tcompat.data.modifier;

import com.pouffydev.tcompat.modifier.TComModifierIds;
import com.pouffydev.tcompat.modifier.TComModifiers;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;

import static slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial.ARMOR_SLOTS;

public class TComModifierProv extends AbstractModifierProvider implements IConditionBuilder {
    public TComModifierProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        EquipmentSlot[] armorSlots = ARMOR_SLOTS;

        buildModifier(TComModifierIds.aetherForged, modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);

        buildModifier(TComModifiers.precipitate.getId(), modLoaded("twilightforest"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.stalwart.getId(), modLoaded("twilightforest"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.superheat.getId(), modLoaded("twilightforest"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.synergy.getId(), modLoaded("twilightforest"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.veiled.getId(), modLoaded("twilightforest"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.twilit.getId(), modLoaded("twilightforest"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);

        buildModifier(TComModifiers.ascension.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.acclimatization.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        //buildModifier(TComModifiers.ambrofusion.getId(), modLoaded("aether_redux"))
        //        .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TComModifiers.ambrogen.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.DEFAULT);
        buildModifier(TComModifiers.orbit.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.DEFAULT);
        buildModifier(TComModifiers.dulling.getId(), modLoaded("deep_aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        //buildModifier(TComModifiers.sentryflare.getId(), modLoaded("aether_redux"))
        //        .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
