package io.github.pouffy.tcompat.common.module;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface ToolSwingModifierHook {

    boolean swingMainHand(IToolStackView toolStackView, ModifierEntry modifierEntry, ItemStack stack, LivingEntity playerIn);

    default boolean swingOffHand(IToolStackView toolStackView, ModifierEntry modifierEntry, ItemStack stack, LivingEntity playerIn) {
        return false;
    };

    record AllMerger(Collection<ToolSwingModifierHook> modules) implements ToolSwingModifierHook {
        @Override
        public boolean swingMainHand(IToolStackView toolStackView, ModifierEntry modifierEntry, ItemStack stack, LivingEntity playerIn) {
            boolean flag = false;
            for(ToolSwingModifierHook module : this.modules) {
                boolean success = module.swingMainHand(toolStackView, modifierEntry, stack, playerIn);
                if (success) flag = true;
            }
            return flag;
        }

        @Override
        public boolean swingOffHand(IToolStackView toolStackView, ModifierEntry modifierEntry, ItemStack stack, LivingEntity playerIn) {
            boolean flag = false;
            for(ToolSwingModifierHook module : this.modules) {
                boolean success = module.swingOffHand(toolStackView, modifierEntry, stack, playerIn);
                if (success) flag = true;
            }
            return flag;
        }
    }
}
