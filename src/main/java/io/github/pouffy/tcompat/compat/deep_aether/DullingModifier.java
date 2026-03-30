package io.github.pouffy.tcompat.compat.deep_aether;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class DullingModifier extends NoLevelsModifier implements BreakSpeedModifierHook, MeleeDamageModifierHook, ProtectionModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED, ModifierHooks.MELEE_DAMAGE, ModifierHooks.PROTECTION);
    }

    @Override
    public Component getDisplayName(int level) {
        return ModifierLevelDisplay.NO_LEVELS.nameForLevel(this, level);
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        event.setNewSpeed(decreaseStat(tool, miningSpeedModifier * tool.getMultiplier(ToolStats.MINING_SPEED)));
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return decreaseStat(tool, damage);
    }

    @Override
    public float getProtectionModifier(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float modifierValue) {
        return decreaseStat(tool, modifierValue);
    }

    float decreaseStat(IToolStackView tool, float stat) {
        return (float)((double)stat / ((double)2.0F * tool.getDamage() / tool.getMultiplier(ToolStats.DURABILITY)) + (double)0.5F);
    }
}
