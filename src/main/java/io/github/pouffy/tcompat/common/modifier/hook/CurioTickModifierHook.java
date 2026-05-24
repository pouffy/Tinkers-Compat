package io.github.pouffy.tcompat.common.modifier.hook;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface CurioTickModifierHook {

    void curioTick(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, ItemStack stack);

    record AllMerger(Collection<CurioTickModifierHook> modules) implements CurioTickModifierHook {
        @Override
        public void curioTick(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, ItemStack stack) {
            for (CurioTickModifierHook hook : modules) {
                hook.curioTick(tool, entry, slotId, slotIndex, wearer, stack);
            }
        }
    }
}
