package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        Projectile projectile = (Projectile) (Object) this;
        PhoenixTouched.get(projectile).ifPresent(phoenixTouched -> {
            Projectile innerProjectile = phoenixTouched.getProjectile();
            if (phoenixTouched.isPhoenixProjectile() && testValid(innerProjectile)) {
                if (!innerProjectile.level().isClientSide()) {
                    phoenixTouched.setSynched(INBTSynchable.Direction.CLIENT, "setPhoenixProjectile", true); // Sync Phoenix Projectile variable to client.
                    for (int i = 0; i < 2; i++) {
                        this.spawnParticles(innerProjectile);
                    }
                }
            }
        });
    }

    private void spawnParticles(Projectile projectile) {
        if (projectile.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    projectile.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    projectile.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                    projectile.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    1, 0.0, 0.0, 0.0, 0.0F);
        }
    }

    private boolean testValid(Projectile projectile) {
        return !(projectile instanceof AbstractArrow);
    }
}
