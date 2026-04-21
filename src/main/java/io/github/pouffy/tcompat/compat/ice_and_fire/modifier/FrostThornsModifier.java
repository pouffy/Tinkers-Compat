package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import io.github.pouffy.tcompat.common.capability.frozen.Frozen;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class FrostThornsModifier extends NoLevelsModifier implements OnAttackedModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity attacker = (LivingEntity) source.getEntity();
        boolean canUse = false;
        if (context.getEntity() instanceof Player player) {
            canUse = !player.getCooldowns().isOnCooldown(tool.getItem());
        }
        if (attacker != null) {
            canUse = attacker.getRandom().nextIntBetweenInclusive(1, 16) < 3;
        }
        if (attacker != null && isDirectDamage && canUse) {
            LivingEntity user = context.getEntity();
            ToolDamageUtil.damageAnimated(tool, 1, user, slotType);
            int freezeTicks = Math.round((amount * 1.5f) * 20);
            Frozen.get(attacker).ifPresent((frozen) -> frozen.freeze(freezeTicks));
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            attacker.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
            attacker.knockback(1.0, 0.0, 0.0);
            if (context.getEntity() instanceof Player player) {
                player.getCooldowns().addCooldown(tool.getItem(), Math.round((amount / 2) * 20));
            }
        }
    }
}
