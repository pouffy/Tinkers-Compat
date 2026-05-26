package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import io.github.pouffy.tcompat.compat.ice_and_fire.IceFireHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

public class AmphithereAbility extends AbstractProjectileAbility {

    @Override
    public String serializedName() {
        return "amphithere";
    }

    @Override
    public void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {
        if (!inGround) {
            if ((arrow.tickCount == 1 || arrow.tickCount % 70 == 0) && !arrow.onGround()) {
                IceFireHandler.amphithereGust(arrow);
            }
            this.particles(innerProjectile);
        }
    }

    @Override
    public void tickProjectile(Projectile projectile, Projectile innerProjectile) {
        if ((projectile.tickCount == 1 || projectile.tickCount % 70 == 0) && !projectile.onGround()) {
            IceFireHandler.amphithereGust(projectile);
        }
        this.particles(innerProjectile);
    }

    private void particles(Projectile projectile) {
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
}
