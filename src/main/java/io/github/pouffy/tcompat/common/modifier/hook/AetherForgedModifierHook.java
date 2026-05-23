package io.github.pouffy.tcompat.common.modifier.hook;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public interface AetherForgedModifierHook {

    boolean canUse(IToolStackView toolStackView, ModifierEntry modifierEntry);

    boolean canProjectileUse(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifierEntry);
}
