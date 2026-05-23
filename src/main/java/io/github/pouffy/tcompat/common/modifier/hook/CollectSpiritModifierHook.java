package io.github.pouffy.tcompat.common.modifier.hook;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface CollectSpiritModifierHook {

    boolean canTarget(IToolStackView toolStack, ModifierEntry modifierEntry, LivingEntity collector, double arcaneResonance);

    void pickupSpirit(IToolStackView toolStack, ModifierEntry modifierEntry, LivingEntity collector, double arcaneResonance, float effectiveness);
}
