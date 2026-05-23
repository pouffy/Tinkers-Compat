package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class PhoenixTouchedAbility extends AbstractProjectileAbility {

    @Getter @Setter
    private int fireTime;


    @Override
    public String serializedName() {
        return "phoenix_touched";
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("fire_time", this.getFireTime());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setFireTime(tag.getInt("fire_time"));
    }

    @Override
    public void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {
        if (!innerProjectile.level().isClientSide()) {
            if (inGround) { // Spawn less particles when the arrow is in the ground.
                if (inGroundTime % 5 == 0) {
                    for (int i = 0; i < 1; i++) {
                        this.particles(innerProjectile);
                    }
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    this.particles(innerProjectile);
                }
            }
        }
    }

    @Override
    public void tickProjectile(Projectile projectile, Projectile innerProjectile) {
        if (!innerProjectile.level().isClientSide()) {
            for (int i = 0; i < 2; i++) {
                this.particles(innerProjectile);
            }
        }
    }

    @Override
    public void impactEvent(ProjectileImpactEvent event, Projectile projectile) {
        if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
            if (this.getFireTime() > 0) {
                entityHitResult.getEntity().setSecondsOnFire(this.getFireTime());
            }
        }
    }

    private void particles(Projectile projectile) {
        if (projectile.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    projectile.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    projectile.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                    projectile.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    1, 0.0, 0.0, 0.0, 0.0F);
        }
    }
}
