package com.pouffydev.tcompat.modifier.twilightforest;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class PrecipitateModifier extends NoLevelsModifier implements BreakSpeedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED);
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        event.setNewSpeed(event.getNewSpeed() + (this.getBonusPercentage(event.getEntity()) * event.getOriginalSpeed()));
    }

    private float getBonusPercentage(LivingEntity entity) {
        float maxHealth = entity.getMaxHealth();
        return (maxHealth - entity.getHealth()) / maxHealth;
    }
}
