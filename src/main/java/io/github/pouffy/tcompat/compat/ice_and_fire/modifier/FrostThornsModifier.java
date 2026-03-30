package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import io.github.pouffy.tcompat.common.capability.compatible.Compatibility;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
        if (attacker != null && isDirectDamage) {
            LivingEntity user = context.getEntity();
            ToolDamageUtil.damageAnimated(tool, 1, user, slotType);
            Compatibility.get(attacker).ifPresent((compat) -> compat.freeze(200));
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            attacker.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
            attacker.knockback(1.0, 0.0, 0.0);
            if (attacker.getType().is(TCEntityTagProv.create("tcompat:fire_dragons"))) {
                attacker.knockback(1.0, user.getX() - attacker.getX(), user.getZ() - attacker.getZ());
                attacker.hurt(attacker.damageSources().drown(), 13.5F);
            }
        }
    }
}
