package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        Projectile projectile = (Projectile) (Object) this;
        ProjectileAbility.get(projectile).ifPresent(projectileAbility -> {
            Projectile innerProjectile = projectileAbility.projectile();
            if (tcompat$testValid(innerProjectile)) {
                projectileAbility.getActiveAbilities().forEach((name, ability) -> {
                    ability.tickProjectile(projectile, innerProjectile);
                    projectileAbility.sendClient(ability);
                });
            }
        });
    }

    @Unique
    private boolean tcompat$testValid(Projectile projectile) {
        return !(projectile instanceof AbstractArrow);
    }
}
