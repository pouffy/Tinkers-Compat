package com.pouffydev.tcompat.modifier.malum.rune;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class RuneModifier extends NoLevelsModifier implements ModifierRemovalHook {
    @Override
    public @Nullable Component onRemoved(IToolStackView tool, Modifier modifier) {
        return null;
    }
}
