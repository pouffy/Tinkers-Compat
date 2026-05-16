package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.capability.projectile.phoenix_touched.PhoenixTouched;
import io.github.pouffy.tcompat.common.capability.projectile.leeching.Leeching;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.compat.ice_and_fire.modifier.IceFireHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
        PhoenixTouched.get(projectile).ifPresent(phoenixTouched -> {
            Projectile innerProjectile = phoenixTouched.getProjectile();
            if (phoenixTouched.isPhoenixProjectile() && tcompat$testValid(innerProjectile)) {
                if (!innerProjectile.level().isClientSide()) {
                    phoenixTouched.setSynched(INBTSynchable.Direction.CLIENT, "setPhoenixProjectile", true); // Sync Phoenix Projectile variable to client.
                    for (int i = 0; i < 2; i++) {
                        this.tcompat$phoenixParticles(innerProjectile);
                    }
                }
            }
        });
        Leeching.get(projectile).ifPresent(leeching -> {
            Projectile innerProjectile = leeching.getProjectile();
            if (tcompat$testValid(innerProjectile)) {
                if (leeching.isLeeching()) {
                    for (int i = 0; i < 2; i++) {
                        this.tcompat$leechingParticles(innerProjectile);
                    }
                    leeching.setSynched(INBTSynchable.Direction.CLIENT, "setLeeching", true); // Sync Leeching variable to client.
                }
                if (leeching.isAmphithere()) {
                    if ((projectile.tickCount == 1 || projectile.tickCount % 70 == 0) && !projectile.onGround()) {
                        IceFireHandler.amphithereGust(projectile);
                    }
                    this.tcompat$amphithereParticles(innerProjectile);
                    leeching.setSynched(INBTSynchable.Direction.CLIENT, "setAmphithere", true); // Sync Amphithere variable to client.
                }
                if (leeching.isStymphalian()) {
                    float sqrt = Mth.sqrt((float)(projectile.getDeltaMovement().x * projectile.getDeltaMovement().x + projectile.getDeltaMovement().z * projectile.getDeltaMovement().z));
                    if (sqrt < 0.1F) {
                        projectile.setDeltaMovement(projectile.getDeltaMovement().add(0.0F, -0.01F, 0.0F));
                    }
                    if (!projectile.isNoGravity()) projectile.setNoGravity(true);
                    leeching.setSynched(INBTSynchable.Direction.CLIENT, "setStymphalian", true); // Sync Stymphalian variable to client.
                }
            }
        });
    }

    @Unique
    private void tcompat$phoenixParticles(Projectile projectile) {
        if (projectile.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    projectile.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    projectile.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                    projectile.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    1, 0.0, 0.0, 0.0, 0.0F);
        }
    }

    @Unique
    private void tcompat$leechingParticles(Projectile projectile) {
        RandomSource random = projectile.level().random;
        if (projectile.level().isClientSide && !projectile.onGround()) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double xRatio = projectile.getDeltaMovement().x * (double)projectile.getBbHeight();
            double zRatio = projectile.getDeltaMovement().z * (double)projectile.getBbHeight();
            IceFireHandler.hydraParticles(projectile, xRatio, zRatio, d0, d1, d2);
        }
    }

    @Unique
    private void tcompat$amphithereParticles(Projectile projectile) {
        RandomSource random = projectile.level().random;
        if (projectile.level().isClientSide && !projectile.onGround()) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double xRatio = projectile.getDeltaMovement().x * (double) projectile.getBbWidth();
            double zRatio = projectile.getDeltaMovement().z * (double) projectile.getBbWidth();
            projectile.level().addParticle(ParticleTypes.CLOUD, projectile.getX() + xRatio + (double) (random.nextFloat() * projectile.getBbWidth() * 1.0F) - (double) projectile.getBbWidth() - d0 * (double) 10.0F, projectile.getY() + (double) (random.nextFloat() * projectile.getBbHeight()) - d1 * (double) 10.0F, projectile.getZ() + zRatio + (double) (random.nextFloat() * projectile.getBbWidth() * 1.0F) - (double) projectile.getBbWidth() - d2 * (double) 10.0F, d0, d1, d2);
        }
    }

    @Unique
    private boolean tcompat$testValid(Projectile projectile) {
        return !(projectile instanceof AbstractArrow);
    }
}
