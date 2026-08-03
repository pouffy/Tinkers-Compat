package io.github.pouffy.tcompat.compat.caverns_chasms.modifier.combat.ranged;

import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbilityHooks;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

public class BluntModifier extends NoLevelsModifier implements ProjectileLaunchModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.PROJECTILE_SHOT, ModifierHooks.PROJECTILE_THROWN);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        ProjectileAbility.activate(projectile, ProjectileAbilityHooks.BLUNT);
    }

    public static void negateDamage(LivingHurtEvent event) {
        var entity = event.getSource().getDirectEntity();
        if (entity instanceof Projectile projectile) {
            if (ProjectileAbility.isActive(projectile, ProjectileAbilityHooks.BLUNT)) {
                event.setCanceled(true);
            }
        }
    }

    public static void negateMeleeDamage(LivingHurtEvent event) {
        var entity = event.getSource().getEntity();
        if (entity instanceof LivingEntity livingEntity && OnAttackedModifierHook.isDirectDamage(event.getSource())) {
            if (EquipmentHelper.hasModifier(livingEntity, TCModifiers.bluntHands)) {
                event.setCanceled(true);
            }
        }
    }
}
