package io.github.pouffy.tcompat.common.module;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface AetherForgedModifierHook {

    boolean canUse(IToolStackView toolStackView, ModifierEntry modifierEntry);
}
