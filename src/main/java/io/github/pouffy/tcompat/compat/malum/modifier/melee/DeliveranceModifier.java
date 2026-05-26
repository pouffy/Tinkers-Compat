package io.github.pouffy.tcompat.compat.malum.modifier.melee;

import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.world.effect.MobEffectInstance;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.concurrent.atomic.AtomicReference;

public class DeliveranceModifier extends NoLevelsModifier implements MeleeDamageModifierHook {

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
        super.registerHooks(hookBuilder);
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        var target = context.getLivingTarget();
        if (target == null) return damage;
        AtomicReference<Float> newDamage = new AtomicReference<>(damage);
        ObjectRetriever.getEffect("malum:imminent_deliverance").ifPresent(effect -> {
            if (target.hasEffect(effect)) {
                newDamage.set(newDamage.get() * 2);
                MalumHandler.deliverance(target, context.getAttacker());
                target.removeEffect(effect);
            } else {
                newDamage.set(newDamage.get() * 0.5f);
                if (context.makeDamageSource().is(ObjectRetriever.damageKey("malum:hidden_blade_counter")) && context.getAttacker().getRandom().nextFloat() >= 0.4F) {
                    return;
                }
                target.addEffect(new MobEffectInstance(effect, 60));
            }
        });
        return newDamage.get();
    }
}
