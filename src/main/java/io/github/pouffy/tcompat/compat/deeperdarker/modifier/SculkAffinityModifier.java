package io.github.pouffy.tcompat.compat.deeperdarker.modifier;

import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class SculkAffinityModifier extends NoLevelsModifier implements ProjectileLaunchModifierHook, MeleeHitModifierHook, OnAttackedModifierHook, BlockBreakModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.MELEE_HIT, ModifierHooks.ON_ATTACKED, ModifierHooks.BLOCK_BREAK);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        ObjectRetriever.getEffect("deeperdarker:sculk_affinity").ifPresent(effect -> shooter.addEffect(new MobEffectInstance(effect, 100)));
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()) {
            ObjectRetriever.getEffect("deeperdarker:sculk_affinity").ifPresent(effect -> context.getAttacker().addEffect(new MobEffectInstance(effect, 100)));
        }
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        boolean canUse = context.getEntity().getRandom().nextIntBetweenInclusive(1, 16) < 5 && isDirectDamage;
        if (canUse) {
            ObjectRetriever.getEffect("deeperdarker:sculk_affinity").ifPresent(effect -> context.getEntity().addEffect(new MobEffectInstance(effect, 200)));
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        ObjectRetriever.getEffect("deeperdarker:sculk_affinity").ifPresent(effect -> context.getLiving().addEffect(new MobEffectInstance(effect, 100)));
    }
}
