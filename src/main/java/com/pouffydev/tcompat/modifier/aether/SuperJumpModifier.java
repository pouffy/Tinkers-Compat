package com.pouffydev.tcompat.modifier.aether;

import com.pouffydev.tcompat.modifier.SetBonusModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.capability.TinkerDataKeys;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class SuperJumpModifier extends SetBonusModifier implements ProtectionModifierHook, TooltipModifierHook, EquipmentChangeModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROTECTION, ModifierHooks.TOOLTIP, ModifierHooks.EQUIPMENT_CHANGE);
    }

    public static void addStat(EquipmentChangeContext context, TinkerDataCapability.TinkerDataKey<Float> key, float amount) {
        context.getTinkerData().ifPresent(data -> {
            float totalLevels = data.get(key, 0f) + amount;
            if (totalLevels <= 0.005f) {
                data.remove(key);
            } else {
                data.put(key, totalLevels);
            }
        });
    }

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (context.getChangedSlot().getType() == EquipmentSlot.Type.ARMOR && !tool.isBroken() && getArmorCoverage() > 0.6) {
            addStat(context, TinkerDataKeys.JUMP_BOOST, 2);
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (context.getChangedSlot().getType() == EquipmentSlot.Type.ARMOR && !tool.isBroken()) {
            addStat(context, TinkerDataKeys.JUMP_BOOST, -2);
        }
    }

    @Override
    public float getProtectionModifier(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float modifierValue) {
        if (source.is(DamageTypes.FALL)) {
            return 10 * getArmorCoverage();
        }
        return 0;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        float prot = 10 * getArmorCoverage();
        if (prot > 0) {
            TooltipModifierHook.addPercentBoost(this, getDisplayName(), prot, tooltip);
        }
    }
}
