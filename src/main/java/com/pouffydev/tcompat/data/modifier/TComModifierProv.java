package com.pouffydev.tcompat.data.modifier;

import com.pouffydev.tcompat.modifier.TComModifierIds;
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
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
