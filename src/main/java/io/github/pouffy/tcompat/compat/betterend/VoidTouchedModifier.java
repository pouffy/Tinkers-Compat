package io.github.pouffy.tcompat.compat.betterend;

import io.github.pouffy.tcompat.common.capability.TCSounds;
import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouched;
import io.github.pouffy.tcompat.common.data.TCTags;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import javax.annotation.Nullable;

public class VoidTouchedModifier extends Modifier implements ProjectileHitModifierHook, MeleeHitModifierHook, BreakSpeedModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.MELEE_HIT, ModifierHooks.BREAK_SPEED);
    }

    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
        if (notBlocked && target != null) {
            voidTarget(target, modifier.getLevel());
        }
        return notBlocked;
    }

    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (target != null) {
            voidTarget(target, modifier.getLevel());
        }
    }

    private void voidTarget(LivingEntity target, int modifierLevel) {
        VoidTouched.get(target).ifPresent(touched -> {
            if (touched.isVoided()) {
                int amplifier = touched.getAmplifier();
                int duration = touched.getDuration();
                target.level().playSound(null, target.blockPosition(), TCSounds.VOID_TOUCHED_ACTIVATE.getSound(), SoundSource.PLAYERS, 1.0f, 1.0f + (0.001f * duration));
                touched.setAmplifier(amplifier + modifierLevel);
                touched.setDuration(duration + modifierLevel * 20);
            } else {
                touched.setVoided(true);
                touched.setAmplifier(modifierLevel);
                touched.setDuration(modifierLevel * 60);
            }
        });
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isEffective && event.getState().is(TCTags.Blocks.VOID_TOUCHED_EFFICIENT)) {
            event.setNewSpeed(event.getNewSpeed() * 3);
        }
    }
}
