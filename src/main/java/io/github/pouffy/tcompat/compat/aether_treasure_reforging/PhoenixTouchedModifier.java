package io.github.pouffy.tcompat.compat.aether_treasure_reforging;

import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.data.ModifierIds;

//TODO: Test & Fix this with bows
public class PhoenixTouchedModifier extends Modifier implements ProjectileLaunchModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.PROJECTILE_SHOT, ModifierHooks.PROJECTILE_THROWN);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        touch(tool, projectile);
    }

    private void touch(IToolStackView tool, Projectile projectile) {
        PhoenixTouched.get(projectile).ifPresent(phoenixProjectile -> {
            phoenixProjectile.setPhoenixProjectile(true);
            int defaultTime = 20;
            if (tool.getModifierLevel(ModifierIds.fiery) > 0) {
                defaultTime = 40;
            }
            phoenixProjectile.setFireTime(defaultTime);
        });
    }
}
