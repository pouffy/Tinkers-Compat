package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import io.github.pouffy.tcompat.common.capability.compatible.Compatibility;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;

public class IcedModifier extends NoLevelsModifier implements MeleeHitModifierHook, ProjectileHitModifierHook, TooltipModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT, ModifierHooks.TOOLTIP);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getAttacker();

        if (target != null) {
            Compatibility.get(target).ifPresent((compat) -> compat.freeze(200));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
            target.knockback(1.0, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
            if (target.getType().is(TCEntityTagProv.create("tcompat:fire_dragons"))) {
                target.hurt(target.damageSources().drown(), 13.5F);
            }
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target, boolean notBlocked) {
        if (target != null && notBlocked) {
            Compatibility.get(target).ifPresent((compat) -> compat.freeze(200));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
            if (target.getType().is(TCEntityTagProv.create("tcompat:fire_dragons"))) {
                target.hurt(target.damageSources().drown(), 13.5F);
            }
        }
        return notBlocked;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        float amount = 8.0f;
        tooltip.add(applyStyle(Component.literal(Util.BONUS_FORMAT.format(amount)).append(" ").append(Component.translatable("modifier.tcompat.iced.attack_bonus"))));
    }
}
