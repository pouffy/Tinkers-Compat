package com.pouffydev.tcompat.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class SetBonusModifier extends NoLevelsModifier implements InventoryTickModifierHook {

    private float armorCoverage = 0f;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        armorCoverage = calcArmorCoverage(holder);
    }

    public float getArmorCoverage() {
        return armorCoverage;
    }

    private float calcArmorCoverage(LivingEntity holder) {
        if (armorCoverage < 0) {
            armorCoverage = 0;
        }
        if (armorCoverage > 1) {
            armorCoverage = 1;
        }
        for (ItemStack stack : holder.getArmorSlots()) {
            ToolStack tool = ToolStack.copyFrom(stack);
            if (tool.getModifier(this.getId()) != ModifierEntry.EMPTY) {
                armorCoverage += 0.2f;
            } else {
                armorCoverage -= 0.2f;
            }
        }
        return armorCoverage;
    }
}
