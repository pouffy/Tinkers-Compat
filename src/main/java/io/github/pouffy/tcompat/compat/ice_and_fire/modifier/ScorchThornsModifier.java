package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.world.damagesource.DamageSource;
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

public class ScorchThornsModifier extends NoLevelsModifier implements OnAttackedModifierHook {

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
            if (!attacker.isOnFire()) {
                attacker.setSecondsOnFire(5);
                attacker.knockback(1.0, user.getX() - attacker.getX(), user.getZ() - attacker.getZ());
            }
            if (attacker.getType().is(TCEntityTagProv.create("tcompat:ice_dragons"))) {
                attacker.hurt(attacker.damageSources().inFire(), 13.5F);
            }
        }
    }
}
