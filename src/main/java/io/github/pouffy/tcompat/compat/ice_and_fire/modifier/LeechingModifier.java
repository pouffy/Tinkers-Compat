package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import io.github.pouffy.tcompat.common.capability.projectile.leeching.ProjectileAbility;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.json.RandomLevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileShootModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class LeechingModifier extends NoLevelsModifier implements ProjectileShootModifierHook, MeleeHitModifierHook, ProjectileHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_SHOT, ModifierHooks.PROJECTILE_THROWN, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
        hookBuilder.addModule(MobEffectModule.builder(MobEffects.POISON).time(RandomLevelingValue.flat(300)).build());
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        context.getAttacker().heal(damageDealt);
        if (context.getLivingTarget() instanceof Player player) {
            ProjectileAbility.damageShield(player, damageDealt);
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow) {
            if (attacker != null) attacker.heal((float) arrow.getBaseDamage());
            if (target instanceof Player player) ProjectileAbility.damageShield(player, (float) arrow.getBaseDamage());
        }
        return false;
    }

    private void leech(Projectile projectile, LivingEntity shooter) {
        ProjectileAbility.get(projectile).ifPresent(leeching -> {
            leeching.setLeeching(true);
        });
    }

    @Override
    public void onProjectileShoot(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        leech(projectile, shooter);
    }
}
