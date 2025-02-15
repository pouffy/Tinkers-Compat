package com.pouffydev.tcompat.data.modifier;

import com.pouffydev.tcompat.modifier.TComModifierIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;

public class TComModifierProv extends AbstractModifierProvider implements IConditionBuilder {
    public TComModifierProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        buildModifier(TComModifierIds.aetherForged, modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
        ;
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
