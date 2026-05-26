package io.github.pouffy.tcompat.compat.malum.modifier.melee;

import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MonsterMeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CertaintyModifier extends NoLevelsModifier implements MeleeDamageModifierHook, MonsterMeleeHitModifierHook.RedirectAfter, MeleeHitModifierHook {

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.MONSTER_MELEE_DAMAGE, ModifierHooks.MELEE_HIT, ModifierHooks.MONSTER_MELEE_DAMAGE);
        super.registerHooks(hookBuilder);
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return MalumHandler.weightOfWorlds(damage, context.getAttacker(), context.getTarget());
    }

    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (target == null) return;
        if (target.isDeadOrDying()) {
            ObjectRetriever.getEffect("malum:grim_certainty").ifPresent(effect -> context.getAttacker().addEffect(new MobEffectInstance(effect, 200)));
        }
    }

    public static void addEffect(LivingEntity attacker) {
        if (attacker == null) return;
        ObjectRetriever.getEffect("malum:grim_certainty").ifPresent(effect -> attacker.addEffect(new MobEffectInstance(effect, 200)));
    }
}
