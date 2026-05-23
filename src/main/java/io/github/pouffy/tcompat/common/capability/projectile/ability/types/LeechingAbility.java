package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import io.github.pouffy.tcompat.compat.ice_and_fire.modifier.IceFireHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

public class LeechingAbility implements IProjectileAbility {

    private boolean isActive;

    @Override
    public String serializedName() {
        return "leeching";
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {
        if (!inGround) {
            for (int i = 0; i < 2; i++) {
                this.particles(innerProjectile);
            }
        }
    }

    @Override
    public void tickProjectile(Projectile projectile, Projectile innerProjectile) {
        for (int i = 0; i < 2; i++) {
            this.particles(innerProjectile);
        }
    }

    private void particles(Projectile projectile) {
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
}
