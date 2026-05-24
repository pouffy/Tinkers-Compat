package io.github.pouffy.tcompat.common.modifier.base;

import io.github.pouffy.tcompat.common.modifier.hook.CurioTickModifierHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public abstract class TotemicRuneModifier extends NoLevelsModifier implements CurioTickModifierHook, InventoryTickModifierHook {

    public void curioTick(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, ItemStack stack) {

    }

    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity living, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {

    }
}
