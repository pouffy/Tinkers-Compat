package io.github.pouffy.tcompat.common.modifier.hook;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface SoulExposureModifierHook {
    boolean canUse(IToolStackView toolStackView, ModifierEntry modifierEntry);
}
